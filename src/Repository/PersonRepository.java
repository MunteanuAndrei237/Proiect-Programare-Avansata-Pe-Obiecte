package Repository;

import Model.Person;
import DatabaseManager.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository implements RepositoryInterface<Person> {

    private static PersonRepository instance;

    private PersonRepository() {
        // Private constructor to prevent instantiation
    }

    // Singleton pattern
    public static synchronized PersonRepository getInstance() {
        if (instance == null) {
            instance = new PersonRepository();
        }
        return instance;
    }

    @Override
    public Person findById(int id, DatabaseManager dbManager) {
        String query = "SELECT * FROM Person WHERE id = " + id;
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    return extractPersonFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Person> findAll(DatabaseManager dbManager) {
        List<Person> people = new ArrayList<>();
        String query = "SELECT * FROM Person";
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    people.add(extractPersonFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return people;
    }

    @Override
    public void create(Person entity, DatabaseManager dbManager) {
        String query = "INSERT INTO Person ( name, email) VALUES ( '" + entity.getName() + "', '" + entity.getEmail() + "')";
        dbManager.executeQuery(query);
    }

    @Override
    public void update(Person entity, DatabaseManager dbManager) {
        String query = "UPDATE Person SET name = '" + entity.getName() + "', email = '" + entity.getEmail() + "' WHERE id = " + entity.getId();
        dbManager.executeQuery(query);
    }

    @Override
    public void delete(int id, DatabaseManager dbManager) {
        String query = "DELETE FROM Person WHERE id = " + id;
        dbManager.executeQuery(query);
    }

    private Person extractPersonFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new Person(id, name, email);
    }
}