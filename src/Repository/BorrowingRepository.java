package Repository;
import java.text.SimpleDateFormat;
import Model.Borrowing;
import DatabaseManager.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowingRepository implements RepositoryInterface<Borrowing> {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

    @Override
    public Borrowing findById(int id, DatabaseManager dbManager) {
        String query = "SELECT * FROM Borrowing WHERE id = " + id;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    return extractBorrowingFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Borrowing> findAll(DatabaseManager dbManager) {
        List<Borrowing> borrowings = new ArrayList<>();
        String query = "SELECT * FROM Borrowing";
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    borrowings.add(extractBorrowingFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return borrowings;
    }

    @Override
    public void create(Borrowing entity, DatabaseManager dbManager) {
        String query = "INSERT INTO Borrowing (id, resourceId, membershipId, dateBorrowed, dateReturned) VALUES (" + entity.getId() + ", " + entity.getResourceId() + ", " + entity.getMembershipId() + ", '" + sdf.format(entity.getDateBorrowed()) + "', '" + sdf.format(entity.getDateReturned()) + "')";
        dbManager.executeQuery(query);
    }

    @Override
    public void update(Borrowing entity, DatabaseManager dbManager) {
        String query = "UPDATE Borrowing SET resourceId = " + entity.getResourceId() + ", membershipId = " + entity.getMembershipId() + ", dateBorrowed = '" + sdf.format(entity.getDateBorrowed()) + "', dateReturned = '" + sdf.format(entity.getDateReturned()) + "' WHERE id = " + entity.getId();
        dbManager.executeQuery(query);
    }

    @Override
    public void delete(int id, DatabaseManager dbManager) {
        String query = "DELETE FROM Borrowing WHERE id = " + id;
        dbManager.executeQuery(query);
    }

    private Borrowing extractBorrowingFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int resourceId = resultSet.getInt("resourceId");
        int membershipId = resultSet.getInt("membershipId");
        Date dateBorrowed = resultSet.getDate("dateBorrowed");
        Date dateReturned = resultSet.getDate("dateReturned");
        return new Borrowing(id, resourceId, membershipId, dateBorrowed, dateReturned);
    }
}