package com.abc.transactions;

import com.abc.DateProvider;
import com.abc.exceptions.NonPositiveAmountException;

import java.util.Date;


public class Deposit implements Transaction {
    private Date transactionDate;
    private double amount;
    private double currentBalance;

    public Deposit(double currentBalance, double amount) {
        this.transactionDate = DateProvider.getInstance().now();
        this.currentBalance = currentBalance;
        this.amount = amount;
    }

    public double executeTransaction() throws NonPositiveAmountException {

        if (amount <= 0) {
            throw new NonPositiveAmountException("amount must be greater than zero");
        } else {
            currentBalance = currentBalance + amount;
        }
        return currentBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deposit deposit = (Deposit) o;

        if (Double.compare(deposit.amount, amount) != 0) return false;
        if (Double.compare(deposit.currentBalance, currentBalance) != 0) return false;
        return transactionDate != null ? transactionDate.equals(deposit.transactionDate) : deposit.transactionDate == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = transactionDate != null ? transactionDate.hashCode() : 0;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(currentBalance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }


    public double getAmount() {
        return amount;
    }

}
