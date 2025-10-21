import javafx.application.Application;
import javafx.stage.Stage;

public class BankAuraApp extends Application {
    @Override
    public void start(Stage stage) {
        BankingSystem system = DatabaseManager.loadData();
        new LoginView(stage, new LoginController(stage, system)).show();
    }
    public static void main(String[] args) { launch(args); }
}