import java.text.SimpleDateFormat;

/**
 *
 * @author batuhan yilmaz
 */

public class Transaction {
    private final double amount;
    private final SimpleDateFormat transactionDate;
    private final String type;
    
    public Transaction(double amount, String type) {
        this.amount = amount;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.transactionDate = formatter;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    
    
}
