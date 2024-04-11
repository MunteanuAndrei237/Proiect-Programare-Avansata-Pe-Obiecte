package Model;

public class Book extends Resource {
    private final int resourceId;
    private String genre;
    private String publisher;
    private int nrPages;

    public Book(int id, int sectionId, String title, String author, int inStock, int toReturn, int yearPublished,
                int resourceId, String genre, String publisher, int nrPages) {
        super(id, sectionId, title, author, inStock, toReturn, yearPublished);
        this.resourceId = resourceId;
        this.genre = genre;
        this.publisher = publisher;
        this.nrPages = nrPages;
    }
    // Getters and Setters
    public int getResourceId() {
        return resourceId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNrPages() {
        return nrPages;
    }

    public void setNrPages(int nrPages) {
        this.nrPages = nrPages;
    }

    public String toString() {
        return "Book{" +
                "genre='" + genre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", nrPages=" + nrPages +
                "} " + super.toString();
    }
}
