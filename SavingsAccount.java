// Savings Account class
public class SavingsAccount extends Account implements InterestBearing {
    public SavingsAccount(String accountNumber, double balance, String branch, Customer customer) {
        super(accountNumber, balance, branch, customer);
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
        // Savings account does not allow withdrawals
        System.out.println("Withdrawals not allowed from Savings Account");
        return false;
    }
    
    @Override
    public void addInterest() {
        // 0.05% monthly interest
        balance += balance * 0.0005;
    }
}
