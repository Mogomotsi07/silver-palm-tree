import java.util.Scanner;
import java.util.List;

// Main class to run the banking system
public class BankingSystem {
    private static Bank bank = new Bank();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Welcome to the BANKAURA!");
        
        // Login first
        if (!login()) {
            System.out.println("Login failed. Exiting system.");
            return;
        }
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    openAccount();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    viewAccounts();
                    break;
                case 6:
                    viewAllCustomers();
                    break;
                case 7:
                    viewCustomerDetails();
                    break;
                case 8:
                    addMonthlyInterest();
                    break;
                case 9:
                    changePassword();
                    break;
                case 10:
                    bank.logout();
                    System.out.println("Logged out successfully.");
                    if (!login()) {
                        System.out.println("Login failed. Exiting system.");
                        return;
                    }
                    break;
                case 11:
                    System.out.println("Thank you for using the BANKAURA!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static boolean login() {
        System.out.println("\n--- Login ---");
        String username = getStringInput("Username: ");
        String password = getStringInput("Password: ");
        
        User user = bank.login(username, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getFirstName() + " " + user.getLastName());
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }
    
    private static void changePassword() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to change password.");
            return;
        }
        
        System.out.println("\n--- Change Password ---");
        String oldPassword = getStringInput("Current password: ");
        String newPassword = getStringInput("New password: ");
        String confirmPassword = getStringInput("Confirm new password: ");
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("New passwords do not match.");
            return;
        }
        
        System.out.println("Password change functionality would be implemented here.");
        System.out.println("For security reasons, this would connect to a secure user database.");
    }
    
    private static void displayMenu() {
        System.out.println("\n == MENU ==");
        System.out.println("Logged in as: " + (bank.isLoggedIn() ? bank.getCurrentUser() : "No one"));
        System.out.println("1. Create New Customer");
        System.out.println("2. Open Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. View Accounts for a Customer");
        System.out.println("6. View All Customers");
        System.out.println("7. View Customer Details");
        System.out.println("8. Add Monthly Interest");
        System.out.println("9. Change Password");
        System.out.println("10. Logout");
        System.out.println("11. Exit");
        System.out.println("===============================");
    }
    
    private static void createCustomer() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        
        System.out.println("\n--- Create New Customer ---");
        String firstName = getStringInput("Enter first name: ");
        String surname = getStringInput("Enter surname: ");
        String address = getStringInput("Enter address: ");
        
        Customer customer = bank.createCustomer(firstName, surname, address);
        System.out.println("Customer created successfully: " + customer);
    }
    
    private static void openAccount() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        
        System.out.println("\n--- Open Account ---");
        Customer customer = selectCustomer();
        if (customer == null) return;
        
        System.out.println("Select account type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Investment Account");
        System.out.println("3. Cheque Account");
        int type = getIntInput("Enter choice: ");
        
        String branch = getStringInput("Enter branch: ");
        double initialDeposit = getDoubleInput("Enter initial deposit: ");
        
        Account account = null;
        switch (type) {
            case 1:
                account = bank.openSavingsAccount(customer, initialDeposit, branch);
                break;
            case 2:
                account = bank.openInvestmentAccount(customer, initialDeposit, branch);
                break;
            case 3:
                String employer = getStringInput("Enter employer: ");
                String companyAddress = getStringInput("Enter company address: ");
                account = bank.openChequeAccount(customer, initialDeposit, branch, employer, companyAddress);
                break;
            default:
                System.out.println("Invalid account type.");
                return;
        }
        
        if (account != null) {
            System.out.println("Account opened successfully: " + account);
        }
    }
    
    private static void deposit() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        
        System.out.println("\n--- Deposit Funds ---");
        Account account = selectAccount();
        if (account == null) return;
        
        double amount = getDoubleInput("Enter deposit amount: ");
        if (bank.deposit(account, amount)) {
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Deposit failed. Please check the amount.");
        }
    }
    
    private static void withdraw() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        
        System.out.println("\n--- Withdraw Funds ---");
        Account account = selectAccount();
        if (account == null) return;
        
        double amount = getDoubleInput("Enter withdrawal amount: ");
        if (bank.withdraw(account, amount)) {
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Withdrawal failed. Please check the amount and account type.");
        }
    }
    
    private static void viewAccounts() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        
        System.out.println("\n--- View Accounts ---");
        Customer customer = selectCustomer();
        if (customer == null) return;
        
        bank.displayCustomerDetails(bank.getCustomers().indexOf(customer));
    }
    
    private static void viewAllCustomers() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        
        bank.displayAllCustomers();
    }
    
    private static void viewCustomerDetails() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        
        System.out.println("\n--- View Customer Details ---");
        bank.displayAllCustomers();
        
        if (bank.getCustomers().isEmpty()) return;
        
        int choice = getIntInput("Select customer to view details: ") - 1;
        bank.displayCustomerDetails(choice);
    }
    
    private static void addMonthlyInterest() {
        if (!bank.isLoggedIn()) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        
        // Check if user has permission (manager or admin)
        User currentUser = bank.getCurrentUser();
        if (!currentUser.getRole().equals("manager") && !currentUser.getRole().equals("admin")) {
            System.out.println("Only managers and administrators can add monthly interest.");
            return;
        }
        
        bank.addMonthlyInterest();
        System.out.println("Monthly interest added to all applicable accounts.");
    }
    
    private static Customer selectCustomer() {
        List<Customer> customers = bank.getCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found. Please create a customer first.");
            return null;
        }
        
        bank.displayAllCustomers();
        
        int choice = getIntInput("Enter customer number: ") - 1;
        if (choice < 0 || choice >= customers.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        
        return customers.get(choice);
    }
    
    private static Account selectAccount() {
        Customer customer = selectCustomer();
        if (customer == null) return null;
        
        List<Account> accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this customer.");
            return null;
        }
        
        System.out.println("Select an account:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i));
        }
        
        int choice = getIntInput("Enter account number: ") - 1;
        if (choice < 0 || choice >= accounts.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        
        return accounts.get(choice);
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}