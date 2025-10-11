import java.io.Serializable;
import java.util.*;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int accCounter = 1000;

    protected final String accountNumber;
    protected double balance;
    protected final List<String> history = new ArrayList<>();

    public Account() {
        this.accountNumber = "ACC" + (accCounter++);
        this.balance = 0;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance()       { return balance; }

    public void deposit(double amt) {
        if (amt <= 0) return;
        balance += amt;
        history.add("Deposit: +" + amt + " | Balance=" + balance);
    }
    public boolean withdraw(double amt) {
        if (amt <= 0 || amt > balance) return false;
        balance -= amt;
        history.add("Withdraw: -" + amt + " | Balance=" + balance);
        return true;
    }
    public void checkBalance() {
        System.out.println(accountNumber + " | Balance = " + balance);
    }
    public void viewTransactionHistory() {
        if (history.isEmpty()) System.out.println("No transactions.");
        else history.forEach(System.out::println);
    }
    public abstract void applyInterest();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " | " + accountNumber + " | " + balance;
    }
}