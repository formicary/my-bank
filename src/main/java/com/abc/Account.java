package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.abc.Transaction.TransactionType;

import java.util.Date;

/**
 * A class to control the function of a customer's account.
 * It keeps track of past transactions and account type to calculate a balance and what interest should be paid
 */
public class Account {
    /**
     * Enum detailing the types of account, each type has a different method of calculating daily interest gain
     */
    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS
    }

    /**
     * Variable storing the account's type
     */
    private final AccountType accountType;

    /**
     * A list tracking all past transactions related to the account, with oldest at the front and most recent at the end
     */
    private List<Transaction> transactions;

    /**
     * Constructor accepting the account type and initialising the list of transactions
     * @param accountType the type of account to be created
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Allows money to be deposited into the account
     * @param amount the amount of money to be deposited, must be greater than 0
     */
    public void deposit(double amount) {
        // if the amount is larger than 0, add the deposit to the list of transaction
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, TransactionType.DEPOSIT));
        }
    }

    /**
     * Allows money to be withdrawn from the account, but only if the account has a sufficient balance
     * @param amount the amount of money to be withdrawn
     */
    public void withdraw(double amount) {
        // check the amount to ensure it's positive
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            // check that the account has enough money to complete the transaction and not end with a negative balance
            if (calculateBalance() < amount) {
                throw new IllegalArgumentException("not enough money in account for withdrawal of that size");
            } else {
                transactions.add(new Transaction(-amount, TransactionType.WITHDRAWAL));
            }
        }
    }

    /**
     * Allows a premade transaction object to be added to the account's transactions
     * @param t the transaction object to be added
     */
    public void addTransaction(Transaction t) {
        // first check that if the transaction withdraws money from the account that it won't make the balance negative
        if (t.getTransactionType() == TransactionType.WITHDRAWAL || t.getTransactionType() == TransactionType.TRANSFER_OUT) {
            if (calculateBalance() < t.getAmount()) {
                throw new IllegalArgumentException("not enough money for that transaction");
            }
        }
        transactions.add(t);
    }

    /**
     * Calculates the total amount of interest that the account has earned by summing all 'interest' type transactions
     * @return the sum of interest that the account has earned
     */
    public double getTotalInterestEarned() {
        double total = 0.0;

        // iterate over all past transactions and add the transaction's amount to the total if it was an interest payment
        for (Transaction t : transactions) {
            if (t.getTransactionType() == TransactionType.INTEREST) {
                total += t.getAmount();
            }
        }

        return total;
    }

    /**
     * Calculates how much money the account should be given for an individual day's interest payment,
     * depending on the account type and balance
     * @return the value of interest that should be paid
     */
    public double getDailyInterestEarned() {
        return yearlyInterestEarned() / 365.25;
    }

    /**
     * Calculates the yearly interest rate for the account
     * @return the amount of interest that the account should earn in a year.
     */
    private double yearlyInterestEarned() {
        // first obtain the account's balance to calculate interest against
        double balance = calculateBalance();

        // the amount of interest depends on the type of account
        switch(accountType){
            case SAVINGS:
                // savings account gives 0.1% interest under $1000 and 0.2% on money above that
                if (balance <= 1000) {
                    return balance * 0.001;
                } else {
                    // the first $1000 would total to $1 in interest so add that and calculate the higher rate
                    // for the rest
                    return 1 + (balance - 1000) * 0.002;
                }

            case MAXI_SAVINGS:
                // Maxi savings accounts either give 0.1% if a withdrawal occurred within 10 days,
                // or 5% if no withdrawal happened. So first check whether a withdrawal occurred using the helper
                // function and calculate interest depending on it.
                if (afterMaxiWithdrawalPeriod()) {
                    return balance * 0.05;
                } else {
                    return balance * 0.001;
                }
                
            default:
                // A checking account (assumed to be the default) has a flat 0.1% interest
                return balance * 0.001;
        }
    }

    /**
     * Used to determine whether or not the customer has made a withdrawal from the account within 10 days
     * for the purposes of maxi savings interest calculation
     * @return true if no recent withdrawal occurred.
     */
    private boolean afterMaxiWithdrawalPeriod() {
        // if there are no transactions there's no chance of finding a withdrawal
        if (transactions.isEmpty()) {
            return true;
        }

        // calculate the date 10 days ago by getting the current date and rolling it back 10 days
        Date currentDate = DateProvider.getInstance().now();
        Calendar dateCutoff = Calendar.getInstance();
        dateCutoff.setTime(currentDate);

        dateCutoff.add(Calendar.DATE, -10);

        // iterate over the transactions to check for a withdrawal, starting at the end so that once a
        // transaction is found to be over 10 days ago, it means that all transactions before that in the
        // list are also before then, so they don't need to be considered
        for (int i = transactions.size() - 1; i >= 0; i--) {
            // get the date of the currently considered transaction
            Date lastTransactionDate = transactions.get(i).getDate();

            if (lastTransactionDate.compareTo(dateCutoff.getTime()) >= 0) {
                if (transactions.get(i).getAmount() < 0) {
                    // if the transaction has a negative amount then it's a withrawal so return false
                    return false;
                }
            } else {
                // if a transaction is reached that wasn't in the last 10 days then none of them will be
                return true;
            }
        }

        // after iterating over everything and not finding a match return true
        return true;
    }

    /**
     * Similar to the maxi withdrawal period method but checks for interest transactions within the last day,
     * to determine whether the account should be given a new daily interest payment
     * @return true if an interest transaction was not found recently and should be given to the account
     */
    public boolean checkInterestEligibility() {
        if (transactions.isEmpty()) {
            return false;
        }

        Date lastTransactionDate = transactions.get(transactions.size() - 1).getDate();

        Date currentDate = DateProvider.getInstance().now();
        Calendar dateCutoff = Calendar.getInstance();
        dateCutoff.setTime(currentDate);

        dateCutoff.add(Calendar.DATE, -1);

        for (int i = transactions.size() - 1; i >= 0; i--) {
            lastTransactionDate = transactions.get(i).getDate();

            if (lastTransactionDate.compareTo(dateCutoff.getTime()) >= 0) {
                if (transactions.get(i).getTransactionType() == TransactionType.INTEREST) {
                    return false;
                }
            } else {
                return true;
            }
        }

        return true;
    }

    /**
     * Iterates over every transaction to calculate the account's current balance
     * @return the account's balance
     */
    public double calculateBalance() {
        double balance = 0.0;
        for (Transaction t : transactions)
            balance += t.getAmount();
        return balance;
    }

    /**
     * Getter for the account's transaction list
     * @return the list of transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Getter for the account's type
     * @return the account type
     */
    public AccountType getAccountType() {
        return accountType;
    }
}
