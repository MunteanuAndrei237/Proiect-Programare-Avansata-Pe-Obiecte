package Model;

public class Cd extends Resource {
    private final int resourceId;
    private String format;
    private int size;
    private int duration;


    public Cd(int id, int sectionId, String title, String author, int inStock, int toReturn, int yearPublished,
              int resourceId, String format, int size, int duration) {
        super(id, sectionId, title, author, inStock, toReturn, yearPublished);
        this.resourceId = resourceId;
        this.format = format;
        this.size = size;
        this.duration = duration;
    }

    // Getters and Setters
    public int getResourceId() {
        return resourceId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String toString() {
        return "CD{" +
                "format='" + format + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                "} " + super.toString();
    }

}

