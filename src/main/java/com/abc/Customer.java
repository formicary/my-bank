package com.abc;

import java.math.BigDecimal;
import java.util.HashMap;

/** Represents a bank customer.
 * @author James Rogers
 * @version 1.0
 * @since 1.0
*/
public class Customer {
    
    /** Name of customer. */
    private final String name;
    
    /** Accounts opened by the customer. */
    private final HashMap<Account.AccountType, Account> accounts;

    /** 
    * Creates a customer with specified name.
    * @param name The name of the customer.
    */
    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<Account.AccountType, Account>() {};
    }

    /**
    * Gets the name of the customer. 
     * @return The name
    */
    public String getName() {
        return name;
    }

    /**
    * Opens an account for the customer. 
    * The customer can only have one account of each type.
    * @param accountType the type of account to open.
    * @throws IllegalAccountException if an account of this type already exists.
    */
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
    
    /**
    * Gets the account of the specified type. 
    * @param accountType the type of account to get.
    * @return If the account exists, returns the account. If the account does
    * exist, returns null.
    */
    public Account getAccount(Account.AccountType accountType) {
        return accounts.get(accountType);
    }

    /**
    * Gets the number of open accounts.
    * @return If the account exists, returns the account. If the account does
    * exist, returns null.
    */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
    * Gets the total interest earned for all open accounts.
    * @return The total amount of interest earned.
    */
    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts.values())
            total = total.add(a.interestEarned());
        return total;
    }

    /**
    * Gets the statement for the customer.
    * @return The statement as a formatted string.
    */
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
    
    /**
    * Gets the summary of the customer.
    * @return The formatted string of the customers summary.
    */
    public String getSummary() {
        return "\n - " + name + " (" + format(getNumberOfAccounts(), "account") + ")";
    }
    
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
    
    /**
    * Transfers money from one account to another.
    * @param amount The amount of money to transfer.
    * @param from The Account to withdraw money from.
    * @param to The account to deposit money in to.
    * @throws InsufficientFundsException if there are insufficient funds in the from account.
    * @throws IllegalAccountException if the two accounts equal each other or the customer does not own one of the accounts. 
    */
    public void transfer(BigDecimal amount, Account.AccountType from, Account.AccountType to) throws InsufficientFundsException, IllegalAccountException {
        
        // Check if these accounts are the same
        if(from.equals(to)) {
            // Throw Exception
            throw new IllegalArgumentException("Cannot transfer between the same account.");
        }
        
        // Check if customer has accounts
        if(!accounts.containsKey(from) || !accounts.containsKey(to)) {
            // Throw Exception
            throw new IllegalAccountException("Customer does not have both accounts.");
        }
        
        // Make transaction
        accounts.get(from).withdraw(amount);
        accounts.get(to).deposit(amount);  
    }
}
