package com.abc;

import java.util.ArrayList;
import java.util.List;


public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
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
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.statementForAccount() + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + Utilities.toDollars(total);
        return statement;
    }



    /**
     * 0. validate that accounts are not null and belong to this Customer
     * 1. validate if accountFrom has sufficient amount of money to be transfered
     * 2. amount must be more than zero
     * 3. 
     * TODO: consider adding transfer statuses to be outputted for particular interface instead of exceptions
     *  
     * @param accountTo
     * @param amount
     */
    public void transferMoney(Account accountFrom, Account accountTo, double amount) throws NullPointerException, IllegalArgumentException {
        if(amount <= 0){
            throw new IllegalArgumentException("Amount of money transfered must be more than 0. Current: " + amount);
        }
        if(accountFrom == null || accountTo == null){
            throw new NullPointerException("Account money trasfered from or/and account transfered to does not exist");
        }
        if(accountFrom == accountTo){
            throw new IllegalArgumentException("Cannot transfer money to the same account");
        }

        double accountFromDebit = accountFrom.sumTransactions();
        if(accountFromDebit < amount){
            throw new IllegalArgumentException(
            "Account has has insufficient funds to perform the operation.\n" +
            "Money in the account :" + 
            Utilities.toDollars(accountFromDebit) + ". Amount attempted to transfer: " + Utilities.toDollars(amount));
        }

        //validate if accountFrom and accountTo belong to the same Customer
        boolean belongToSameAccount = false;
        boolean customersAccountFrom = false;
        boolean customersAccountTo = false;

        for(Account a : accounts){
            if(a == accountFrom) customersAccountFrom = true;
            if(a == accountTo) customersAccountTo = true;
            if( customersAccountFrom && customersAccountTo ){
                belongToSameAccount = true;
                break;
            }
        }

        if(!belongToSameAccount){
            throw new IllegalArgumentException("Provided account(s) do not belong the same Customer");
        }

        //transfer the money
        accountFrom.withdraw(amount);
        accountTo.deposit(amount);

    }



}
