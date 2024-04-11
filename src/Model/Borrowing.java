package Model;
import java.sql.Date;
public class Borrowing {
    private final int id;
    private int resourceId;
    private int membershipId;
    private Date dateBorrowed;
    private Date dateReturned;

    // Constructor
    public Borrowing(int id, int resourceId, int membershipId, Date dateBorrowed, Date dateReturned) {
        this.id = id;
        this.resourceId = resourceId;
        this.membershipId = membershipId;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String toString() {
        return "Borrowing{" +
                "id=" + id +
                ", resourceId=" + resourceId +
                ", membershipId=" + membershipId +
                ", dateBorrowed=" + dateBorrowed +
                ", dateReturned=" + dateReturned +
                '}';
    }
}
