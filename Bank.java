import java.util.ArrayList;
import java.util.List;

// Bank class to manage the system
public class Bank {
    private List<Customer> customers;
    private List<Account> accounts;
    private AuthenticationService authService;
    private int accountCounter;
    private User currentUser;
    
    public Bank() {
        customers = new ArrayList<>();
        accounts = new ArrayList<>();
        authService = new AuthenticationService();
        accountCounter = 1000;
        currentUser = null;
    }
    
    public User login(String username, String password) {
        User user = authService.authenticate(username, password);
        if (user != null) {
            currentUser = user;
        }
        return user;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public Customer createCustomer(String firstName, String surname, String address) {
        Customer customer = new Customer(firstName, surname, address);
        customers.add(customer);
        return customer;
    }
    
    public Account openSavingsAccount(Customer customer, double initialDeposit, String branch) {
        String accountNumber = "SAV" + (accountCounter++);
        Account account = new SavingsAccount(accountNumber, initialDeposit, branch, customer);
        customer.addAccount(account);
        accounts.add(account);
        return account;
    }
    
    public Account openInvestmentAccount(Customer customer, double initialDeposit, String branch) {
        if (initialDeposit < 500) {
            System.out.println("Investment account requires minimum BWP500.00");
            return null;
        }
        String accountNumber = "INV" + (accountCounter++);
        Account account = new InvestmentAccount(accountNumber, initialDeposit, branch, customer);
        customer.addAccount(account);
        accounts.add(account);
        return account;
    }
    
    public Account openChequeAccount(Customer customer, double initialDeposit, String branch, 
                                    String employer, String companyAddress) {
        String accountNumber = "CHQ" + (accountCounter++);
        Account account = new ChequeAccount(accountNumber, initialDeposit, branch, 
                                          customer, employer, companyAddress);
        customer.addAccount(account);
        accounts.add(account);
        return account;
    }
    
    public boolean deposit(Account account, double amount) {
        return account.deposit(amount);
    }
    
    public boolean withdraw(Account account, double amount) {
        return account.withdraw(amount);
    }
    
    public void addMonthlyInterest() {
        for (Account account : accounts) {
            if (account instanceof InterestBearing) {
                ((InterestBearing) account).addInterest();
            }
        }
    }
    
    public List<Customer> getCustomers() { return customers; }
    public List<Account> getAccounts() { return accounts; }
    
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        
        System.out.println("\n--- All Customers ---");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i));
        }
    }
    
    public void displayCustomerDetails(int index) {
        if (index < 0 || index >= customers.size()) {
            System.out.println("Invalid customer index.");
            return;
        }
        
        Customer customer = customers.get(index);
        System.out.println("\n--- Customer Details ---");
        System.out.println(customer.getDetailedInfo());
    }
}