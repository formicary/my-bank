package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.*;

public class Account {

    //-CONSTANTS-
    //Account types
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    //Interest rates (tenths of a percent)
    private static final int CHECKING_RATE = 1;
    private static final int BOTTOM_SAVINGS_RATE = 1;
    private static final int TOP_SAVINGS_RATE = 2;
    private static final int BOTTOM_MAXI_RATE = 1;
    private static final int TOP_MAXI_RATE = 50;

    //Account tiers (cents)
    private static final int FIRST_TIER_SAVINGS = 100000;

    //Number of days of no withdrawals on Maxi-Savings account to access higher interest rate
    private static final int MAXI_DAYS = 10;

    
    private final int accountType;
    private boolean hasWithdrawn;
    private List<Transaction> transactions;

    Account() {
        this.accountType = CHECKING;
        this.transactions = new ArrayList<Transaction>();
        this.hasWithdrawn = false;
    }

    Account(int accountType) {
        if (accountType != SAVINGS && accountType != MAXI_SAVINGS)
            this.accountType = CHECKING;
        else
            this.accountType = accountType;

        this.transactions = new ArrayList<Transaction>();
        this.hasWithdrawn = false;
    }

    void deposit(double dollars) {
        try {
            isValidAmount(dollars);
            isValidDeposit(dollars);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        long cents = toCents(dollars);
        transactions.add(new Transaction(cents));
    }

    void withdraw(double dollars) {
        try {
            isValidAmount(dollars);
            isValidWithdrawal(dollars);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        long cents = toCents(dollars);
        transactions.add(new Transaction(-cents));

        if (!hasWithdrawn)
            hasWithdrawn = true;
    }

    //Returns a formatted statement as a string containing all the transactions on the account as well as the total net
    //balance.
    String createStatement() {
        String statement = "";

        //Add correct account name to statement
        switch(accountType){
            case SAVINGS:
                statement += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                statement += "Maxi Savings Account\n";
                break;
            default: //CHECKING
                statement += "Checking Account\n";
        }

        //Get the total dollar balance as a formatted string.
        String totalDollarsFormatted = "";
        double totalDollars = getBalanceDollars();

        //If total balance is negative, append a minus sign to the total dollar balance.
        if (totalDollars < 0)
            totalDollarsFormatted += "-";

        totalDollarsFormatted += formatDollars(totalDollars);

        //Add each transaction to statement.
        for (Transaction t : transactions) {
            statement += "  " + (t.cents < 0 ? "withdrawal" : "deposit") + " " + formatDollars(toDollars(t.cents)) + "\n";
        }

        //Finally, add the total balance to the end of the statement.
        statement += "Total " + totalDollarsFormatted;

        return statement;
    }

    //Returns accumulated interest in dollars on the account as a double. Interest on positive balances is owed to the
    //customer. Interest on negative balances is owed to the bank and is negative in value.
    double interestEarned() {
        final long balance = getBalanceCents();
        final int conversionFactor = 1000; //For converting interest to correct units
        long interest; //Thousandths of one cent

        //Calculate correct interest for account type
        switch(accountType){
            case SAVINGS:
                if (balance <= FIRST_TIER_SAVINGS)
                    interest = balance * BOTTOM_SAVINGS_RATE;
                else
                    interest = (FIRST_TIER_SAVINGS * BOTTOM_SAVINGS_RATE) + ((balance-FIRST_TIER_SAVINGS) * TOP_SAVINGS_RATE);
                break;
            case MAXI_SAVINGS:
                Date now = DateProvider.getInstance().now();
                Date lastWithdrawalDate = getLastWithdrawalDate();

                if (hasWithdrawn && daysBetweenDates(now, lastWithdrawalDate) <= MAXI_DAYS)
                    interest = balance * BOTTOM_MAXI_RATE;
                else
                    interest = balance * TOP_MAXI_RATE;
                break;
            default: // CHECKING ACCOUNT
                interest = balance * CHECKING_RATE;
        }

        //Round interest to nearest thousand (nearest cent)
        interest = round(interest, 1000);

        //Convert interest to cents
        interest /= conversionFactor;

        //Finally, convert to dollars
        return toDollars(interest);
    }

    int getAccountType() {
        return accountType;
    }

    long getBalanceCents() {
        long balance = (long)0;

        for (Transaction t : transactions) {
            balance += t.cents;
        }

        return balance;
    }

    double getBalanceDollars() {
        return (getBalanceCents()/100.00);
    }

    private void isValidAmount(double dollars) {
        if (dollars <= 0)
            throw new IllegalArgumentException("amount must be greater than zero");
    }

    private void isValidDeposit(double dollars) {
        final long balance = getBalanceCents();

        if ((balance > 0) && (toCents(dollars) > Long.MAX_VALUE - balance))
            throw new IllegalArgumentException("balance will be too high after deposit");
    }

    private void isValidWithdrawal(double dollars) {
        final long balance = getBalanceCents();

        if ((toCents(dollars) > 0) && (balance < Long.MIN_VALUE + toCents(dollars)))
            throw new IllegalArgumentException("balance will be too low after deposit");
    }

    //Rounds n to the nearest multiple
    private long round(long n, int multiple) {
        if (n < 0)
            return ((n - multiple/2) / multiple) * multiple;

        return ((n + multiple/2) / multiple) * multiple;
    }


    private long daysBetweenDates(Date a, Date b) {
        long diffInMillis = abs(a.getTime() - b.getTime());
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    private Date getLastWithdrawalDate() {
        Date last = new Date(0);
        ListIterator<Transaction> itr = transactions.listIterator(transactions.size());

        while (itr.hasPrevious()) {
            if (itr.previous().cents < 0) {
                last = itr.previous().transactionDate;
                break;
            }
        }

        return last;
    }

    private long toCents(double dollars) {
        return ((long)(dollars*1000.0)) / 10;
    }

    private double toDollars(long cents) {
        return cents/100.00;
    }

    private String formatDollars(double dollars) {
        return String.format("$%,.2f", abs(dollars));
    }
}
