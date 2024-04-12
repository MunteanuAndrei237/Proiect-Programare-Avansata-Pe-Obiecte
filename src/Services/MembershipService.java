package Services;

import DatabaseManager.DatabaseManager;
import Model.Borrowing;
import Repository.MembershipRepository;
import Model.Membership;

import java.sql.*;
import java.util.List;

import DateUtils.DateUtils;
import Model.Resource;


public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final ResourceService ResourceService;
    private final BorrowingService borrowingService;
    private final PersonService personService;
    private final LibraryService libraryService;
    private final AuditService auditService;

    public MembershipService() {
        this.membershipRepository = MembershipRepository.getInstance();
        this.ResourceService = new ResourceService();
        this.borrowingService = new BorrowingService();
        this.personService = new PersonService();
        this.libraryService = new LibraryService();
        this.auditService = AuditService.getInstance();
    }

    public Membership findById(int id, DatabaseManager dbManager) {
        auditService.logAction("Membership find by id ");
        return membershipRepository.findById(id, dbManager);
    }

    public List<Membership> findAll(DatabaseManager dbManager) {
        auditService.logAction("Membership find all ");
        return membershipRepository.findAll(dbManager);
    }

    public void create(int personId, int libraryId, String type, Date dateJoined, DatabaseManager dbManager) {

        if (personService.findById(personId, dbManager) == null) {
            System.out.println("Person with id " + personId + " does not exist");
            return;
        }

        if (libraryService.findById(libraryId, dbManager) == null) {
            System.out.println("Library with id " + libraryId + " does not exist");
            return;
        }

        for (Membership membership : membershipRepository.findAll(dbManager)) {
            if (membership.getLibraryId() == libraryId && membership.getPersonId() == personId) {
                System.out.println("This person is already a member of this library");
                return;
            }
        }
        //check is the user completed dateJoined and dateExpired , if not complete with default values
        if (dateJoined == null) {
            dateJoined = new Date(System.currentTimeMillis());
        }

        Date dateExpired = DateUtils.addThreeYearsToDate(dateJoined);

        if (dateJoined.compareTo(dateExpired) > 0) {
            System.out.println("Date expired cannot be before date joined");
            return;
        }

        Membership newMembership = new Membership(0, personId, libraryId, type, dateJoined, dateExpired);
        membershipRepository.create(newMembership, dbManager);
        auditService.logAction("Membership created ");
        System.out.println(newMembership + " has been created");

    }

    public void update(int id, String type, Date dateExpired, DatabaseManager dbManager) {
        Membership membership = membershipRepository.findById(id, dbManager);
        if (membership == null) {
            System.out.println("Membership with id " + id + " does not exist");
            return;
        }
        membership.setType(type);
        //check is the user completed dateJoined and dateExpired , if not complete with default values
        if (dateExpired != null) {
            membership.setDateExpired(dateExpired);
        }

        if (dateExpired.compareTo(membership.getDateJoined()) < 0) {
            System.out.println("Date expired cannot be before date joined");
            return;
        }

        membershipRepository.update(membership, dbManager);
        auditService.logAction("Membership updated ");
        System.out.println("Membership has been updated to " + membership);
    }

    public void delete(int id, DatabaseManager dbManager) {
        Membership membership = membershipRepository.findById(id, dbManager);
        if (membership == null) {
            System.out.println("Membership with id " + id + " does not exist");
            return;
        }
        membershipRepository.delete(id, dbManager);
        auditService.logAction("Membership deleted ");
        System.out.println(membership + " has been deleted");
    }

    public void borrowResource(int membershipId, int resourceId, Date dateBorrowed, Date dateReturned, DatabaseManager dbManager) {
        Membership membership = membershipRepository.findById(membershipId, dbManager);
        if (membership == null) {
            System.out.println("Membership with id " + membershipId + " does not exist");
            return;
        }
        Resource resource = ResourceService.findById(resourceId, dbManager);
        if (resource == null) {
            System.out.println("Resource with id " + resourceId + " does not exist");
            return;
        }
        List<Borrowing> borrowings = borrowingService.findAll(dbManager);
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMembershipId() == membershipId && borrowing.getResourceId() == resourceId) {
                System.out.println("This resource is already borrowed by this member");
                return;
            }
        }
        //check is the user completed dateBorrowed and dateReturned , if not complete with default values
        if (dateBorrowed == null) {
            dateBorrowed = new Date(System.currentTimeMillis());
        }

        if (dateReturned == null) {
            dateReturned = DateUtils.addOneWeekToDate(dateBorrowed);
        }
        if (dateBorrowed.compareTo(dateReturned) > 0) {
            System.out.println("Return date cannot be before borrow date");
            return;
        }

        String borrowQuery = "SELECT \"Resource\".inStock " +
                "FROM \"Resource\" " +
                "JOIN section ON \"Resource\".sectionId = section.id " +
                "JOIN library ON section.libraryId = library.id " +
                "JOIN membership ON library.id = membership.libraryId " +
                "WHERE \"Resource\".id = " + resourceId + " AND membership.id = " + membershipId;

        try {
            ResultSet borrowResult = dbManager.executeQuery(borrowQuery);
            if (borrowResult.next()) {
                int borrowCount = borrowResult.getInt("inStock");
                borrowResult.close();
                if (borrowCount > 0) { //if the resource is available in the library then borrow it and update the inStock and toReturn count
                    ResourceService.modifyInStock(resourceId, dbManager, -1);
                    ResourceService.modifyToReturn(resourceId, dbManager, 1);
                    borrowingService.create(resourceId, membershipId, dateBorrowed, dateReturned, dbManager);
                    System.out.println("Resource borrowed successfully!");
                } else {
                    System.out.println("Wait for the resource to be returned by someone else.");
                }
            } else {
                System.out.println("This library does not have the resource.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnResource(int membershipId, int resourceId, DatabaseManager dbManager) {
        Membership membership = membershipRepository.findById(membershipId, dbManager);
        if (membership == null) {
            System.out.println("Membership with id " + membershipId + " does not exist");
            return;
        }
        Resource resource = ResourceService.findById(resourceId, dbManager);
        if (resource == null) {
            System.out.println("Resource with id " + resourceId + " does not exist");
            return;
        }

        List<Borrowing> borrowings = borrowingService.findAll(dbManager);
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMembershipId() == membershipId && borrowing.getResourceId() == resourceId) { //if the resource is borrowed by this member then return it and update the inStock and toReturn count and delete the borrowing record
                ResourceService.modifyInStock(resourceId, dbManager, 1);
                ResourceService.modifyToReturn(resourceId, dbManager, -1);
                borrowingService.delete(borrowing.getId(), dbManager);
                System.out.println("Resource returned successfully!");
                return;
            }

        }
        System.out.println("This resource is not borrowed by this member");
    }

    public void extendResource(int membershipId, int resourceId, Date newDateReturned, DatabaseManager dbManager) {
        Membership membership = membershipRepository.findById(membershipId, dbManager);
        if (membership == null) {
            System.out.println("Membership with id " + membershipId + " does not exist");
            return;
        }
        Resource resource = ResourceService.findById(resourceId, dbManager);
        if (resource == null) {
            System.out.println("Resource with id " + resourceId + " does not exist");
            return;
        }
        //premium members can extend the deadline more than one week from current date
        if (newDateReturned != null && newDateReturned.compareTo(DateUtils.addOneWeekToDate(new Date(System.currentTimeMillis()))) > 0 && membership.getType().equals("Regular")) {
            System.out.println("You cannot extend the deadline more than one week from current date if you have a regular membership");
            return;
        }

        List<Borrowing> borrowings = borrowingService.findAll(dbManager);
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMembershipId() == membershipId && borrowing.getResourceId() == resourceId) {

                if (newDateReturned == null) {
                    newDateReturned = DateUtils.addOneWeekToDate(borrowing.getDateReturned());
                }
                try {
                    borrowingService.update(borrowing.getId(), newDateReturned, dbManager);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return;
                }
                System.out.println("Resource extended successfully!");
                return;
            }
        }
        System.out.println("This resource is not borrowed by this member");
    }

    //premium members can request a resource to be added to the library
    public void askToAddResource(int membershipId, int resourceId, DatabaseManager dbManager) {
        Membership membership = membershipRepository.findById(membershipId, dbManager);
        if (membership == null) {
            System.out.println("Membership with id " + membershipId + " does not exist");
            return;
        }
        Resource resource = ResourceService.findById(resourceId, dbManager);
        if (resource == null) {
            System.out.println("Resource with id " + resourceId + " does not exist");
            return;
        }

        if (membership.getType().equals("Premium")) {
            List<Borrowing> borrowings = borrowingService.findAll(dbManager);
            for (Borrowing borrowing : borrowings) {
                if (borrowing.getMembershipId() == membershipId && borrowing.getResourceId() == resourceId) {
                    ResourceService.modifyInStock(resourceId, dbManager, 1);
                    System.out.println("Resource added successfully!");
                    return;
                }
            }
            System.out.println("This resource is not avalabile");
        } else {
            System.out.println("You need a premium membership to ask for a resource to be added.");
        }

    }
}