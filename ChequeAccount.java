// Cheque Account class
public class ChequeAccount extends Account {
    private String employer;
    private String companyAddress;
    
    public ChequeAccount(String accountNumber, double balance, String branch, 
                        Customer customer, String employer, String companyAddress) {
        super(accountNumber, balance, branch, customer);
        this.employer = employer;
        this.companyAddress = companyAddress;
    }
    
    @Override
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    public String getEmployer() { return employer; }
    public String getCompanyAddress() { return companyAddress; }
    
    @Override
    public String toString() {
        return super.toString() + ", Employer: " + employer + ", Company Address: " + companyAddress;
    }
}