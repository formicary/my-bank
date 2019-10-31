package com.abc;

import java.time.Instant;
import java.util.*;


public final class Customer {
    private final String name;
    private final HashMap<String,Account> accounts; //Faster look-up than list, reference to an Account object is not
                                                    // needed

    public Customer(String name) {
        this.name = name;
        this.accounts = new LinkedHashMap<>();
    }

    public String getName() {
        return this.name;
    }

    /**
     * New Account object instantiated and added to HashMap<String,Account> accounts directly,
     * an Account cannot be created, modified and then added to a customer's accounts later.
     * @param accountType CHECKING,SAVINGS OR MAXI_SAVINGS
     * @param accountName String name of account, Mapped to Account object
     */
    public void openNewAccount(AccountType accountType, String accountName) {
        if (this.accounts.containsKey(accountName)){
            throw new IllegalArgumentException("An account with this name already exists");
        }
        Account customerAccount = new Account( accountType,accountName);
        this.accounts.put(accountName,customerAccount);
    }

    /**
     * Customers can only deposit/withdraw from their own Accounts
     * Customer needs the String name of their account rather than the reference to the Account object
     * @param accountName String name of their account
     */
    public void depositIntoAccount(double amount, String accountName){
        depositIntoAccount(amount,accountName,Instant.now());
    }
    /**
     * for testing purposes, allow deposits at future dates
     */
    public  void depositIntoAccount(double amount, String accountName, Instant depositDate){
        if (!this.accounts.containsKey(accountName)){
            throw new IllegalArgumentException("Customer does not own an account with this name");
        }
        accounts.get(accountName).deposit(amount,depositDate);
    }




    public  void withDrawFromAccount(double amount, String accountName){
        withDrawFromAccount(amount,accountName,Instant.now());
    }
    public  void withDrawFromAccount(double amount, String accountName, Instant withdrawalDate){
        if (!this.accounts.containsKey(accountName)){
            throw new IllegalArgumentException("Customer does not own account with this name");
        }
        this.accounts.get(accountName).withdraw(amount,withdrawalDate);
    }


    /**
     * Create statement showing totals and transactions for each of the customer's accounts
     * @return (String) statement
     */
    public String getStatement() {
        List<Account> currentAccounts = List.copyOf(this.accounts.values());
        StringBuilder statement = new StringBuilder("Statement for " + this.name + "\n");
        double totalTransactions = 0.0;
        double totalBalance =0.0;
        for (Account a : currentAccounts) {
            statement.append("\n").append(statementForAccount(a)).append("\n");
            totalTransactions += a.sumTransactions();
            totalBalance+=a.getBalance();
        }
        statement.append("\nTotal In All Accounts ").append(Transaction.toDollars(totalBalance));
        statement.append("\nTotal Interest Earned ").append(Transaction.toDollars(totalBalance-totalTransactions));
        return statement.toString();
    }
    private String statementForAccount(Account a) {

        StringBuilder s = new StringBuilder();
        switch(a.getAccountType()){
            case CHECKING:
                s.append("Checking Account: ");
                break;
            case SAVINGS:
                s.append("Savings Account: ");
                break;
            case MAXI_SAVINGS:
                s.append("Maxi-Savings Account: ");
                break;
        }
        s.append(a.getAccountName()).append("\n");

        for (Transaction t : a.getTransactions()) {
            s.append("\t").append(t.toString()).append("\n");
        }
        s.append("Account Balance ").append(Transaction.toDollars(a.getBalance())).append("\n");
        s.append("Interest Earned ").append(Transaction.toDollars(a.getTotalInterestEarned()));
        return s.toString();
    }


    public void transferBetweenAccounts(String fromAccount, String toAccount, double amount){
        if(!this.accounts.containsKey(fromAccount)||!this.accounts.containsKey(toAccount)){
            throw new IllegalArgumentException("Both accounts must belong to customer");
        }
        withDrawFromAccount(amount,fromAccount);

        depositIntoAccount(amount,toAccount);
    }


    public int getNumberOfAccounts() {
        return this.accounts.size();
    }

    public  double totalInterestEarned() {
        List<Account> currentAccounts = List.copyOf(this.accounts.values());
        double total = 0;
        for (Account a: currentAccounts){
            total+=a.getTotalInterestEarned();
        }
        return total;
    }

    /**
     * Provide a list copy of Account Transactions
     * @param accountName key to retrieve the Account object from HashMap
     * @return copy of the list of transactions in a customer's account
     */
    public List<Transaction> getAccount(String accountName){
        return accounts.get(accountName).getTransactions(); //Account.getTransactions() returns a copy of Transactions
    }


}
