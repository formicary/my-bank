package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private double balance;
    private boolean isLinkedToCustomer;

    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }


    //Basic checks for any transaction
    public void basicTransactionChecks(double amount) {
        if (!isLinkedToCustomer) {
            throw new UnsupportedOperationException("Account not linked with customer");
        } else if (amount <= 0) {
            throw new IllegalArgumentException("The amount needs to be a positive number");
        }
    }

    public void deposit(double amount) {
        basicTransactionChecks(amount);
        finishTransaction(amount, false);

    }

    public void withdraw(double amount) {
        basicTransactionChecks(amount);
        if (amount >= balance) {
            throw new IllegalArgumentException("The withdraw amount should be less or equal to the balance");
        } else {
            finishTransaction(amount, true);
        }
    }


    public void finishTransaction(double amount, boolean isWithDrawable) {
        balance = isWithDrawable ? balance - amount : balance + amount;
        transactions.add(new Transaction(accountType, amount, isWithDrawable));
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount - 1000) * 0.05;
                return 70 + (amount - 2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }


    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions)

            //TODO: Delete hardcoded value
            amount += 3;
        return amount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public int getAccountType() {
        return accountType;
    }

    public boolean linkAccWithCustomer() {
        return isLinkedToCustomer = true;
    }

    public boolean isAccLinkedCustomer() {
        return isLinkedToCustomer;
    }

    public double getBalance() {
        return balance;
    }

}
