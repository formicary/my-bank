package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    public static final int DAYS_IN_A_YEAR = 365;

    private Customer owner;
    private final int accountType;
    public List<Transaction> transactions;

    public Account(Customer owner, int accountType) {
        this.accountType = accountType;
        this.owner = owner;
        this.transactions = Collections.synchronizedList(new ArrayList<Transaction>());
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new CreditTransaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new DebitTransaction(amount));
        }
    }

    abstract public double interestEarned(double amount);

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.amount;
        }
        return amount;
    }

    public Customer getOwner() {
        return owner;
    }

    public int getOwnerID() {
        return owner.getId();
    }

    public int getAccountType() {
        return accountType;
    }

    //Acrue interest daily to calculate total amount in account
    public double getTotal() {

        if (transactions.isEmpty()) {
            return 0.0;
        }

        //Order transactions from first transaction date to last 
        Collections.sort(transactions, new Comparator<Transaction>() {
            public int compare(Transaction t1, Transaction t2) {
                return t1.getTransactionDate().compareTo(t2.getTransactionDate());
            }
        });

        Transaction prevTransaction = transactions.get(0); 
        double amount = prevTransaction.amount;

        for (int i = 1; i < transactions.size(); i++) {
            Transaction tmp = prevTransaction; 
            prevTransaction = transactions.get(i); 
            //Get difference in days between consecutive transactions
            int dayDiff = getDayDifference(tmp.getTransactionDate(), prevTransaction.getTransactionDate());

            //For each day increment amount by interest rate
            for (int j = 0; j < dayDiff; j++) {
                amount += interestEarned(amount);
            }
            amount += prevTransaction.amount;
        }
        
        //Increment amount by interest rate for each day from the last transaction to now
        int daysDiff = getDayDifference(prevTransaction.getTransactionDate(), DateProvider.getInstance().now());
        for (int k = 0; k < daysDiff; k++) {
            amount += interestEarned(amount);
        }
        return amount;
    }

    //Find the number of days between two dates
    public int getDayDifference(Date date1, Date date2) {
        long timeDiff = date2.getTime() - date1.getTime();
        int dayDiff = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
        return dayDiff;
    }

    protected void addTransaction(Transaction t) {
        transactions.add(t);
    }
}
