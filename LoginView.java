import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginView {
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
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(20));
        gp.getStyleClass().add("card");

        gp.add(new Label("Role:"), 0, 0);
        gp.add(roleCbo, 1, 0);
        gp.add(new Label("Username:"), 0, 1);
        gp.add(userFld, 1, 1);
        gp.add(new Label("Password:"), 0, 2);
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
        gp.add(btnBox, 1, 3);

        Scene scene = new Scene(gp, 520, 380);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("bankaura.css")).toExternalForm());

        stage.setTitle("Bank Aura – Login");
        stage.setScene(scene);
        stage.show();
    }

    public void clearPassword() { passFld.clear(); }
}