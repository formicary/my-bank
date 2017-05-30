package com.abc.transactions;

import com.abc.Account;
import com.abc.DateProvider;
import com.abc.exceptions.ExceedsNegativeBalanceException;
import com.abc.exceptions.NonPositiveAmountException;

import java.util.Date;


public class Transfer implements Transaction {
    private Date transactionDate;
    private double amount;
    private double currentBalance;
    private Account fromAccount;
    private Account toAccount;


    public Transfer(double currentBalance, double amount, Account a, Account b) {
        this.transactionDate = DateProvider.getInstance().now();
        this.currentBalance = currentBalance;
        this.amount = amount;
        this.fromAccount = a;
        this.toAccount = b;
    }

    public double executeTransaction() throws ExceedsNegativeBalanceException, NonPositiveAmountException {
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        return currentBalance - amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfer = (Transfer) o;

        if (Double.compare(transfer.amount, amount) != 0) return false;
        if (Double.compare(transfer.currentBalance, currentBalance) != 0) return false;
        if (transactionDate != null ? !transactionDate.equals(transfer.transactionDate) : transfer.transactionDate != null)
            return false;
        if (fromAccount != null ? !fromAccount.equals(transfer.fromAccount) : transfer.fromAccount != null)
            return false;
        return toAccount != null ? toAccount.equals(transfer.toAccount) : transfer.toAccount == null;

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
        result = 31 * result + (fromAccount != null ? fromAccount.hashCode() : 0);
        result = 31 * result + (toAccount != null ? toAccount.hashCode() : 0);
        return result;
    }
}
