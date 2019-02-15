package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    //Fields to represent an account
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private final double balance;
    public List<Transaction> transactions;

    /**
     * Creates a new instance of an account
     *
     * @param accountType the account type of the customer
     *
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    /**
     *
     * @param amount The amount of money the customer wish to deposit to this
     * account
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     *
     * @param amount The amount of money the customer wish to withdraw from this
     * account
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if (amount >= balance) {
                throw new IllegalArgumentException("you do not have enough balance in account");
            } else {
                transactions.add(new Transaction(-amount));
            }
        }
    }

    /**
     *
     * @return The amount of interest earned providing the customer's account
     * type
     */
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount >= 0.0) {
            switch (accountType) {
                case CHECKING:
                    return amount * 0.001;
                case SAVINGS:
                    if (amount <= 1000) {
                        return amount * 0.001;
                    } else {
                        return 1 + (amount - 1000) * 0.002;
                    }
                case MAXI_SAVINGS:
                    if (transactions.size() > 2) {
                        LocalDate lastTransactionDate = transactions.get(transactions.size() - 1).getTransactionDate();
                        LocalDate secoundToLastTransactionDate = transactions.get(transactions.size() - 2).getTransactionDate();
                        if (lastTransactionDate.getDayOfYear() >= secoundToLastTransactionDate.getDayOfYear()
                                && lastTransactionDate.getMonthValue() >= secoundToLastTransactionDate.getMonthValue()
                                && lastTransactionDate.getDayOfMonth() < 10 + secoundToLastTransactionDate.getDayOfMonth()) {
                            return amount * 0.05;
                        }
                    }
                default:
                    return amount * 0.001;
            }
        }
        return 0;
    }

    /**
     *
     * @return The sum of transactions of an account
     */
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.amount;
        }
        return amount;
    }

    /**
     *
     * @return The account type of this account
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     *
     * @return The account balance
     */
    public double getBalance() {
        return balance;
    }
}
