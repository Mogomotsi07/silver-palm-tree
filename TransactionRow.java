import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TransactionRow {
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final LocalDateTime dateTime;
    private final String type;
    private final double amount;
    private final double balanceAfter;

    public TransactionRow(String raw) {
        if (raw.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} .*")) {
            String[] parts = raw.split(" \\| ", 3);
            this.dateTime = LocalDateTime.parse(parts[0], FMT);
            String[] left = parts[1].split(": ");
            this.type = left[0];
            this.amount = Double.parseDouble(left[1]);
            this.balanceAfter = Double.parseDouble(parts[2].replace("Balance=", ""));
        } else {
            this.dateTime = LocalDateTime.now();
            String[] parts = raw.split(" \\| ");
            String[] left = parts[0].split(": ");
            this.type = left[0];
            this.amount = Double.parseDouble(left[1]);
            this.balanceAfter = Double.parseDouble(parts[1].replace("Balance=", ""));
        }
    }

    public String getDateTime()     { return dateTime.format(FMT); }
    public String getType()         { return type; }
    public double getAmount()       { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
}