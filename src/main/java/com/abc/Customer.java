package com.abc;

import java.math.BigDecimal;
import java.util.HashMap;

public class Customer {
    private final String name;
    private final HashMap<Account.AccountType, Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<Account.AccountType, Account>() {};
    }

    public String getName() {
        return name;
    }

    public void openAccount(Account.AccountType accountType) throws IllegalAccountException {
        
        if(accounts.containsKey(accountType)) {
            throw new IllegalAccountException("Cannot open multiple accounts of the same type.");
        }
        
        switch(accountType) {
            case CHECKING:
                accounts.put(Account.AccountType.CHECKING, 
                        new Account(Account.AccountType.CHECKING));
                break;
            case SAVINGS:
                accounts.put(Account.AccountType.SAVINGS, 
                        new Account(Account.AccountType.SAVINGS));
                break;
            case MAXI_SAVINGS:
                accounts.put(Account.AccountType.MAXI_SAVINGS, 
                        new Account(Account.AccountType.MAXI_SAVINGS));
                break;
            default: 
        } 
    }
    
    public Account getAccount(Account.AccountType accountType) {
        return accounts.get(accountType);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts.values())
            total = total.add(a.interestEarned());
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        
        statement.append("Statement for ");
        statement.append(name);
        statement.append("\n");
        
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts.values()) {
            statement.append("\n");
            statement.append(a.statementForAccount());
            statement.append("\n");
            total = total.add(a.sumTransactions());
        }
        statement.append("\nTotal In All Accounts ");
        statement.append(Account.toDollars(total));
        return statement.toString();
    }
    
    public String getSummary() {
        return "\n - " + name + " (" + format(getNumberOfAccounts(), "account") + ")";
    }
    
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
}
