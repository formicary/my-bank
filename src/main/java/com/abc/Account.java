package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private double balance;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposited amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawn amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    /**
     * Generates the statement for the account
     * @return String displaying the statement for the account
     */
    String statementForAccount() {
        String s = "";

        //Translate to account type
        switch(getAccountType()){
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

        //Total up all the transactions
        for (Transaction t : transactions) {
            s += "  " + t.getType() + ": " + t.getTransactionDate()
                    + "         " + toDollars(t.amount) + "\n";
        }
        s += "Total " + toDollars(getBalance());
        return s;
    }


    /**
     * Calculates the total interest earned annually based on the account type
     * @return The amount of interest earned annually on this account based on current balance
     */
    public double interestEarned() {
        double amount = balance;
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    /**
     * Updates the balance daily based on interest earned. Initiated from the bank and through a method in the
     * customer class
     */
    public void applyInterest() {
        balance = balance + (interestEarned()/360);
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

    public double getBalance() {
        return balance;
    }

    String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
