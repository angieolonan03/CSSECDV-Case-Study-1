package Controller;

import Model.User;
import java.sql.*;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

public class SQLite {
    private static final String DB_URL = "jdbc:sqlite:database/database.db";

    /**
     * Establishes a connection to the SQLite database.
     * @return Connection object if successful, otherwise null.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to SQLite!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }
    
    /**
     * Initializes the database with required tables.
     */
    public void initializeDatabase() {
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("Database connection established.");
            }
        } catch (SQLException e) {
            System.out.println("Error setting up the database: " + e.getMessage());
        }
    }
    
    /**
     * Removes the 'users' table from the database.
     * Useful for resetting the database during development.
     */
    public void removeUserTable(){
        String sql = "DROP TABLE IF EXISTS users;";
        
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table removed.");
        } catch (SQLException e) {
            System.out.println("Error deleting users table: " + e.getMessage());
        }
    }
    
    /**
     * Creates the 'users' table if it does not already exist.
     * Stores user credentials securely.
     */
   public void createUsersTable() {
    String sql = "CREATE TABLE IF NOT EXISTS users ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "username TEXT UNIQUE NOT NULL, "
            + "password TEXT NOT NULL, "
            + "role INTEGER DEFAULT 2"
            + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating users table: " + e.getMessage());
        }
    } 
        
    /**
     * Adds a new user with a hashed password and role.
     * @param username Unique username.
     * @param password Plaintext password (hashed before storing).
     * @param role User role (default 2 for clients).
     */
    public void addUser(String username, String password, int role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password)); // Securely hash password
            pstmt.setInt(3, role);
            pstmt.executeUpdate();
            
            System.out.println("User " + username + " added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
    
    /**
     * Retrieves all users from the database.
     * @return ArrayList of User objects.
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, role FROM users";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("role")));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return users;
    }
    
    /**
     * Deletes a user by username.
     * @param username Username of the user to delete.
     */
    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("User " + username + " deleted successfully.");
            } else {
                System.out.println("⚠️ No user found with username: " + username);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }
    
    /**
     * Authenticates a user by verifying username and password.
     * @param username Entered username.
     * @param password Entered password.
     * @return True if credentials match, false otherwise.
     */
    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next() && verifyPassword(password, rs.getString("password"))) {
                return true; // Password matches the stored hash
            }
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Hashes a password using BCrypt.
     * @param password Plaintext password.
     * @return Hashed password.
     */
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifies a password by comparing the plaintext with the stored hash.
     * @param enteredPassword Entered plaintext password.
     * @param storedHash Stored hashed password.
     * @return True if the password matches, false otherwise.
     */
    private boolean verifyPassword(String enteredPassword, String storedHash) {
        return BCrypt.checkpw(enteredPassword, storedHash);
    }
    
    

    public static void main(String[] args) {
        SQLite db = new SQLite();
        db.initializeDatabase();
    }
}
