// Abstract Account class
public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected Customer customer;
    
    public Account(String accountNumber, double balance, String branch, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.branch = branch;
        this.customer = customer;
    }
    
    public abstract boolean deposit(double amount);
    public abstract boolean withdraw(double amount);
    
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getBranch() { return branch; }
    public Customer getCustomer() { return customer; }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Account Number: " + accountNumber + 
               ", Balance: " + balance + ", Branch: " + branch + "]";
    }
}
