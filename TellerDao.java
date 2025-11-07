import java.sql.*;
import java.util.*;

public final class TellerDao {
    public static void save(BankTeller t) {
        String sql = "MERGE INTO bank_teller (username, password, full_name) VALUES (?,?,?)";
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getUsername());
            ps.setString(2, t.getPassword());
            ps.setString(3, t.getName());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public static BankTeller find(String username) {
        String sql = "SELECT * FROM bank_teller WHERE username=?";
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new BankTeller(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"));
            return null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public static List<BankTeller> all() {
        String sql = "SELECT * FROM bank_teller";
        try (Connection c = JdbcConnection.get();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<BankTeller> list = new ArrayList<>();
            while (rs.next()) list.add(new BankTeller(rs.getString(1), rs.getString(2), rs.getString(3)));
            return list;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}