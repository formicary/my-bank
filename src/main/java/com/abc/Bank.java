package com.abc;

import static java.lang.Math.abs;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class Bank {

	private List<Customer> customers;
	private List<Account> accounts;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	/**
	 * Add customer(s) and associated accounts(s) to bank
	 * @param customer
	 */
	public void addCustomer(Customer customer) {
		customers.add(customer);
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
					System.out.println("Withdrawn " + Common.toDollars(amount) +
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
					System.out.println("Deposited " + Common.toDollars(amount) + 
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

	public List<Customer> getCustomers() {
		return customers;
	}
	
	@SuppressWarnings("deprecation")
	public static void main (String [] args) {
		
		Bank bank = new Bank();
		Customer anon = new Customer("Anon");
		MaxiSavingsAccount ms = new MaxiSavingsAccount();
		ms.deposit(100.00);
		ms.withdraw(20.00);
		Date date = new GregorianCalendar(2017, 2, 3).getTime();
		ms.getTransactions().get(1).setTransactionDate(date);
		anon.openAccount(ms);
		bank.addCustomer(anon);
		System.out.println(anon.totalInterestEarned());
	}

}
