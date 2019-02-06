package com.abc;

import java.util.*;
import java.util.stream.Collectors;
import java.util.GregorianCalendar;

public class Account {

    private final ACCOUNT_TYPE accountType;
    private List<Transaction> transactions;
    private double balance;
    private final short DAYS_WITHOUT_WITHDRAWAL = 10;
    private boolean isLinkedWithCustomer;

    public enum ACCOUNT_TYPE {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS
    }

    public Account(ACCOUNT_TYPE accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        performCommonTransactionChecks(amount);
        completeTransaction(amount, false);
    }

    public void withdraw(double amount) {
        performCommonTransactionChecks(amount);

        if (amount >= balance)
            throw new IllegalArgumentException("Withdrawal amount should not exceed account balance");
        else
            completeTransaction(amount, true);
    }

    // Common checks for both deposit and withdrawal transactions
    public void performCommonTransactionChecks(double amount) {
        if (!isLinkedWithCustomer)
            throw new UnsupportedOperationException("Account is not linked with a customer");
        else if (amount <= 0)
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
    }

    private void completeTransaction(double amount, boolean isWithdrawal) {
        balance = isWithdrawal ? balance - amount : balance + amount;
        transactions.add(new Transaction(accountType, amount, isWithdrawal));
    }

    // Checks whether the account has any withdrawal transactions in the period
    // daysAgo - today(inclusive)
    public boolean hasWithdrawalInTheLastNDays(short daysAgo) {

        if (daysAgo < 0)
            throw new IllegalArgumentException("The number of days ago should be positive");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, - (daysAgo + 1));

        final Date fromDate = calendar.getTime();

        List<Transaction> relevantTransactions = transactions.stream().filter(t -> !t.getTransactionDate().before(fromDate)).collect(Collectors.toList());

        for (Transaction transaction : relevantTransactions)
            if (transaction.isWithdrawal())
                return true;

        return false;
    }

    //Calculates the account's total earned interest depending on its type
    public Double getEarnedInterest() {
        double default_interest = 0.001;
        int threshold_increment = 1000;
        double earnings;

        switch (accountType) {
            case SAVINGS:
                if (balance <= threshold_increment)
                    earnings = balance * default_interest;
                else
                    earnings = 1 + (balance - threshold_increment) * 0.002;
                break;
            case MAXI_SAVINGS:
                if (!hasWithdrawalInTheLastNDays(DAYS_WITHOUT_WITHDRAWAL)) {
                    earnings = balance * 0.05;
                    break;
                }
            default:
                earnings = balance * default_interest;
                break;
        }

        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(calendar.DAY_OF_YEAR);

        return Double.valueOf(ReportFormatter.decimalFormatter.format(earnings / daysInYear * dayOfYear));
    }

    public ACCOUNT_TYPE getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return balance;
    }

    public void linkWithCustomer() {
        isLinkedWithCustomer = true;
    }

    public boolean isLinkedWithCustomer() {
        return isLinkedWithCustomer;
    }

}
