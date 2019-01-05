package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaxiSavingsAccount extends Account {

	public MaxiSavingsAccount() {
		this.transactions = new ArrayList<Transaction>();
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();
		DateProvider dp = new DateProvider();
		Date date1 = null;
		Date date2 = dp.tenDaysBeforeCurrentDate();
		int counter = 0;

		for (Transaction t : transactions) {
			date1 = t.getTransactionDate();

			if (date1.before(date2) == true || date1.compareTo(date2) == 0) {
				amount = amount * 0.001;
				break;

			} else {
				counter++;
			}
		}

		if (counter == transactions.size()) {
			amount = amount * 0.05;
			return amount;
		} else {
			return amount;
		}
	}
}
