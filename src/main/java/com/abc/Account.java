
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * This is my Attempt at the task, I basically attempted the project from scratch and made some changed i.e finding accounts by uid
 * these changes were made to increase flexibility/code for the future, which is also why I made some design changes to increase cohesion.
 * I did not implement the last two additional features as I wasnt exactly sure the way it was asked to implement (didnt fully understand the description).
 */

/**
 *
 * @author batuhan yilmaz
 */

public class Account {
    private final AccountType accountType;
    private List<Transaction> transactions;
    private double balance = 0;
    private final String uniqueID ; 
    
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
        this.uniqueID = UUID.randomUUID().toString();
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount,"Deposit"));
            balance = balance + amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount,"Withdrawal"));
            balance = balance - amount;
        }
    }
    
    public double calculateInterest(){
        switch(accountType){
            case SAVINGS:
                if (balance <= 1000){
                    return balance += balance * 0.001;
                }
                else{
                    return balance += 1 + (balance-1000) * 0.002;
                }
            case MAXI_SAVINGS:
                if (balance <= 1000){
                    return balance += balance * 0.02;
                }
                if (balance <= 2000){
                    return balance += 20 + (balance-1000) * 0.05;
                }
                return balance += 70 + (balance-2000) * 0.1;
            default:
                return balance += balance * 0.001;
        }
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return balance;
    }

    public String getUniqueID() {
        return uniqueID;
    }
    
    
    
    
    
    
}
