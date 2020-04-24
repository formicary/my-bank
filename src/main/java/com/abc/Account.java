package com.abc;

import java.util.List;

public interface Account {

    double calculateInterest();
    String getAccountType();
    void deposit(double amount);
    void withdraw(double amount);
    void transferAmount(double amount, Account otherAccount);
    List<Transaction> getTransactions();
    double getBalance();
}
