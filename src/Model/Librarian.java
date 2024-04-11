package Model;

public class Librarian {
    private final int id;
    private int libraryId;
    private String name;
    private String email;
    private String program;

    // Constructor
    public Librarian(int id, int libraryId, String name, String email, String program) {
        this.id = id;
        this.libraryId = libraryId;
        this.name = name;
        this.email = email;
        this.program = program;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String toString() {
        return "Librarian{" +
                "id=" + id +
                ", libraryId=" + libraryId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", program='" + program + '\'' +
                '}';
    }
}
