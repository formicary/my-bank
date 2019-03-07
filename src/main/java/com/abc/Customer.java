package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Customer.
 */
public class Customer {
    private String name;
    private List<Account> accounts;

    /**
     * Instantiates a new customer.
     *
     * @param name the customers name
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Gets the customers name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the customers accounts.
     *
     * @return the accounts
     */
    public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * Opens an account of specified account type.
	 *
	 * @param account the account to be opened
	 * @return the customer
	 */
	public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
	
	/**
	 * Transfer funds between two of the customers account.
	 *
	 * @param accountTransferFrom the account to transfer from
	 * @param accountTransferTo the account to transfer to
	 * @param amount the amount to transfer
	 */
	public void transferFunds(Account accountTransferFrom, Account accountTransferTo, double amount) {
		if(accountTransferFrom.equals(accountTransferTo)) {
			throw new IllegalArgumentException("Cannot transfer funds between the same account"); 
		}
		
		accountTransferFrom.withdraw(amount);
		accountTransferTo.deposit(amount);
	}

    /**
     * Gets the number of accounts the customer has.
     *
     * @return the number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Total interest earned over all accounts.
     *
     * @return the interest
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts)
            total += account.interestEarned();
        return total;
    }

    /**
     * Gets the customers overview statement.
     * Contains information for all accounts.
     *
     * @return the statement
     */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account account : accounts) {
            statement += "\n" + formatAccountStringForStatement(account) + "\n";
            total += account.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + doubleToDollars(total);
        return statement;
    }

    private String formatAccountStringForStatement(Account account) {
        String s = "";

        switch(account.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        double total = 0.0;
        for (Transaction transaction : account.transactions) {
            s += "  " + (transaction.amount < 0 ? "withdrawal" : "deposit") + " " + doubleToDollars(transaction.amount) + "\n";
            total += transaction.amount;
        }
        s += "Total " + doubleToDollars(total);
        return s;
    }

    private String doubleToDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
