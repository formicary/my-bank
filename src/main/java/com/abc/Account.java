package com.abc;

import java.util.*;

public class Account {

    //could use enum but in my opinion it's not worth it here
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public Transaction deposit(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Transaction t = new Transaction(amount, Transaction.DEPOSIT);
            transactions.add(t);
            return t;
        }
    }

    public Transaction withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if(amount <= sumTransactions()) {
                Transaction t = new Transaction(-amount, Transaction.WITHDRAWAL);
                transactions.add(t);
                return t;
            } else {
                throw new IllegalStateException("amount must be less than account total");
            }
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount + calculateAccruedInterest(0.001);
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                Transaction t = getLatestWithdrawal();
                if(t == null || t.transactionDate.before(DateProvider.getInstance().daysAgo(10)))
                {
                    return amount * 0.05;
                }
                else
                {
                    return amount *0.001;
                }
            default:
                return amount * 0.001;
        }
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

    public String statementForAccount() {
        String s = "";

        //Translate to pretty account type
        switch(this.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : this.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + Format.toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + Format.toDollars(total);
        return s;
    }

    private double calculateAccruedInterest(double rate)
    {
        if(accountType == MAXI_SAVINGS) {
            //this requires checking every transaction for withdrawals and applying an interest cooldown for 10 days
            //after withdrawal. A simple formula won't work, I think it's out of the scope of this exercise.
            return 0.0;
        }
        else{
            if(transactions.size() > 0) {
                double principal = sumTransactions();
                DateProvider dp = DateProvider.getInstance();
                int daysSinceOpening = dp.daysBetween(transactions.get(0).transactionDate, dp.now());
                double interest = Math.pow((1 + (rate/365)), daysSinceOpening);
                interest = (interest*principal) - principal;
                return interest;
            }
            else
            {
                return 0.0;
            }
        }
    }

    private Transaction getLatestWithdrawal() {
        ListIterator<Transaction> it = transactions.listIterator(transactions.size());
        Transaction prev;

        while(it.hasPrevious())
        {
            prev = it.previous();
            if(prev.type == Transaction.WITHDRAWAL)
            {
                return prev;
            }
        }
        return null;
    }

}
