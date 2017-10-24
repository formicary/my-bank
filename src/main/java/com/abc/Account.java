package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @author tomhendra
 *	This Class allows the creation of an Account object which allows the deposit and withdrawal of funds to be inserted into a statement 
 *to be returned
 *
 * @param CHECKING	int classifying this account as of type CHECKING
 * @param SAVINGS	int classifying this account as of type SAVINGS
 * @param MAXI_SAVINGS	int classifying this account of type MAXI_SAVINGS
 * @param transactions	ArrayList of Transaction objects that holds the transactions specified for this account
 * @param accountType	int classifying the account upon creation specfying one of the set types
 */
public class Account {

	protected static final int CHECKING = 0;
	protected static final int SAVINGS = 1;
	protected static final int MAXI_SAVINGS = 2;
	protected List<Transaction> transactions;
	private final int accountType;

	protected Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	/**
	 * When called, adds creates a transaction object with the amount specified and adds to the transactions arrayList 
	 * @param amount	   the amount specified to be deposited into the account as a double
	 * @param convertedAmount	the amount to be deposited as a BigDecimal to enable effective rounding and scale specification
	 * @param roundedAmount	the amount to be deposited ignoring any values after the second decimal point e.g. 10.5056 would be 10.50
	 */
	protected void deposit(double amount) {
		BigDecimal convertedAmount = BigDecimal.valueOf(amount);
		BigDecimal roundedAmount = convertedAmount.setScale(2, RoundingMode.DOWN);
		if (amount <= 0.00) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(roundedAmount.doubleValue()));
		}
	}

	/**
	 * When called, adds creates a transaction object with the amount specified and adds to the transactions arrayList 
	 * The distinction between deposits and withdrawals in the transactions arraylist is only by their positive or negative values
	 * @param amount to be withdrawn as a double 
	 * @param convertedAmount	the amount to be withdrawn as a BigDecimal to enable effective rounding and scale specification as a negative value
	 * @param roundedAmount	the amount to be withdrawn ignoring any values after the second decimal point e.g. 10.5056 would be 10.50
	 */
	protected void withdraw(double amount) {
		BigDecimal convertedAmount = BigDecimal.ZERO.subtract(BigDecimal.valueOf(amount));
		BigDecimal roundedAmount = convertedAmount.setScale(2, RoundingMode.DOWN);
		if (amount <= 0.00) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(roundedAmount.doubleValue()));
		}
	}

	/**
	 * Returns the numerical value of the account type
	 * @return	the int identifier for the account type of this account
	 */
	protected int getAccountType() {
		return accountType;
	}

	/**
	 * 
	 * @param checkAll	a boolean to call the method, allowing the encapsulation of the transactions as a private method, preventing the creation of fake transactions
	 * @param total	the total value of the summed transactions of this account
	 * @param	convertedTotal	the total value as a BigDecimal object for effective rounding and scaling
	 * @param	roundedTotal	the total value rounding any values after the secound decimal place using the  so called 'bankers rounding' mode
	 * @return	the total rounded value of all the transactions as a BigDecimal
	 */
	private BigDecimal checkIfTransactionsExist(boolean checkAll) {
		double total = 0.00;
		BigDecimal convertedTotal;
		BigDecimal roundedTotal;
		if (checkAll) {
			for (Transaction t: transactions) {
				total+=t.amount;
			}       
		}
		convertedTotal = BigDecimal.valueOf(total);
		roundedTotal = Bank.scale(convertedTotal);
		return roundedTotal;
	}

	/**
	 * 
	 * @return the result of the summing of all the transactions in the transactions arraylist
	 */
	protected BigDecimal sumTransactions() {
		return checkIfTransactionsExist(true);
	}
	/**
	 * @param now	the current time and date
	 * @param last	the date and time of the last withdrawal, if no withdrawals have been made 
	 * @param noWithdrawals	the number of days that pushes the maxi_savings account interest rate over the higher threshold
	 * @return	the number of days passed since the last withdrawal was made from this account
	 */
	protected int daysSinceLastWithdrawal() {
		Date now = DateProvider.getInstance().now();
		Date last = now;
		int noWithdrawals = 11;
		outer: {
			for (int i = transactions.size()-1; i>=0; i--) {
				if (transactions.get(i).amount>0) {
					last = transactions.get(i).transactionDate;
					break outer;
				} 
			}
			return noWithdrawals;
		}
		if (last!=now) {
			return (int)((now.getTime() - last.getTime()) / (1000 * 60 * 60 * 24));
		}
		else {
			return 0;
		}       
	}

	/**
	 * @param	roundedAmount	the sum of the transactions for this account rounded to two decimal places 
	 * @return	the amount of interest earned on the amount stored in this account as a double depending on thhis accountype
	 */
	protected BigDecimal interestEarned() {
		BigDecimal roundedAmount = sumTransactions();
		switch(this.accountType) {
		case SAVINGS:
			if (roundedAmount.doubleValue() <= 1000) {
				return Bank.scale(roundedAmount.multiply(BigDecimal.valueOf(0.001))); 
			}
			else {
				return Bank.scale(BigDecimal.ONE.add(roundedAmount.subtract(BigDecimal.valueOf(1000)).multiply(BigDecimal.valueOf(0.002))));
			}
		case MAXI_SAVINGS:
			if (daysSinceLastWithdrawal() > 10) {
				return Bank.scale(roundedAmount.multiply(BigDecimal.valueOf(0.05)));
			}
			else {
				return Bank.scale(roundedAmount.multiply(BigDecimal.valueOf(0.001)));
			}
		default:
			return Bank.scale(roundedAmount.multiply(BigDecimal.valueOf(0.001)));
		}
	}
}
