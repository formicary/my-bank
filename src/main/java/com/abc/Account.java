package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
    public List<Transaction> transactions;

    public Account(){
        transactions = new ArrayList<Transaction>();
    }

    public double interestEarned(){
        double amount = sumTransactions();
        double interest = amount * (0.1/365);
        this.deposit(interest);
        return interest;
    }

    public void deposit(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Illegal Withdrawal. Make sure amount is greater than zero.");
        }else if (amount > sumTransactions()){
            throw new IllegalArgumentException("Illegal Withdrawal. You do not have that amount in your account");
        }else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public String getAccountType() {
        return "Current Account\n";
    }
}
