import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class TellerController {
    private final Stage stage;
    private final BankingSystem system;
    private final TellerMenuView view;

    public TellerController(Stage stage, BankingSystem system, BankTeller teller) {
        this.stage = stage;
        this.system = system;
        this.view = new TellerMenuView(stage, this);
    }

    public void start() {
        refreshCustomerList();
        view.show();
    }

    public void addIndividual() {
        String id = "CUST" + System.currentTimeMillis();
        String name  = AlertUtil.askString("Individual", "Full Name:");
        if (name == null) return;
        String addr  = AlertUtil.askString("Individual", "Residential Address:");
        String phone = AlertUtil.askString("Individual", "Mobile Number:");
        String email = AlertUtil.askString("Individual", "Email Address:");
        String user  = AlertUtil.askString("Individual", "Username:");
        String pass  = AlertUtil.askString("Individual", "Password:");
        String idNum = AlertUtil.askString("Individual", "ID Number:");
        String dob   = AlertUtil.askString("Individual", "Date of Birth (yyyy-MM-dd):");
        String gender= AlertUtil.askString("Individual", "Gender:");
        String nat   = AlertUtil.askString("Individual", "Nationality:");
        String occ   = AlertUtil.askString("Individual", "Occupation:");
        String mob   = AlertUtil.askString("Individual", "Mobile (repeat):");

        IndividualCustomer c = new IndividualCustomer(id, name, addr, phone, email, user, pass,
                idNum, dob, gender, nat, occ, mob);
        boolean ok = system.addCustomer(c);
        AlertUtil.info(ok ? "Individual customer created." : "Username already exists.");
        refreshCustomerList();
    }

    public void addCompany() {
        String id = "COMP" + System.currentTimeMillis();
        String compName = AlertUtil.askString("Company", "Company Name:");
        if (compName == null) return;
        String addr     = AlertUtil.askString("Company", "Business Address:");
        String phone    = AlertUtil.askString("Company", "Business Phone:");
        String email    = AlertUtil.askString("Company", "Business Email:");
        String user     = AlertUtil.askString("Company", "Username:");
        String pass     = AlertUtil.askString("Company", "Password:");
        String reg      = AlertUtil.askString("Company", "Business Registration Number:");
        String type     = AlertUtil.askString("Company", "Type of Business:");
        String incDate  = AlertUtil.askString("Company", "Date of Incorporation:");
        String sigName  = AlertUtil.askString("Company", "Authorized Signatory Name:");
        String sigId    = AlertUtil.askString("Company", "Signatory ID:");
        String sigRole  = AlertUtil.askString("Company", "Signatory Role:");

        CompanyCustomer c = new CompanyCustomer(id, compName, addr, phone, email, user, pass,
                reg, type, incDate, phone, email, sigName, sigId, sigRole);
        boolean ok = system.addCustomer(c);
        AlertUtil.info(ok ? "Company customer created." : "Username already exists.");
        refreshCustomerList();
    }

    public void createAccount(int customerIndex) {
        List<Customer> customers = system.getCustomers();
        if (customerIndex < 0 || customerIndex >= customers.size()) {
            AlertUtil.error("No customer selected.");
            return;
        }
        Customer owner = customers.get(customerIndex);

        String[] types = {"Savings (min P 50)", "Investment (min P 500)", "Cheque"};
        String choice = AlertUtil.choice("Account Type", types);
        if (choice == null) return;

        String typeStr = choice.split(" ")[0].toUpperCase();
        String initS = AlertUtil.askString("Initial Deposit", "Amount (P):");
        if (initS == null) return;
        try {
            double init = Double.parseDouble(initS);
            String branch = AlertUtil.askString("Branch", "Branch name:");

            String employer = null, employerAddress = null;
            if ("CHEQUE".equals(typeStr)) {
                employer = AlertUtil.askString("Cheque", "Employer:");
                employerAddress = AlertUtil.askString("Cheque", "Employer Address:");
            }

            Account acc = system.createAccount(typeStr, owner, init, branch, employer, employerAddress);
            AlertUtil.info(acc != null ? "Account created: " + acc.getAccountNumber()
                    : "Account creation failed.");
        } catch (NumberFormatException e) {
            AlertUtil.error("Invalid amount.");
        }
    }

    public void viewAllCustomers() {
        StringBuilder sb = new StringBuilder();
        for (Customer c : system.getCustomers()) {
            sb.append(c.toString()).append("\n");
            for (Account a : system.getCustomerAccounts(c)) {
                sb.append("  → ").append(a).append("\n");
            }
        }
        if (sb.isEmpty()) sb.append("No customers.");
        AlertUtil.info(sb.toString());
    }

    public void applyInterest() {
        system.applyMonthlyInterest();
    }

    public void logout() {
        DatabaseManager.saveData(system);
        new LoginView(stage, new LoginController(stage, system)).show();
    }

    private void refreshCustomerList() {
        List<String> list = system.getCustomers()
                .stream()
                .map(Customer::toString)
                .collect(Collectors.toList());
        view.refreshCustomers(list);
    }
}