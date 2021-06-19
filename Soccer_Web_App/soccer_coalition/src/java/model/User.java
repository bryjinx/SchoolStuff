package model;

/**
 * Stores information about the user.
 *
 * @author Mike
 */
public class User {

    private String firstname, lastname, username, password, email;
    private int access;

    public User() {
    }

    /**
     * Use when creating a user from form data
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Use this constructor when initializing a User object from database
     * information to user as a session object
     *
     * @param username
     * @param email
     * @param access
     */
    public User(String username, String email, int access) {
        this.username = username;
        this.email = email;
        this.access = access;
        this.password = "";
    }

    /**
     * Use this constructor when initializing a User object from database
     * information to user as a session object
     *
     * @param firstname user's first name
     * @param lastname user's last name
     * @param username user's handle
     * @param email user's email address
     * @param access user's access level (1, 2, 3)
     */
    public User(String firstname, String lastname, String username, String email, int access) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.access = access;
        this.password = "";
    }

    /**
     * Use this constructor when initializing a User object to insert into the
     * database
     *
     * @param firstname user's first name
     * @param lastname user's last name
     * @param username user's handle
     * @param password user's password
     * @param email user's email address
     * @param access user's access level (1, 2, 3)
     */
    public User(String firstname, String lastname, String username, String password, String email, int access) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.access = access;
        this.password = password;
    }

    /* setters and getters */
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
