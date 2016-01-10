package com.abc;

import java.util.Hashtable;

public class Customer {
    
    private final String name;
    private int numberOfAccounts;
    // Each account will be associated with a unique account number
    // (currently account numbers are 0, 1, 2, ... for simplicity)
    private Hashtable<Integer, Account> accounts;

    public Customer(String name) {
        this.name = name;
        numberOfAccounts = 0;
        accounts = new Hashtable<Integer, Account>();
    }

    public String getName() {
        return name;
    }

    public void openSavingsAccount() {
        openAccount(new SavingsAccount());
    }
    
    public void openCheckingAccount() {
        openAccount(new CheckingAccount());
    }
    
    public void openMaxiSavingsAccount() {
        openAccount(new MaxiSavingsAccount());
    }
    
    // Private method: to enforce opening of account inside this class
    private void openAccount(Account a) {
        accounts.put(numberOfAccounts, a);
        numberOfAccounts++;
    }
    
    public void depositFunds(int accountNumber, double amount) {
        checkAccountNumberExists(accountNumber);
        accounts.get(accountNumber).deposit(amount);
    }
    
    public void withdrawFunds(int accountNumber, double amount) {
        checkAccountNumberExists(accountNumber);
        accounts.get(accountNumber).withdraw(amount);
    }
    
    public void transferFundFundFrom(int thisAccount, int thatAccount, double amount) {
        checkAccountNumberExists(thisAccount);
        checkAccountNumberExists(thatAccount);
        accounts.get(thisAccount).withdraw(amount);
        accounts.get(thatAccount).deposit(amount);
    }
    
    private void checkAccountNumberExists(int accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Customer's account number " +
                   accountNumber + " does not exist.");
        }
    }
    
    public int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public double totalInterestEarned() {
        double total = 0;
        for (int accountNumber = 0; accountNumber < numberOfAccounts; accountNumber++)
            total += accounts.get(accountNumber).interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for " + name + "\n");
        double total = 0.0;
        for (int accountNumber = 0; accountNumber < numberOfAccounts; accountNumber++) {
            Account a = accounts.get(accountNumber);
            statement.append("\n" + a.statementForAccount() + "\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts " + Utils.toDollars(total));
        return statement.toString();
    }
   
    @Override
    public String toString() {
        return name;
    }
}
