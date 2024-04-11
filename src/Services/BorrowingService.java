package Services;

import DatabaseManager.DatabaseManager;
import Repository.BorrowingRepository;
import Model.Borrowing;
import DateUtils.DateUtils;
import java.sql.Date;
import java.util.List;

public class BorrowingService {
    private final BorrowingRepository borrowingRepository;

    public BorrowingService() {
        this.borrowingRepository = new BorrowingRepository();
    }

    public Borrowing findById(int id , DatabaseManager dbManager) {
        return borrowingRepository.findById(id , dbManager);
    }

    public List<Borrowing> findAll(DatabaseManager dbManager) {
        return borrowingRepository.findAll(dbManager);
    }

    public void create( int resourceId, int membershipId, Date dateBorrowed, Date dateReturned , DatabaseManager dbManager) {
        int borrowingId = borrowingRepository.findAll(dbManager).size() + 1;
        if(dateBorrowed == null) {
            dateBorrowed = new Date(System.currentTimeMillis());
        }
        if(dateReturned == null) {
            dateReturned = DateUtils.addOneWeekToDate(dateBorrowed);
        }
        Borrowing newBorrowing = new Borrowing(borrowingId, resourceId, membershipId, dateBorrowed, dateReturned);

        borrowingRepository.create(newBorrowing,dbManager);
    }

    public void update(int id, int resourceId, int membershipId , Date dateBorrowed , Date dateReturned  , DatabaseManager dbManager) {
        if(dateReturned == null) {
            dateReturned = DateUtils.addOneWeekToDate(dateBorrowed);
        }
        Borrowing updatedBorrowing = new Borrowing(id, resourceId, membershipId, dateBorrowed, dateReturned);

        borrowingRepository.update(updatedBorrowing,dbManager);
    }

    public void delete(int id , DatabaseManager dbManager) {
        borrowingRepository.delete(id,dbManager);
    }

}