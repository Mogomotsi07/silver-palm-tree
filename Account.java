import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int accCounter = 1000;
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected final String accountNumber;
    protected double balance;
    protected final List<String> history = new ArrayList<>();

    public Account() {
        this.accountNumber = "ACC" + (accCounter++);
        this.balance = 0;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance()       { return balance; }
    public List<String> getHistory() { return history; }
    public String getBalanceText()   { return format(balance); }

    public void deposit(double amt) {
        if (amt <= 0) return;
        balance += amt;
        history.add(timeNow() + " | Deposit: +" + format(amt) + " | Balance=" + format(balance));
    }

    public boolean withdraw(double amt) {
        if (amt <= 0 || amt > balance) return false;
        balance -= amt;
        history.add(timeNow() + " | Withdraw: -" + format(amt) + " | Balance=" + format(balance));
        return true;
    }

    public void checkBalance() {
        System.out.println(accountNumber + " | Balance = " + format(balance));
    }

    public void viewTransactionHistory() {
        if (history.isEmpty()) System.out.println("No transactions.");
        else history.forEach(System.out::println);
    }

    public abstract void applyInterest();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " | " + accountNumber + " | " + format(balance);
    }

    private static String format(double d) {
        return String.format("%.2f", d);
    }
    private static String timeNow() {
        return LocalDateTime.now().format(FMT);
    }
}