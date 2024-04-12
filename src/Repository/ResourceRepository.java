package Repository;

import java.sql.*;

import DatabaseManager.DatabaseManager;
import Model.Resource;

public class ResourceRepository {
    private static ResourceRepository instance;

    private ResourceRepository() {
        // Private constructor to prevent instantiation
    }

    // Singleton pattern
    public static synchronized ResourceRepository getInstance() {
        if (instance == null) {
            instance = new ResourceRepository();
        }
        return instance;
    }

    public Resource findById(int id, DatabaseManager dbManager) {
        String query = "SELECT * FROM \"Resource\" WHERE id = " + id;
        try {
            ResultSet rs = dbManager.executeQuery(query);
            if (rs.next()) {
                return new Resource(rs.getInt("id"), rs.getInt("sectionId"), rs.getString("title"), rs.getString("author"), rs.getInt("inStock"), rs.getInt("toReturn"), rs.getInt("yearPublished"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void modifyInStock(int id, DatabaseManager dbManager, int quantity) {
        String query = "UPDATE \"Resource\" SET inStock = inStock + " + quantity + " WHERE id = " + id;
        try {
            dbManager.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyToReturn(int id, DatabaseManager dbManager, int quantity) {
        String query = "UPDATE \"Resource\" SET toReturn = toReturn + " + quantity + " WHERE id = " + id;
        try {
            dbManager.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
