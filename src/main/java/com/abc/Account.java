package com.abc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private final Date creationDate;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.creationDate = DateProvider.getInstance().now();
    }

    //Note: this method will mostly be used for testing purposes
    public Account(int accountType, Date cDate) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.creationDate = cDate;
    }

    /**
     * Creates a transaction for a deposit with the date set to today,
     * first checks that the amount is greater than zero
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Date currentDate = DateProvider.getInstance().now();
            transactions.add(new Transaction(amount, currentDate, Transaction.DEPOSIT));
        }
    }

    /**
     * Creates a transaction for a deposit that was made on DATE, first checking that date is after the account was created
     * and is the amount is greater than zero
     */
    public void deposit(double amount, Date transactionDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (creationDate.after(transactionDate)) {
            throw new IllegalArgumentException("transactions must be done after the account is created");
        } else {
            transactions.add(new Transaction(amount, transactionDate, Transaction.DEPOSIT));
        }
    }

    /**
     * creates a transaction for a withdrawal with the date set to today,
     * first checks that the amount is greater than zero
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Date currentDate = DateProvider.getInstance().now();
            transactions.add(new Transaction(-amount, currentDate, Transaction.WITHDRAWAL));
        }
    }

    /**
     * Creates a transaction for a withdrawal that was made on DATE, first checking that date is after the account was created
     * and is the amount is greater than zero
     */
    public void withdraw(double amount, Date transactionDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (creationDate.after(transactionDate)) {
            throw new IllegalArgumentException("transactions must be done after the account is created");
        } else {
            transactions.add(new Transaction(-amount, transactionDate, Transaction.WITHDRAWAL));
        }
    }

    /**
     * Add and returns daily interest to the users account in the form of a interest transactions.
     * Checking accounts have a flat rate of 0.1%
     * Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
     * Maxi-Savings accounts have a rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
     * Note all above percentages are per annum and to gain daily values divide the values by 365
     * The final amount is rounded to 2 decimal places
     */
    public double addInterest() {
        double interest = 0.0;
        Date currentDate = DateProvider.getInstance().now();
        double balance = sumAllTransactions();
        switch (accountType) {
            case SAVINGS:
                if (balance <= 1000) {
                    interest = (balance * (0.001 / 365));
                    break;
                } else {
                    interest = (1 / 365) + ((balance - 1000) * (0.002 / 365));
                    break;
                }
            case MAXI_SAVINGS:
                if (lastWithdrawal(10)) {
                    interest = balance * (0.05 / 365);
                    break;
                } else {
                    interest = balance * (0.001 / 365);
                    break;
                }
            default:
                interest = balance * (0.001 / 365);
        }
        //round interest to 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        interest = Double.valueOf(df.format(interest));

        transactions.add(new Transaction(interest, currentDate, Transaction.INTEREST));
        return interest;
    }

    /**
     * This method returns the total amounts of interest ever earned on this account
     */
    public double interestEarned() {
        double amount = 0;
        for (Transaction t : transactions) {
            if (t.getTransactionType() == Transaction.INTEREST) {
                amount += t.amount;
            }
        }
        return amount;
    }

    /**
     * This get the sum of all transactions that have occured on this account
     * Also accounts for the current balance of the account.
     */
    public double sumAllTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.amount;
        }
        return amount;
    }


    public int getAccountType() {
        return accountType;
    }

    /**
     * This method returns true if the last withdrawal was more than DAYS ago,
     * and false other wise. If no withdrawal is there return true as well
     * To do this first find the current date, and then find the last withdrawal
     * and compare the find out how long time has occured between these two.
     * Assumptions based on transactions being in order of when they were done,
     * so latest transactions is at the rear of the list.
     */
    private boolean lastWithdrawal(int days) {
        Date currentDate = DateProvider.getInstance().now();
        for (int i = transactions.size(); i-- > 0; ) {
            if (transactions.get(i).getTransactionType() == Transaction.WITHDRAWAL) {
                //difference in milliseconds
                long diff = currentDate.getTime() - transactions.get(i).getTransactionDate().getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);
                return diffDays > days;
            }
        }
        return true;
    }
}
