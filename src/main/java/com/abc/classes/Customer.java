package com.abc.classes;

import java.util.ArrayList;
import java.util.List;

import com.abc.classes.Account.AccountType;
import com.abc.helpers.CustomerStatementBuilder;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        accounts = new ArrayList<Account>();
    }

    public Account openAccount(AccountType accountType) {
        Account newAccount = new Account(accountType);
        accounts.add(newAccount);
        return newAccount;
    }

    // Getters//
    public Customer getCustomer() {
        return this;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public String getAccountStatement(Customer customer, Account account) {
        String name = customer.getName();

        return CustomerStatementBuilder.createStatement(name, account);
    }

    public String getAllAccountStatements(Customer customer) {
        String name = customer.getName();

        return CustomerStatementBuilder.createStatement(name, accounts);
    }

    public double getTotalInterestEarned() {
        double total = 0;
        try {
            for (Account a : accounts)
                total += a.getAccruedInterest();
            return total;
        } catch (NullPointerException e) {
            System.out.println("No customers found");
            e.printStackTrace();
            return total;
        }
    }

    // ADDITIONAL FEATURES//
    // A customer can transfer between their accounts
    public void transferBetweenAccounts(double amount, Account from, Account to) {
        from.tryWithdraw(amount);
        to.tryDeposit(amount);
    }

}
