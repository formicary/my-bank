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
//          case SUPER_SAVINGS:
//              if (amount <= 4000)
//                  return 20;
            case MAXI_SAVINGS:
                if (recentWithdraw())
                    return amount * 0.001;
                return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    private boolean recentWithdraw(){
        Transaction t;
        for (int i = transactions.size()-1; i >= 0; i--){
            t = transactions.get(i);

            if (t.getTransactionAge() > 10){
                i = 0;
            } else if (t.amount < 0){
                return true;
            }
        }
        return false;
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
