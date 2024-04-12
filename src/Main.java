import DatabaseManager.DatabaseManager;
import Reader.Reader;


public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = DatabaseManager.getInstance();  // Connect to the database
        Reader reader = new Reader(dbManager); //Initialize the reader
        reader.startReader(); //Start the reader
    }
}
