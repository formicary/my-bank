package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    // declared here in order to later use shorter name (in interestEarned())
    private static final double CHECKING_RATE = Constants.CHECKING_ACCOUNTS_RATE;
    private static final double SAVING_FIRST_RATE = Constants.SAVING_ACCOUNTS_FIRST_RATE;
    private static final double SAVING_SECOND_RATE = Constants.SAVING_ACCOUNTS_SECOND_RATE;
    private static final double MAXI_FIRST_RATE = Constants.MAXI_ACCOUNTS_FIRST_RATE;
    private static final double MAXI_SECOND_RATE = Constants.MAXI_ACCOUNTS_SECOND_RATE;
    private static final double MAXI_THIRD_RATE = Constants.MAXI_ACCOUNTS_THIRD_RATE;

    private static final float SAVING_LIMIT = Constants.SAVING_ACCOUNT_LIMIT;
    private static final float MAXI_FIRST_LIMIT = Constants.MAXI_SAVINGS_ACCOUNT_FIRST_LIMIT;
    private static final float MAXI_SECOND_LIMIT = Constants.MAXI_SAVINGS_ACCOUNT_SECOND_LIMIT;

    private final int accountType;
    private List<Transaction> transactions;

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
            case CHECKING:
                return amount * CHECKING_RATE;
            case SAVINGS:
                if (amount <= SAVING_LIMIT)
                    return amount * SAVING_FIRST_RATE;
                else
                    return (SAVING_LIMIT * SAVING_FIRST_RATE) + ((amount-SAVING_LIMIT) * SAVING_SECOND_RATE);
            case MAXI_SAVINGS:
                if (amount <= MAXI_FIRST_LIMIT)
                    return amount * MAXI_FIRST_RATE;
                else if (amount > MAXI_FIRST_LIMIT && amount <= MAXI_SECOND_LIMIT)
                    return (MAXI_FIRST_LIMIT * MAXI_FIRST_RATE) + ((amount-MAXI_FIRST_LIMIT) * MAXI_SECOND_RATE);
                else if (amount > MAXI_SECOND_LIMIT)
                return ((MAXI_FIRST_LIMIT * MAXI_FIRST_RATE) + (MAXI_SECOND_LIMIT-MAXI_FIRST_LIMIT)*MAXI_SECOND_RATE) + ((amount-MAXI_SECOND_LIMIT) * MAXI_THIRD_RATE);
            default:
                return amount * CHECKING_RATE;
        }
    }

    public double sumTransactions() {
        if (checkIfTransactionsExist()) {
            double amount = 0.0;
            for (Transaction t : transactions)
                amount += t.getAmount();
            return amount;
        } else {
            return 0.00;
        }
    }

    private boolean checkIfTransactionsExist() {
        return !transactions.isEmpty();
    }

    public int getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}