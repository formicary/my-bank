package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;


    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }


    /**
     * Getter for list of transactions, preventing outside modification
     * @return list of all transactions for this account
     */
    public List<Transaction> getTransactions(){
        return transactions;
    }


    /**
     * Add given amount from this account. Optional title parameter allows the Transaction
     * to be easily identified. Defaults to "Transaction" if not provided.
     */
    public void deposit(String title, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(title, amount));
        }
    }

    public void deposit(double amount) {
        deposit("Transaction", amount);
    }


    /**
     * Remove given amount from this account. Optional title parameter allows the Transaction
     * to be easily identified. Defaults to "Transaction" if not provided.
     */
    public void withdraw (String title, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(title, -amount));
        }
    }

    public void withdraw(double amount) {
        withdraw("Transaction", amount);
    }


    /**
     * Returns true if any transaction occurred within the last n days of current date
     */
    private Boolean recentTransaction(List<Transaction> transactions, int n) {
        // Get current date and subtract n days
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Calendar.getInstance().getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -10);
        Date thresholdDate = calendar.getTime();

        // See if any transactions occurred after this threshold
        for (Transaction t: transactions){
            Date transactionDate = t.getTransactionDate();
            if(transactionDate.after(thresholdDate)){
                return true;
                }
        }
        return false;
    }

    /**
     * Returns list of transactions only where money was withdrawn
     */
    private List<Transaction> getWithdrawals(List<Transaction> transactions) {
        List<Transaction> withdrawals = new ArrayList<>();
        for (Transaction t: transactions) {
            if (t.getAmount() < 0) {
                withdrawals.add(t);
            }
        }
        return withdrawals;
    }


    /**
     * Calculate interest for current account type
     */
    public double interestEarned() {
        double amount = sumTransactions();

        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                return 1 + (amount-1000) * 0.002;

            case MAXI_SAVINGS:
                // Get withdrawals only then check their dates to determine interest
                List<Transaction> withdrawals = getWithdrawals(transactions);
                Boolean recentTransaction = recentTransaction(withdrawals, 10);
                if (recentTransaction)
                    return amount * 0.001;
                else
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

    /**
     * @return account type in string format, to be used e.g. in generating account statement
     */
    public String getStringAccountType(){
        switch(accountType){
            case Account.CHECKING: return  "Checking Account";
            case Account.SAVINGS: return "Savings Account";
            case Account.MAXI_SAVINGS: return "Maxi Savings Account";
            default: return "Unknown account type";
        }
    }
}
