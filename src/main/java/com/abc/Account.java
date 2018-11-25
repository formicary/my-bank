package main.java.com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;

	public Account(final int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(final double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}
	int counter=0;
	public void withdraw(final double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
			counter++;
		}

	}
	// will need to add a time frame for the counter (ie to reset every 10 days)
	boolean withdrawnWithinTenDays = false;
	public boolean hasWithdrawnWithinTenDays() {
		if (counter>0) {
			withdrawnWithinTenDays = true;
		} else {
			System.out.println("user has not withdrawn");
		}
		return withdrawnWithinTenDays;
	}
	public double interestEarned() {
		final double amount = sumTransactions();
		switch(accountType){
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount-1000) * 0.002;
			//            case SUPER_SAVINGS:
				//                if (amount <= 4000)
			//                    return 20;               
		case MAXI_SAVINGS:
			if (hasWithdrawnWithinTenDays() == false)
				if (Transaction.isLeapYear()==true) {
					return amount*(0.05/365);
				} else {
					return amount*(0.05/364);
				}

		default:
			if (Transaction.isLeapYear()==true) {
				return amount * (0.001/365);
			} else {
				return amount * (0.001/364);
			}
		}
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(final boolean checkAll) {
		double amount = 0.0;
		for (final Transaction t: transactions)
			amount += t.amount;
		return amount;
	}

	public int getAccountType() {
		return accountType;
	}

}
