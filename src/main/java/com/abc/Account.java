package com.abc;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
// import java.util.concurrent.TimeUnit;

import com.abc.Enums.AccountType;
import com.abc.Enums.TransactionType;
import static com.abc.AccountUtility.isBalanceEnough;
import static com.abc.AccountUtility.isValidAmount;
import static com.abc.AccountUtility.daysSinceFirstTransaction;

public class Account {

    private final AccountType accountType;
    public List<Transaction> transactions;
    private double balance = 0.0;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount, TransactionType transactionType) {
        isValidAmount(amount);
        addBalance(amount);
        transactions.add(new Transaction(amount, TransactionType.DEPOSIT));

    }

    public void withdraw(double amount, TransactionType transactionType) {
        isValidAmount(amount);
        isBalanceEnough(amount, this);
        removeBalance(amount);
        transactions.add(new Transaction(amount, transactionType));

    }

    // Balance functions
    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        double tempBalance = this.getBalance();
        tempBalance += amount;
        setBalance(tempBalance);
    }

    public void removeBalance(double amount) {
        double tempBalance = this.getBalance();
        tempBalance -= amount;
        setBalance(tempBalance);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double interestEarned() {
        double amount = sumTransactions();

        ;
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * (Math.pow(1 + (0.001 / 365), daysSinceFirstTransaction(this)) - 1);
                else
                    return (1000 * (Math.pow(1 + (0.001 / 365), daysSinceFirstTransaction(this)) - 1))
                            + (amount - 1000) * (Math.pow(1 + (0.002 / 365), daysSinceFirstTransaction(this)) - 1);
            case MAXI_SAVINGS:
                if (!checkWithdrawInPastTenDays()) {
                    return amount * (Math.pow(1 + (0.05 / 365), daysSinceFirstTransaction(this)) - 1);
                } else {
                    // 0.1% interest rate
                    return amount * (Math.pow(1 + (0.001 / 365), daysSinceFirstTransaction(this)) - 1);
                }
            default:
                return amount * (Math.pow(1 + (0.001 / 365), daysSinceFirstTransaction(this)) - 1);
        }
    }

    private boolean checkWithdrawInPastTenDays() {
        Date curDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        Date tenDaysBackDate = calendar.getTime();
        for (Transaction t : transactions) {
            if (t.getTransactionDate().after(tenDaysBackDate)) { // did withdraw
                if (t.getTransactionType() == TransactionType.WITHDRAW) {
                    return true;
                }
            }
        }
        return false;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
