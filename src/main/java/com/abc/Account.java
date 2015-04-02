package com.abc;

import java.util.*;

/**
 * Class Account represents a bank account
 * Account has a Type of Account and a List of Transactions
 * An Account has the following characteristics
 * withdraw, deposit, interests earned, balance
 * and can transfer money to another account
 *
 * @author Fragkakis Manos
 */
public class Account {

    private AccountType typeOfAccount;
    private List<Transaction> transactions;

    public Account(AccountType typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Returns a read only copy of the transactions
     *
     * @return List of account transactions
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Deposit amount on specific date
     * Method created only for test purposes, for transactions on the pas
     *
     * @param amount amount of money for the deposit
     * @param date   specific date for the transaction
     */
    public void specificDeposit(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, date));
        }
    }

    /**
     * Withdraw amount on specific date
     * Method created only for test purposes, for transactions on the past
     *
     * @param amount amount of money for the withdrawal
     * @param date   specific date for the transaction
     */
    public void specificWithdraw(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (amount > getSumTransactions()) {
            throw new IllegalArgumentException("insufficient balance on account");
        } else {
            transactions.add(new Transaction(-amount, date));
        }
    }

    /**
     * Deposit amount into tha account
     *
     * @param amount Amount for the deposit
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Withdraw amount from the account
     *
     * @param amount Amount of withdrawal
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (amount > getSumTransactions()) {
            throw new IllegalArgumentException("insufficient balance on account");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * Calculates the interest of the account, based on type of account
     *
     * @return Interest calculated based on account type and balance of the account
     */
    public double getInterestEarned() {
        double balance = getSumTransactions();
        switch (typeOfAccount) {
            case SAVINGS:
                if (balance <= 1000)
                    return balance * 0.001;
                else
                    return 1 + (balance - 1000) * 0.002;
            case MAXI_SAVINGS:
                // have an interest rate of 5% assuming no withdrawals in the past 10 days
                if (!searchForWithdrawals(10))
                    return balance * 0.05;
                    // otherwise 0.1%
                else return balance * 0.001;
            default:
                return balance * 0.001;
        }
    }

    /**
     * Based on type of account returns calculated interest
     *
     * @param dateOfSearch Day on which the interests are calculated
     * @return Amount of Interest Calculated
     */
    public double getInterestEarned(Date dateOfSearch) {
        if (typeOfAccount == AccountType.SAVINGS) {
            return savingsInterestEarned(dateOfSearch);
        } else if (typeOfAccount == AccountType.MAXI_SAVINGS) {
            return maxiInterestEarned(dateOfSearch);
        } else {
            return checkingInterestEarned(dateOfSearch);
        }
    }

    /**
     * Return Interests that accrue daily, for Savings Account
     *
     * @param dateOfSearch Day on which the interests are calculated
     * @return Amount of Interest Calculated
     */
    private double savingsInterestEarned(Date dateOfSearch) {
        Date dayOfTransactionCheck = null;
        Date firstTransaction = transactions.get(0).getTransactionDate();
        int daysBetween;
        double interestAccrue = 0.0;
        double balance = 0.0d;
        boolean oppositeDaysFlag = dateOfSearch.before(firstTransaction);

        // iterate through the transactions
        for (Transaction transaction : transactions) {
            // get every transaction date
            dayOfTransactionCheck = transaction.getTransactionDate();

            // and calculate the days between the first transaction and the one is checked
            daysBetween = (int) daysBetweenDates(firstTransaction, dayOfTransactionCheck);
            // if days are positive, and balance less or equal to 1000, increase interest
            if (daysBetween > 0 && balance <= 1000) {
                interestAccrue += balance * 0.001 * (daysBetween / 365.0);
            }
            // if days are positive, and balance bigger than 1000, increase interest
            else if (daysBetween > 0 && balance > 1000) {
                interestAccrue += 1 + (balance - 1000) * 0.002 * (daysBetween / 365.0);
            }

            // increase balance, with the transaction amount
            balance += transaction.getAmount();
        }
        // interest calculation after the last transaction
        if (!oppositeDaysFlag) {
            daysBetween = (int) daysBetweenDates(dayOfTransactionCheck, dateOfSearch);
            interestAccrue += balance * 0.001 * (daysBetween / 365.0);
        }

        return interestAccrue;
    }

    /**
     * Return Interests that accrue daily, for Maxi Saving Account
     *
     * @param dateOfSearch Day on which the interests are calculated
     * @return Amount of Interest Calculated
     */
    private double maxiInterestEarned(Date dateOfSearch) {
        Date firstTransaction = transactions.get(0).getTransactionDate();
        Date dayOfTransactionCheck = null;
        int daysBetween;
        double interestAccrue = 0.0d;
        double balance = 0.0d;
        double intRate = 0;
        boolean oppositeDaysFlag = dateOfSearch.before(firstTransaction);

        // iterate through the transactions
        for (Transaction transaction : transactions) {
            // get every transaction date
            dayOfTransactionCheck = transaction.getTransactionDate();

            // and calculate the days between the first transaction and the one is checked
            daysBetween = (int) daysBetweenDates(firstTransaction, dayOfTransactionCheck);

            // if withdrawals occurred on the last ten days, set specific interest rate
            if (searchForWithdrawals(daysBetween)) {
                intRate = 0.001;
            }
            // if NO withdrawals occurred on the last ten days, set specific interest rate
            else if (!searchForWithdrawals(daysBetween)) {
                intRate = 0.05;
            }

            if(daysBetween > 0)
                interestAccrue += balance * intRate * (daysBetween / 365.0);

            // increase balance, with the transaction amount
            balance += transaction.getAmount();
        }

        // interest calculation after the last transaction
        if (!oppositeDaysFlag) {
            daysBetween = (int) daysBetweenDates(dayOfTransactionCheck, dateOfSearch);
            interestAccrue += balance * intRate * (daysBetween / 365.0);
        }

        return interestAccrue;
    }

    /**
     * Return Interests that accrue daily, for Checking Account
     *
     * @param dateOfSearch Day on which the interests are calculated
     * @return Amount of Interest Calculated
     */
    private double checkingInterestEarned(Date dateOfSearch) {
        Date firstTransaction = transactions.get(0).getTransactionDate();
        Date dayOfTransactionCheck = null;
        int daysBetween;
        double balance = 0.0d;
        double interestAccrue = 0.0d;
        boolean oppositeDaysFlag = dateOfSearch.before(firstTransaction);

        // iterate through the transactions
        for (Transaction transaction : transactions) {
            // get every transaction date
            dayOfTransactionCheck = transaction.getTransactionDate();

            // and calculate the days between the first transaction and the one is checked
            daysBetween = (int) daysBetweenDates(firstTransaction, dayOfTransactionCheck);
            // if days are positive, increase interest
            if (daysBetween > 0) {
                interestAccrue += balance * 0.001 * (daysBetween / 365.0);
            }
            // increase balance, with the transaction amount
            balance += transaction.getAmount();
        }
        // interest calculation after the last transaction
        if (!oppositeDaysFlag) {
            daysBetween = (int) daysBetweenDates(dayOfTransactionCheck, dateOfSearch);
            interestAccrue += balance * 0.001 * (daysBetween / 365.0);
        }
        return interestAccrue;
    }


    /**
     * Method Searches for specific period of time (days) for withdraws, from now to the past
     *
     * @param daysToSearch The number of days that method is going to search for withdraws
     * @return True if method find transactions on the specific period of time
     */
    private boolean searchForWithdrawals(int daysToSearch) {
        //we search the list in reverse order as we assume the most recent one will be at the end .
        if (!transactions.isEmpty() && transactions.size() > 0) {
            for (int index = transactions.size() - 1; index >= 0; index--) {
                Transaction trans = transactions.get(index);
                if (trans.getAmount() < 0 && daysBetweenDates(trans.getTransactionDate(), Calendar.getInstance().getTime()) < daysToSearch)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns the balance of the account calculated from all the transactions
     *
     * @return Balance of the account
     */
    public double getSumTransactions() {
        double balance = 0.0;
        for (Transaction transaction : transactions)
            balance += transaction.getAmount();
        return balance;
    }

    /**
     * Return the type fo the account
     *
     * @return The account type
     */
    public AccountType getAccountType() {
        return typeOfAccount;
    }

    /**
     * Transfers an amount of money from one account to another
     *
     * @param amount             The amount of money will be transferred
     * @param destinationAccount The account on which the money will be transferred
     */
    public void transferAmountTo(double amount, Account destinationAccount) {
        if (this == destinationAccount) {
            throw new IllegalArgumentException("Operation not supported");
        } else if (this.getSumTransactions() < amount) {
            throw new IllegalArgumentException("Insufficient balance to Account");
        } else {
            this.withdraw(amount);
            destinationAccount.deposit(amount);
        }
    }

    /**
     * Finds the number of days, between two dates
     *
     * @param startDate The date from which the search will begin
     * @param endDate   The date on which the search will end
     * @return The number of days between the dates
     */
    private static long daysBetweenDates(Date startDate, Date endDate) {
        // Get msec from each, and subtract.
        long diff = endDate.getTime() - startDate.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }


}
