import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public final class AlertUtil {

    private AlertUtil() {}

    public static void error(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public static void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public static String askString(String title, String prompt) {
        TextInputDialog d = new TextInputDialog();
        d.setTitle(title);
        d.setHeaderText(prompt);
        return d.showAndWait().orElse(null);
    }

    /*  NEW – drop-down choice  */
    public static String choice(String title, String... items) {
        ChoiceDialog<String> d = new ChoiceDialog<>(items[0], items);
        d.setTitle(title);
        d.setHeaderText("Select:");
        return d.showAndWait().orElse(null);
    }
}