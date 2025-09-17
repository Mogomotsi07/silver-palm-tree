// Investment Account class
public class InvestmentAccount extends Account implements InterestBearing {
    public InvestmentAccount(String accountNumber, double balance, String branch, Customer customer) {
        super(accountNumber, balance, branch, customer);
        if (balance < 500) {
            throw new IllegalArgumentException("Investment account requires minimum BWP500.00");
        }
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
    
    @Override
    public void addInterest() {
        // 5% monthly interest
        balance += balance * 0.05;
    }
}
