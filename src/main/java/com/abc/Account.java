package com.abc;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import static java.lang.Math.abs;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private PriorityQueue<Transaction> transactions;

    public Account(int accountType) {
        if (accountType < 0 || accountType > 2)
            throw new IllegalArgumentException("Invalid account type " + accountType);
        this.accountType = accountType;
        this.transactions = new PriorityQueue<>(11, Comparator.comparing(Transaction::getTransactionDate));
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else if (sumTransactions() < amount) {
            throw new IllegalArgumentException("Amount must not be greater than available balance");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double interestEarned() {
        if (transactions.isEmpty()) return 0;

        double amount = 0;

        Iterator<Transaction> it = transactions.iterator();
        Transaction nextTransaction = it.next();

        Calendar currentTime = nextTransaction.getTransactionDate();
        CalendarHelper.calendarToMidnight(currentTime);
        Calendar now = CalendarHelper.now();


        while (currentTime.before(now)) {
            amount += dayInterestOnAmount(amount, currentTime);

            while (nextTransaction != null && nextTransaction.getTransactionDate().before(currentTime)) {
                amount += nextTransaction.amount;
                if (!it.hasNext()) nextTransaction = null;
                else nextTransaction = it.next();
            }

            currentTime.add(Calendar.DATE, 1);
        }

        return amount - sumTransactions();
    }

    private double dayInterestOnAmount(double amount, Calendar from) {
        switch (accountType) {
            case CHECKING:
                return amount * 0.001 / 365;
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001 / 365;
                } else {
                    return 1D / 365 + (amount - 1000) * 0.002 / 365;
                }
            case MAXI_SAVINGS:
                if (isRecentWithdrawals(10, from)) {
                    return amount * 0.001 / 365;
                } else {
                    return amount * 0.05 / 365;
                }
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }

    private double dayInterestOnAmount(double amount) {
        return dayInterestOnAmount(amount, CalendarHelper.now());
    }

    private boolean isRecentWithdrawals(int numberOfDays) {
        return isRecentWithdrawals(numberOfDays, CalendarHelper.now());
    }

    private boolean isRecentWithdrawals(int numberOfDays, Calendar from) {
        Calendar fromCopy = (Calendar) from.clone();
        CalendarHelper.calendarToMidnight(fromCopy);
        Calendar transactionCalendar;
        for (Transaction t : transactions) {
            transactionCalendar = t.getTransactionDate();
            CalendarHelper.calendarToMidnight(transactionCalendar);
            if (CalendarHelper.milisecondDifference(fromCopy, transactionCalendar) <= numberOfDays * 24 * 60 * 60 * 1000) {
                return true;
            }
        }
        return false;
    }

    public double sumTransactions() {
        return sumTransactions(CalendarHelper.now());
    }

    private double sumTransactions(Calendar upto) {
        return (sumTransactions(CalendarHelper.minimumDate(), upto));
    }

    private double sumTransactions(Calendar from, Calendar upto) {
        if (from.after(upto)) throw new IllegalArgumentException("date from cannot be after upto");
        double amount = 0.0;
        for (Transaction t : transactions) {
            if (t.getTransactionDate().before(from)) continue;
            if (t.getTransactionDate().after(upto)) break;
            amount += t.amount;
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    private static String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    public String statement() {
        StringBuilder s = new StringBuilder();

        //Translate to pretty account type
        switch (getAccountType()) {
            case Account.CHECKING:
                s.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                s.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            s.append("  ").append(t.amount < 0 ? "withdrawal" : "deposit").append(" ").append(toDollars(t.amount)).append("\n");
            total += t.amount;
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }
}
