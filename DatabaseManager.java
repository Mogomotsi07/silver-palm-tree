import java.nio.file.*;
import java.io.*;

public final class DatabaseManager {

    private static final Path FILE = Paths.get("bankaura.dat"); // fallback file kept for first-run import

    public static BankingSystem loadData() {
        JdbcConnection.createSchema();

        if (Files.exists(FILE)) {
            BankingSystem old = loadSerialized();
            if (old != null) {
                importOldSystem(old);
                try { Files.move(FILE, Paths.get("bankaura.dat.bak")); } catch (IOException ignore) {}
            }
        }

        BankingSystem system = new BankingSystem();

        system.addTeller(new BankTeller("Spojah", "073616613", "Administrator"));
        system.addTeller(new BankTeller("alice", "alice123", "Branch Manager"));

        TellerDao.all().forEach(system::addTeller);
        CustomerDao.all().forEach(c -> {
            system.addCustomer(c);
            AccountDao.findByOwner(c.getId()).forEach(a -> system.getCustomerAccounts(c).add(a));
        });

        return system;
    }

    public static void saveData(BankingSystem sys) {
        /* persist tellers + customers + accounts */
        sys.getTellers().forEach(TellerDao::save);
        sys.getCustomers().forEach(c -> {
            CustomerDao.save(c);
            sys.getCustomerAccounts(c).forEach(a -> AccountDao.save(a, c.getId()));
        });
    }

    /* ---------- helpers ---------- */
    private static BankingSystem loadSerialized() {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(FILE))) {
            return (BankingSystem) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    private static void importOldSystem(BankingSystem old) {
        saveData(old);
    }
}