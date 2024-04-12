package Services;

import DatabaseManager.DatabaseManager;
import Repository.LibraryRepository;
import Model.Library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final AuditService auditService;

    public LibraryService() {
        this.auditService = AuditService.getInstance();
        this.libraryRepository = LibraryRepository.getInstance();
    }

    public Library findById(int id, DatabaseManager dbManager) {
        auditService.logAction("Library find by id ");
        return libraryRepository.findById(id, dbManager);
    }

    public List<Library> findAll(DatabaseManager dbManager) {
        auditService.logAction("Library find all ");
        return libraryRepository.findAll(dbManager);
    }

    public void create(String address, String name, String program, DatabaseManager dbManager) {
        Library newLibrary = new Library(0, address, name, program);
        libraryRepository.create(newLibrary, dbManager);
        auditService.logAction("Library created ");
        System.out.println(newLibrary + " has been created");
    }

    public void update(int id, String address, String name, String program, DatabaseManager dbManager) {
        if (libraryRepository.findById(id, dbManager) == null) {
            System.out.println("Library with id " + id + " does not exist");
            return;
        }
        Library updatedLibrary = new Library(id, address, name, program);
        libraryRepository.update(updatedLibrary, dbManager);
        auditService.logAction("Library updated ");
        System.out.println("Library has been updated to " + updatedLibrary);
    }

    public void delete(int id, DatabaseManager dbManager) {
        Library library = libraryRepository.findById(id, dbManager);
        if (library == null) {
            System.out.println("Library with id " + id + " does not exist");
            return;
        }
        libraryRepository.delete(id, dbManager);
        auditService.logAction("Library deleted ");
        System.out.println(library + " has been deleted");
    }

    public void checkResourceValability(int libraryId, int resourceId, DatabaseManager dbManager) {
        Library library = libraryRepository.findById(libraryId, dbManager);
        if (library == null) {
            System.out.println("Library with id " + libraryId + " does not exist");
            return;
        }

        String resourceCountQuery = "SELECT \"Resource\".inStock FROM \"Resource\"" +
                "JOIN section  ON \"Resource\".sectionId = section.id " +
                "JOIN library ON section.libraryId = library.id " +
                "WHERE library.id = " + libraryId + " AND \"Resource\".id = " + resourceId;

        try {
            ResultSet inStockResult = dbManager.executeQuery(resourceCountQuery);
            if (inStockResult.next()) {
                int inStockCount = inStockResult.getInt("inStock");
                inStockResult.close();
                System.out.println("There are " + inStockCount + " more resources in the library ");
            } else {
                System.out.println("Resource not found in library");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exploreRandomResource(int libraryId, DatabaseManager dbManager) {

        Library library = libraryRepository.findById(libraryId, dbManager);
        if (library == null) {
            System.out.println("Library with id " + libraryId + " does not exist");
            return;
        }

        String resourceQuery = "SELECT * FROM \"Resource\" " +
                "JOIN section ON \"Resource\".sectionId = section.id " +
                "JOIN library ON section.libraryId = library.id " +
                "WHERE library.id = " + libraryId;
        String resourceSize = "SELECT * FROM \"Resource\"" +
                "JOIN section  ON \"Resource\".sectionId = section.id " +
                "JOIN library ON section.libraryId = library.id " +
                "WHERE library.id = " + libraryId;
        try {
            //get size first , then get random index and get the random resource corresponding to that index
            ResultSet resultSet = dbManager.executeQuery(resourceQuery);
            ResultSet resources = dbManager.executeQuery(resourceSize);
            int size = 0;
            try {
                while (resources.next()) {
                    size += 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int randomIndex = (int) (Math.random() * size);
            int index = 0;
            while (resultSet.next()) {
                if (index == randomIndex)
                    System.out.println("Resource recommended: " + resultSet.getString("title") + " by " + resultSet.getString("author"));
                ++index;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortLibraries(String filter, DatabaseManager dbManager) {
        if (filter.equals("hoursOpened")) {
            ArrayList<Library> libraries = (ArrayList<Library>) libraryRepository.findAll(dbManager);
            libraries.sort((library1, library2) -> {
                // Split program into hours
                String[] hours1 = library1.getProgram().split("-");
                String[] hours2 = library2.getProgram().split("-");

                // Extract opening times
                int openingTime1 = Integer.parseInt(hours1[0].replace(":", ""));
                int openingTime2 = Integer.parseInt(hours2[0].replace(":", ""));

                // Compare opening times
                return Integer.compare(openingTime1, openingTime2);
            });
            System.out.println("Libraries sorted by hours opened: ");
            for (Library library : libraries) {
                System.out.println(library.getName() + " " + library.getProgram());
            }
        } else if (filter.equals("numberOfResources")) {
            String filterQuery = "SELECT library.name, COUNT(\"Resource\".id) as numberOfResources " +
                    "FROM library " +
                    "JOIN section ON library.id = section.libraryId " +
                    "JOIN \"Resource\" ON section.id = \"Resource\".sectionId " +
                    "GROUP BY library.id, library.name " +
                    "ORDER BY numberOfResources DESC"; //sort libraries by number of resources in descending order
            ResultSet resultSet = dbManager.executeQuery(filterQuery);
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
        } else {
            System.out.println("Invalid filter");
        }

    }
}
