package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private double currentBalance = 0;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            currentBalance += amount;
            transactions.add(new Transaction(amount));

        }
    }

    public void withdraw(double amount) {

        if (amount <= 0)
            throw new IllegalArgumentException("amount must be greater than zero");

        if (amount > currentBalance)
            throw new IllegalArgumentException("Amount greater than funds available.");

        else
            transactions.add(new Transaction(-amount));

    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public double interestEarned(double amount) {

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
                if (!checkNoWithdrawals(10))
                    return amount * 0.05;
                else
                    return amount * 0.001;

            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    public boolean checkIfTransactionsExist(Transaction transaction) {
        return transactions.contains(transaction);
    }

    /*
     * Returns True if there was an withdrawal in the last 'noOfDays'
     * */
    private boolean checkNoWithdrawals(int noOfDays) {


        Calendar cal = Calendar.getInstance();
        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.DATE, -noOfDays);
        Date xDaysAgo = cal.getTime();

        for (Transaction t : transactions)
            if (t.getDate().after(xDaysAgo) && t.getAmount() < 0)
                return true;

        return false;
    }

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillis = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }


    /*
     *    The Accrued interest is calculated as the sum of daily interest
     * */
    public double getAccruedInterest() {
        double interest = 0;
        Transaction previous = transactions.get(0);
        double amount = 0;

        for (Transaction t : transactions) {

            long daysDifference = getDateDiff(previous.getDate(), t.getDate(), TimeUnit.DAYS);

            interest += (interestEarned(amount) / 365) * daysDifference;
            amount += t.getAmount();
            previous = t;

            // If the last transaction didn't happen today than calculate the interest accrued from the last transaction until today
            if (t.equals(transactions.get(transactions.size() - 1)) && t.getDate().compareTo(Calendar.getInstance().getTime()) <= 0) {
                daysDifference = getDateDiff(previous.getDate(), Calendar.getInstance().getTime(), TimeUnit.DAYS);
                interest += (interestEarned(amount) / 365) * daysDifference; // assume the contract has a 356 day year
            }

        }

        return interest;
    }

    public double getCompoundInterest() {

        double interest = 0;
        double amount = 0;
        Transaction previous = transactions.get(0);


        for (Transaction t : transactions) {

            long daysDifference = getDateDiff(previous.getDate(), t.getDate(), TimeUnit.DAYS);

            interest += (interestEarned(amount + interest) / 365) * daysDifference;
            amount += t.getAmount();
            previous = t;

            // If the last transaction didn't happen today than calculate the compound interest from the last transaction until today
            if (t.equals(transactions.get(transactions.size() - 1)) && t.getDate().compareTo(Calendar.getInstance().getTime()) <= 0) {
                daysDifference = getDateDiff(previous.getDate(), Calendar.getInstance().getTime(), TimeUnit.DAYS);
                interest += (interestEarned(amount + interest) / 365) * daysDifference;
            }

        }

        return interest;
    }

    public int getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
