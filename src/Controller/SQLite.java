package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;


public class SQLite {
    
    public int DEBUG_MODE = 0;
    String driverURL = "jdbc:sqlite:" + "database.db";
    
    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Database database.db created.");
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL,\n"
            + " name TEXT NOT NULL,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createLogsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " event TEXT NOT NULL,\n"
            + " username TEXT NOT NULL,\n"
            + " desc TEXT NOT NULL,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " name TEXT NOT NULL UNIQUE,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " price REAL DEFAULT 0.00\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL UNIQUE,\n"
            + " password TEXT NOT NULL,\n"
            + " role INTEGER DEFAULT 2,\n"
            + " locked INTEGER DEFAULT 0\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addHistory(String username, String name, int stock, String timestamp) {
        String sql = "INSERT INTO history(username,name,stock,timestamp) VALUES('" + username + "','" + name + "','" + stock + "','" + timestamp + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES('" + event + "','" + username + "','" + desc + "','" + timestamp + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addProduct(String name, int stock, double price) {
        String sql = "INSERT INTO product(name,stock,price) VALUES('" + name + "','" + stock + "','" + price + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    /**
     * Adds a new user to the database with a specified role (client).
     * @param username the username of the new user.
     * @param password the password of the new user (hashed before storing)
     * @param role the role assigned to the user.
     */
    
    public void addUser(String username, String password, int role) {
        //String sql = "INSERT INTO users(username,password) VALUES('" + username + "','" + password + "')";
        
        String sql = "INSERT INTO users(username,password, role) VALUES(?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            //Statement stmt = conn.createStatement()){
            //stmt.execute(sql);
            
//      PREPARED STATEMENT EXAMPLE
//      String sql = "INSERT INTO users(username,password) VALUES(?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, username);
        pstmt.setString(2, hashPassword(password));
        pstmt.setInt(3, role);
        pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    /*
    public void addUser(String username, String password, int role) {
        String sql = "INSERT INTO users(username,password,role) VALUES(?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password)); // Hash password before storing
            pstmt.setInt(3, role);
            pstmt.executeUpdate();
            
            System.out.println("User " + username + " added successfully.");
        } catch (Exception ex) {
            System.out.println("Error adding user: " + ex.getMessage());
        }
    }
    */
 
    public ArrayList<History> getHistory(){
        String sql = "SELECT id, username, name, stock, timestamp FROM history";
        ArrayList<History> histories = new ArrayList<History>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return histories;
    }
    
    public ArrayList<Logs> getLogs(){
        String sql = "SELECT id, event, username, desc, timestamp FROM logs";
        ArrayList<Logs> logs = new ArrayList<Logs>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                logs.add(new Logs(rs.getInt("id"),
                                   rs.getString("event"),
                                   rs.getString("username"),
                                   rs.getString("desc"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return logs;
    }
    
    public ArrayList<Product> getProduct(){
        String sql = "SELECT id, name, stock, price FROM product";
        ArrayList<Product> products = new ArrayList<Product>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return products;
    }
    
    public ArrayList<User> getUsers(){
        String sql = "SELECT id, username, password, role, locked FROM users";
        ArrayList<User> users = new ArrayList<User>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("password"),
                                   rs.getInt("role"),
                                   rs.getInt("locked")));
            }
        } catch (Exception ex) {}
        return users;
    }
    
    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username='" + username + "';";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User " + username + " has been deleted.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public Product getProduct(String name){
        String sql = "SELECT name, stock, price FROM product WHERE name='" + name + "';";
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            product = new Product(rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price"));
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return product;
    }
    
    /**
     * Retrieves a user based on their unique user ID.
     * @param userID the ID of the user to retrieve.
     * @return  a User object if found, otherwise null;
     */
    private User findUserByID(final int userID){
        for(User user : getUsers()){
            if(user.getId() == userID){
                return user;
            }
        }
        return null;
    }
    
    /**
     * Retrieves a user based on their username.
     * @param username the username of the user.
     * @return a User object if found, otherwise null.
     */
    private User findUserByUsername(final String username){
        for(User user : getUsers()){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    /**
     * Retrieves the username associated with a given user ID.
     * @param id the ID of the user.
     * @return The username if found, otherwise null.
     */
    public String getUsername(final int id){
        User user = findUserByID(id);
        return (user != null) ? user.getUsername() : null;
    }
    
    /**
     * Retrieves the user ID corresponding to a given username.
     * @param username The username to search for.
     * @return  The user ID if found, otherwise -1 (indicating not found).
     */
    public int getUserID(final String username) {
        User user = findUserByUsername(username);
        
        return (user != null) ? user.getId() : -1;
    }
    
    /**
     * Updates the role of a user in the database.
     * @param username the username of the user.
     * @param role the new role to be assigned.
     * @return  true if the update was successful, false otherwise.
     */
    private boolean updateUserRole(String username, int role){
        if(role < 0 || role > 5){
            return false;
        }
        String sql = "UPDATE users SET role = ? WHERE username = ?";
        
        try(Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, role);
            pstmt.setString(2, username);
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e){
            System.err.println("Error updating role: "+ e.getMessage());
            
            return false;
        }
    }
    
    /**
     * Checks if a user exists in the database.
     * @param username the username to check.
     * @return true if the user exists, false otherwise.
     */
    public boolean doesUserExist(String username){
        return findUserByUsername(username) != null;
    }
    
    //TO BE DONE
    //PROBLEM: when entering wrong pw, the user can still login
    /**
     * Authenticates a user by verifying their password and tracking failed attempts.
     * @param username the username of the user.
     * @param password the password entered by the user.
     * @return  true if authentication is successful, false otherwise.
     */
    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT password, failed_attempts FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
            int failedAttempts = rs.getInt("failed_attempts");

            if (verifyPassword(password, storedHash)) {
                resetFailedAttempts(username); // Reset failed attempts on successful login
                return true;
            } else {
                failedAttempts++;
                updateFailedAttempts(username, failedAttempts);
                if (failedAttempts >= 5) {
                    lockUserAccount(username); // Lock account after 5 failed attempts
                    System.out.println("Account locked due to too many failed login attempts.");
                }
            }
        }
        } catch (SQLException ex) {
            System.out.println("Authentication error: " + ex.getMessage());
        }
        return false;
    }
    
    /**
     * Checks if a user account is locked.
     * @param username the username to check.
     * @return true if the account is locked, false otherwise.
     */
    public boolean isAccountLocked(String username) {
        String sql = "SELECT locked FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            return rs.next() && rs.getInt("locked") == 1;
        } catch (SQLException ex) {
            System.out.println("Error checking account lock status: " + ex.getMessage());
        }
        return false;
    }
    
    /**
     * Locks a user account.
     * @param username the username of the account to lock.
     * @return  true if the account was successfully locked, false otherwise.
     */
    public boolean lockUserAccount(String username) {
        return updateUserRole(username, 1);
    }
    
    /**
     * Unlocks a user account.
     * @param username the username of the account to unlock.
     * @return true if the account was successfully unlocked, false otherwise.
     */
    public boolean unlockUserAccount(String username) {
        return updateUserRole(username, 2);
    }
    
    /**
     * Hashes a password using BCrypt.
     * @param password The plain text password.
     * @return A hashed version of the password.
     */
    private String hashPassword(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifies if an entered password matches the stored hash.
     * @param enteredPassword The password entered by the user.
     * @param storedHash The hashed password stored in the database.
     * @return True if the password is correct, false otherwise.
     */
    private boolean verifyPassword(final String enteredPassword, final String storedHash) {
        return BCrypt.checkpw(enteredPassword, storedHash);
    }
    
    //TO BE TESTED
    /**
     * Updates the number of failed login attempts for a user.
     * @param username the username of the user.
     * @param failedAttempts the new count of failed attempts.
     */
    private void updateFailedAttempts(String username, int failedAttempts){
        String sql = "UPDATE users SET failed_attempts = ? WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, failedAttempts);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error updating failed attempts: " + ex.getMessage());
        }
    }
    
    /**
     * Resets the failed login attempts count for a user.
     * @param username the username of the user.
     */
    private void resetFailedAttempts(String username) {
        String sql = "UPDATE users SET failed_attempts = 0 WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error resetting failed attempts: " + ex.getMessage());
        }
    }
    
}