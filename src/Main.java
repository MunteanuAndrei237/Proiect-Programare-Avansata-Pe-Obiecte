import DatabaseManager.DatabaseManager;
import Reader.Reader;


public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        Reader reader = new Reader(dbManager);
        reader.startReader();
    }
}
