package Repository;

import Model.Librarian;
import DatabaseManager.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianRepository implements RepositoryInterface<Librarian> {
    private static LibrarianRepository instance;


    private LibrarianRepository() {
        // Private constructor to prevent direct instantiation
    }

    // Singleton pattern
    public static synchronized LibrarianRepository getInstance() {
        if (instance == null) {
            instance = new LibrarianRepository();
        }
        return instance;
    }

    @Override
    public Librarian findById(int id, DatabaseManager dbManager) {
        String query = "SELECT * FROM Librarian WHERE id = " + id;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    return extractLibrarianFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Librarian> findAll(DatabaseManager dbManager) {
        List<Librarian> librarians = new ArrayList<>();
        String query = "SELECT * FROM Librarian";
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    librarians.add(extractLibrarianFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return librarians;
    }

    @Override
    public void create(Librarian entity, DatabaseManager dbManager) {
        String query = "INSERT INTO Librarian (libraryId, name, email, program) VALUES (" +
                entity.getLibraryId() + ", '" + entity.getName() + "', '" + entity.getEmail() + "', '" + entity.getProgram() + "')";
        dbManager.executeQuery(query);
    }

    @Override
    public void update(Librarian entity, DatabaseManager dbManager) {
        String query = "UPDATE Librarian SET libraryId = " + entity.getLibraryId() + ", name = '" + entity.getName() +
                "', email = '" + entity.getEmail() + "', program = '" + entity.getProgram() + "' WHERE id = " + entity.getId();
        dbManager.executeQuery(query);
    }

    @Override
    public void delete(int id, DatabaseManager dbManager) {
        String query = "DELETE FROM Librarian WHERE id = " + id;
        dbManager.executeQuery(query);
    }


    private Librarian extractLibrarianFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int libraryId = resultSet.getInt("libraryId");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String program = resultSet.getString("program");
        return new Librarian(id, libraryId, name, email, program);
    }
}
