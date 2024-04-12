package Repository;

import Model.Membership;
import DatabaseManager.DatabaseManager;

import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MembershipRepository implements RepositoryInterface<Membership> {
    private final SimpleDateFormat sdf;
    private static MembershipRepository instance;

    private MembershipRepository() {
        this.sdf = new SimpleDateFormat("dd-MMM-yy");
    }

    // Singleton pattern
    public static synchronized MembershipRepository getInstance() {
        if (instance == null) {
            instance = new MembershipRepository();
        }
        return instance;
    }

    @Override
    public Membership findById(int id, DatabaseManager dbManager) {
        String query = "SELECT * FROM Membership WHERE id = " + id;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    return extractMembershipFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Membership> findAll(DatabaseManager dbManager) {
        List<Membership> memberships = new ArrayList<>();
        String query = "SELECT * FROM Membership";
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    memberships.add(extractMembershipFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return memberships;
    }

    @Override
    public void create(Membership entity, DatabaseManager dbManager) {
        String query = "INSERT INTO Membership ( personId, libraryId, type, dateJoined, dateExpired) VALUES ( " + entity.getPersonId() + ", " + entity.getLibraryId() + ", '" + entity.getType() + "', '" + sdf.format(entity.getDateJoined()) + "', '" + sdf.format(entity.getDateExpired()) + "')";
        dbManager.executeQuery(query);
    }

    @Override
    public void update(Membership entity, DatabaseManager dbManager) {
        String query = "UPDATE Membership SET personId = " + entity.getPersonId() + ", libraryId = " + entity.getLibraryId() + ", type = '" + entity.getType() + "', dateJoined = '" + sdf.format(entity.getDateJoined()) + "', dateExpired = '" + sdf.format(entity.getDateExpired()) + "' WHERE id = " + entity.getId();
        dbManager.executeQuery(query);
    }

    @Override
    public void delete(int id, DatabaseManager dbManager) {
        String query = "DELETE FROM Membership WHERE id = " + id;
        dbManager.executeQuery(query);
    }

    private Membership extractMembershipFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int personId = resultSet.getInt("personId");
        int libraryId = resultSet.getInt("libraryId");
        String type = resultSet.getString("type");
        Date dateJoined = resultSet.getDate("dateJoined");
        Date dateExpired = resultSet.getDate("dateExpired");
        return new Membership(id, personId, libraryId, type, dateJoined, dateExpired);
    }
}