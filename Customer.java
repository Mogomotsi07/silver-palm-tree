import java.io.Serializable;

public abstract class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    protected final String id;
    protected final String name;
    protected final String address;
    protected final String phone;
    protected final String email;
    protected final String username;
    protected final String password;

    public Customer(String id, String name, String address,
                    String phone, String email, String user, String pass) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.username = user;
        this.password = pass;
    }

    public String getId()       { return id; }
    public String getName()     { return name; }

    public String getAddress()  { return address; }
    public String getPhone()    { return phone; }
    public String getEmail()    { return email; }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return name + " (" + username + ")";
    }
}