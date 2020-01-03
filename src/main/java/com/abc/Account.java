package com.abc;

import java.util.Calendar;
import java.util.Comparator;
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
        this.transactions = new PriorityQueue<Transaction>(11, new Comparator<Transaction>() {
            public int compare(Transaction o1, Transaction o2) {
                return o1.getTransactionDate().compareTo(o2.getTransactionDate());
            }
        });
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
        double amount = sumTransactions();
        switch (accountType) {
            case CHECKING:
                return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                } else {
                    return 1 + (amount-1000) * 0.002;
                }
            case MAXI_SAVINGS:
                boolean noRecentWithdrawals = true;
                Calendar now = Calendar.getInstance();
                // Set now to the start of the day
                CalendarHelper.calendarToMidnight(now);
                Calendar transactionCalendar;
                for (Transaction t : transactions) {
                    transactionCalendar = t.getTransactionDate();
                    CalendarHelper.calendarToMidnight(transactionCalendar);
                    if (CalendarHelper.milisecondDifference(now, transactionCalendar) <= 10 * 24 * 60 * 60 * 1000) {
                        noRecentWithdrawals = false;
                        break;
                    }
                }
                if (noRecentWithdrawals) {
                    return amount * 0.05;
                } else {
                    return amount * 0.001;
                }
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
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
