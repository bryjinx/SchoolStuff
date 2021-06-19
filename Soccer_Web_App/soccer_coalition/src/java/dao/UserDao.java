package dao;

import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import model.Player;
import util.Database;
import model.User;

/**
 * This class defines operations users can perform, verifies credentials and
 * verifies permissions.
 *
 * @author Michael Malett
 */
public class UserDao {

    public static enum UserAccessType {
        ROOT, REF, CAPTAIN
    };
    private Connection connection;

    public UserDao() {
    }

    /**
     * creates a new user and inserts into the database. requires root privilege
     * to create a ref, requires ref or root privilege to create a captain
     *
     * @param user The User object attempting to create the new User
     * @param newUser the User object to create -- requires username, password
     * and access to be initialized (email optional)
     * @return a string indicating the result
     */
    public String insertUser(User user, User newUser) {
        connection = Database.getConnection();

        String insert = "INSERT INTO `USERS` VALUES ('" + newUser.getUsername()
                + "', '" + newUser.getPassword() + "', '" + newUser.getFirstname()
                + "', '" + newUser.getLastname() + "', '" + newUser.getEmail()
                + "', " + newUser.getAccess() + ");";
        String checkUsernames = "SELECT username FROM USERS WHERE username='" + newUser.getUsername() + "';";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(checkUsernames);
            if (rs.next()) {
                Database.close(connection);
                return "That username is taken, please try another";
            }
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Database.close(connection);
            return newUser.getFirstname() + " "
                    + newUser.getLastname() + " was not created";
        }

        Database.close(connection);
        return "The new user " + newUser.getFirstname() + " "
                + newUser.getLastname() + " was created successfully";
    }

    public void modify(User user) {

    }

    /**
     * deletes a user from the USERS table
     *
     * @param user the user slated for deletion
     * @param actingUser the user performing the deletion
     * @return a string indicating the result
     */
    public String delete(User actingUser, User user) {
        if (user.getUsername().equals("admin")) {
            return "You cannot delete THE admin";
        }
        if (actingUser.getUsername().equals(user.getUsername())) {
            return "You cannot delete yourself!...yet";
        }
        connection = Database.getConnection();
        String delete = "DELETE FROM `USERS` WHERE username='" + user.getUsername() + "';";
        try {
            PreparedStatement ps = connection.prepareStatement(delete);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Unable to delete " + user.getUsername();
        }

        return user.getUsername() + " has been deleted";
    }

    /**
     * verifies the entered username and password
     *
     * @param user a User object with the username and password to check.
     * @return a fully initialized User object (minus the password) if login
     * credentials are correct, null otherwise.
     */
    public User login(User user) {
        connection = Database.getConnection();
        User newUser = null;
        try {
            String query = "SELECT * FROM USERS WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // user in database
                // check credentials -- this statement should use a password hash.

                if (user.getPassword().equals(rs.getString("password"))) {
                    // correct
                    newUser = new User(
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getInt("access"));

                } else {
                    //incorrect password
                    newUser = null;
                }
            } else {
                // user not in database
                newUser = null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Database.close(connection);
            return newUser;
        }
        Database.close(connection);
        return newUser;
    }

    /**
     * verifies the user access level for a given operation. The required access
     * level is passed in through the UserAccessType enumeration. note: this
     * would be better handled with an improved user table design.
     *
     * @param user The User object that is requesting to perform the operation
     * @param uat The access requirement of the operation to be performed.
     * @return true if the user can perform the operation, false otherwise.
     */
    public boolean verifyAccess(User user, UserAccessType uat) {
        if (user == null) {
            return false;
        }
        switch (uat) {
            case ROOT:
                if (user.getAccess() > 2) {
                    return true;
                }
                break;
            case REF:
                if (user.getAccess() > 1) {
                    return true;
                }
                break;
            case CAPTAIN:
                return true;
            default:
                return false;
        }
        return false;
    }
}
