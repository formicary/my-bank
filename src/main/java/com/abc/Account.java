package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
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

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                for(Transaction t: transactions){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -10);
                    //Check whether transactions are in last 10 days
                    if(t.getTransactionDate().after(cal.getTime())){
                        if(t.getAmount() < 0){
                            return amount * 0.001;
                        }
                    }
                }
                return amount * 0.05;
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

    public String getAccountName(){
        switch (accountType){
            case SAVINGS:
                return "Savings Account";
            case MAXI_SAVINGS:
                return "Maxi-Savings Account";
            default:
                return "Checking Account";
        }
    }

}
