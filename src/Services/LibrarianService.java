package Services;

import DatabaseManager.DatabaseManager;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import DateUtils.DateUtils;
import Model.Librarian;
import Model.Library;
import Repository.LibrarianRepository;


public class LibrarianService {
    private final LibrarianRepository librarianRepository;
    private final AuditService auditService;
    private final LibraryService libraryService;

    public LibrarianService() {
        this.auditService = AuditService.getInstance();
        this.libraryService = new LibraryService();
        this.librarianRepository = LibrarianRepository.getInstance();
    }

    public Librarian findById(int id, DatabaseManager dbManager) {
        auditService.logAction("Librarian find by id ");
        return librarianRepository.findById(id, dbManager);
    }

    public List<Librarian> findAll(DatabaseManager dbManager) {
        auditService.logAction("Librarian find all ");
        return librarianRepository.findAll(dbManager);
    }

    public void create(int libraryId, String name, String email, String program, DatabaseManager dbManager) {
        Library library = libraryService.findById(libraryId, dbManager);
        if (library == null) {
            System.out.println("Library with id " + libraryId + " does not exist");
            return;
        }

        Librarian newLibrarian = new Librarian(0, libraryId, name, email, program);
        //check if librarian program is within library program
        if (DateUtils.isFirstWithinSecond(newLibrarian.getProgram(), library.getProgram())) {
            librarianRepository.create(newLibrarian, dbManager);
            auditService.logAction("Librarian created ");
            System.out.println(newLibrarian + " has been created");
        } else {
            System.out.println("Librarian program should be within library program");
        }

    }

    public void update(int id, int libraryId, String name, String email, String program, DatabaseManager dbManager) {
        Library library = libraryService.findById(libraryId, dbManager);
        if (library == null) {
            System.out.println("Library with id " + libraryId + " does not exist");
            return;
        }

        if (librarianRepository.findById(id, dbManager) == null) {
            System.out.println("Librarian with id " + id + " does not exist");
            return;
        }
        Librarian updatedLibrarian = new Librarian(id, libraryId, name, email, program);
        //check if librarian program is within library program
        if (DateUtils.isFirstWithinSecond(updatedLibrarian.getProgram(), library.getProgram())) {
            librarianRepository.update(updatedLibrarian, dbManager);
            auditService.logAction("Librarian updated ");
            System.out.println("Librarian has been updated to " + updatedLibrarian);
        } else {
            System.out.println("Librarian program should be within library program");
        }
    }

    public void delete(int id, DatabaseManager dbManager) {
        Librarian librarian = librarianRepository.findById(id, dbManager);
        if (librarian == null) {
            System.out.println("Librarian with id " + id + " does not exist");
            return;
        }
        librarianRepository.delete(id, dbManager);
        auditService.logAction("Librarian deleted ");
        System.out.println(librarian + " has been deleted");
    }

    public void checkIn(int librarianId, DatabaseManager dbManager) {
        Librarian librarian = librarianRepository.findById(librarianId, dbManager);

// Get the current time
        LocalTime currentTime = LocalTime.now();
        String program = librarian.getProgram();

// Split the program string into start and end times
        String[] times = program.split("-");
        LocalTime startTime = LocalTime.parse(times[0]);
        LocalTime endTime = LocalTime.parse(times[1]);
        if (librarian.getId() == librarianId && (currentTime.isAfter(startTime) || currentTime.isBefore(endTime))) {
            System.out.println(librarian.getName() + " is checked in");
        }
    }

    public void checkOut(int librarianId, DatabaseManager dbManager) {
        Librarian librarian = librarianRepository.findById(librarianId, dbManager);

// Get the current time
        LocalTime currentTime = LocalTime.now();
        String program = librarian.getProgram();

// Split the program string into start and end times
        String[] times = program.split("-");
        LocalTime startTime = LocalTime.parse(times[0]);
        LocalTime endTime = LocalTime.parse(times[1]);
        if (librarian.getId() == librarianId && (currentTime.isBefore(startTime) || currentTime.isAfter(endTime))) {
            System.out.println(librarian.getName() + " is checked out");
        }
    }

    public void notifyBorrowingDeadlines(int librarianId, int daysBefore, DatabaseManager dbManager) {
        Librarian librarian = librarianRepository.findById(librarianId, dbManager);
        if (librarian == null) {
            System.out.println("Librarian with id " + librarianId + " does not exist");
            return;
        }
        String borrowingQuery = "SELECT b.* " +
                "FROM borrowing b " +
                "         JOIN membership m ON b.membershipId = m.id " +
                "         JOIN library l ON m.libraryId = l.id " +
                "         JOIN librarian lb ON lb.libraryId = l.id " +
                "WHERE lb.id = " + librarianId + " AND b.dateReturned < CURRENT_DATE + " + daysBefore;
        ResultSet resultSet = dbManager.executeQuery(borrowingQuery);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    System.out.println("Notifying borrowing deadline for membership " + resultSet.getInt("membershipId")
                            + " with resource " + resultSet.getInt("resourceId") + " and deadline " + resultSet.getDate("dateReturned"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyMemberShipDeadLines(int librarianId, int daysBefore, DatabaseManager dbManager) {
        Librarian librarian = librarianRepository.findById(librarianId, dbManager);
        if (librarian == null) {
            System.out.println("Librarian with id " + librarianId + " does not exist");
            return;
        }
        String membershipQuery = "SELECT m.* " +
                "FROM membership m " +
                " JOIN library l ON m.libraryId = l.id " +
                " JOIN librarian lb ON lb.libraryId = l.id " +
                "WHERE lb.id = " + librarianId + " AND m.dateExpired < CURRENT_DATE + " + daysBefore;
        ResultSet resultSet = dbManager.executeQuery(membershipQuery);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    System.out.println("Notifying membership deadline for membership " + resultSet.getInt("id")
                            + " with expiry date " + resultSet.getDate("dateExpired"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
