import java.io.*;
import java.nio.file.*;

public final class DatabaseManager {
    private static final Path FILE = Paths.get("bankaura.dat");

    public static BankingSystem loadData() {
        if (Files.notExists(FILE)) {
            BankingSystem system = new BankingSystem();
            system.addTeller(new BankTeller("Spojah", "073616613", "Administrator"));
            return system;
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(FILE))) {
            return (BankingSystem) ois.readObject();
        } catch (Exception e) {
            System.out.println("Corrupt save file – starting fresh.");
            return new BankingSystem();
        }
    }

    public static void saveData(BankingSystem bs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(FILE))) {
            oos.writeObject(bs);
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }
}