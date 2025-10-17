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
    public void stop() {
        DatabaseManager.saveData(BankingSystemHolder.INSTANCE);
    }

    private static class BankingSystemHolder {
        static final BankingSystem INSTANCE = DatabaseManager.loadData();
    }

    public static void main(String[] args) {
        launch(args);

    }
}