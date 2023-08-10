package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    enum AccountType {CHECKING, SAVINGS, MAXI_SAVINGS}
    AccountType accountType;
    public List<Transaction> transactions;
    private double balance;

    public Account(AccountType test) {
        this.accountType = test;
        this.balance = 0; //Assuming a new account opens with an empty balance 
        this.transactions = new ArrayList<Transaction>();
    }

    //Getters//
    public Account getAccount(){
        return this;
    }

    public AccountType getAccountType(){
        return accountType;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public double getBalance(){
        return balance;
    }

    //Transaction Functions//
    //Update balance, called everytime a deposit or withdrawal is made
    public void updateBalance(double amount){
        balance += amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            updateBalance(amount);
        }
    }

public void withdraw(double amount) {
    if (amount <= 0 || amount > balance) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
        updateBalance(-amount);
    }
}

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

    //CURRENTLY UNUSED, LOOK AT LATER DATE///
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }





public static void main(String[] args) {
    Account testAccount = new Account(AccountType.CHECKING);

    testAccount.deposit(200);
    testAccount.withdraw(20);
    testAccount.withdraw(20);

    System.out.println(testAccount.balance);

    List<Transaction> transactions = testAccount.getTransactions();
    for (Transaction t: transactions)
            System.out.println(t.getTransactionDate() + " " + t.getTransactionAmount());


}
}
