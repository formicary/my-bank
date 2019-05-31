package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Account {

    private static final String txtNOT_DEFINED = "Not Defined";
    private static final String txtAccountTypeName_SAVINGS = "Savings Account";
    private static final String txtAccountTypeName_CHECKING = "Checking Account";
    private static final String txtAccountTypeName_MAXI_SAVINGS = "Maxi Savings Account";

    private static final double interestLimitSAVINGS = 1000;
    private static final double interestPercentBelowLimitSAVINGS = 0.001;
    private static final double interestPercentAboveLimitSAVINGS = 0.002;

    private static final double interestPercentCHECKING = 0.001;

    private static final double interestLimit_1_MAXI_SAVINGS = 1000;
    private static final double interestLimit_2_MAXI_SAVINGS = 2000;
    private static final double interestPercentBelowLimit_1_MAXI_SAVINGS = 0.02;
    private static final double interestPercentBelowLimit_2_MAXI_SAVINGS = 0.05;
    private static final double interestPercentAboveLimit_2_MAXI_SAVINGS = 0.10;

    private static final double interestPercentNoWithdrawal_MAXI_SAVINGS = 0.05;
    private static final double interestNoWithdrawalPeriodInDays = 10;

    private static final double millisecondsInADay = 24 * 3600 * 1000;


    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS;
    }

    private AccountType accountType;
    public List<Transaction> transactions;

    public AccountType getAccountType() {
        return this.accountType;
    }


    public String getAccountTypeName() {

        String accountTypeName = txtNOT_DEFINED;

        switch (this.accountType) {
            case CHECKING:
                accountTypeName = txtAccountTypeName_CHECKING;
                break;
            case SAVINGS:
                accountTypeName = txtAccountTypeName_SAVINGS;
                break;
            case MAXI_SAVINGS:
                accountTypeName = txtAccountTypeName_MAXI_SAVINGS;
                break;
        }
        return accountTypeName;
    }


    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount, Date dueDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, dueDate));
        }
    }

    public void withdraw(double amount, Date dueDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, dueDate));
        }
    }


    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    public Date getLastWithdrawalDate() {
        Date lastWithdrawal = new Date(70, 01, 01);
        for (Transaction t : transactions)
            if (t.getTransactionType() == Transaction.TransactionType.WITHDRAWAL) {
                lastWithdrawal = t.getTransactionDate();
            }
        return lastWithdrawal;
    }

    public double interestEarnedPORTFOLIO_1() {
        double interest = 0;

        double amount = sumTransactions();

        if (amount > 0) {

            switch (this.accountType) {
                case CHECKING:
                    interest = amount * interestPercentCHECKING;
                    break;
                case SAVINGS:
                    if (amount <= interestLimitSAVINGS)
                        interest = amount * interestPercentBelowLimitSAVINGS;
                    else
                        interest = (interestLimitSAVINGS * interestPercentBelowLimitSAVINGS)
                                + (amount - interestLimitSAVINGS) * interestPercentAboveLimitSAVINGS;
                    break;
                case MAXI_SAVINGS:
                    // Original feature
                    if (amount <= interestLimit_1_MAXI_SAVINGS)
                        interest = amount * interestPercentBelowLimit_1_MAXI_SAVINGS;
                    else {

                        if (amount <= interestLimit_2_MAXI_SAVINGS)
                            interest = (interestLimit_1_MAXI_SAVINGS * interestPercentBelowLimit_1_MAXI_SAVINGS)
                                    + (amount - interestLimit_1_MAXI_SAVINGS) * interestPercentBelowLimit_2_MAXI_SAVINGS;

                        else
                            interest = (interestLimit_1_MAXI_SAVINGS * interestPercentBelowLimit_1_MAXI_SAVINGS)
                                    + (interestLimit_2_MAXI_SAVINGS - interestLimit_1_MAXI_SAVINGS) * interestPercentBelowLimit_2_MAXI_SAVINGS
                                    + (amount - interestLimit_2_MAXI_SAVINGS) * interestPercentAboveLimit_2_MAXI_SAVINGS;
                    }
            }

        }

        return interest;
    }

    public double interestEarnedPORTFOLIO_2() {
        double interest = 0;

        double amount = sumTransactions();

        if (amount > 0) {

            switch (this.accountType) {
                case CHECKING:
                    interest = amount * interestPercentCHECKING;
                    break;
                case SAVINGS:
                    if (amount <= interestLimitSAVINGS)
                        interest = amount * interestPercentBelowLimitSAVINGS;
                    else
                        interest = (interestLimitSAVINGS * interestPercentBelowLimitSAVINGS)
                                + (amount - interestLimitSAVINGS) * interestPercentAboveLimitSAVINGS;
                    break;
                case MAXI_SAVINGS:
                    // Additional feature
                    // Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10 days
                    if (this.getLastWithdrawalDate().getTime() > interestNoWithdrawalPeriodInDays * millisecondsInADay) {
                        interest = amount * interestPercentNoWithdrawal_MAXI_SAVINGS;
                    } else {
                        // otherwise 0.1%
                        interest = amount * interestPercentBelowLimit_1_MAXI_SAVINGS;
                    }
            }

        }

        return interest;
    }

}
