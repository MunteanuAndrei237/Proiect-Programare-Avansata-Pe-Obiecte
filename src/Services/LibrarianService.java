package Services;
import DatabaseManager.DatabaseManager;
import java.sql.*;

public class LibrarianService {
    public void checkIn(int librarianId, DatabaseManager dbManager) {
        String query = "SELECT * FROM Librarian WHERE id = " + librarianId;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    System.out.println(resultSet.getString("name") +" is checked in");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void checkOut(int librarianId, DatabaseManager dbManager) {
        String query = "SELECT * FROM Librarian WHERE id = " + librarianId;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    System.out.println(resultSet.getString("name") +" is checked out");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyBorrowingDeadlines(int librarianId, int daysBefore ,  DatabaseManager dbManager) {
        String query = "SELECT b.* " +
                "FROM borrowing b " +
                "         JOIN membership m ON b.membershipId = m.id " +
                "         JOIN library l ON m.libraryId = l.id " +
                "         JOIN librarian lb ON lb.libraryId = l.id " +
                "WHERE lb.id = " + librarianId + " AND b.dateReturned < CURRENT_DATE + " + daysBefore;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    System.out.println("Notifying borrowing deadline for membership" + resultSet.getInt("membershipId")
                            + " with resource " + resultSet.getInt("resourceId") + " and deadline " + resultSet.getDate("dateReturned"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyMemberShipDeadLines(int librarianId , int daysBefore , DatabaseManager dbManager) {
        String query = "SELECT m.* " +
                "FROM membership m " +
                " JOIN library l ON m.libraryId = l.id " +
                " JOIN librarian lb ON lb.libraryId = l.id " +
                "WHERE lb.id = " + librarianId + " AND m.dateExpired < CURRENT_DATE + " + daysBefore;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    System.out.println("Notifying membership deadline for membership" + resultSet.getInt("id")
                            + " with expiry date " + resultSet.getDate("dateExpired"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
