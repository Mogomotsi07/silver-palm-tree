// User class for authentication
public class User {
    private String username;
    private String password;
    private String role; // "teller", "manager", "admin"
    private String firstName;
    private String lastName;
    
    public User(String username, String password, String role, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    
    public void setPassword(String password) { this.password = password; }
    
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + role + ")";
    }
}