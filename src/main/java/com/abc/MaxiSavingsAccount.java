package com.abc;

import java.time.LocalDate;
import java.util.Collections;

public class MaxiSavingsAccount extends Account{

	@Override
	public double interestEarned() {
		if (!transactions.isEmpty()) {
			Collections.reverse(transactions);
			for (Transaction t: transactions) {
				LocalDate lastTransaction = t.getTransactionDate();
				LocalDate limit = LocalDate.now().minusDays(10);
				if ((lastTransaction.isBefore(limit))) {
					return balance * 0.05;
				} else if (t.getAmount() < 0){
					return balance * 0.001;
				}
			}
		}
		return balance * 0.05;
	}

	@Override
	public String getAccountType() {
		return "Maxi Savings Account";
	}
}
