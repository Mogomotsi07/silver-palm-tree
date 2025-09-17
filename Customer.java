import java.util.ArrayList;
import java.util.List;

// Customer class
public class Customer {
    private String firstName;
    private String surname;
    private String address;
    private List<Account> accounts;
    
    public Customer(String firstName, String surname, String address) {
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.accounts = new ArrayList<>();
    }
    
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    public List<Account> getAccounts() { return accounts; }
    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    
    @Override
    public String toString() {
        return "Customer: " + firstName + " " + surname + ", Address: " + address;
    }
    
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer: ").append(firstName).append(" ").append(surname)
          .append(", Address: ").append(address).append("\n");
        
        if (accounts.isEmpty()) {
            sb.append("  No accounts\n");
        } else {
            sb.append("  Accounts:\n");
            for (Account account : accounts) {
                sb.append("    ").append(account.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
