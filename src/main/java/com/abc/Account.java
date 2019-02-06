package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private double money;
    private double overdraft;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        money = 0;
        overdraft = 100.00;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            //Calculating money over course of x days with y interest rate
            money = newAmount(lastTransaction(), money);
            money += amount;
        }
    }

    //Added overdraft case.
    public void withdraw(double amount) {
        if (money - amount < -overdraft){
            throw new IllegalStateException("not enough money in account to withdraw");
        } else {
            if (amount <= 0) {
                throw new IllegalArgumentException("amount must be greater than zero");
            } else {
                transactions.add(new Transaction(-amount));
                money = newAmount(lastTransaction(), money);
                money -= amount;
            }
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

    //Accrued Interest.
    public double newAmount(long days, double amount){
        //Daily interest gain for  annual 0.1%
        double interestA = 1 + (0.001/365);
        //Daily interest gain for  annual 0.2%
        double interestB = 1 + (0.002/365);
        //Daily interest gain for  annual 5%
        double interestC = 1 + (0.05/365);
        if (days == 0){
            return amount;
        } else {
            switch(accountType){
                case SAVINGS:
                    if (amount <= 1000)
                        return newAmount(days - 1,amount * interestA);
                    else
                        return newAmount(days - 1, (1000 * interestA) + ((amount - 1000) * interestB));
                case MAXI_SAVINGS:
                    if (recentWithdraw())
                        return newAmount(days - 1,amount * interestA);
                    return newAmount(days - 1,amount * interestC);
                default:
                    return newAmount(days - 1,amount * interestA);
            }
        }
    }

    //Returns how many days ago the most recent transaction is.
    private long lastTransaction(){
        Transaction t = transactions.get(transactions.size()-1);
        return t.getTransactionAge();
    }

    //Returns true if there has been a withdrawal in the past 10 days
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
