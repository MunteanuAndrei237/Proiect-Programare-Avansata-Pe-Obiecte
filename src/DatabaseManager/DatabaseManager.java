package DatabaseManager;

import java.sql.*;

public class DatabaseManager {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String username = "C##andrei";
    private static final String password = "parola";
    private static Connection connection;

    // Singleton pattern
    private static final DatabaseManager instance = new DatabaseManager();

    private DatabaseManager() {
        try {
            // Register JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Open a connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    // Execute a SQL query
    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
