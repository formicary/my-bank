package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account {

    private List<Transaction> transactions;
    private static final double INTEREST_RATE = 0.001;

    /**
     * Account Constructor
     */
    public Account(){
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Deposit method adds the amount passed as a parameter to the account.
     * @param amount Amount to be deposited.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, TransactionType.DEPOSIT));
        }
    }

    /**
     * Withdraw method creates a new withdraw transaction that negates the amount passed as the parameter.
     * @param amount Amount to be withdrawn.
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, TransactionType.WITHDRAW));
        }
    }

    /**
     * InterestEarned method calculates the interest earned.
     * @return Returns the interest earned.
     */
    public double interestEarned() {
        return sumOfAllTransactions() * INTEREST_RATE;
    }

    protected double sumOfAllTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    /**
     * GetTransactions method gets the list of transactions made in the account.
     * @return Returns the list of transactions made in the account.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * CheckTWithdrawTransactionForLast10Days checks if a withdrawal is done within the last 10 days.
     * @return Returns true if there has been a withdrawal in the last 10 days, or false otherwise.
     */
    public boolean checkWithdrawTransactionForLast10Days() {

        DateProvider date = new DateProvider();
        Date dateNow = date.now();
        Date date10DaysAgo = date.getDateFromPast(10);
        Date transactionDate;

        for(Transaction t: transactions){
             transactionDate = t.getTransactionDate();
            if(!((transactionDate.before(date10DaysAgo)) || (transactionDate.after(dateNow)))
                    && (t.getTransactionType().equals(TransactionType.WITHDRAW))){
                return true;
            }
        }
        return false;
    }
}
