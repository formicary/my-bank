package com.abc.Interfaces;

import com.abc.MainClasses.AccountType;
//Interface for Account class
public interface AccountInterface {
    public void deposit(double amount);

    public void withdraw(double amount);

    public double interestEarned();

    public double sumTransactions();

    public AccountType getAccountType();
}