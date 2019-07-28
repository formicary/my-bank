package com.abc;

import java.util.ArrayList;
import java.util.List;


public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        return Statement.generateCustomerStatement(this);
    }

    /**
     * Transfers money from one account to another, provided that they both belong to this customer
     * @param from account to withdraw money from
     * @param to account to deposit money into
     */
    public void transferBetweenAccounts(Account from, Account to, double amount){

        // Ensure that both accounts belong to the customer
        Boolean foundFrom = false;
        Boolean foundTo = false;
        for(Account a : this.accounts){
            if (a==from) foundFrom = true;
            if (a==to) foundTo = true;
        }

        if (foundFrom && foundTo){
            from.withdraw("Account transfer", amount);
            to.deposit("Account transfer", amount);
        }
        else{
            throw new IllegalArgumentException("Accounts most both belong to customer");
        }

    }

}
