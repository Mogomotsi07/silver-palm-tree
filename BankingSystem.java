import java.io.Serializable;
import java.util.*;

public class BankingSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String,Customer> customers = new HashMap<>();
    private final Map<String,BankTeller> tellers = new HashMap<>();
    private final Map<String,List<Account>> customerAccounts = new HashMap<>();

    public BankingSystem() {}

    /* ---------- teller ---------- */
    public void addTeller(BankTeller t) { tellers.put(t.getUsername(), t); }
    public BankTeller authenticateTeller(String u, String p) {
        BankTeller t = tellers.get(u);
        return (t != null && t.getPassword().equals(p)) ? t : null;
    }
    public List<BankTeller> getTellers() { return new ArrayList<>(tellers.values()); }

    /* ---------- customer ---------- */
    public boolean addCustomer(Customer c) {
        if (customers.containsKey(c.getUsername())) return false;
        customers.put(c.getUsername(), c);
        customerAccounts.put(c.getId(), new ArrayList<>());
        return true;
    }
    public Customer authenticateCustomer(String u, String p) {
        Customer c = customers.get(u);
        return (c != null && c.getPassword().equals(p)) ? c : null;
    }
    public List<Customer> getCustomers() { return new ArrayList<>(customers.values()); }
    public List<Account> getCustomerAccounts(Customer c) {
        return customerAccounts.getOrDefault(c.getId(), List.of());
    }

    /* ---------- account ---------- */
    public Account createAccount(String type, Customer owner,
                                 double initialDeposit,
                                 String branch,
                                 String employer, String employerAddress) {

        Account acc;
        switch (type) {
            case "SAVINGS" -> {
                if (initialDeposit < SavingsAccount.MIN_OPENING) {
                    AlertUtil.info("Savings account requires minimum P 50.");
                    return null;
                }
                acc = new SavingsAccount();
            }
            case "INVESTMENT" -> {
                if (initialDeposit < InvestmentAccount.MIN_OPENING) {
                    AlertUtil.info("Investment account requires minimum P 500.");
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
        customerAccounts.values().forEach(list -> list.forEach(a -> {
            if (a instanceof InterestBearing ib) ib.applyInterest();
        }));
        AlertUtil.info("Monthly interest applied.");
    }
}