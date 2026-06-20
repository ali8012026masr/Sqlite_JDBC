import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqliteJdbc {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        System.err.println("# CRUD operations using SQLite JDBC:");

        String ddl = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)";
        String dml = "INSERT INTO users (name, age) VALUES ('Ali', 25), ('Boss', 30), ('Charlie', 35)";
        String dql = "SELECT * FROM users";
        String dmlUpdate = "UPDATE users SET name = ? WHERE id = ?";
        String dmlDelete = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");) {

            // Create table
            System.out.println("# Creating table...");
            try (PreparedStatement stmt = conn.prepareStatement(ddl)) {
                stmt.execute();
            }

            // Insert data
            System.out.println("# Inserting data...");
            try (PreparedStatement stmt = conn.prepareStatement(dml)) {
                stmt.executeUpdate();
            }

            // Select data
            System.out.println("# Retrieving data...");
            try (PreparedStatement stmt = conn.prepareStatement(dql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age"));
                }
            }

            // Update data
            System.out.println("# Updating data...");
            try (PreparedStatement stmt = conn.prepareStatement(dmlUpdate)) {
                stmt.setString(1, "Alice");
                stmt.setInt(2, 1);
                stmt.executeUpdate();
            }

            // Retrieve data
            System.out.println("# Retrieving data after update...");
            try (PreparedStatement stmt = conn.prepareStatement(dql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age"));
                }
            }

            // Delete data
            System.out.println("# Deleting data...");
            try (PreparedStatement stmt = conn.prepareStatement(dmlDelete)) {
                stmt.setString(1, "Charlie");
                stmt.executeUpdate();
            }

            // Retrieve data
            System.out.println("# Retrieving data after deletion...");
            try (PreparedStatement stmt = conn.prepareStatement(dql)) {
                ResultSet rs = stmt.executeQuery(); 
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age"));
                }
            }

            // Drop table
            System.out.println("# Dropping table...");
            try (PreparedStatement stmt = conn.prepareStatement("DROP TABLE IF EXISTS users")) {
                    stmt.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// javac SqliteJdbc.java
// java -cp "sqlite_jdbc.jar;." SqliteJdbc