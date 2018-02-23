package com.abc;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Account {

    /**
     * This enum holds the three types of account there are.
     */
    public enum AccountType {
        CHECKING("CHECKING"),
        SAVINGS("SAVINGS"),
        MAXI_SAVINGS("MAXI_SAVINGS")
        ;

        private final String text;

        /**
         * @param text The type of account to be opened.
         */
        AccountType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private final Date dateOpened;
    private final AccountType accountType;
    private double grossInterest;
    // I am assuming the bank wants to hold the total gross interest paid out, not the net interest paid and received.

    private double balance;

    /*
        Balance effectively behaves like a cache, storing an old value until the most recent transaction. I.e, we will
        store an old value of balance until it is needed, at which point we add interest accrued since the last time
        it was looked at - as opposed to updating interest constantly which would have bad time complexity - and then
        update balance as needed (withdraw or remove).

        I want to make explicit, interest is added to an account only at the time of a transaction.
     */

    //Functionality has been commented in withdraw() that would allow overdrafts. Change 0 to whatever limit one wants.

    private LinkedList<Transaction> transactions;
    /*
        I changed to a LinkedList as the number of transactions will grow quickly and will always be queried
        sequentially and as a whole list so to O(n) time complexity. The LinkedList has an add/remove complexity of
        O(1) so is ideal for this operation. An ArrayList would have poor time complexity from the doubling when list
        size grows too much. Furthermore, I am assuming this application is not concurrent so I do not use
        CopyOnWriteArrayList. Also, I opted against a FIFO queue out of personal preference, performance-wise there is
        no difference.

        We put the most recent transaction first for fastest retrieval.
     */

    /**
     * This constructor initialises a new account given the type of account it is to be.
     * @param accountType
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.dateOpened = Calendar.getInstance().getTime();
        this.balance = 0;
        this.transactions = new LinkedList<Transaction>();
    }

    /**
     * This method will first, find the amount of interest the account has accrued since the last transaction.
     * The method will then remove the amount the Customer wants to withdraw from the bank if the customer has enough
     * money.
     * @param amount
     */
    public void deposit(double amount){
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero");

        if (BigDecimal.valueOf(amount).scale() > 2)
            throw new IllegalArgumentException("Amount cannot be greater than two decimal places");

         else {
            double interestSinceLatestTransaction = recentInterestEarned();
            updateGrossInterest(interestSinceLatestTransaction);
            updateCurrentBalance(interestSinceLatestTransaction + amount);
            transactions.addFirst(new Transaction(amount));
        }
    }

    /**
     * This method will first, find the amount of interest the account has accrued since the last transaction.
     * The method will then remove the amount the Customer wants to withdraw from the bank if the customer has enough
     * money.
     * @param amount The amount the customer wants to withdraw
     */
    public void withdraw(double amount) {
        /*if (balance <=0)
            throw new OverdraftException();
        */
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero");


        if (BigDecimal.valueOf(amount).scale() > 2)
            throw new IllegalArgumentException("Amount cannot be greater than two decimal places");

        else {
            double interestSinceLatestTransaction = recentInterestEarned();
            updateGrossInterest(interestSinceLatestTransaction);
            updateCurrentBalance(interestSinceLatestTransaction - amount);
            transactions.addFirst(new Transaction(-amount));
        }
    }

    /**
     * This method appends the amount to the balance
     * @param amount The amount to be added to the balance
     */
    private void updateCurrentBalance(double amount){
        balance+=amount;
    }

    /**
     * This method appends the amount to the balance
     * @param amount The amount to be added to the balance
     */
    private void updateGrossInterest(double amount){
        if(amount > 0)
            grossInterest+=amount;
    }

    /**
     * This method will calculate the interest earned, compounding daily, since the date of the last transaction.
     * @return The interest earned, compounding daily, since the date of the last transaction.
     */
    public double recentInterestEarned() {
        if (transactions.isEmpty())
            return 0;

        long daysSinceLatestTransaction = daysSinceDate(transactions.getFirst().getDate());

        if (daysSinceLatestTransaction == 0)
            return 0;

        switch(accountType){
            case SAVINGS:
                if (balance <= 1000)
                    return balance*(daysSinceLatestTransaction*(0.001/365));

                else
                    return balance*(daysSinceLatestTransaction*(0.002/365));

            case MAXI_SAVINGS:
                long daysSinceLastWithdrawal = daysSinceDate(getDateOfLastWithdrawal());


                if (daysSinceLastWithdrawal <= 10)
                    return balance*(daysSinceLatestTransaction*(0.001/365));
                else
                    return balance*(daysSinceLatestTransaction*(0.05/365));

            default:
                return balance*(daysSinceLatestTransaction*(0.001/365));
        }
    }

    /**
     * This method will get the date of the last withdrawal from the account. If no such date exists
     *  the date the account was opened will be used.
     * @return The date of the last withdrawal.
     */
    private Date getDateOfLastWithdrawal(){
        if(!transactions.isEmpty())
            for (Transaction t: transactions)
                if (t.getAmount() < 0)
                    return t.getDate();
        return dateOpened;
    }

    /**
     * This method calculates the days since the date entered.
     * @param date The date since which we want to know the number of days that has transpired
     * @return The days since the date.
     */
    private long daysSinceDate(Date date){
        long dateDiff = Math.abs(date.getTime() - Calendar.getInstance().getTime().getTime());
        return TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
    } // Test

    /**
     * The total interest paid to this account on the day the method is called.
     * @return Total interest paid to this account
     */
    public double getGrossInterestEarned(){
        return recentInterestEarned()+grossInterest;
    }

    /**
     * This method returns the type of account the customer has
     * @return Type of account
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * This method returns the linkedList of transactions.
     * @return The linkedList of transactions.
     */
    public LinkedList<Transaction> getTransactions(){
        if (!transactions.isEmpty())
            return transactions;
        else
            return null;
    }

    /**
     * This method returns the balance in this account.
     * @return Returns the balance in this account.
     */
    public double getCurrentBalance (){
        return balance+recentInterestEarned();
    }

    public double getCachedBalance() {
        return balance;
    }


    /*
    /**
     * This private class allows the throwing of an overdraft exception if the balance is too low to withdraw

    private class OverdraftException extends ArithmeticException{
        OverdraftException(){
            super("Balance is too low for this transaction.");
        }
    }
    */
}
