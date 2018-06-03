package com.abc;

import java.math.BigDecimal;
import java.util.*;

/**
 * This is a class for Account. It contains the its account type and a list of transactions.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version  03/05/2018
 */
public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    /**
     * A defensive constructor that allows the creation of 3 different type of accounts
     * @param accountType The account type of the account.
     */
    public Account(int accountType) {
        if (accountType != 0 && accountType != 1 && accountType != 2) {
            throw new IllegalArgumentException("The chosen account type does not exits");
        } else {
            this.accountType = accountType;
            this.transactions = new ArrayList<Transaction>();
        }
    }

    /**
     * A getter for a list of transactions
     * @return A list of transactions.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * A method for depositing fund into the account.
     * @param amount The amount of money to be deposited.
     */
    public void deposit(BigDecimal amount) {
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * A method for withdrawing fund from the account.
     * @param amount The amount of money to be withdrawn.
     */
    public void withdraw(BigDecimal amount) {
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if (sumTransactions().doubleValue() > amount.doubleValue()) {
                transactions.add(new Transaction(amount.negate()));
            } else {
                throw new IllegalArgumentException("Insufficient funds in this account");
            }
        }
    }

    //days used 360 for financial market

    /**
     * This is a method for calculating the amount of interest earned in this account.
     * Assumption: For financial calculation, 360 days is used as a year.
     * Checking account have a flat rate of 0.1%
     * Savings account have a rate of 0.1% for the first $1,000 then 0.2%
     * Maxi-Savings account have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
     * Interest rates is accrue and compound daily (incl. weekends).
     * @return The total amount of interest earned.
     */
    public BigDecimal interestEarned() {

        BigDecimal amount = new BigDecimal(0);
        BigDecimal totalInterest = new BigDecimal(0);
        Date now = DateProvider.getInstance().now();

        switch(accountType){

            case SAVINGS:

                for (int i = 0; i < transactions.size(); i++) {

                    amount = amount.add(transactions.get(i).getAmount());

                    if (i == transactions.size() - 1) {
                        if (amount.doubleValue() <= 1000) {
                            totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), now))).multiply(new BigDecimal((0.001 / 360))));
                        } else {
                            totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), now))).multiply(new BigDecimal((0.002 / 360))));
                        }
                    } else {
                        if (amount.doubleValue() <= 1000) {
                            totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()))).multiply(new BigDecimal((0.001 / 360))));
                        } else {
                            totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()))).multiply(new BigDecimal((0.002 / 360))));
                        }
                    }
                }

                return totalInterest;

            case MAXI_SAVINGS:

                for (int i = 0; i < transactions.size(); i++) {

                    amount = amount.add(transactions.get(i).getAmount());

                    if (i == transactions.size() - 1) {
                        if (DateProvider.getInstance().now().compareTo(transactions.get(i).getTransactionDatePlus10Days()) >= 0) {
                            totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), now))).multiply(new BigDecimal(0.05 / 360)));
                        } else
                            totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), now))).multiply(new BigDecimal(0.001 / 360)));
                    } else {
                        if (transactions.get(i).getTransactionDate().compareTo(transactions.get(i + 1).getTransactionDatePlus10Days()) < 0) {
                            totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()))).multiply(new BigDecimal(0.05 / 360)));
                        } else
                            totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()))).multiply(new BigDecimal(0.001 / 360)));
                    }
                }
                return totalInterest;

            default:

                for (int i = 0; i < transactions.size(); i++) {

                    amount = amount.add(transactions.get(i).getAmount());

                    if (i == transactions.size() - 1) {
                        totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), now))).multiply(new BigDecimal((0.001 / 360))));
                    } else {
                        totalInterest = totalInterest.add(amount.multiply(new BigDecimal(daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()))).multiply(new BigDecimal((0.001 / 360))));
                    }

                }
                return totalInterest;
        }
    }

    /**
     * This is a method for calculating the number of days between two dates.
     * The second date should be greater or equal to the first date.
     * @param d1 The first date
     * @param d2 The second date
     * @return The number of dates between two dates.
     */
    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * This method works out the total saving of the account.
     * @return The total saving.
     */
    public BigDecimal sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    /**
     * This is a helper method for sumTransactions
     * @param checkAll checks if transactions exist.
     * @return the total amount of saving.
     */
    private BigDecimal checkIfTransactionsExist(boolean checkAll) {
        BigDecimal amount = new BigDecimal(0);
        for (Transaction t: transactions)
            amount = amount.add(t.getAmount());
        return amount;
    }

    /**
     * A getter for the account type.
     * @return The account type.
     */
    public int getAccountType() {
        return accountType;
    }

}
