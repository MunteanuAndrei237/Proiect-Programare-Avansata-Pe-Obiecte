package Reader;

import DatabaseManager.DatabaseManager;
import Model.Membership;
import Services.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import DateUtils.DateUtils;
import Model.*;

public class Reader {
    BorrowingService borrowingService;
    LibrarianService librarianService;
    LibraryService libraryService;
    MembershipService membershipService;
    PersonService personService;
    DatabaseManager dbManager;
    Scanner scanner;
    SimpleDateFormat sdf;

    public Reader(DatabaseManager dbManager) {
        borrowingService = new BorrowingService();
        librarianService = new LibrarianService();
        libraryService = new LibraryService();
        membershipService = new MembershipService();
        personService = new PersonService();
        this.dbManager = dbManager;
        scanner = new Scanner(System.in);
        sdf = new SimpleDateFormat("dd-MMM-yy");
    }

    public void startReader() {
        System.out.println("Reader started , choose an action to perform (use listActions to see all actions)");
        System.out.println("DATE SYNTAX IS YYYY-MM-DD");
        while (true) {
            System.out.print("Enter action name: ");
            String action = scanner.nextLine();
            switch (action) {
                case "listActions":
                    listActions();
                    break;

                //////////////////////////////////////////////////////////////////// MEMBERSHIP ACTIONS ////////////////////////////////////////////////////

                case "askToAddResource":
                    askToAddResource();
                    break;
                case "borrowResource":
                    borrowResource();
                    break;

                case "extendResource":
                    extendResource();
                    break;

                case "returnResource":
                    returnResource();
                    break;

                //////////////////////////////////////////////////////////////////// LIBRARIAN ACTIONS ////////////////////////////////////////////////////

                case "checkIn":
                    checkIn();
                    break;

                case "checkOut":
                    checkOut();
                    break;

                case "notifyBorrowingDeadlines":
                    notifyBorrowingDeadlines();
                    break;

                case "notifyMemberShipDeadLines":
                    notifyMemberShipDeadLines();
                    break;

                //////////////////////////////////////////////////////////////////// LIBRARY ACTIONS ////////////////////////////////////////////////////

                case "checkResourceValability":
                    checkResourceValability();
                    break;

                case "exploreRandomResource":
                    exploreRandomResource();
                    break;

                case "sortLibraries":
                    sortLibraries();
                    break;

                //////////////////////////////////////////////////////////////////// CRUD ACTIONS ////////////////////////////////////////////////////

                case "findById":
                    System.out.print("Enter the entity ( Borrowing , Library , Membership , Person , Librarian ): ");
                    String entity = scanner.nextLine();

                    System.out.print("Enter the id: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    findById(id, entity);
                    break;
                case "findAll":
                    System.out.print("Enter the entity ( Borrowing , Library , Membership , Person  , Librarian ): ");
                    String entity2 = scanner.nextLine();

                    findAll(entity2);
                    break;
                case "create":
                    System.out.print("Enter the entity ( Borrowing , Library , Membership , Person , Librarian  ): ");
                    String entity3 = scanner.nextLine();

                    create(entity3);
                    break;
                case "update":
                    System.out.print("Enter the entity ( Borrowing , Library , Membership , Person  , Librarian ): ");
                    String entity4 = scanner.nextLine();

                    update(entity4);
                    break;
                case "delete":
                    System.out.print("Enter the entity ( Borrowing , Library , Membership , Person  , Librarian ): ");
                    String entity5 = scanner.nextLine();

                    delete(entity5);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid action");
                    break;
            }
        }
    }

    private void listActions() {
        System.out.println("Custom actions:");
        System.out.println("checkIn");
        System.out.println("checkOut");
        System.out.println("notifyBorrowingDeadlines");
        System.out.println("notifyMemberShipDeadLines");

        System.out.println("checkResourceValability");
        System.out.println("exploreRandomResource");
        System.out.println("sortLibraries");

        System.out.println("askToAddResource");
        System.out.println("borrowResource");
        System.out.println("extendResource");
        System.out.println("returnResource");

        System.out.println("Or type one of the follwong actions : findById , findAll , create , update , delete nad then the entity name");
        System.out.println("Or type exit to exit the reader");
    }


////////////////////////////////////////////////////////////////////////////// CRUD ACTIONS ////////////////////////////////////////////////////

    private void findAll(String entity) {
        switch (entity) {
            case "Person":
                System.out.println("All persons: " + personService.findAll(dbManager));
                break;
            case "Membership":
                System.out.println("All memberships: " + membershipService.findAll(dbManager));
                break;
            case "Library":
                System.out.println("All libraries: " + libraryService.findAll(dbManager));
                break;
            case "Borrowing":
                System.out.println("All borrowings: " + borrowingService.findAll(dbManager));
                break;
            case "Librarian":
                System.out.println("All librarians: " + librarianService.findAll(dbManager));
                break;
            default:
                System.out.println("Invalid entity");
                break;
        }
    }

    private void findById(int id, String entity) {
        switch (entity) {
            case "Person":
                Person person = personService.findById(id, dbManager);
                if (person == null)
                    System.out.println("Person with id " + id + " does not exist");
                else
                    System.out.println("Person found: " + person);
                break;
            case "Membership":
                Membership membership2 = membershipService.findById(id, dbManager);
                if (membership2 == null)
                    System.out.println("Membership with id " + id + " does not exist");
                else
                    System.out.println("Membership found: " + membership2);
                break;
            case "Library":
                Library library = libraryService.findById(id, dbManager);
                if (library == null)
                    System.out.println("Library with id " + id + " does not exist");
                else
                    System.out.println("Library found: " + library);
                break;
            case "Borrowing":
                Borrowing borrowing = borrowingService.findById(id, dbManager);
                if (borrowing == null)
                    System.out.println("Borrowing with id " + id + " does not exist");
                else
                    System.out.println("Borrowing found: " + borrowing);
                break;
            case "Librarian":
                Librarian librarian = librarianService.findById(id, dbManager);
                if (librarian == null)
                    System.out.println("Librarian with id " + id + " does not exist");
                else
                    System.out.println("Librarian found: " + librarian);
                break;
            default:
                System.out.println("Invalid entity");
                break;
        }
    }

    private void create(String entity) {
        switch (entity) {
            case "Person":
                System.out.print("Enter the name: ");
                String name = scanner.nextLine();
                System.out.print("Enter the email: ");
                String email = scanner.nextLine();
                personService.create(name, email, dbManager);
                break;
            case "Membership":
                System.out.print("Enter the person id: ");
                int personId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter the library id: ");
                int libraryId4 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter the type(Regular or Premium): ");
                String type = scanner.nextLine();
                System.out.print("Enter the date joined(leave blank if you want starting date to be right now): ");
                String dateJoined = scanner.nextLine();

                Date dateJoinedDate;
                if (dateJoined.isEmpty())
                    dateJoinedDate = null;
                else
                    dateJoinedDate = Date.valueOf(dateJoined);

                membershipService.create(personId, libraryId4, type, dateJoinedDate, dbManager);
                break;
            case "Library":
                System.out.print("Enter the address: ");
                String address = scanner.nextLine();
                System.out.print("Enter the name: ");
                String nameL = scanner.nextLine();
                System.out.print("Enter the program(HH:MM-HH:MM): ");
                String program = scanner.nextLine();
                libraryService.create(address, nameL, program, dbManager);
                break;
            case "Borrowing":
                System.out.print("Enter the membership id: ");
                int membershipId6 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter the resource id: ");
                int resourceId6 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter the date borrowed(leave blank to borrow right now): ");
                String dateBorrowed2 = scanner.nextLine();
                System.out.print("Enter the date returned(leave blank to borrow for one week): ");
                String dateReturned2 = scanner.nextLine();

                Date dateBorrowedDate2;
                if (dateBorrowed2.isEmpty())
                    dateBorrowedDate2 = null;
                else
                    dateBorrowedDate2 = Date.valueOf(dateBorrowed2);

                Date dateReturnedDate2;
                if (dateReturned2.isEmpty())
                    dateReturnedDate2 = null;
                else
                    dateReturnedDate2 = Date.valueOf(dateReturned2);

                borrowingService.create(resourceId6, membershipId6, dateBorrowedDate2, dateReturnedDate2, dbManager);
                break;
            case "Librarian":
                System.out.print("Enter the library id: ");
                int libraryId5 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter the name: ");
                String nameL2 = scanner.nextLine();
                System.out.print("Enter the email: ");
                String emailL = scanner.nextLine();
                System.out.print("Enter the program(HH:MM-HH:MM): ");
                String program2 = scanner.nextLine();
                librarianService.create(libraryId5, nameL2, emailL, program2, dbManager);
                break;
            default:
                System.out.println("Invalid entity");
                break;

        }
    }

    private void update(String entity) {
        switch (entity) {
            case "Person":
                System.out.print("Enter the id: ");
                int id2 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new name: ");
                String name2 = scanner.nextLine();
                System.out.print("Enter new email: ");
                String email2 = scanner.nextLine();
                personService.update(id2, name2, email2, dbManager);
                break;
            case "Membership":
                System.out.print("Enter the id: ");
                int idM = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new type(Regular or Premium): ");
                String type2 = scanner.nextLine();
                System.out.print("Enter new expiry date: ");
                String dateExpired2 = scanner.nextLine();

                Date dateExpiredDate;
                if (dateExpired2.isEmpty())
                    dateExpiredDate = null;
                else
                    dateExpiredDate = Date.valueOf(dateExpired2);

                membershipService.update(idM, type2, dateExpiredDate, dbManager);
                break;
            case "Library":
                System.out.print("Enter new id: ");
                int idL = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new address: ");
                String address2 = scanner.nextLine();
                System.out.print("Enter new name: ");
                String nameL2 = scanner.nextLine();
                System.out.print("Enter new program(HH:MM-HH:MM): ");
                String program2 = scanner.nextLine();
                libraryService.update(idL, address2, nameL2, program2, dbManager);
                break;
            case "Borrowing":
                System.out.print("Enter the id: ");
                int idB = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new return date: ");
                String dateReturned3 = scanner.nextLine();

                Date dateReturnedDate3;
                if (dateReturned3.isEmpty())
                    dateReturnedDate3 = null;
                else
                    dateReturnedDate3 = Date.valueOf(dateReturned3);
                try {
                    borrowingService.update(idB, dateReturnedDate3, dbManager);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                break;
            case "Librarian":
                System.out.print("Enter the id: ");
                int idL2 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new library id: ");
                int libraryId7 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new name: ");
                String nameL3 = scanner.nextLine();
                System.out.print("Enter new email: ");
                String emailL2 = scanner.nextLine();
                System.out.print("Enter new program: ");
                String program3 = scanner.nextLine();
                librarianService.update(idL2, libraryId7, nameL3, emailL2, program3, dbManager);
                break;
            default:
                System.out.println("Invalid entity");
                break;
        }
    }

    private void delete(String entity) {
        switch (entity) {
            case "Person":
                System.out.print("Enter the id: ");
                int id3 = Integer.parseInt(scanner.nextLine());
                personService.delete(id3, dbManager);
                break;
            case "Membership":
                System.out.print("Enter the id: ");
                int idM2 = Integer.parseInt(scanner.nextLine());
                membershipService.delete(idM2, dbManager);
                break;
            case "Library":
                System.out.print("Enter the id: ");
                int idL2 = Integer.parseInt(scanner.nextLine());
                libraryService.delete(idL2, dbManager);
                break;
            case "Borrowing":
                System.out.print("Enter the id: ");
                int idB2 = Integer.parseInt(scanner.nextLine());
                borrowingService.delete(idB2, dbManager);
                break;
            case "Librarian":
                System.out.print("Enter the id: ");
                int idL3 = Integer.parseInt(scanner.nextLine());
                librarianService.delete(idL3, dbManager);
                break;
            default:
                System.out.println("Invalid entity");
                break;
        }
    }

//////////////////////////////////////////////////////////////////// MEMBERHSIP ACTIONS ////////////////////////////////////////////////////

    private void askToAddResource() {
        System.out.print("Enter the membership id: ");
        int membershipId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the resource id: ");
        int resourceId = Integer.parseInt(scanner.nextLine());

        membershipService.askToAddResource(membershipId, resourceId, dbManager);
    }

    private void borrowResource() {
        System.out.print("Enter the membership id: ");
        int membershipId = Integer.parseInt(scanner.nextLine());
        Membership membership = membershipService.findById(membershipId, dbManager);
        if (membership == null) {
            System.out.println("Membership with id " + membershipId + " does not exist");
            return;
        }
        System.out.print("Enter the resource id: ");
        int resourceId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the date borrowed(leave blank to borrow right now): ");
        String dateBorrowed = scanner.nextLine();

        String dateReturned = null;
        if (membership.getType().equals("Premium")) {
            System.out.print("Enter the date returned(leave blank to borrow for one week): ");
            dateReturned = scanner.nextLine();
        }

        Date dateBorrowedDate = null;
        if (!dateBorrowed.isEmpty())
            dateBorrowedDate = Date.valueOf(dateBorrowed);

        Date dateReturnedDate = null;
        if (dateReturned != null && !dateReturned.isEmpty())
            dateReturnedDate = Date.valueOf(dateReturned);

        membershipService.borrowResource(membershipId, resourceId, dateBorrowedDate, dateReturnedDate, dbManager);
    }

    private void extendResource() {
        System.out.print("Enter the membership id: ");
        int membershipId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the resource id: ");
        int resourceId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the new date returned(leave blank to extend for one week): ");
        String newDateReturned = scanner.nextLine();

        Date dateReturnedDate = null;
        if (!newDateReturned.isEmpty())
            dateReturnedDate = Date.valueOf(newDateReturned);
        if (dateReturnedDate != null && dateReturnedDate.compareTo(DateUtils.addOneWeekToDate(new Date(System.currentTimeMillis()))) > 0) {
            System.out.println("You cannot extend the deadline more than one week if you have a regular membership");
            return;
        }

        membershipService.extendResource(membershipId, resourceId, dateReturnedDate, dbManager);
    }

    private void returnResource() {
        System.out.print("Enter the membership id: ");
        int membershipId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the resource id: ");
        int resourceId = Integer.parseInt(scanner.nextLine());

        membershipService.returnResource(membershipId, resourceId, dbManager);
    }

//////////////////////////////////////////////////////////////////// LIBRARIAN ACTIONS ////////////////////////////////////////////////////

    private void checkIn() {
        System.out.print("Enter the librarian id: ");
        int librarianId = Integer.parseInt(scanner.nextLine());

        librarianService.checkIn(librarianId, dbManager);
    }

    private void checkOut() {
        System.out.print("Enter the librarian id: ");
        int librarianId = Integer.parseInt(scanner.nextLine());

        librarianService.checkOut(librarianId, dbManager);
    }

    private void notifyBorrowingDeadlines() {
        System.out.print("Enter the librarian id: ");
        int librarianId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the days before: ");
        int daysBefore = Integer.parseInt(scanner.nextLine());

        librarianService.notifyBorrowingDeadlines(librarianId, daysBefore, dbManager);
    }

    private void notifyMemberShipDeadLines() {
        System.out.print("Enter the librarian id: ");
        int librarianId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the days before: ");
        int daysBefore = Integer.parseInt(scanner.nextLine());

        librarianService.notifyMemberShipDeadLines(librarianId, daysBefore, dbManager);
    }

//////////////////////////////////////////////////////////////////// LIBRARY ACTIONS ////////////////////////////////////////////////////

    private void checkResourceValability() {
        System.out.print("Enter the library id: ");
        int libraryId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the resource id: ");
        int resourceId = Integer.parseInt(scanner.nextLine());

        libraryService.checkResourceValability(libraryId, resourceId, dbManager);
    }

    private void exploreRandomResource() {
        System.out.print("Enter the library id: ");
        int libraryId = Integer.parseInt(scanner.nextLine());

        libraryService.exploreRandomResource(libraryId, dbManager);
    }

    private void sortLibraries() {
        System.out.print("Enter the filter( hoursOpened or numberOfResources ): ");
        String filter = scanner.nextLine();
        libraryService.sortLibraries(filter, dbManager);
    }


}
