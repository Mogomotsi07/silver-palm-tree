import java.sql.*;

public final class JdbcConnection {
    private static final String URL = "jdbc:h2:./bankaura";

    static {
        try { Class.forName("org.h2.Driver"); }
        catch (ClassNotFoundException e) { throw new ExceptionInInitializerError(e); }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, "sa", "");
    }

    public static void createSchema() {
        String ddl = """
            CREATE TABLE IF NOT EXISTS bank_teller (
                username     VARCHAR(30)  PRIMARY KEY,
                password     VARCHAR(30),
                full_name    VARCHAR(60)
            );

            CREATE TABLE IF NOT EXISTS customer (
                id            VARCHAR(30) PRIMARY KEY,
                name          VARCHAR(60),
                address       VARCHAR(120),
                phone         VARCHAR(30),
                email         VARCHAR(60),
                username      VARCHAR(30) UNIQUE,
                password      VARCHAR(30),
                dtype         VARCHAR(1)   -- 'I' = Individual, 'C' = Company
            );

            CREATE TABLE IF NOT EXISTS company_customer (
                id                    VARCHAR(30) PRIMARY KEY,
                reg_number            VARCHAR(40),
                business_type         VARCHAR(40),
                incorporation_date    VARCHAR(20),
                business_phone        VARCHAR(30),
                business_email        VARCHAR(60),
                signatory_name        VARCHAR(60),
                signatory_id          VARCHAR(30),
                signatory_role        VARCHAR(40),
                /* duplicate core columns so DAO can read them directly */
                name                  VARCHAR(60),
                address               VARCHAR(120),
                phone                 VARCHAR(30),
                email                 VARCHAR(60),
                username              VARCHAR(30),
                password              VARCHAR(30),
                FOREIGN KEY (id) REFERENCES customer(id) ON DELETE CASCADE
            );

            CREATE TABLE IF NOT EXISTS account (
                account_number  VARCHAR(20) PRIMARY KEY,
                owner_id        VARCHAR(30),
                balance         DOUBLE,
                type            VARCHAR(20),
                employer        VARCHAR(60),
                employer_addr   VARCHAR(120),
                FOREIGN KEY (owner_id) REFERENCES customer(id) ON DELETE CASCADE
            );

            CREATE TABLE IF NOT EXISTS transaction (
                id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                account_number  VARCHAR(20),
                tx_time         TIMESTAMP,
                tx_type         VARCHAR(20),
                amount          DOUBLE,
                balance_after   DOUBLE,
                FOREIGN KEY (account_number) REFERENCES account(account_number) ON DELETE CASCADE
            );
            """;
        try (Connection c = get(); Statement s = c.createStatement()) {
            s.execute(ddl);
        } catch (SQLException e) {
            throw new RuntimeException("DDL failed", e);
        }
    }

    private JdbcConnection() {}
}