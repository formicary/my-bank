package com.abc;

import java.util.ArrayList;
import java.util.List;

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
            transactions.add(new Transaction(amount, "Deposit"));
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if(this.sumTransactions() - amount > 0) {//Prevents accounts being overdrawn since this is not a requested feature
            transactions.add(new Transaction(-amount, "Withdrawal"));
            return true;
        }
        return false;
    }

    public double dailyInterestEarned() {
        // some of the rates are so low that deposits don't earn interest daily. I haven'd come up with a solution for this yet.
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return (amount * 0.001)/DateProvider.getInstance().daysThisYear();
                else
                    return (1+ ((amount-1000) * 0.002))/DateProvider.getInstance().daysThisYear();
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return (amount * 0.02)/DateProvider.getInstance().daysThisYear();
                if (amount <= 2000)
                    return (20 + ((amount-1000) * 0.05))/DateProvider.getInstance().daysThisYear();
                return (70 + ((amount-2000) * 0.1))/DateProvider.getInstance().daysThisYear();
            case CHECKING:
                return (amount * 0.001)/DateProvider.getInstance().daysThisYear();
            default:
                return 0; //Could not id account type so made no changes.
        }
    }

    public void compoundInterest(){
        transactions.add(new Transaction(dailyInterestEarned(), "Interest Payment"));
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
