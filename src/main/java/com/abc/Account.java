package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A class representing a customer account at the bank. The account has a
 * specific type and contains a list of transactions.
 * 
 * @author Filippos Zofakis
 */
public class Account {

    /**
     * An enumerator representing the account types on offer at the bank.
     */
    public enum Type {
        CHECKING, SAVINGS, MAXI_SAVINGS
    }

    private final Type accountType;

    private List<Transaction> transactions;

    private double totalInterestEarned;

    /**
     * Constructor initialising an account of a given type with an empty list of
     * transactions.
     * 
     * @param accountType
     */
    public Account(Type accountType) {
        this.accountType = accountType;
        transactions = new ArrayList<Transaction>();
        totalInterestEarned = 0;
    }

    /**
     * Adds a deposit transaction of a positive amount to the list of
     * transactions of the account.
     * 
     * @param amount
     *            A double representing the positive amount to be deposited to
     *            the account.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero!");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Adds a withdrawal transaction of a positive amount to the list of
     * transactions of the account.
     * 
     * @param amount
     *            A double representing the positive amount to be withdrawn from
     *            the account.
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero!");
        } else if (amount > getBalance()) {
            System.out.println("Insufficient account balance; please deposit first!");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * Returns the interest earned on the current account, based on type and
     * time elapsed.
     * 
     * @return A double representing the interest earned on the account.
     */
    public double accrueInterestDaily(Bank bank) {
        double balance = getBalance();
        double checkingInterestRate = bank.getDailyInterestRate(Type.CHECKING);
        double savingsInterestRate = bank.getDailyInterestRate(Type.SAVINGS);
        double maxiSavingsInterestRate = bank.getDailyInterestRate(Type.MAXI_SAVINGS);
        double dailyInterestAccrued = 0;

        switch (accountType) {
        case CHECKING:
            dailyInterestAccrued = balance * checkingInterestRate;
            break;
        case SAVINGS:
            if (balance <= 1000)
                dailyInterestAccrued = balance * checkingInterestRate;
            else
                dailyInterestAccrued = 1000 * checkingInterestRate + (balance - 1000) * savingsInterestRate;
            break;
        case MAXI_SAVINGS:
            // Getting the current date as a Calendar object.
            Calendar cutOffDate = Calendar.getInstance();
            // Subtracting ten days.
            cutOffDate.add(Calendar.DAY_OF_MONTH, -10);

            // If there is a transaction in the past ten days with a negative
            // amount:
            for (Transaction transaction : transactions) {
                if (transaction.getDate().after(cutOffDate) && transaction.getAmount() < 0) {
                    dailyInterestAccrued = balance * checkingInterestRate;
                    break;
                }
            }

            // If dailyInterestAccrued is zero, when this point is reached,
            // then there has not been any withdrawal in the past ten days.
            if (dailyInterestAccrued == 0)
                dailyInterestAccrued = balance * maxiSavingsInterestRate;
            break;
        default:
            throw new IllegalArgumentException("Invalid account type!");
        }

        totalInterestEarned += dailyInterestAccrued;
        return dailyInterestAccrued;
    }

    /**
     * Returns the total interest earned, since the date of the creation of the
     * account.
     * 
     * @return A double representing the total account interest earned.
     */
    public double getTotalInterestEarned() {
        return totalInterestEarned;
    }

    /**
     * Returns the balance (sum of all transactions, positive and negative) of
     * the account.
     * 
     * @return A double representing the balance of the account.
     */
    public double getBalance() {
        double accountBalance = 0;

        for (Transaction transaction : transactions)
            accountBalance += transaction.getAmount();

        accountBalance += totalInterestEarned;

        return accountBalance;
    }

    /**
     * Returns the type of the account.
     * 
     * @return A constant from the enum Type representing the type of the
     *         account.
     */
    public Type getType() {
        return accountType;
    }

    /**
     * Returns the list of transactions of the account.
     * 
     * @return Returns a List of Transaction objects.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }
}
