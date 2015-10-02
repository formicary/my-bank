package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public double interestEarned() {
		double amount = sumTransactions();
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
			//                if (amount <= 1000)
			//                    return amount * 0.02;
			//                if (amount <= 2000)
			//                    return 20 + (amount-1000) * 0.05;
			//                return 70 + (amount-2000) * 0.1;

			int lastTransactionDay = transactions.get(transactions.size() - 1).transactionDate.getDay();
			int lastTransactionMonth = transactions.get(transactions.size() - 1).transactionDate.getMonth();

			int currentDay = DateProvider.getInstance().day();
			int currentMonth = DateProvider.getInstance().month();

			if (currentMonth == lastTransactionMonth){
				for (Transaction t: transactions){
					if((Math.abs(lastTransactionDay - currentDay) <= 10) && t.isWithdrawal() ){
						return amount * 0.001;
					}
				}
			}
			else{
				return amount * 0.05;
			}
			return amount * 0.05;
		default:
			return amount * 0.001;
		}
	}

	/*
	 * I'm not sure how to calculate daily compound interest. If I was aware, then I would simply
	 * calculate how many days have elapsed from the currentDate to an individual transaction day
	 * then calculated the interest, and then added it to the total interest amount then loop for 
	 * every single transaction
	 */
	public double compoundInterestEarned(){
		double numberOfDaysDifference = 0;
		long currentTime = DateProvider.getInstance().now().getTime();
		double amount = sumTransactions();

		for (Transaction t: transactions){
			numberOfDaysDifference = TimeUnit.DAYS.convert(currentTime - t.transactionDate.getTime(), TimeUnit.MILLISECONDS);

		}
		return amount;
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t: transactions)
			amount += t.amount;
		return amount;
	}

	public int getAccountType() {
		return accountType;
	}

}
