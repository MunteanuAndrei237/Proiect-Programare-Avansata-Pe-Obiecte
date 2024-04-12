package Model;

import java.sql.Date;

public class Membership {
    private final int id;
    private int personId;
    private int libraryId;
    private String type;
    private Date dateJoined;
    private Date dateExpired;

    public Membership(int id, int personId, int libraryId, String type, Date dateJoined, Date dateExpired) {
        this.id = id;
        this.personId = personId;
        this.libraryId = libraryId;
        this.type = type;
        this.dateJoined = dateJoined;
        this.dateExpired = dateExpired;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public String toString() {
        return "Membership{" +
                ", personId=" + personId +
                ", libraryId=" + libraryId +
                ", type='" + type + '\'' +
                ", dateJoined=" + dateJoined +
                ", dateExpired=" + dateExpired +
                '}';
    }
}
