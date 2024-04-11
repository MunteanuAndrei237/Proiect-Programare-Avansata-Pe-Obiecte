package Model;

public class ResearchPaper extends Resource {
    private final int resourceId;
    private String field;
    private String university;
    private String sources;

    // Constructor
    public ResearchPaper(int id, int sectionId, String title, String author, int inStock, int toReturn, int yearPublished,
                         int resourceId, String field, String university, String sources) {
        super(id, sectionId, title, author, inStock, toReturn, yearPublished);
        this.resourceId = resourceId;
        this.field = field;
        this.university = university;
        this.sources = sources;
    }

    // Getters and Setters
    public int getResourceId() {
        return resourceId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String toString() {
        return "ResearchPaper{" +
                "field='" + field + '\'' +
                ", university='" + university + '\'' +
                ", sources='" + sources + '\'' +
                "} " + super.toString();
    }
}
