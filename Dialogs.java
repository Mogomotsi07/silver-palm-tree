import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;

public final class Dialogs {
    public static String askString(String title, String prompt) {
        TextInputDialog d = new TextInputDialog();
        d.setTitle(title);
        d.setHeaderText(prompt);
        return d.showAndWait().orElse(null);
    }
    public static String choice(String title, String... items) {
        ChoiceDialog<String> d = new ChoiceDialog<>(items[0], items);
        d.setTitle(title);
        d.setHeaderText("Select:");
        return d.showAndWait().orElse(null);
    }
}