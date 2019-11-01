package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Changed interestEarned() method to comply with Maxi-Savings interest new requirement.
 * Changed sumTransactions() body.
 * @author Andreas Neokleous
 */
public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    
    // Left public for testing purposes (to add a 10day old transaction )
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * Changed MAXI_SAVINGS to take into account days.
     * @return interest
     */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                Date now = DateProvider.getInstance().now();
                Date transactionDate = transactions.get(transactions.size()-1).getTransactionDate();
                long milliseconds1 = now.getTime();
                long milliseconds2 = transactionDate.getTime();
                long diff = milliseconds1 - milliseconds2;
                if ( (diff / (24 * 60 * 60 * 1000))>=10){
                    return amount * 0.05;
                }else{
                    return amount * 0.001;
                }
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }


    public int getAccountType() {
        return accountType;
    }

}
