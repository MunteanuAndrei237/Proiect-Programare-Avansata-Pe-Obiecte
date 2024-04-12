package Services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String CSV_FILE_PATH = "audit_log.csv";
    private static AuditService instance;

    // Private constructor to prevent instantiation from outside
    private AuditService() {
    }

    // Public method to get the singleton instance
    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    // Method to log an action
    public void logAction(String actionName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println(actionName + "," + timestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

