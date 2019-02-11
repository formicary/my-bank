package com.abc.accounts;

import com.abc.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * An Abstract class Account, It has 3 subclasses CheckingAccount, SavingsAcocunt & MaxiSavingsAccount
 * These 3 classes are the available account options for the customer.
 */
public abstract class Account {

    private ArrayList<Transaction> transactions;
    private double balance; //Could use long or BigDecimal for more precision but in my tests double works fine.
    private final AccountType type;
    private final int accountID;

    /**
     * A Constructor for the Account class, it's used when opening a new account.
     *
     * @param type      3 options available: CHECKING, SAVINGS, MAXISAVINGS.
     * @param accountID A unique ID for the new account.
     */
    public Account(AccountType type, int accountID) {
        this.type = type;
        this.accountID = accountID;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Getter for accountID
     *
     * @return The unique account id.
     */

    public int getAccountID() {
        return accountID;
    }

    /**
     * Private function to get the balance. This is only used for calculation as it's more than 2 d.p.
     *
     * @return The account balance.
     */
    private double getBalance() {
        return this.balance;
    }

    /**
     * Getter for the account type.
     *
     * @return the account type as a string.
     */
    public String getAccountType() {
        return this.type.getName();
    }

    /**
     * Getter for the transactions.
     *
     * @return ArrayList of all transactions.
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Gets the balance and round it to d.p
     *
     * @return the balance
     */
    public double viewBalance() {
        return Math.round(getBalance() * 100.00) / 100.00;
    }

    /**
     * Method to add a transaction to the account
     *
     * @param amount          The amount of the transaction.
     * @param transactionType e.g deposit, withdrawal or transfer.
     */
    public void addTransaction(Double amount, String transactionType) {

        this.transactions.add(new Transaction(amount, transactionType));
        this.balance += amount;
    }

    /**
     * Method to deposit money into the account
     *
     * @param amount          The amount being added to the account.
     * @param transactionType e.g deposit, pay or received from another account.
     * @throws IllegalArgumentException if the amount being withdrawn is less than or equal to 0.
     */
    public void depositAmount(double amount, String transactionType) throws IllegalArgumentException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            addTransaction(amount, transactionType);
        }
    }

    /**
     * Method to withdraw money from the account.
     *
     * @param amount          The amount being withdrawn.
     * @param transactionType e.g withdrawal, rent or bill.
     * @throws IllegalArgumentException If amount being withdrawn is more than account balance.
     */
    public void withdrawAmount(double amount, String transactionType) throws IllegalArgumentException {

        if ((amount <= 0) || ((balance - amount) < 0)) {
            throw new IllegalArgumentException("Invalid transaction or Insufficient funds");
        } else {
            addTransaction(amount * -1, transactionType);
        }
    }

    /**
     * Method to calculate the difference between two dates. This is a difference between dates and not time.
     *
     * @param date1 Difference between day 1
     * @param date2 and Day 2.
     * @return Integer value of the difference in days .
     */

    public static int getDateDiff(Date date1, Date date2) {

        // Create calendar instances of each day.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        // Set the time to be 0 for day 1 as we are only interested in date.
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        // Set the time to be 0 for day as we are only interested in date.
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);


        return Math.toIntExact(TimeUnit.MILLISECONDS.toDays(Math.abs(calendar2.getTimeInMillis() - calendar1.getTimeInMillis())));
    }

    /**
     * Method that implements the compound interest formula.
     * Balance * (1 + (rate/365))^Number of days
     *
     * @param balance      The balance to work out the interest
     * @param interestRate The yearly interest rate
     * @param numberOfDays Number of days for the compound interest
     * @return The compound interest over the number of days
     */

    public double compoundFormula(double balance, double interestRate, int numberOfDays) {

        if (numberOfDays > 0) {
            return (balance * Math.pow(1 + (interestRate / 365.00), (double) numberOfDays) - balance);
        } else {
            return 0;
        }
    }

    /**
     * Method to calculate the compound daily interest on the account.
     * If account was created on 01/01/2019 and the interest is being calculated on 01/02/2019
     * Then the function will return the compounded daily interest earned between 01/01/2019 to 31/01/2019
     *
     * @return The interest earned by on account up until now.
     * @throws IllegalArgumentException If the date format is invalid.
     */

    public double compoundDailyInterest() throws IllegalArgumentException {

        /*
         * It is important to group the transactions per day as the interest is compounded daily.
         * Grouping the transactions will give the balance for each day thus interest can be calculated daily.
         */
        // Define the date format, ignoring the time.
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        // Create a new Linked Hash Map
        Map<String, Double> balanceByDate = new LinkedHashMap<>();

        // Group the transactions by day. e.g all transaction that occurred on 1st feb will be added.
        for (Transaction transaction : transactions) {
            String key = dateFormatter.format(transaction.getTransactionDate());
            balanceByDate.put(key, balanceByDate.getOrDefault(key, 0.0) + transaction.getAmount());
        }

        balanceByDate.put(dateFormatter.format(Calendar.getInstance().getTime()), 0.0);

        // Get all the dates from the linked hash map of the grouped transactions.
        String[] dates = balanceByDate.keySet().toArray(String[]::new);

        // Define the starting balance as the transactions on the first day.
        double balance = balanceByDate.get(dates[0]);
        double balanceWithInterest = balanceByDate.get(dates[0]);

        // Loop through each day and calculate the interest earned each day.
        // Also calculates the interest earned on days no transactions happened.

        for (int i = 1; i < dates.length; i++) {

            try {
                Date previousDate = dateFormatter.parse(dates[i - 1]);
                Date currentDate = dateFormatter.parse(dates[i]);
                int numberOfDays = getDateDiff(previousDate, currentDate);

                balance += balanceByDate.get(dates[i]);

                balanceWithInterest += interestEarned(balanceWithInterest, numberOfDays, currentDate) + balanceByDate.get(dates[i]);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Wrong date format");
            }
        }

        return balanceWithInterest - balance;
    }

    /**
     * An abstract method, each of the subclasses will define their own interest rates.
     *
     * @param balance      The amount the interest is earned on.
     * @param numberOfDays The number of days interest being earned.
     * @return
     */
    public abstract double interestEarned(Double balance, int numberOfDays, Date from);

    public double interestEarned(Double balance, int numberOfDays) {
        return interestEarned(balance, numberOfDays, null);
    }

    /**
     * A toString Method that returns the account ID, account type and all transactions.
     *
     * @return account ID, account type and transactions in a string format.
     */

    @Override
    public String toString() {
        String toString = "All transactions for account: " + getAccountID() + " Account Type: " + getAccountType() + "\n";
        for (Transaction t : transactions) {
            toString += t.toString() + "\n";
        }
        toString += "Your current account balance: " + viewBalance() + "\n";

        return toString;
    }
}