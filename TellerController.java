import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class TellerController {
    private final Stage stage;
    private final BankingSystem system;
    private final BankTeller teller;
    private final TellerMenuView view;

    public TellerController(Stage s, BankingSystem bs, BankTeller t) {
        this.stage = s;
        this.system = bs;
        this.teller = t;
        this.view = new TellerMenuView(s, this);
    }

    public void start() {
        refreshCustomers();
        view.show();
    }

    private void refreshCustomers() {
        List<String> list = system.getCustomers()
                .stream()
                .map(c -> c.toString())
                .collect(Collectors.toList());
        view.refreshCustomers(list);
    }

    public void addIndividual() {
        // quick-and-dirty – collect all fields in one go
        String name = Dialogs.askString("New Individual", "Full name:");
        if (name == null) return;
        String addr = Dialogs.askString("New Individual", "Address:");
        String phone = Dialogs.askString("New Individual", "Phone:");
        String email = Dialogs.askString("New Individual", "Email:");
        String user = Dialogs.askString("New Individual", "Username:");
        String pass = Dialogs.askString("New Individual", "Password:");
        String idNum = Dialogs.askString("New Individual", "ID Number:");
        String dob = Dialogs.askString("New Individual", "Date of birth (dd/mm/yyyy):");

        String id = "CUST" + System.currentTimeMillis();
        IndividualCustomer c = new IndividualCustomer(id, name, addr, phone, email, user, pass, idNum, dob);
        boolean ok = system.addCustomer(c);
        AlertUtil.info(ok ? "Customer created." : "Username already exists.");
        refreshCustomers();
    }

    public void addCompany() {
        // same pattern – omitted for brevity
        AlertUtil.info("Company creation dialog – same pattern as individual.");
    }

    public void createAccount(int custIdx) {
        List<Customer> list = system.getCustomers();
        if (custIdx < 0 || custIdx >= list.size()) return;
        Customer owner = list.get(custIdx);

        String[] types = {"SAVINGS", "INVESTMENT", "CHEQUE"};
        String type = Dialogs.choice("Account Type", types);
        if (type == null) return;

        String amtStr = Dialogs.askString("Initial Deposit", "Amount:");
        if (amtStr == null) return;
        try {
            double init = Double.parseDouble(amtStr);
            String branch = Dialogs.askString("Branch", "Branch name:");

            String emp = null, empAddr = null;
            if ("CHEQUE".equals(type)) {
                emp = Dialogs.askString("Employer", "Employer name:");
                empAddr = Dialogs.askString("Employer", "Employer address:");
            }

            Account acc = system.createAccount(type, owner, init, branch, emp, empAddr);
            AlertUtil.info(acc != null ? "Account created: " + acc.getAccountNumber()
                    : "Account creation failed.");
        } catch (NumberFormatException ex) {
            AlertUtil.error("Invalid amount.");
        }
    }

    public void applyInterest() {
        system.applyMonthlyInterest();
        AlertUtil.info("Monthly interest applied.");
    }

    public void logout() {
        DatabaseManager.saveData(system);
        new LoginView(stage, new LoginController(stage, system)).show();
    }
}