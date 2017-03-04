package com.abc;

import static java.lang.Math.abs;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Bank implements Common {

	private List<Customer> customers;
	private List<Account> accounts;

	public Bank() {
		customers = new ArrayList<Customer>();
		accounts = new ArrayList<Account>();
	}

	/**
	 * Add customer(s) and associated accounts(s) to bank
	 * @param customer
	 */
	public void addCustomer(Customer customer) {
		customers.add(customer);
		addAccounts(customer);
	}

	/**
	 * Add customer account(s) with account number
	 * @param customer
	 */
	public void addAccounts(Customer customer) {
		for(Account a: customer.getAccounts()) {
			a.setAccountNo(generateAccountNo());
			accounts.add(a);
		}
	}
	/**
	 * Summary of customer names and number of associated accounts
	 * @return String
	 */
	public String customerSummary() {
		String summary = "Customer Summary";
		for (Customer c : getCustomers())
			summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	/**
	 * Singular form of word returned if number equal to one
	 * Otherwise, plural returned
	 * @param number
	 * @param word
	 * @return word
	 */
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}
	/**
	 * Calculate total amount of interest paid to customers
	 * @return interest paid
	 */
	public double totalInterestPaid() {
		double total = 0;
		for(Customer c: getCustomers())
			total += c.totalInterestEarned();
		return total;
	}
	/**
	 * Generate bank account number
	 * @return account no
	 */
	public int generateAccountNo() {
		return accounts.size()+1;
	}
	/**
	 * Withdraw amount from an account
	 * @param accountNo account to withdraw from
	 * @param amount amount to withdraw
	 * @return result of transaction
	 */    
	public boolean withdrawFrom(int accountNo, double amount) {
		boolean result = false;
		for(int i=0; i<accounts.size();i++) {
			if(accountNo == accounts.get(i).getAccountNo()) {
				result = accounts.get(i).withdraw(amount);
				if(result) {
					System.out.println("Withdrawn " + toDollars(amount) +
							" from account no " + accountNo);    				
				}
			}
		}
		return result;
	}
	/**
	 * Deposit amount into an account
	 * @param accountNo account to deposit to
	 * @param amount amount to deposit
	 * @return result of transaction
	 */
	public boolean depositTo(int accountNo, double amount) {
		boolean result = false;
		for(int i=0; i<accounts.size();i++) {
			if(accountNo == accounts.get(i).getAccountNo()) {
				result = accounts.get(i).deposit(amount);
				if(result) {
					System.out.println("Deposited " + toDollars(amount) + 
							" to account no " + accountNo);    				
				}
			}
		}
		return result;
	}
	/**
	 * Transfer between bank accounts
	 * @param sourceAccountNo account to withdraw from
	 * @param destAccountNo account to deposit to
	 * @param amount amount to withdraw
	 * @return result of transaction
	 */    
	public void transfer(int sourceAccountNo, int destAccountNo, double amount) {
		boolean validCheck = false;
		validCheck = withdrawFrom(sourceAccountNo, amount);
		if(validCheck) {
			depositTo(destAccountNo, amount);
		}
	}
	/**
	 * Display amount in Canadian currency
	 */
	@Override
	public String toDollars(double d) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CANADA);
		String currency = nf.format(abs(d));
		return currency;	
	}

	public List<Customer> getCustomers() {
		return customers;
	}

}
