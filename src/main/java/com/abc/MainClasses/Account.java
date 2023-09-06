package com.abc.MainClasses;

import java.util.ArrayList;
import java.util.List;

import com.abc.interestEarned;
import com.abc.Interfaces.AccountInterface;

public class Account implements AccountInterface {
    private final AccountType accountType;
    private double balance;
    //Changed transactions to private type and added corresponding get method
    private List<Transaction> transactions;
    
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    public double getBalance() {
        return balance;
    }

    public double interestEarned() {
        double amount = sumTransactions();

        switch(accountType) {
            //Return interest earned for the specific account
            case CHECKING:
                return interestEarned.interestEarnedForChecking(amount);
            case SAVINGS:
                return interestEarned.interestEarnedForSavings(amount);
            case MAXI_SAVINGS:
                return interestEarned.interestEarnedForMaxiSavings(this, amount);
            default:
                System.out.println("Account type " + accountType + " doesn't exist. It must be " +
                "CHECKING, SAVINGS, or MAXI_SAVINGS");
                return -1.0;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        
        for (Transaction t: transactions)
            amount += t.getAmount();
            
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}