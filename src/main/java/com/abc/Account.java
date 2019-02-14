package com.abc;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    private final int DAYS_WITHOUT_WITHDRAW = 10;
    public static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");


    private final int accountType;
    private double balance;
    private boolean isLinkedToCustomer;


    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }


    //Basic checks for any transaction
    public void basicTransactionChecks(double amount) {
        if (!isLinkedToCustomer) {
            throw new UnsupportedOperationException("Account not linked with customer");
        } else if (amount <= 0) {
            throw new IllegalArgumentException("The amount needs to be a positive number");
        }
    }

    public void deposit(double amount) {
        basicTransactionChecks(amount);
        finishTransaction(amount, false);

    }

    public void withdraw(double amount) {
        basicTransactionChecks(amount);
        if (amount >= balance) {
            throw new IllegalArgumentException("The withdraw amount should be less or equal to the balance");
        } else {
            finishTransaction(amount, true);
        }
    }


    public void finishTransaction(double amount, boolean isWithDrawable) {
        balance = isWithDrawable ? balance - amount : balance + amount;
        transactions.add(new Transaction(accountType, amount, isWithDrawable));
    }


    //Calculate the amount of total interest based on the account type
    public double getEarnedInterest() {
        double defaultInterest = 0.001;
        int limitIncrement = 1000;

        double earnings;


        switch (accountType) {
            case SAVINGS:
                if (balance <= limitIncrement) {
                    earnings = balance * defaultInterest;
                } else {
                    earnings = 1 + (balance - limitIncrement) * 0.002;
                }
                break;

            case MAXI_SAVINGS:
                if (!hasWithdrawInLastNDays(DAYS_WITHOUT_WITHDRAW)) {
                    earnings = balance * 0.05;
                    break;
                }

            default:
                earnings = balance * defaultInterest;
                break;
        }


        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int daysOfYear = calendar.get(calendar.DAY_OF_YEAR);

        return Double.valueOf(decimalFormatter.format(earnings / daysInYear * daysOfYear));
    }

    /**
     * Check if there has been any transactions in the period N-days ago.
     *
     * @param daysAgo
     * @return
     */
    public boolean hasWithdrawInLastNDays(int daysAgo) {

        if (daysAgo < 0) {
            throw new IllegalArgumentException("Days ago should be positive");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -(daysAgo + 1));


        final Date fromDate = calendar.getTime();

        List<Transaction> relevantTransactions = transactions.stream().filter(t -> !t.getTransactionDate().before(fromDate)).collect(Collectors.toList());

        for (Transaction t : relevantTransactions)
            if (t.isWithdrawable()) {
                return true;
            }

        return false;


    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public int getAccountType() {
        return accountType;
    }

    public boolean linkAccWithCustomer() {
        return isLinkedToCustomer = true;
    }

    public boolean isAccLinkedCustomer() {
        return isLinkedToCustomer;
    }

    public double getBalance() {
        return balance;
    }

}
