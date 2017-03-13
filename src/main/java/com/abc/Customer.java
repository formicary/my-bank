package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Customer {

	private String name;
	private List<Account> accounts;

	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * Open new account for customer
	 * @param account account to add
	 * @return customer with new account
	 */
	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}
	/**
	 * Close a specified account
	 * @param account account to close
	 * @return customer with removed account
	 */
	public Customer closeAccount(Account account) {
		if(accounts.contains(account)) {
			accounts.remove(account);
		}
		return this;
	}
	/**
	 * Return number of accounts held by customer
	 * @return number of accounts
	 */
	public int getNumberOfAccounts() {
		if(!accounts.isEmpty()) {
			return accounts.size();
		}
		else {
			return 0;
		}

	}
	/**
	 * Check if account with specified account number exists
	 * @param accountNo account number of account
	 * @return true if account exits
	 */
	private boolean isValidAcc(int accountNo) {
		boolean result = false;
		for(Account a: accounts) {
			if(accountNo == a.getAccountNo()) {
				return true;
			}
		}
		return result;
	}
	/**
	 * Return account associated with specified account number
	 * @param accountNo account number of account
	 * @return account
	 * @throws IllegalArgumentException if account does not exist
	 */
	public Account getAccount(int accountNo) throws IllegalArgumentException {
		if(!isValidAcc(accountNo)) {
			throw new IllegalArgumentException("Error: Account with account number " + accountNo + " does not exist");
		}
		Account account = null;
		for (Account a: accounts) {
			if (accountNo == a.getAccountNo()) {
				account = a;
			}
		}
		return account;
	}
	/**
	 * Calculate total interest earned on all accounts held by customer
	 * @return total interest earned
	 */
	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
		return total;
	}

	/**
	 * Prints bank statement of all accounts held by customer
	 * @return customer bank statement
	 */
	public String getStatement() {
		String statement = null;
		statement = "Statement for " + name + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + "\n";
			total += a.sumTransactions();
		}
		statement += "\nTotal In All Accounts " + Common.toDollars(total);
		return statement;
	}
	/**
	 * Displays account type and account number as a string
	 * @param a account
	 * @return account type
	 */
	private String statementForAccount(Account a) {
		String s = "";

		if(a instanceof CheckingAccount) {
			s += "Account No: " + a.getAccountNo() +"\n";
			s += "Checking Account\n";
		}
		else if(a instanceof SavingsAccount) {
			s += "Account No: " + a.getAccountNo() +"\n";
			s += "Savings Account\n";
		}
		else if(a instanceof MaxiSavingsAccount) {
			s += "Account No: " + a.getAccountNo() +"\n";
			s += "Maxi Savings Account\n";
		}
		s = getTotals(a, s);
		return s;
	}
	/**
	 * Returns transaction totals a string
	 * @return 
	 * @return transaction totals
	 */
	private String getTotals(Account a, String s) {
		double total = 0.0;
		int type;
		for (Map.Entry<Transaction, Integer> entry: a.getTransactions().entrySet()) {		
			s += "\t"; 
			type = entry.getValue();
			
			if(type == Common.DEPOSIT) {
				s += Common.DEPOSIT_STR + " " + Common.toDollars(entry.getKey().amount) + "\n";
			}
			else if(type == Common.WITHDRAW) {
				s += Common.WITHDRAW_STR + " " + Common.toDollars(entry.getKey().amount) + "\n";
			}
			else if(type == Common.TRANSFER_DEPOSIT) {
				s += Common.TRANSFER_DEPOSIT_STR + " " + Common.toDollars(entry.getKey().amount) + "\n";
			}
			else {
				s += Common.TRANSFER_WITHDRAW_STR + " " + Common.toDollars(entry.getKey().amount) + "\n";
			}
			total += entry.getKey().amount;
		}
		s += "Total " + Common.toDollars(total);
		return s;
	}
	public String getName() {
		return name;
	}    

	public List<Account> getAccounts() {
		return accounts;
	}

}
