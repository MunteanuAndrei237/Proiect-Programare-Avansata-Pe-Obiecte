package Services;
import DatabaseManager.DatabaseManager;
import Repository.LibraryRepository;
import Model.Library;
import java.util.Collections;
import java.util.Comparator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {

    private final LibraryRepository libraryRepository;

    public LibraryService() {
        this.libraryRepository = new LibraryRepository();
    }

    public Library findById(int id, DatabaseManager dbManager) {
        return libraryRepository.findById(id, dbManager);
    }

    public List<Library> findAll(DatabaseManager dbManager) {
        return libraryRepository.findAll(dbManager);
    }

    public void create( String address, String name, String program, DatabaseManager dbManager) {
        int libraryId = libraryRepository.findAll(dbManager).size() + 1;
        Library newLibrary = new Library(libraryId, address, name, program);
        libraryRepository.create(newLibrary, dbManager);
    }

    public void update(int id, String address, String name, String program, DatabaseManager dbManager) {
        Library updatedLibrary = new Library(id, address, name, program);
        libraryRepository.update(updatedLibrary, dbManager);
    }

    public void delete(int id, DatabaseManager dbManager) {
        libraryRepository.delete(id, dbManager);
    }

    public void checkResourceValability(int libraryId, int resourceId, DatabaseManager dbManager) {
        String query = "SELECT \"Resource\".inStock FROM \"Resource\"" +
                "JOIN section  ON \"Resource\".sectionId = section.id " +
                "JOIN library ON section.libraryId = library.id " +
                "WHERE library.id = "+ libraryId +" AND \"Resource\".id = " + resourceId;
        System.out.println(query);
        try {
            ResultSet inStockResult = dbManager.executeQuery(query);
            if (inStockResult.next()) {
                int inStockCount = inStockResult.getInt("inStock");
                inStockResult.close();
                System.out.println("There are " + inStockCount + " more resources in library ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exploreRandomResource(int libraryId, DatabaseManager dbManager) {
        String query = "SELECT * FROM \"Resource\" " +
                "JOIN section ON \"Resource\".sectionId = section.id " +
                "JOIN library ON section.libraryId = library.id " +
                "WHERE library.id = " + libraryId;
        try {
            ResultSet resultSet = dbManager.executeQuery(query);
            int size = libraryRepository.findAll(dbManager).size();
            int randomIndex = 1 + (int) (Math.random() * size);
            while (resultSet.next()) {
                if(resultSet.getInt("id") == randomIndex)
                    System.out.println("Resource recommended: " + resultSet.getString("title") + " by " + resultSet.getString("author"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortLibraries(String filter, DatabaseManager dbManager) {
        if(filter == "hoursOpened")
        {
            ArrayList<Library> libraries = (ArrayList<Library>) libraryRepository.findAll(dbManager);
            Collections.sort(libraries, new Comparator<Library>() {
                @Override
                public int compare(Library library1, Library library2) {
                    // Split program into hours
                    String[] hours1 = library1.getProgram().split("-");
                    String[] hours2 = library2.getProgram().split("-");

                    // Extract opening times
                    int openingTime1 = Integer.parseInt(hours1[0].replace(":", ""));
                    int openingTime2 = Integer.parseInt(hours2[0].replace(":", ""));

                    // Compare opening times
                    return Integer.compare(openingTime1, openingTime2);
                }
            });
            System.out.println("Libraries sorted by hours opened: ");
            for (Library library : libraries) {
                System.out.println(library.getName() + " " + library.getProgram());
            }
        }
        else if(filter == "numberOfResources")
        {
            String query = "SELECT library.name, COUNT(\"Resource\".id) as numberOfResources " +
                    "FROM library " +
                    "JOIN section ON library.id = section.libraryId " +
                    "JOIN \"Resource\" ON section.id = \"Resource\".sectionId " +
                    "GROUP BY library.id, library.name " +
                    "ORDER BY numberOfResources DESC";
            ResultSet resultSet = dbManager.executeQuery(query);
            if (resultSet != null) {
                try {
                    System.out.println("Libraries sorted by number of resources: ");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("name") + " has " + resultSet.getInt("numberOfResources") + " resources");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            System.out.println("Invalid filter");
        }

    }
}
