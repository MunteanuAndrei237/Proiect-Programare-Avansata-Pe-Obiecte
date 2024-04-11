package Model;

public class Resource {
    private final int id;
    private int sectionId;
    private String title;
    private String author;
    private int inStock;
    private int toReturn;
    private int yearPublished;

    public Resource(int id, int sectionId, String title, String author, int inStock, int toReturn, int yearPublished) {
        this.id = id;
        this.sectionId = sectionId;
        this.title = title;
        this.author = author;
        this.inStock = inStock;
        this.toReturn = toReturn;
        this.yearPublished = yearPublished;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getToReturn() {
        return toReturn;
    }

    public void setToReturn(int toReturn) {
        this.toReturn = toReturn;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", sectionId=" + sectionId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", inStock=" + inStock +
                ", toReturn=" + toReturn +
                ", yearPublished=" + yearPublished +
                '}';
    }
}
