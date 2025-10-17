import java.io.Serializable;
import java.util.*;

public class BankingSystem implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, BankTeller> tellers = new HashMap<>();
    private final Map<String, List<Account>> customerAccounts = new HashMap<>();

   
    public void addTeller(BankTeller t) {
        tellers.put(t.getUsername(), t);
    }

    public BankTeller authenticateTeller(String username, String password) {
        BankTeller t = tellers.get(username);
        return (t != null && t.getPassword().equals(password)) ? t : null;
    }

    /* ---------- customer ---------- */
    public boolean addCustomer(Customer c) {
        if (customers.containsKey(c.getUsername())) return false;
        customers.put(c.getUsername(), c);
        customerAccounts.put(c.getId(), new ArrayList<>());
        return true;
    }

    public Customer authenticateCustomer(String username, String password) {
        Customer c = customers.get(username);
        return (c != null && c.getPassword().equals(password)) ? c : null;
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }

    public List<Account> getCustomerAccounts(Customer c) {
        return customerAccounts.getOrDefault(c.getId(), List.of());
    }

    public Account createAccount(String type, Customer owner,
                                 double initialDeposit,
                                 String branch,
                                 String employer,
                                 String employerAddress) {

        Account acc;
        switch (type) {
            case "SAVINGS" -> {
                if (initialDeposit < SavingsAccount.MIN_OPENING) {
                    System.out.println("Savings account requires a minimum opening deposit of $"
                            + SavingsAccount.MIN_OPENING);
                    return null;
                }
                acc = new SavingsAccount();
            }
            case "INVESTMENT" -> {
                if (initialDeposit < 500) {
                    System.out.println("Investment account requires minimum $500.");
                    return null;
                }
                acc = new InvestmentAccount();
            }
            case "CHEQUE" -> acc = new ChequeAccount(employer, employerAddress);
            default -> { return null; }
        }
        acc.deposit(initialDeposit);
        customerAccounts.get(owner.getId()).add(acc);
        return acc;
    }

    public void applyMonthlyInterest() {
        customerAccounts.values().forEach(list -> list.forEach(Account::applyInterest));
        System.out.println("Monthly interest applied to all eligible accounts.");
    }
    
}