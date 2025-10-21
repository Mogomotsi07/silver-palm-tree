import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class LoginView {
    private final Stage stage;
    private final LoginController controller;

    private final TextField userFld   = new TextField();
    private final PasswordField passFld = new PasswordField();
    private final ComboBox<String> roleCbo = new ComboBox<>();

    public LoginView(Stage s, LoginController c) {
        this.stage = s;
        this.controller = c;
        roleCbo.getItems().addAll("Customer", "Bank Teller");
        roleCbo.setValue("Customer");
    }

    public void show() {
        VBox root = new VBox(25);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("card");

        /*  title  */
        Text title = new Text("BankAura");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setFill(Color.web("#00695C"));

        /*  role  */
        Label roleLbl = new Label("I am a:");
        roleLbl.setStyle("-fx-text-fill: #212121; -fx-font-size: 14px;");
        roleCbo.setPrefWidth(250);

        /*  username  */
        Label userLbl = new Label("Username");
        userLbl.setStyle("-fx-text-fill: #212121; -fx-font-size: 14px;");
        userFld.setPrefWidth(250);
        userFld.textProperty().addListener((o, oldV, newV) -> {
            if (newV.length() < 3) userFld.setStyle("-fx-border-color: #E53935;");
            else userFld.setStyle("-fx-border-color: #43A047;");
        });

        /*  password  */
        Label passLbl = new Label("Password");
        passLbl.setStyle("-fx-text-fill: #212121; -fx-font-size: 14px;");
        passFld.setPrefWidth(250);
        passFld.textProperty().addListener((o, oldV, newV) -> {
            int len = newV.length();
            if (len < 6)      passFld.setStyle("-fx-border-color: #E53935;");
            else if (len < 10) passFld.setStyle("-fx-border-color: #FFD54F;");
            else               passFld.setStyle("-fx-border-color: #43A047;");
        });

        /*  buttons  */
        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);
        loginBtn.setOnAction(e -> controller.handleLogin(
                roleCbo.getValue(),
                userFld.getText().trim(),
                passFld.getText().trim()));

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> controller.exit());

        HBox btnBox = new HBox(15, loginBtn, exitBtn);
        btnBox.setAlignment(Pos.CENTER);

        /*  layout  */
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.add(roleLbl, 0, 0);
        grid.add(roleCbo, 1, 0);
        grid.add(userLbl, 0, 1);
        grid.add(userFld, 1, 1);
        grid.add(passLbl, 0, 2);
        grid.add(passFld, 1, 2);

        root.getChildren().addAll(title, grid, btnBox);

        Scene scene = new Scene(root, 420, 380);
        scene.getStylesheets().add(getClass().getResource("bankaura.css").toExternalForm());

        stage.setTitle("BankAura – Login");
        stage.setScene(scene);
        stage.show();
    }

    public void clearPassword() { passFld.clear(); }
}