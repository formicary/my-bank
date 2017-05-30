package com.abc;

import com.abc.exceptions.*;
import com.abc.transactions.Deposit;
import com.abc.transactions.Transaction;
import com.abc.transactions.Transfer;
import com.abc.transactions.Withdrawal;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Account {
    protected long accountID;
    protected Map<Date, Transaction> transactions;
    protected double balance;
    protected static final int DAYS_A_YEAR = 365;
    protected Date lastTransactionDate;
    protected Lock lock = new ReentrantLock();

    public abstract double interestEarned();


    public void deposit(double amount) throws NonPositiveAmountException {

        lock.lock();
        Deposit deposit = new Deposit(balance, amount);
        balance = deposit.executeTransaction();
        transactions.put(deposit.getTransactionDate(), deposit);
        lastTransactionDate = deposit.getTransactionDate();
        lock.unlock();
    }

    public void withdraw(double amount) throws ExceedsNegativeBalanceException, NonPositiveAmountException {
        lock.lock();
        Withdrawal withdrawal = new Withdrawal(balance, amount);
        balance = withdrawal.executeTransaction();
        transactions.put(withdrawal.getTransactionDate(), withdrawal);
        lastTransactionDate = withdrawal.getTransactionDate();
        lock.unlock();
    }

    public void transfer(int toCustomerId, int toAccountID, double amount, Bank bank) throws CustomerNotExistException, IdenticalAccountIDException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {

        if (!(bank.getSystemManagement().getCustomers().containsKey(toCustomerId))) {
            throw new CustomerNotExistException("Transfer transaction failed: Customer with Id " + toCustomerId + " and account id " + toAccountID + " does not exist!");

        } else if (!bank.getSystemManagement().getCustomers().get(toCustomerId).getAccountManagement().getAccounts().containsKey(toAccountID)) {
            throw new AccountNotExistException("Transfer transaction failed: Account id " + toAccountID + " does not exist!");

        } else {
            Account account = bank.getSystemManagement().getCustomers().get(toCustomerId).getAccountManagement().getAccounts().get(toAccountID);
            if (this.accountID == account.accountID) {
                throw new IdenticalAccountIDException("Transfer transaction failed: The source account should be different from the destination account!");

            }
            lock.lock();
            Transfer transfer = new Transfer(balance, amount, this, account);
            balance = transfer.executeTransaction();
            transactions.put(transfer.getTransactionDate(), transfer);
            lastTransactionDate = transfer.getTransactionDate();
            lock.unlock();
        }

    }

    public double accrueInterestDaily() {
        double interest = interestEarned();
        Deposit deposit = new Deposit(balance, interest / DAYS_A_YEAR);
        transactions.put(deposit.getTransactionDate(), deposit);
        return interest / DAYS_A_YEAR;
    }

    public Collection<Transaction> getTransactions() {
        return this.transactions.values();
    }

    public double getBalance() {
        return balance;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public Date getLastTransactionDate() {
        return lastTransactionDate;
    }
}
