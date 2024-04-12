package Services;

import DatabaseManager.DatabaseManager;
import Repository.BorrowingRepository;
import Model.Borrowing;
import DateUtils.DateUtils;

import java.sql.Date;
import java.util.List;
import java.lang.Exception;

import Model.Resource;

public class BorrowingService {
    private final BorrowingRepository borrowingRepository;
    private final ResourceService resourceService;
    private final AuditService auditService;


    public BorrowingService() {
        this.borrowingRepository = BorrowingRepository.getInstance();
        this.resourceService = new ResourceService();
        this.auditService = AuditService.getInstance();
    }

    public Borrowing findById(int id, DatabaseManager dbManager) {
        auditService.logAction("Borrowing find by id ");
        return borrowingRepository.findById(id, dbManager);
    }

    public List<Borrowing> findAll(DatabaseManager dbManager) {
        auditService.logAction("Borrowing find all ");
        return borrowingRepository.findAll(dbManager);
    }

    public void create(int resourceId, int membershipId, Date dateBorrowed, Date dateReturned, DatabaseManager dbManager) {

        Resource resource = resourceService.findById(resourceId, dbManager);
        if (resource == null) {
            System.out.println("Resource with id " + resourceId + " does not exist");
            return;
        }
        //check is the user completed dateBorrowed and dateReturned , if not complete with default values
        if (dateBorrowed == null) {
            dateBorrowed = new Date(System.currentTimeMillis());
        }
        if (dateReturned == null) {
            dateReturned = DateUtils.addOneWeekToDate(dateBorrowed);
        }

        if (dateBorrowed.compareTo(dateReturned) > 0) {
            System.out.println("Date returned cannot be before date borrowed");
            return;
        }

        Borrowing newBorrowing = new Borrowing(0, membershipId, resourceId, dateBorrowed, dateReturned);

        borrowingRepository.create(newBorrowing, dbManager);
        auditService.logAction("Borrowing created ");
        System.out.println(newBorrowing + " has been created");
    }

    public void update(int id, Date dateReturned, DatabaseManager dbManager) throws Exception {
        Borrowing borrowing = borrowingRepository.findById(id, dbManager);
        if (borrowing == null) {
            System.out.println("Borrowing with id " + id + " does not exist");
            return;
        }

        //check is the user completed dateReturned , if not complete with default values
        if (dateReturned == null) {
            borrowing.setDateReturned(DateUtils.addOneWeekToDate(borrowing.getDateBorrowed()));
        } else {
            borrowing.setDateReturned(dateReturned);
        }

        if (borrowing.getDateBorrowed() != null && borrowing.getDateBorrowed().compareTo(dateReturned) > 0) {
            throw new Exception("Date returned cannot be before date borrowed");
        }

        borrowingRepository.update(borrowing, dbManager);
        auditService.logAction("Borrowing updated ");
        System.out.println("Borrowing  has been updated to" + borrowing);
    }

    public void delete(int id, DatabaseManager dbManager) {
        Borrowing borrowing = borrowingRepository.findById(id, dbManager);
        if (borrowing == null) {
            System.out.println("Borrowing with id " + id + " does not exist");
            return;
        }
        borrowingRepository.delete(id, dbManager);
        auditService.logAction("Borrowing deleted ");
        System.out.println(borrowing + " has been deleted");
    }

}