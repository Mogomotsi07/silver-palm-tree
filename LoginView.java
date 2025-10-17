import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Objects;

public class LoginView {
    private final Stage stage;
    private final LoginController controller;

    private final TextField userFld     = new TextField();
    private final PasswordField passFld = new PasswordField();
    private final ComboBox<String> roleCbo = new ComboBox<>();

    public LoginView(Stage s, LoginController c) {
        this.stage = s;
        this.controller = c;
        roleCbo.getItems().addAll("Customer", "Bank Teller");
        roleCbo.setValue("Customer");
    }

    public void show() {
        GridPane gp = new GridPane();
        gp.setHgap(12);
        gp.setVgap(12);
        gp.setPadding(new Insets(32));
        gp.getStyleClass().add("card");

        Label roleLbl = new Label("Role");
        Label userLbl = new Label("Username");
        Label passLbl = new Label("Password");

        roleLbl.getStyleClass().add("field-label");
        userLbl.getStyleClass().add("field-label");
        passLbl.getStyleClass().add("field-label");

        gp.add(roleLbl, 0, 0);
        gp.add(roleCbo, 1, 0);
        gp.add(userLbl, 0, 1);
        gp.add(userFld, 1, 1);
        gp.add(passLbl, 0, 2);
        gp.add(passFld, 1, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);
        loginBtn.setOnAction(e -> controller.handleLogin(
                roleCbo.getValue(),
                userFld.getText().trim(),
                passFld.getText().trim()));

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> controller.exit());

        HBox btnBox = new HBox(10, loginBtn, exitBtn);
        btnBox.getStyleClass().add("button-bar");
        gp.add(btnBox, 1, 3);

        Scene scene = new Scene(gp, 480, 360);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("bankaura.css")).toExternalForm());

        stage.setTitle("Bank Aura – Login");
        stage.setScene(scene);
        stage.show();
    }

    public void clearPassword() { passFld.clear(); }
}