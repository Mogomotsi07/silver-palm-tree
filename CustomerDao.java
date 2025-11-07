import java.sql.*;
import java.util.*;

public final class CustomerDao {

    /* ---------- SAVE ---------- */
    public static void save(Customer cust) {
        String sql = "MERGE INTO customer (id,name,address,phone,email,username,password,dtype) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cust.getId());
            ps.setString(2, cust.getName());
            ps.setString(3, cust.getAddress());
            ps.setString(4, cust.getPhone());
            ps.setString(5, cust.getEmail());
            ps.setString(6, cust.getUsername());
            ps.setString(7, cust.getPassword());
            ps.setString(8, cust instanceof IndividualCustomer ? "I" : "C");
            ps.executeUpdate();

            if (cust instanceof CompanyCustomer cc) saveCompany(cc);
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private static void saveCompany(CompanyCustomer cc) throws SQLException {
        String sql = """
            MERGE INTO company_customer
            (id,reg_number,business_type,incorporation_date,business_phone,business_email,
             signatory_name,signatory_id,signatory_role,name,address,phone,email,username,password)
            VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """;
        try (Connection c = JdbcConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cc.getId());
            ps.setString(2, cc.getRegNumber());
            ps.setString(3, cc.getBusinessType());
            ps.setString(4, cc.getIncorporationDate());
            ps.setString(5, cc.getBusinessPhone());
            ps.setString(6, cc.getBusinessEmail());
            ps.setString(7, cc.getSignatoryName());
            ps.setString(8, cc.getSignatoryId());
            ps.setString(9, cc.getSignatoryRole());
            /* duplicate core columns */
            ps.setString(10, cc.getName());
            ps.setString(11, cc.getAddress());
            ps.setString(12, cc.getPhone());
            ps.setString(13, cc.getEmail());
            ps.setString(14, cc.getUsername());
            ps.setString(15, cc.getPassword());
            ps.executeUpdate();
        }
    }

    /* ---------- LOAD ---------- */
    public static List<Customer> all() {
        String sql = "SELECT * FROM customer";
        try (Connection c = JdbcConnection.get();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            List<Customer> list = new ArrayList<>();
            while (rs.next()) {
                String dtype = rs.getString("dtype");
                String id = rs.getString("id");
                if ("I".equals(dtype)) list.add(buildIndividual(rs));
                else list.add(buildCompany(id, c));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private static IndividualCustomer buildIndividual(ResultSet rs) throws SQLException {
        return new IndividualCustomer(
                rs.getString("id"), rs.getString("name"), rs.getString("address"),
                rs.getString("phone"), rs.getString("email"),
                rs.getString("username"), rs.getString("password"),
                "", "", "", "", "", "");   // extra cols not stored yet – defaults
    }

    private static CompanyCustomer buildCompany(String id, Connection c) throws SQLException {
        String sql = "SELECT * FROM company_customer WHERE id=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new CompanyCustomer(
                    id, rs.getString("name"), rs.getString("address"),
                    rs.getString("phone"), rs.getString("email"),
                    rs.getString("username"), rs.getString("password"),
                    rs.getString("reg_number"), rs.getString("business_type"),
                    rs.getString("incorporation_date"),
                    rs.getString("business_phone"), rs.getString("business_email"),
                    rs.getString("signatory_name"), rs.getString("signatory_id"),
                    rs.getString("signatory_role"));
        }
    }
}