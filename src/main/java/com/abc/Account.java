package com.abc;

import java.math.BigDecimal;
import java.util.*;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(BigDecimal amount) {
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

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

    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }


    public BigDecimal sumTransactions() {
       return checkIfTransactionsExist(true);
    }


    private BigDecimal checkIfTransactionsExist(boolean checkAll) {
        BigDecimal amount = new BigDecimal(0);
        for (Transaction t: transactions)
            amount = amount.add(t.getAmount());
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
