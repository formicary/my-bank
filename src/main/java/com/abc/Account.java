package com.abc;

import java.util.*;

import static java.lang.Math.abs;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public long lastWithdrawalTime;

    private static final long tenDaysInMs = (long) 8.64e+8;

    /** Account object constructor
     *
     * @param accountType Type of the account
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /** Deposits specified amount of money to the account
     *
     * @param amount amount being deposited
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /** Withdraws specified amount of money from the account
     *
     * @param amount amount being withdrawn
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        /* Last withdrawal time value is used for interest calculation*/
            lastWithdrawalTime = transactions.get(transactions.size() - 1).getTransactionDate().getTime();
        }
    }

    /** Generates a statement for account
     *
     * @return statement of the account
     */
    public String statementForAccount() {
        String s = "";

        //Specifying account type
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

        //Now total up all the transactions for that account
        double total = 0.0;
        for (Transaction t : this.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    /** Calculates interest earned depending on account type
     *
     * @return interest earned
     */
    public double interestEarned() {
        double balance = getBalance();
        switch(accountType){
            case CHECKING:
                return balance * 0.001;
            case SAVINGS:
                if (balance <= 1000)
                    return balance * 0.001;
                else
                    return 1 + (balance-1000) * 0.002;
            case MAXI_SAVINGS:
                if (noWithdrawalsInLast10days()){
                    return balance * 0.005;
                }
            default:
                /*CHECKING account*/
                return balance * 0.001;
        }
    }

    /** Adds all transactions up (calculates overall balance)
     *
     * @return balance
     */
    public double getBalance() {
        double balance = 0.0;
        for (Transaction t: transactions)
            balance += t.amount;
        return balance;
    }

    /** Returns account type
     *
     * @return accountType
     */
    public int getAccountType() {
        return accountType;
    }

    /** Calculates whether there was a withdrawal in last 10 days
     *
     * @return true/false
     */
    private boolean noWithdrawalsInLast10days(){
        Date now = Calendar.getInstance().getTime();

        if(now.getTime() - lastWithdrawalTime < tenDaysInMs) {
            return false;
        }
        return true;
    }

    /** Formats a money value to dollars
     *
     * @param amount amount to be formatted
     * @return formatted string
     */
    private String toDollars(double amount){
        return String.format("$%,.2f", abs(amount));
    }

}




















