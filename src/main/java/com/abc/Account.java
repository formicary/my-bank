package com.abc;

import com.abc.ENUMS.AccountType;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private double balance;

    private final AccountType accountType;
    public List<Transaction> transactions;

    // constructor
    public Account(AccountType accountType) { // I think this constructor should have balance as a property. Is setBalance needed then?
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    // move this function to a utils folder...
    public static void isPositiveAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    private void deductAmount(double amount) {
        double balance = this.getBalance();
        balance += amount;
        setBalance(balance);
    }

    public void deposit(double amount) {
        isPositiveAmount(amount);
        transactions.add(new Transaction(amount));
        deductAmount(amount);
    }

    public void withdraw(double amount) {
        isPositiveAmount(amount);
        transactions.add(new Transaction(-amount));
        balance -= amount; // extract to own function and call that function here
    }

    // refactor into modular functions
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) { // what is checkAll?
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
