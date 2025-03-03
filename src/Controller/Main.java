package Controller;

import Model.User;
import View.Frame;
import java.util.ArrayList;

public class Main {
    private SQLite database;
    
    public static void main(String[] args) {
        new Main().initializeApplication();
    }
    
    public void initializeApplication() {
        // Set up database connection
        database = new SQLite();
        
        setupDatabase();
        
        // Print sample user data for verification
        displayUsers();
        
        // Close database connection
        database = null;
        
        // Initialize the application frame
        Frame mainFrame = new Frame();
        mainFrame.setVisible(true); 
    }
    
    /**
     * Retrieves and prints user details from the database.
     */
    private void displayUsers() {
        if (database == null) {
            database = new SQLite(); // Ensure database is initialized before calling getAllUsers()
        }

        ArrayList<User> users = database.getAllUsers();
        for (User user : users) {
            System.out.println("User ID: " + user.getId() + " | Username: " + user.getUsername() + " | Role: " + user.getRole());
        }
    }
    
    /**
     * Resets and initializes the database with default users.
     * Ensures passwords are securely hashed.
     */
    private void setupDatabase() {
        // Create a new database instance
        database.initializeDatabase();
        
        // Drop existing tables if needed
        database.removeUserTable();
        
        // Create necessary tables
        database.createUsersTable();
        
        // Insert default users with hashed passwords
        database.addUser("admin", "securePass123", 5);
        database.addUser("manager", "securePass123", 4);
        database.addUser("staff", "securePass123", 3);
        database.addUser("client", "securePass123", 2);       
        database.addUser("disabled", "securePass123", 1);
                
        // Display newly added users
        displayUsers();
    }
}
