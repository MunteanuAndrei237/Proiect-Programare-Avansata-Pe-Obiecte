package Repository;

import Model.Library;
import DatabaseManager.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryRepository implements RepositoryInterface<Library> {

    private static LibraryRepository instance;

    private LibraryRepository() {
        // Private constructor to prevent direct instantiation
    }

    // Singleton pattern
    public static synchronized LibraryRepository getInstance() {
        if (instance == null) {
            instance = new LibraryRepository();
        }
        return instance;
    }

    @Override
    public Library findById(int id, DatabaseManager dbManager) {
        String query = "SELECT * FROM Library WHERE id = " + id;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    return extractLibraryFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Library> findAll(DatabaseManager dbManager) {
        List<Library> libraries = new ArrayList<>();
        String query = "SELECT * FROM Library";
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    libraries.add(extractLibraryFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return libraries;
    }

    @Override
    public void create(Library entity, DatabaseManager dbManager) {
        String query = "INSERT INTO Library ( address, name, program) VALUES ('" + entity.getAddress() + "', '" + entity.getName() + "', '" + entity.getProgram() + "')";
        dbManager.executeQuery(query);
    }

    @Override
    public void update(Library entity, DatabaseManager dbManager) {
        String query = "UPDATE Library SET address = '" + entity.getAddress() + "', name = '" + entity.getName() + "', program = '" + entity.getProgram() + "' WHERE id = " + entity.getId();
        dbManager.executeQuery(query);
    }

    @Override
    public void delete(int id, DatabaseManager dbManager) {
        String query = "DELETE FROM Library WHERE id = " + id;
        dbManager.executeQuery(query);
    }


    private Library extractLibraryFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String address = resultSet.getString("address");
        String name = resultSet.getString("name");
        String program = resultSet.getString("program");
        return new Library(id, address, name, program);
    }
}