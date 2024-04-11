package Model;

public class Library {
    private final int id;
    private String address;
    private String name;
    private String program;

    public Library(int id, String address, String name, String program) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.program = program;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProgram() {
        return program;
    }
    public void setProgram(String program) {
        this.program = program;
    }

    public String toString() {
        return "Library{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", program='" + program + '\'' +
                '}';
    }
}