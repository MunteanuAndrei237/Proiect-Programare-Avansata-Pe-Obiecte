package Model;

public class Section {
    private final int id;
    private int libraryId;
    private int numberShelves;
    private String categoryName;

    // Constructor
    public Section(int id, int libraryId, int numberShelves, String categoryName) {
        this.id = id;
        this.libraryId = libraryId;
        this.numberShelves = numberShelves;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public int getNumberShelves() {
        return numberShelves;
    }

    public void setNumberShelves(int numberShelves) {
        this.numberShelves = numberShelves;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String toString() {
        return "Section{" +
                "id=" + id +
                ", libraryId=" + libraryId +
                ", numberShelves=" + numberShelves +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
