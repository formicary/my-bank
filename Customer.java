package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private static long customersEverCreated = 1L;
    private long id;
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.id = customersEverCreated++;
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    // creates a new account for a customer based on account type (1-Checking account, 2-Savings account, 3-Maxi-Savings account)
    // account is created with a unique number
    // return value is the new instance of Account object
    // null is returned in case of unrecognized parameter value
    public Account openAccount(int accountType) {
        switch (accountType) {
            case 1: {
                CheckingAccount newAccount = new CheckingAccount();
                accounts.add(newAccount);
                System.out.println("New "+newAccount.getType()+" account with account number "+newAccount.getNumber()+" has been successfully opened.");
                return newAccount;
            }
            case 2: {
                SavingsAccount newAccount = new SavingsAccount();
                accounts.add(newAccount);
                System.out.println("New "+newAccount.getType()+" account with account number "+newAccount.getNumber()+" has been successfully opened.");
                return newAccount;
            }
            case 3: {
                MaxiSavingsAccount newAccount = new MaxiSavingsAccount();
                accounts.add(newAccount);
                System.out.println("New "+newAccount.getType()+" account with account number "+newAccount.getNumber()+" has been successfully opened.");
                return newAccount;
            }
            default: {
                System.out.println("Unknown account type, no account has been opened.");
                return null;
            }
        }
    }

    // finds customer's account based on account number
    // return value is the instance of Account object found
    // null is returned in case account was not found
    private Account findAccount(long number) {
        if(!accounts.isEmpty()) {
            for(Account account:accounts) {
                if(account.getNumber() == number) {
                    return account;
                }
            }
        }
        return null;
    }

    // deposit of positive amount on customer's account
    // returns true if the deposit was successful
    // returns false if the account does not exist
    public boolean deposit(long accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if(account != null) {
            return (account.deposit(amount));
        }else {
            System.out.println("Account with number "+accountNumber+" does not exist.");
            return false;
        }
    }

    // withdrawal of either positive or negative amount from customer's account
    // returns true if the withdrawal was successful
    // returns false if the account does not exist
    public boolean withdraw(long accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if(account != null) {
            return (account.withdraw(amount));
        }else {
            System.out.println("Account with number "+accountNumber+" does not exist.");
            return false;
        }
    }

    // prints the formatted output of all transactions including interest earning deposits at the end of each day for a customer's account
    public void showAllTransactions(long number) {
        Account account = findAccount(number);
        if(account != null) {
            System.out.println(account.showAllTransactions());
            }else {
            System.out.println("Account with number " + number + " does not exist.");
        }
    }

    // gets the list of all transactions including interest earning deposits at the end of each day for a customer's account
    // return value is a List of consecutive transactions of the account as instances of Transaction
    // returns null if the account does not exist
    public List<Transaction> getAllTransactions(long number) {
        Account account = findAccount(number);
        if (account != null) {
            return account.buildTransactions();
        } else {
            return null;
        }
    }

    // prints the formatted output of current balance of the customer's account
    public void showTotal(long number) {
        Account account = findAccount(number);
        if(account != null) {
            System.out.println("Current balance of account number "+number+" is: "+account.makeTotal());
        }else {
            System.out.println("Account with number "+number+" does not exist.");
        }
    }

    // gets the current balance of the customer's account
    // return value is a double containing the current balance
    // returns -1 double if the account does not exist
    public double getTotal(long number) {
        Account account = findAccount(number);
        if (account != null) {
            return account.makeTotal();
        } else {
            return -1d;
        }
    }

    // makes transfer of funds between two of customer's accounts
    // returns true if the transfer was successful
    // returns false in case one of the accounts does not exist
    public boolean makeTransfer(long accountNumberFrom, long accountNumberTo, double amount) {
        Account account1 = findAccount(accountNumberFrom);
        Account account2 = findAccount(accountNumberTo);
        if (account1 == null) {
            System.out.println("Account with account number " + accountNumberFrom + " does not exist.");
            return false;
        }else if (account2 == null) {
            System.out.println("Account with account number " + accountNumberTo + " does not exist.");
            return false;
        }else {
            if(!account1.withdraw(amount)) {
                return false;
            }else {
                account2.deposit(Math.abs(amount));
                return true;
            }
        }
    }

    // counts the number of customer's accounts
    // return value is integer containing the number of accounts customer owns
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // creates a copy of List, so no accounts can be added or deleted directly
    // returns a List of accounts referencing the original Account instances
    /*public List<Account> getAccounts() {
        List<Account> accountsCopy = new ArrayList<>(accounts);
        return accountsCopy;
    }*/

    // only temporary use for testing purposes
    public List<Account> getAccounts() {
        return accounts;
    }
}