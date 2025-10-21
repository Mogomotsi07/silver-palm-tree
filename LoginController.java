import javafx.application.Platform;
import javafx.stage.Stage;

public final class LoginController {
    private final Stage stage;
    private final BankingSystem system;

    public LoginController(Stage stage, BankingSystem system) {
        this.stage = stage;
        this.system = system;
    }

    public void handleLogin(String role, String user, String pass) {
        if (user.isEmpty() || pass.isEmpty()) {
            AlertUtil.error("Username / password required.");
            return;
        }
        if ("Customer".equals(role)) {
            Customer c = system.authenticateCustomer(user, pass);
            if (c == null) {
                AlertUtil.error("Invalid customer credentials.");
                return;
            }
            new CustomerController(stage, system, c).start();
        } else {
            BankTeller t = system.authenticateTeller(user, pass);
            if (t == null) {
                AlertUtil.error("Invalid teller credentials.");
                return;
            }
            new TellerController(stage, system, t).start();
        }
    }

    public void exit() {
        DatabaseManager.saveData(system);
        Platform.exit();
    }
}