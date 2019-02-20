 
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    public List<Transaction> withdrawTransactions;
    public List<Transaction> depositTransactions;
    
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            depositTransactions.add(new Transaction(amount));
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
        withdrawTransactions.add(new Transaction(-amount));
    }
}

    public void transfer(double amount) {
        if(amount <=0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        
        else{
            deposit(amount);
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                Date today = new Date();
                
                for (Transaction t: withdrawTransactions){
                    long diff = today.getTime() - t.getTransactionDate().getTime();
                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                    if(diffDays >= 10)
                        return amount * 0.02;
                    
                    else
                        return amount * 0.1;
                }
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
