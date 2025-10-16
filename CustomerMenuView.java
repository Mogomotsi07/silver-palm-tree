import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public final class CustomerMenuView {

    private final Stage stage;
    private final CustomerController ctrl;
    private final ListView<String> accountLst = new ListView<>();
    private final Label welcomeLbl = new Label();

    private final VBox historyBox = new VBox(5);
    private final TableView<TransactionRow> historyTable = new TableView<>();
    private boolean historyVisible = false;

    public CustomerMenuView(Stage s, CustomerController c) {
        this.stage = s;
        this.ctrl = c;
        buildHistoryTable();
    }

    public void show() {
        VBox vb = new VBox(18);
        vb.setPadding(new Insets(20));
        vb.getStyleClass().add("card");

        welcomeLbl.getStyleClass().add("header");
        vb.getChildren().add(welcomeLbl);

        Label accLbl = new Label("Your Accounts:");
        vb.getChildren().addAll(accLbl, accountLst);

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button historyBtn = new Button("Transaction History");
        Button logoutBtn = new Button("Logout");

        depositBtn.setOnAction(e -> ctrl.deposit(selectedIndex()));
        withdrawBtn.setOnAction(e -> ctrl.withdraw(selectedIndex()));
        historyBtn.setOnAction(e -> toggleHistory());
        logoutBtn.setOnAction(e -> ctrl.logout());

        vb.getChildren().addAll(depositBtn, withdrawBtn, historyBtn, logoutBtn);

        historyBox.setVisible(false);
        historyBox.setManaged(false);
        historyBox.getChildren().addAll(new Label("Transaction History"), historyTable);
        vb.getChildren().add(historyBox);

        Scene scene = new Scene(vb, 620, 600);
        scene.getStylesheets().add(
                getClass().getResource("bankaura.css").toExternalForm());

        stage.setTitle("Bank Aura – Customer");
        stage.setScene(scene);
    }

    public void setWelcome(String name) { welcomeLbl.setText("Welcome, " + name); }

    public void refreshAccounts(List<String> list) {
        accountLst.getItems().setAll(list);
    }

    public void refreshHistory(List<String> rawHistory) {
        List<TransactionRow> rows = rawHistory.stream()
                .map(TransactionRow::new)
                .toList();
        historyTable.setItems(FXCollections.observableList(rows));
    }

    public void toggleHistory() {
        historyVisible = !historyVisible;
        historyBox.setVisible(historyVisible);
        historyBox.setManaged(historyVisible);
    }

    private int selectedIndex() {
        return accountLst.getSelectionModel().getSelectedIndex();
    }

    private void buildHistoryTable() {
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        historyTable.setPrefHeight(280);

        TableColumn<TransactionRow, String> dateCol = new TableColumn<>("Date-Time");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        TableColumn<TransactionRow, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<TransactionRow, Double> amtCol = new TableColumn<>("Amount");
        amtCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<TransactionRow, Double> balCol = new TableColumn<>("Balance");
        balCol.setCellValueFactory(new PropertyValueFactory<>("balanceAfter"));

        amtCol.setCellFactory(tc -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : String.format("%.2f", item));
            }
        });
        balCol.setCellFactory(tc -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : String.format("%.2f", item));
            }
        });

        historyTable.getColumns().addAll(dateCol, typeCol, amtCol, balCol);
    }
}