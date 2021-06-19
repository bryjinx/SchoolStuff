package util;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * General functions for a database connections
 * @author Mike
 */

public class Database {
    public static String DRIVER = "com.mysql.jdbc.Driver";
    public static String HOST = "localhost";
    // create a user that isn't root for general queries?
    public static String USERNAME = "root";
    public static String PASSWORD = "Carlitos!";
    
    /**
     * opens a database connection to the default (soccer) database
     * @return a connection to the default database.
     */
    public static Connection getConnection() {
        try {
            String dbURL = "jdbc:mysql://" + HOST + ":3306/soccer" ;
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(dbURL, USERNAME, PASSWORD);
            return connection;
        } catch (Exception e) {
            System.out.println("Error in Database.getConnection: " + e.getMessage());
            return null;
        }
    }
    /**
     * opens a database connection to the specified database
     * @param db the name of the database to connect
     * @return a connection to the database
     */
    public static Connection getConnection(String db) {
        try {
            String dbURL = "jdbc:mysql://" + HOST + ":3306/" + db ;
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(dbURL, USERNAME, PASSWORD);
            return connection;
        } catch (Exception e) {
            System.out.println("Error in Database.getConnection: " + e.getMessage());
            return null;
        }
    }
    /**
     * closes connection
     * @param connection connection to close 
     */
    public static void close(Connection connection) {
        try {
            connection.close();
        }
        catch(Exception e) {
            System.out.println("Error in Database.close: " + e.getMessage());
        }
    }
}
