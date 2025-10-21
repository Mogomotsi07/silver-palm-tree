import java.io.Serializable;

public class BankTeller implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username, password, fullName;

    public BankTeller(String u, String p, String fn) {
        this.username = u;
        this.password = p;
        this.fullName = fn;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName()     { return fullName; }
}