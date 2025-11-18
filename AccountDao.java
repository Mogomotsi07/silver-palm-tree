import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class AccountDao {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /* ========== SAVE ========== */
    public static void save(Account acc, String ownerId) {
        String sql = "MERGE INTO account(account_number,owner_id,balance,type,employer,employer_addr) VALUES (?,?,?,?,?,?)";
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, acc.getAccountNumber());
            ps.setString(2, ownerId);
            ps.setBigDecimal(3, BigDecimal.valueOf(acc.getBalance()));
            String t = acc instanceof SavingsAccount ? "SAVINGS" :
                    acc instanceof InvestmentAccount ? "INVESTMENT" : "CHEQUE";
            ps.setString(4, t);
            if (acc instanceof ChequeAccount ch) {
                ps.setString(5, ch.getEmployer());
                ps.setString(6, ch.getEmployerAddress());
            } else {
                ps.setString(5, null);
                ps.setString(6, null);
            }
            ps.executeUpdate();
            saveHistory(acc);
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /* ========== LOAD ========== */
    public static List<Account> findByOwner(String ownerId) {
        String sql = "SELECT * FROM account WHERE owner_id=?";
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ownerId);
            ResultSet rs = ps.executeQuery();
            List<Account> list = new ArrayList<>();
            while (rs.next()) list.add(rebuild(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /* ========== REBUILD (NO REFLECTION) ========== */
    private static Account rebuild(ResultSet rs) throws SQLException {
        String num = rs.getString("account_number");
        double bal = rs.getDouble("balance");
        String type = rs.getString("type");

        Account acc = switch (type) {
            case "SAVINGS"    -> new SavingsAccount();
            case "INVESTMENT" -> new InvestmentAccount();
            default           -> new ChequeAccount(rs.getString("employer"),
                    rs.getString("employer_addr"));
        };
        acc.deposit(bal);              // sets balance & history
        acc.setAccountNumber(num);     // NEW setter instead of reflection
        loadHistory(acc);              // refill transaction list
        return acc;
    }

    /* ========== TRANSACTION HISTORY ========== */
    private static void saveHistory(Account acc) {
        String del = "DELETE FROM transaction WHERE account_number=?";
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(del)) {
            ps.setString(1, acc.getAccountNumber());
            ps.executeUpdate();
        } catch (SQLException ignore) {}

        String ins = "INSERT INTO transaction(account_number,tx_time,tx_type,amount,balance_after) VALUES (?,?,?,?,?)";
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(ins)) {
            for (String line : acc.getHistory()) {
                String[] p = line.split(" \\| ");
                LocalDateTime dt = LocalDateTime.parse(p[0], FMT);
                String[] left = p[1].split(": ");
                double amt = Double.parseDouble(left[1]);
                double baf = Double.parseDouble(p[2].replace("Balance=", ""));
                ps.setString(1, acc.getAccountNumber());
                ps.setTimestamp(2, Timestamp.valueOf(dt));
                ps.setString(3, left[0]);
                ps.setBigDecimal(4, BigDecimal.valueOf(amt));
                ps.setBigDecimal(5, BigDecimal.valueOf(baf));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private static void loadHistory(Account acc) {
        String sql = "SELECT * FROM transaction WHERE account_number=? ORDER BY tx_time";
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, acc.getAccountNumber());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String l = String.format("%s | %s: %.2f | Balance=%.2f",
                        rs.getTimestamp("tx_time").toLocalDateTime().format(FMT),
                        rs.getString("tx_type"),
                        rs.getBigDecimal("amount").doubleValue(),
                        rs.getBigDecimal("balance_after").doubleValue());
                acc.getHistory().add(l);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}