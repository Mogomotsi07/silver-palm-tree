// BankingApp.java
import java.util.List;
import java.util.Scanner;

public class BankingApp {
    private BankingSystem bankingSystem;
    private Scanner scanner;

    public BankingApp() {
        this.bankingSystem = DatabaseManager.loadData();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Bank Aura ===");
            System.out.println("1. Login as Customer");
            System.out.println("2. Login as Bank Teller");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

            switch (getIntInput()) {
                case 1 -> customerLogin();
                case 2 -> tellerLogin();
                case 3 -> {
                    DatabaseManager.saveData(bankingSystem);
                    System.out.println("Thank you for using Bank Aura!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

   
    private void customerLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Customer customer = bankingSystem.authenticateCustomer(username, password);
        if (customer != null) {
            customerMenu(customer);
        } else {
            System.out.println("Invalid credentials or account.");
        }
    }

    private void tellerLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        BankTeller teller = bankingSystem.authenticateTeller(username, password);
        if (teller != null) {
            tellerMenu(teller);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

  
    private void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("Welcome, " + customer.getName());
            System.out.println("1. View Accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. View Transaction History");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (getIntInput()) {
                case 1 -> viewAccounts(customer);
                case 2 -> deposit(customer);
                case 3 -> withdraw(customer);
                case 4 -> checkBalance(customer);
                case 5 -> viewTransactionHistory(customer);
                case 0 -> { return; }          // BACK
                default -> System.out.println("Invalid option.");
            }
        }
    }

   
    private void viewAccounts(Customer customer) {
        System.out.println("\n=== Your Accounts ===");
        List<Account> accounts = bankingSystem.getCustomerAccounts(customer);
        if (accounts.isEmpty()) {
            System.out.println("You don't have any accounts yet.");
            pressEnter();
            return;
        }
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, accounts.get(i));
        }
        pressEnter();
    }

    private void deposit(Customer customer) {
        List<Account> accounts = bankingSystem.getCustomerAccounts(customer);
        if (accounts.isEmpty()) {
            System.out.println("No accounts to deposit to.");
            pressEnter();
            return;
        }
        viewAccounts(customer);
        System.out.print("Enter account number (0 to cancel): ");
        int idx = getIntInput() - 1;
        if (idx == -1) return; // BACK
        if (idx < 0 || idx >= accounts.size()) {
            System.out.println("Invalid selection.");
            pressEnter();
            return;
        }
        System.out.print("Enter deposit amount: $");
        double amt = getDoubleInput();
        accounts.get(idx).deposit(amt);
        System.out.println("Deposit successful.");
        pressEnter();
    }

    private void withdraw(Customer customer) {
        List<Account> accounts = bankingSystem.getCustomerAccounts(customer);
        if (accounts.isEmpty()) {
            System.out.println("No accounts to withdraw from.");
            pressEnter();
            return;
        }
        viewAccounts(customer);
        System.out.print("Enter account number (0 to cancel): ");
        int idx = getIntInput() - 1;
        if (idx == -1) return; // BACK
        if (idx < 0 || idx >= accounts.size()) {
            System.out.println("Invalid selection.");
            pressEnter();
            return;
        }
        System.out.print("Enter withdrawal amount: $");
        double amt = getDoubleInput();
        boolean ok = accounts.get(idx).withdraw(amt);
        System.out.println(ok ? "Withdrawal successful." : "Withdrawal failed.");
        pressEnter();
    }

    private void checkBalance(Customer customer) {
        List<Account> accounts = bankingSystem.getCustomerAccounts(customer);
        if (accounts.isEmpty()) {
            System.out.println("No accounts.");
            pressEnter();
            return;
        }
        accounts.forEach(Account::checkBalance);
        pressEnter();
    }

    private void viewTransactionHistory(Customer customer) {
        List<Account> accounts = bankingSystem.getCustomerAccounts(customer);
        if (accounts.isEmpty()) {
            System.out.println("No accounts.");
            pressEnter();
            return;
        }
        viewAccounts(customer);
        System.out.print("Enter account number (0 to cancel): ");
        int idx = getIntInput() - 1;
        if (idx == -1) return; // BACK
        if (idx < 0 || idx >= accounts.size()) {
            System.out.println("Invalid selection.");
            pressEnter();
            return;
        }
        accounts.get(idx).viewTransactionHistory();
        pressEnter();
    }

    
    private void tellerMenu(BankTeller teller) {
        while (true) {
            System.out.println("\n=== Bank Teller Menu ===");
            System.out.println("Welcome, " + teller.getName());
            System.out.println("1. Create Individual Customer");
            System.out.println("2. Create Company Customer");
            System.out.println("3. Create Bank Account");
            System.out.println("4. View All Customers");
            System.out.println("5. Apply Monthly Interest");
            System.out.println("0. Back");
            System.out.print("Select option: ");
         
            switch (getIntInput()) {
                case 1 -> createIndividualCustomer();
                case 2 -> createCompanyCustomer();
                case 3 -> createBankAccount();
                case 4 -> viewAllCustomers();
                case 5 -> bankingSystem.applyMonthlyInterest();
                case 0 -> { return; }          // BACK
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void createIndividualCustomer() {
        System.out.println("\n=== Create Individual Customer ===");
        String id = "CUST" + System.currentTimeMillis();
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        System.out.print("ID Number: ");
        String idNumber = scanner.nextLine();
        System.out.print("Date of Birth (DD/MM/YYYY): ");
        String dob = scanner.nextLine();

        IndividualCustomer c = new IndividualCustomer(id, name, address, phone, email, user, pass, idNumber, dob);
        boolean ok = bankingSystem.addCustomer(c);
        System.out.println(ok ? "Customer created. ID: " + id : "Username already exists.");
        pressEnter();
    }

    private void createCompanyCustomer() {
        System.out.println("\n=== Create Company Customer ===");
        String id = "COMP" + System.currentTimeMillis();
        System.out.print("Company Name: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        System.out.print("Registration Number: ");
        String reg = scanner.nextLine();
        System.out.print("Contact Person: ");
        String contact = scanner.nextLine();
        System.out.print("Company Type: ");
        String type = scanner.nextLine();

        CompanyCustomer c = new CompanyCustomer(id, name, address, phone, email, user, pass, reg, contact, type);
        boolean ok = bankingSystem.addCustomer(c);
        System.out.println(ok ? "Company created. ID: " + id : "Username already exists.");
        pressEnter();
    }

    private void createBankAccount() {
        List<Customer> customers = bankingSystem.getCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers available.");
            pressEnter();
            return;
        }
        System.out.println("\nSelect customer (0 to cancel):");
        for (int i = 0; i < customers.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, customers.get(i));
        }
        int idx = getIntInput() - 1;
        if (idx == -1) return; // BACK
        if (idx < 0 || idx >= customers.size()) {
            System.out.println("Invalid selection.");
            pressEnter();
            return;
        }
        Customer owner = customers.get(idx);

        System.out.println("Account type:");
        System.out.println("1. Savings");
        System.out.println("2. Investment (min $500)");
        System.out.println("3. Cheque (needs employer info)");
        System.out.print("Choose (0 to cancel): ");
        int type = getIntInput();
        if (type == 0) return; // BACK
        String typeStr = switch (type) {
            case 1 -> "SAVINGS";
            case 2 -> "INVESTMENT";
            case 3 -> "CHEQUE";
            default -> "";
        };
        if (typeStr.isEmpty()) {
            System.out.println("Invalid type.");
            pressEnter();
            return;
        }

        System.out.print("Initial deposit: ");
        double init = getDoubleInput();
        System.out.print("Branch: ");
        String branch = scanner.nextLine();

        String employer = null, employerAddress = null;
        if (typeStr.equals("CHEQUE")) {
            System.out.print("Employer: ");
            employer = scanner.nextLine();
            System.out.print("Employer Address: ");
            employerAddress = scanner.nextLine();
        }

        Account acc = bankingSystem.createAccount(typeStr, owner, init, branch, employer, employerAddress);
        System.out.println(acc != null ? "Account created: " + acc.getAccountNumber()
                                       : "Account creation failed.");
        pressEnter();
    }

    private void viewAllCustomers() {
        List<Customer> list = bankingSystem.getCustomers();
        if (list.isEmpty()) {
            System.out.println("No customers.");
            pressEnter();
            return;
        }
        list.forEach(c -> {
            System.out.println(c);
            bankingSystem.getCustomerAccounts(c).forEach(a -> System.out.println("  - " + a));
        });
        pressEnter();
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Not a number, try again: ");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Not a number, try again: ");
            }
        }
    }

    private void pressEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    
    public static void main(String[] args) {
        new BankingApp().start();
    }
}