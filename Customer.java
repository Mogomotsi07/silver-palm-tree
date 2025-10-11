import java.io.Serializable;

public abstract class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int counter = 1;

    protected final String id;
    protected final String name;
    protected final String address;
    protected final String phoneNumber;
    protected final String email;
    protected final String username;
    protected final String password;

    public Customer(String id, String name, String address,
                    String phoneNumber, String email,
                    String username, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getId()           { return id; }
    public String getName()         { return name; }
    public String getUsername()     { return username; }
    public String getPassword()     { return password; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", id, name, email);
    }
}