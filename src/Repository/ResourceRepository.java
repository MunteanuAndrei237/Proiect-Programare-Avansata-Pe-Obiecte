package Repository;
import java.sql.*;
import DatabaseManager.DatabaseManager;

public class ResourceRepository {
    public void modifyInStock(int id, DatabaseManager dbManager, int quantity) {
        String query = "UPDATE \"Resource\" SET inStock = inStock + " + quantity +" WHERE id = " + id;
        try{
            dbManager.executeQuery(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void modifyToReturn(int id, DatabaseManager dbManager, int quantity) {
        String query = "UPDATE \"Resource\" SET toReturn = toReturn + " + quantity + " WHERE id = " + id;
        try{
            dbManager.executeQuery(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
