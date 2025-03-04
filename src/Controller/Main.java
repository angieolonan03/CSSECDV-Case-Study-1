package Controller;


import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import View.Frame;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;



public class Main {
    
    public SQLite sqlite;
    
    public static void main(String[] args) {
        new Main().init();
    }
    
    public void init(){
        // Initialize a driver object
        sqlite = new SQLite();
        

//        // Create a database
//        sqlite.createNewDatabase();
//        
//        // Drop users table if needed
//        sqlite.dropHistoryTable();
//        sqlite.dropLogsTable();
//        sqlite.dropProductTable();
//        sqlite.dropUserTable();
//        
//        // Create users table if not exist
//        sqlite.createHistoryTable();
//        sqlite.createLogsTable();
//        sqlite.createProductTable();
//        sqlite.createUserTable();
//        
//        // Add sample history
//        sqlite.addHistory("admin", "Antivirus", 1, "2019-04-03 14:30:00.000");
//        sqlite.addHistory("manager", "Firewall", 1, "2019-04-03 14:30:01.000");
//        sqlite.addHistory("staff", "Scanner", 1, "2019-04-03 14:30:02.000");
//        
//        // Add sample logs
//        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());
//        sqlite.addLogs("NOTICE", "manager", "User creation successful", new Timestamp(new Date().getTime()).toString());
//        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());
//        
//        // Add sample product
//        sqlite.addProduct("Antivirus", 5, 500.0);
//        sqlite.addProduct("Firewall", 3, 1000.0);
//        sqlite.addProduct("Scanner", 10, 100.0);
//
//        // Add sample users
//        sqlite.addUser("admin", "qwerty1234" , 5);
//        sqlite.addUser("manager", "qwerty1234", 4);
//        sqlite.addUser("staff", "qwerty1234", 3);
//        sqlite.addUser("client1", "qwerty1234", 2);
//        sqlite.addUser("client2", "qwerty1234", 2);
//        
//        
//        // Get users
//        ArrayList<History> histories = sqlite.getHistory();
//        for(int nCtr = 0; nCtr < histories.size(); nCtr++){
//            System.out.println("===== History " + histories.get(nCtr).getId() + " =====");
//            System.out.println(" Username: " + histories.get(nCtr).getUsername());
//            System.out.println(" Name: " + histories.get(nCtr).getName());
//            System.out.println(" Stock: " + histories.get(nCtr).getStock());
//            System.out.println(" Timestamp: " + histories.get(nCtr).getTimestamp());
//        }
//        
//        // Get users
//        ArrayList<Logs> logs = sqlite.getLogs();
//        for(int nCtr = 0; nCtr < logs.size(); nCtr++){
//            System.out.println("===== Logs " + logs.get(nCtr).getId() + " =====");
//            System.out.println(" Username: " + logs.get(nCtr).getEvent());
//            System.out.println(" Password: " + logs.get(nCtr).getUsername());
//            System.out.println(" Role: " + logs.get(nCtr).getDesc());
//            System.out.println(" Timestamp: " + logs.get(nCtr).getTimestamp());
//        }
//        
//        // Get users
//        ArrayList<Product> products = sqlite.getProduct();
//        for(int nCtr = 0; nCtr < products.size(); nCtr++){
//            System.out.println("===== Product " + products.get(nCtr).getId() + " =====");
//            System.out.println(" Name: " + products.get(nCtr).getName());
//            System.out.println(" Stock: " + products.get(nCtr).getStock());
//            System.out.println(" Price: " + products.get(nCtr).getPrice());
//        }
//        // Get users
//        ArrayList<User> users = sqlite.getUsers();
//        for(int nCtr = 0; nCtr < users.size(); nCtr++){
//            System.out.println("===== User " + users.get(nCtr).getId() + " =====");
//            System.out.println(" Username: " + users.get(nCtr).getUsername());
//            System.out.println(" Password: " + users.get(nCtr).getPassword());
//            System.out.println(" Role: " + users.get(nCtr).getRole());
//            System.out.println(" Locked: " + users.get(nCtr).getLocked());
//        }
        
        setupDatabase();
    
        // Initialize User Interface
        Frame frame = new Frame();
        frame.init(this);
        
        displayLogs();
    }
        
        private void displayLogs(){
            ArrayList<Logs> logs = sqlite.getLogs();
            for(Logs log : logs){
                System.out.println(log.getTimestamp() + ": [" + log.getEvent() + "] " + log.getDesc() + " by " + log.getUsername());
            }
        }
        
        private void displayUsers(){
            ArrayList<User> users = sqlite.getUsers();
            for (User user : users) {
                System.out.println(user.getId() + ": " + user.getUsername() + " | Role: " + user.getRole() + " | Locked: " + user.getLocked());
            }
        }
        
        private void setupDatabase() {
        sqlite.createNewDatabase();

        // Drop existing tables (for reset purposes)
        sqlite.dropHistoryTable();
        sqlite.dropLogsTable();
        sqlite.dropProductTable();
        sqlite.dropUserTable();

        // Create new tables
        sqlite.createHistoryTable();
        sqlite.createLogsTable();
        sqlite.createProductTable();
        sqlite.createUserTable();

        // Sample history records
        sqlite.addHistory("admin", "Antivirus", 1, "2019-04-03 14:30:00.000");
        sqlite.addHistory("manager", "Firewall", 1, "2019-04-03 14:30:01.000");
        sqlite.addHistory("staff", "Scanner", 1, "2019-04-03 14:30:02.000");

        // Sample log records
        String timestamp = new Timestamp(new Date().getTime()).toString();
        sqlite.addLogs("NOTICE", "admin", "User creation successful", timestamp);
        sqlite.addLogs("NOTICE", "manager", "User creation successful", timestamp);
        sqlite.addLogs("NOTICE", "staff", "User creation successful", timestamp);

        // Sample products
        sqlite.addProduct("Antivirus", 5, 500.0);
        sqlite.addProduct("Firewall", 3, 1000.0);
        sqlite.addProduct("Scanner", 10, 100.0);

        // Sample users with hashed passwords
        sqlite.addUser("admin", "qwerty1234", 5);
        sqlite.addUser("manager", "qwerty1234", 4);
        sqlite.addUser("staff", "qwerty1234", 3);
        sqlite.addUser("client", "qwerty1234", 2);
        sqlite.addUser("disabled", "qwerty1234", 1);

        // Display stored records
        displayHistory();
        displayLogs();
        displayProducts();
        displayUsers();
    }

    /**
     * Displays history records from the database.
     */
    private void displayHistory() {
        ArrayList<History> histories = sqlite.getHistory();
        for (History history : histories) {
            System.out.println("===== History " + history.getId() + " =====");
            System.out.println(" Username: " + history.getUsername());
            System.out.println(" Name: " + history.getName());
            System.out.println(" Stock: " + history.getStock());
            System.out.println(" Timestamp: " + history.getTimestamp());
        }
    }

    /**
     * Displays all products in the database.
     */
    private void displayProducts() {
        ArrayList<Product> products = sqlite.getProduct();
        for (Product product : products) {
            System.out.println("===== Product " + product.getId() + " =====");
            System.out.println(" Name: " + product.getName());
            System.out.println(" Stock: " + product.getStock());
            System.out.println(" Price: " + product.getPrice());
        }
    }
}
