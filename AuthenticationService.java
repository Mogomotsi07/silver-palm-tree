import java.util.HashMap;
import java.util.Map;

// Authentication service for user management
public class AuthenticationService {
    private Map<String, User> users;
    
    public AuthenticationService() {
        users = new HashMap<>();
        initializeDefaultUsers();
    }
    
    private void initializeDefaultUsers() {
        // Add some default users
        users.put("teller1", new User("teller1", "07361661w", "teller", "Neo", "Moasi"));
        users.put("manager1", new User("manager1", "manager123", "manager", "Mogomotsi", "Vunika"));
        users.put("admin", new User("admin", "admin123", "admin", "System", "Administrator"));
    }
    
    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }
    
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }
}