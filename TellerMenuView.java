import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TellerMenuView {
    private final Stage stage;
    private final TellerController ctrl;
    private final ListView<String> customerLst = new ListView<>();

    public TellerMenuView(Stage s, TellerController c) {
        this.stage = s;
        this.ctrl = c;
    }

    public void show() {
        VBox vb = new VBox(18);
        vb.setPadding(new Insets(20));
        vb.getStyleClass().add("card");

        vb.getChildren().add(new Label("Customers:"));
        vb.getChildren().add(customerLst);

        Button addIndBtn   = new Button("Add Individual Customer");
        Button addCompBtn  = new Button("Add Company Customer");
        Button addAccBtn   = new Button("Create Account for Selected");
        Button viewBtn     = new Button("View All Customers & Accounts");
        Button interestBtn = new Button("Apply Monthly Interest");
        Button logoutBtn   = new Button("Logout");

        addIndBtn.setOnAction(e -> ctrl.addIndividual());
        addCompBtn.setOnAction(e -> ctrl.addCompany());
        addAccBtn.setOnAction(e -> ctrl.createAccount(selectedIndex()));
        viewBtn.setOnAction(e -> ctrl.viewAllCustomers());
        interestBtn.setOnAction(e -> ctrl.applyInterest());
        logoutBtn.setOnAction(e -> ctrl.logout());

        vb.getChildren().addAll(addIndBtn, addCompBtn, addAccBtn, viewBtn, interestBtn, logoutBtn);

        Scene scene = new Scene(vb, 580, 500);
        scene.getStylesheets().add(getClass().getResource("bankaura.css").toExternalForm());

        stage.setTitle("BankAura – Teller");
        stage.setScene(scene);
    }

    public void refreshCustomers(List<String> list) {
        customerLst.getItems().setAll(list);
    }

    private int selectedIndex() {
        return customerLst.getSelectionModel().getSelectedIndex();
    }
}