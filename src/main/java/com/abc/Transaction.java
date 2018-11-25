package main.java.com.abc;


import java.util.Calendar;
import java.util.Date;

public class Transaction {
	public final double amount;

	private Date transactionDate;

	public Transaction(double amount) {
		this.amount = amount;
		this.setTransactionDate(DateProvider.getInstance().now());
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	// Customer can transfer between their accounts
	public boolean transferMoney (double amount, Account from, Account to, Customer customer) {
		boolean resultOfTransaction = false;
		int numberOfAccounts = customer.getNumberOfAccounts();
		if (numberOfAccounts>1) {
			if (amount>0) {
				from.withdraw(amount);
				to.deposit(amount);
				resultOfTransaction = true;
			}
			else {
				System.out.println("Value must be greater than 0 in order to transfer");
			}
		} else {
			System.out.println("Must have more than one account for a transaction between accounts");
		}
		return resultOfTransaction;


	}
	public static boolean isLeapYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
	}

}
