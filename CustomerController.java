import javafx.stage.Stage;

import java.util.List;

public final class CustomerController {

    private final Stage stage;
    private final BankingSystem system;
    private final Customer customer;
    private final CustomerMenuView view;   // embedded mini-history table

    public CustomerController(Stage stage, BankingSystem system, Customer customer) {
        this.stage = stage;
        this.system = system;
        this.customer = customer;
        this.view = new CustomerMenuView(stage, this);
    }

    /*  entry point from LoginController  */
    public void start() {
        view.setWelcome(customer.getName());
        refreshAccountList();
        view.show();
    }

    /*  ----------------  actions  ----------------  */

    public void deposit(int accountIndex) {
        List<Account> accounts = system.getCustomerAccounts(customer);
        if (indexInvalid(accountIndex, accounts)) return;

        String ans = AlertUtil.askString("Deposit", "Amount to deposit:");
        if (ans == null) return;

        try {
            double amt = Double.parseDouble(ans);
            accounts.get(accountIndex).deposit(amt);
            refreshAccountList();
            refreshHistory(accountIndex);          // update embedded table
            AlertUtil.info("Deposit successful.");
        } catch (NumberFormatException e) {
            AlertUtil.error("Invalid amount.");
        }
    }

    public void withdraw(int accountIndex) {
        List<Account> accounts = system.getCustomerAccounts(customer);
        if (indexInvalid(accountIndex, accounts)) return;

        String ans = AlertUtil.askString("Withdraw", "Amount to withdraw:");
        if (ans == null) return;

        try {
            double amt = Double.parseDouble(ans);
            boolean ok = accounts.get(accountIndex).withdraw(amt);
            refreshAccountList();
            refreshHistory(accountIndex);          // update embedded table
            AlertUtil.info(ok ? "Withdrawal successful." : "Insufficient funds.");
        } catch (NumberFormatException e) {
            AlertUtil.error("Invalid amount.");
        }
    }

    public void showHistory(int accountIndex) {
        List<Account> accounts = system.getCustomerAccounts(customer);
        if (indexInvalid(accountIndex, accounts)) return;

        refreshHistory(accountIndex);   // fill mini table
        view.toggleHistory();           // open/close pane
    }

    public void logout() {
        DatabaseManager.saveData(system);
        new LoginView(stage, new LoginController(stage, system)).show();
    }

    /*  ----------------  helpers  ----------------  */

    private boolean indexInvalid(int idx, List<?> list) {
        if (idx < 0 || idx >= list.size()) {
            AlertUtil.error("No account selected.");
            return true;
        }
        return false;
    }

    private void refreshAccountList() {
        List<String> list = system.getCustomerAccounts(customer)
                .stream()
                .map(Account::toString)
                .toList();
        view.refreshAccounts(list);
    }

    private void refreshHistory(int accountIndex) {
        List<Account> accounts = system.getCustomerAccounts(customer);
        if (!indexInvalid(accountIndex, accounts)) {
            view.refreshHistory(accounts.get(accountIndex).getHistory());
        }
    }
}