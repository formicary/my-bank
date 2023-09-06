package com.abc.Interfaces;

import com.abc.MainClasses.Account;
import com.abc.MainClasses.Customer;

//Interface for Customer class
public interface CustomerInterface {
    public String getName();

    public Customer openAccount(Account account);

    public int getNumberOfAccounts();

    public double totalInterestEarned();

    public String[] getStatement();

    public String statementForAccount(Account account);

    public double[] transferFunds(double amount, Account fromAccount, Account toAccount) throws Exception;

    public String toDollars(double amount);
}
