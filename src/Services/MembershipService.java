package Services;

import DatabaseManager.DatabaseManager;
import Model.Borrowing;
import Repository.MembershipRepository;
import Model.Membership;

import java.sql.*;
import java.util.List;
import DateUtils.DateUtils;
import Repository.ResourceRepository;

public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final ResourceRepository resourceRepository;
    private final BorrowingService borrowingService;

    public MembershipService() {
        this.membershipRepository = new MembershipRepository();
        this.resourceRepository = new ResourceRepository();
        this.borrowingService = new BorrowingService();
    }

    public Membership findById(int id, DatabaseManager dbManager) {
        return membershipRepository.findById(id, dbManager);
    }

    public List<Membership> findAll(DatabaseManager dbManager) {
        return membershipRepository.findAll(dbManager);
    }

    public void create(int personId, int libraryId, String type, Date dateJoined, Date dateExpired, DatabaseManager dbManager) {
        int membershipId = membershipRepository.findAll(dbManager).size() + 1;
        if(dateJoined == null) {
            dateJoined = new Date(System.currentTimeMillis());
        }
        if(dateExpired == null) {
            dateExpired = DateUtils.addThreeYearsToDate(dateJoined);
        }

        for (Membership membership : membershipRepository.findAll(dbManager)) {
            if (membership.getLibraryId() == libraryId && membership.getPersonId() == personId){
                System.out.println("This person is already a member of this library");
                return;
            }
        }

            Membership newMembership = new Membership(membershipId, personId, libraryId, type, dateJoined, dateExpired);
            membershipRepository.create(newMembership, dbManager);

    }

    public void update(int id, int personId, int libraryId, String type, Date dateJoined, Date dateExpired, DatabaseManager dbManager) {
        Membership updatedMembership = new Membership(id, personId, libraryId, type, dateJoined, dateExpired);
        membershipRepository.update(updatedMembership, dbManager);
    }

    public void delete(int id, DatabaseManager dbManager) {
        membershipRepository.delete(id, dbManager);
    }

    public void borrowResource(int membershipId, int resourceId, Date dateBorrowed ,Date dateReturned , DatabaseManager dbManager) {

        List<Borrowing> borrowings = borrowingService.findAll(dbManager);
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMembershipId() == membershipId && borrowing.getResourceId() == resourceId) {
                System.out.println("This resource is already borrowed by this member");
                return;
            }
        }

        String borrowQuery ="SELECT \"Resource\".inStock " +
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

                if (borrowCount > 0) {
                    resourceRepository.modifyInStock(resourceId, dbManager, -1);
                    resourceRepository.modifyToReturn(resourceId, dbManager, 1);
                    borrowingService.create(resourceId, membershipId, dateBorrowed, dateReturned, dbManager);
                    System.out.println("Resource borrowed successfully!");
                } else {
                    System.out.println("Resource not available for borrowing.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnResource(int membershipId, int resourceId, DatabaseManager dbManager) {
        List<Borrowing> borrowings = borrowingService.findAll(dbManager);
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMembershipId() == membershipId && borrowing.getResourceId() == resourceId) {
                resourceRepository.modifyInStock(resourceId, dbManager, 1);
                resourceRepository.modifyToReturn(resourceId, dbManager, -1);
                borrowingService.delete(borrowing.getId(), dbManager);
                System.out.println("Resource returned successfully!");
                return;
            }

        }
        System.out.println("This resource is not borrowed by this member");
    }

    public void extendResource(int membershipId, int resourceId, Date newDateReturned, DatabaseManager dbManager) {

        List<Borrowing> borrowings = borrowingService.findAll(dbManager);
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMembershipId() == membershipId && borrowing.getResourceId() == resourceId) {

                if(newDateReturned == null) {
                    //add so you cant extend inifnitely
                    newDateReturned = DateUtils.addOneWeekToDate(borrowing.getDateReturned());
                }
                borrowingService.update(borrowing.getId(), resourceId, membershipId, borrowing.getDateBorrowed(), newDateReturned, dbManager);
                System.out.println("Resource extended successfully!");
                return;
            }
        }
        System.out.println("This resource is not borrowed by this member");
    }

    public void askToAddResource(int membershipId, int resourceId, DatabaseManager dbManager) {
        Membership membership = membershipRepository.findById(membershipId, dbManager);
        if(membership.getType().equals("Premium")) {
            List<Borrowing> borrowings = borrowingService.findAll(dbManager);
            for (Borrowing borrowing : borrowings) {
                if (borrowing.getMembershipId() == membershipId && borrowing.getResourceId() == resourceId) {
                    resourceRepository.modifyInStock(resourceId, dbManager, 1);
                    System.out.println("Resource added successfully!");
                    return;
                }
            }
            System.out.println("This resource is not avalabile");
        }
        else
        {
            System.out.println("You need a premium membership to ask for a resource to be added.");
        }


    }
}