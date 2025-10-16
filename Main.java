import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        BankingSystem system = DatabaseManager.loadData();
        LoginView view = new LoginView(primaryStage, new LoginController(primaryStage, system));
        view.show();
    }

    @Override
    public void stop() {           // save on exit
        DatabaseManager.saveData(BankingSystemHolder.INSTANCE);
    }

    /* simple singleton so stop() can reach the system */
    private static class BankingSystemHolder {
        static final BankingSystem INSTANCE = DatabaseManager.loadData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}