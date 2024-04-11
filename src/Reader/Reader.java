package Reader;

import DatabaseManager.DatabaseManager;
import Model.Membership;
import Services.*;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import DateUtils.DateUtils;

public class Reader {
    BorrowingService borrowingService;
    LibrarianService librarianService;
    LibraryService libraryService;
    MembershipService membershipService;
    PersonService personService;
    DatabaseManager dbManager;
    Scanner scanner;

    public Reader(DatabaseManager dbManager) {
        borrowingService = new BorrowingService();
         librarianService = new LibrarianService();
         libraryService = new LibraryService();
         membershipService = new MembershipService();
         personService = new PersonService();
        this.dbManager = dbManager;
        scanner = new Scanner(System.in);
    }

    public void startReader() {
        System.out.println("Reader started , choose an action to perform (use listActions to see all actions)");
        while(true)
        {
            System.out.print("Enter action name: ");
            String action=  scanner.nextLine();
            if ( action.equals("listActions"))
            {
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
            }

            //////////////////////////////////////////////////////////////////// MEMBERSHIP ACTIONS ////////////////////////////////////////////////////
            if( action.equals("askToAddResource"))
            {
                System.out.print("Enter the membership id: ");
                int membershipId = scanner.nextInt();
                System.out.print("Enter the resource id: ");
                int resourceId = scanner.nextInt();

                membershipService.askToAddResource( membershipId,resourceId, dbManager);
            }

            if( action.equals("borrowResource"))
            {
                System.out.print("Enter the membership id: ");
                int membershipId = scanner.nextInt();
                System.out.print("Enter the resource id: ");
                int resourceId = scanner.nextInt();
                System.out.print("Enter the date borrowed(leave blank to borrow right now): ");
                String dateBorrowed = scanner.nextLine();

                Membership membership = membershipService.findById(membershipId, dbManager);
                String dateReturned = null;
                if(membership.getType() == "Premium")
                {
                    System.out.print("Enter the date returned: ");
                    dateReturned = scanner.nextLine();
                }

                membershipService.borrowResource( membershipId,resourceId, null, null, dbManager);
            }

            if( action.equals("extendResource"))
            {
                System.out.print("Enter the membership id: ");
                int membershipId = scanner.nextInt();
                System.out.print("Enter the resource id: ");
                int resourceId = scanner.nextInt();
                System.out.print("Enter the new date returned(leave blank to extend for one week): ");
                String newDateReturned = scanner.nextLine();

                membershipService.extendResource( membershipId,resourceId, null, dbManager);
            }

            if( action.equals("returnResource"))
            {
                System.out.print("Enter the membership id: ");
                int membershipId = scanner.nextInt();
                System.out.print("Enter the resource id: ");
                int resourceId = scanner.nextInt();

                membershipService.returnResource( membershipId,resourceId, dbManager);
            }

            //////////////////////////////////////////////////////////////////// LIBRARIAN ACTIONS ////////////////////////////////////////////////////
            if( action.equals("checkIn"))
            {
                System.out.print("Enter the librarian id: ");
                int librarianId = scanner.nextInt();

                librarianService.checkIn( librarianId, dbManager);
            }
            if( action.equals("checkOut"))
            {
                System.out.print("Enter the librarian id: ");
                int librarianId = scanner.nextInt();

                librarianService.checkOut( librarianId, dbManager);
            }

            if( action.equals("notifyBorrowingDeadlines"))
            {
                System.out.print("Enter the librarian id: ");
                int librarianId = scanner.nextInt();
                System.out.print("Enter the days before: ");
                int daysBefore = scanner.nextInt();

                librarianService.notifyBorrowingDeadlines( librarianId, daysBefore, dbManager);
            }

            if( action.equals("notifyMemberShipDeadLines"))
            {
                System.out.print("Enter the librarian id: ");
                int librarianId = scanner.nextInt();
                System.out.print("Enter the days before: ");
                int daysBefore = scanner.nextInt();

                librarianService.notifyMemberShipDeadLines( librarianId, daysBefore, dbManager);
            }

            //////////////////////////////////////////////////////////////////// LIBRARY ACTIONS ////////////////////////////////////////////////////

            if( action.equals("checkResourceValability"))
            {
                System.out.print("Enter the library id: ");
                int libraryId = scanner.nextInt();
                System.out.print("Enter the resource id: ");
                int resourceId = scanner.nextInt();

                libraryService.checkResourceValability( libraryId,resourceId, dbManager);
            }

            if( action.equals("exploreRandomResource"))
            {
                System.out.print("Enter the library id: ");
                int libraryId = scanner.nextInt();

                libraryService.exploreRandomResource( libraryId, dbManager);
            }

            if( action.equals("sortLibraries"))
            {
                System.out.print("Enter the filter( hoursOpened or numberOfResources ): ");
                String filter = scanner.nextLine();
                libraryService.sortLibraries( filter ,dbManager);
            }

            //////////////////////////////////////////////////////////////////// CRUD ACTIONS ////////////////////////////////////////////////////

            if( action.equals("findById"))
            {
                System.out.print("Enter the entity ( Borrowing , Library , Membership , Person ): ");
                String entity = scanner.nextLine();

                System.out.print("Enter the id: ");
                int id = scanner.nextInt();



                switch (entity) {
                    case "Person":
                        System.out.println( "Person found: " + personService.findById(id, dbManager));
                        break;
                    case "Membership":
                        System.out.println("Membership found: " + membershipService.findById(id, dbManager));
                        break;
                    case "Library":
                        System.out.println("Library found: " + libraryService.findById(id, dbManager));
                        break;
                    case "Borrowing":
                        System.out.println("Borrowing found: " + borrowingService.findById(id, dbManager));
                        break;
                }
            }
            if (action.equals("findAll"))
            {
                System.out.print("Enter the entity ( Borrowing , Library , Membership , Person ): ");
                String entity = scanner.nextLine();

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
                }
            }
            if (action.equals("create"))
            {
                System.out.print("Enter the entity ( Borrowing , Library , Membership , Person ): ");
                String entity = scanner.nextLine();

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
                        int personId = scanner.nextInt();
                        System.out.print("Enter the library id: ");
                        int libraryId = scanner.nextInt();
                        System.out.print("Enter the type(Regular or Premium): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter the date joined(leave blank for empty): ");
                        String dateJoined = scanner.nextLine();
                        System.out.print("Enter the date expired: ");
                        String dateExpired = scanner.nextLine();
                        membershipService.create(personId, libraryId, type, null, null, dbManager);
                        break;
                    case "Library":
                        System.out.print("Enter the address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter the name: ");
                        String nameL = scanner.nextLine();
                        System.out.print("Enter the program: ");
                        String program = scanner.nextLine();
                        libraryService.create(address, nameL, program, dbManager);
                        break;
                    case "Borrowing":
                        System.out.print("Enter the membership id: ");
                        int membershipId = scanner.nextInt();
                        System.out.print("Enter the resource id: ");
                        int resourceId = scanner.nextInt();
                        System.out.print("Enter the date borrowed: ");
                        String dateBorrowed = scanner.nextLine();
                        System.out.print("Enter the date returned: ");
                        String dateReturned = scanner.nextLine();
                        borrowingService.create(membershipId, resourceId, null, null, dbManager);
                        break;
                }
            }
            if (action.equals("update"))
            {
                System.out.print("Enter the entity ( Borrowing , Library , Membership , Person ): ");
                String entity = scanner.nextLine();

                switch (entity) {
                    case "Person":
                        System.out.print("Enter the id: ");
                        int id = scanner.nextInt();
                        System.out.print("Enter the name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter the email: ");
                        String email = scanner.nextLine();
                        personService.update(id, name, email, dbManager);
                        break;
                    case "Membership":
                        System.out.print("Enter the id: ");
                        int idM = scanner.nextInt();
                        System.out.print("Enter the person id: ");
                        int personId = scanner.nextInt();
                        System.out.print("Enter the library id: ");
                        int libraryId = scanner.nextInt();
                        System.out.print("Enter the type(Regular or Premium): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter the date joined(leave blank for empty): ");
                        String dateJoined = scanner.nextLine();
                        System.out.print("Enter the date expired: ");
                        String dateExpired = scanner.nextLine();
                        membershipService.update(idM, personId, libraryId, type, null, null, dbManager);
                        break;
                    case "Library":
                        System.out.print("Enter the id: ");
                        int idL = scanner.nextInt();
                        System.out.print("Enter the address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter the name: ");
                        String nameL = scanner.nextLine();
                        System.out.print("Enter the program: ");
                        String program = scanner.nextLine();
                        libraryService.update(idL, address, nameL, program, dbManager);
                        break;
                    case "Borrowing":
                        System.out.print("Enter the id: ");
                        int idB = scanner.nextInt();
                        System.out.print("Enter the membership id: ");
                        int membershipId = scanner.nextInt();
                        System.out.print("Enter the resource id: ");
                        int resourceId = scanner.nextInt();
                        System.out.print("Enter the date borrowed: ");
                        String dateBorrowed = scanner.nextLine();
                        System.out.print("Enter the date returned: ");
                        String dateReturned = scanner.nextLine();
                        borrowingService.update(idB, membershipId, resourceId, null, null, dbManager);
                        break;
                }
            }
            if (action.equals("delete"))
            {
                System.out.print("Enter the entity ( Borrowing , Library , Membership , Person ): ");
                String entity = scanner.nextLine();

                switch (entity) {
                    case "Person":
                        System.out.print("Enter the id: ");
                        int id = scanner.nextInt();
                        personService.delete(id, dbManager);
                        break;
                    case "Membership":
                        System.out.print("Enter the id: ");
                        int idM = scanner.nextInt();
                        membershipService.delete(idM, dbManager);
                        break;
                    case "Library":
                        System.out.print("Enter the id: ");
                        int idL = scanner.nextInt();
                        libraryService.delete(idL, dbManager);
                        break;
                    case "Borrowing":
                        System.out.print("Enter the id: ");
                        int idB = scanner.nextInt();
                        borrowingService.delete(idB, dbManager);
                        break;
                }
            }
        }
    }
}
