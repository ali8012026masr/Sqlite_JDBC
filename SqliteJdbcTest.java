import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqliteJdbcTest {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("# SQLite JDBC Driver not found!");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("# CRUD Operations with SQLite using JDBC:");
        System.out.println("----------------------------------------");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {

            // Create table
            System.out.println("\n# Creating table...");
            try (PreparedStatement pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS employee (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")) {
                pstmt.execute();
            }

            // Insert data
            System.out.println("\n# Inserting data...");
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO employee (name) VALUES ('Alien'), ('Boss'), ('Chand')")) {
                pstmt.executeUpdate();
            }

            // Select data
            System.out.println("\n# Retrieving data after insertion:");
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employee")) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
                }
            }

            // Update data
            System.out.println("\n# Updating data...");
            try (PreparedStatement pstmt = conn.prepareStatement("UPDATE employee SET name = ? WHERE id = ?")) {
                pstmt.setString(1, "Ali");
                pstmt.setInt(2, 1);
                pstmt.executeUpdate();
            }
            
            // Select data
            System.out.println("\n# Retrieving data after update:");
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employee")) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
                }
            }

            // Delete data
            System.out.println("\n# Deleting data...");
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM employee WHERE id = ?")) {
                pstmt.setInt(1, 1);
                pstmt.executeUpdate();
            }
            
            // Select data
            System.out.println("\n# Retrieving data after delete:");
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employee")) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
                }
            }

            // Drop table
            System.out.println("\n# Dropping table...");
            try (PreparedStatement pstmt = conn.prepareStatement("DROP TABLE IF EXISTS employee")) {
                pstmt.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// javac SqliteJdbc.java
// java -cp "sqlite_jdbc.jar;." SqliteJdbc