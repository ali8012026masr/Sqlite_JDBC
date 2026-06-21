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
        String ddl = "CREATE TABLE IF NOT EXISTS employee (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";

        String dml = "INSERT INTO employee (name) VALUES ('Alien'), ('Boss'), ('Chand')";

        String selectQuery = "SELECT * FROM employee";

        String updateQuery = "UPDATE employee SET name = ? WHERE id = ?";

        String deleteQuery = "DELETE FROM employee WHERE id = ?";

        String dropTable = "DROP TABLE IF EXISTS employee";

        int id = 1;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {

            // Create table
            System.out.println("Creating table...");
            try (PreparedStatement pstmt = conn.prepareStatement(ddl)) {
                pstmt.execute();
            }

            // Insert data
            System.out.println("Inserting data...");
            try (PreparedStatement pstmt = conn.prepareStatement(dml)) {
                pstmt.executeUpdate();
            }

            // Select data
            System.out.println("Data after insertion:");
            try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("id");
                    System.out.println("ID: " + id + ", Name: " + rs.getString("name"));
                }
            }

            // Update data
            System.out.println("Updating data...");
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, "Ali");
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
            }
            
            // Select data
            System.out.println("Data after update:");
            try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("id");
                    System.out.println("ID: " + id + ", Name: " + rs.getString("name"));
                }
            }

            // Delete data
            System.out.println("Deleting data...");
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            
            // Select data
            System.out.println("Data after delete:");
            try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("id");
                    System.out.println("ID: " + id + ", Name: " + rs.getString("name"));
                }
            }

            // Drop table
            System.out.println("Dropping table...");
            try (PreparedStatement pstmt = conn.prepareStatement(dropTable)) {
                pstmt.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

// javac SqliteJdbc.java
// java -cp "sqlite_jdbc.jar;." SqliteJdbc