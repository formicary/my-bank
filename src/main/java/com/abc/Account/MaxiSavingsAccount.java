package com.abc.Account;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.abc.Bank.Transaction;

public class MaxiSavingsAccount extends Account {

	public double earnedInterest() {
		double balance = getBalance();
		List<Transaction> transactions = getTransactions();
		int counter = transactions.size() - 1;
		while (counter >= 0) {
			Transaction transaction = transactions.get(counter);
			// Condition to check whether transaction was a withdrawal or not.
			if (transaction.getTransactionAmount() < 0.0) {
				Instant currentTime = Instant.now();
				Instant timeOfTransaction = transaction.getTransactionDate();
				if (Math.abs(ChronoUnit.DAYS.between(currentTime, timeOfTransaction)) > 10) {
					// Latest withdrawal was more than 10 days ago.
					return 0.05 * balance;
				} else {
					// Latest withdrawal was less or equal to 10 days.
					return 0.001 * balance;
				}
			}
			counter--;
		}
		// No withdrawals were made at all.
		return 0.05 * balance;
	}

	public String getAccountType() {
		return "Maxi Savings Account\n";
	}

}
