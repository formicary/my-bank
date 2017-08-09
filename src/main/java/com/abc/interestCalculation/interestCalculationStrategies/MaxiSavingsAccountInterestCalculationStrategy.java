package com.abc.interestCalculation.interestCalculationStrategies;

import com.abc.Account;
import com.abc.DateProvider;
import com.abc.Transaction;
import com.abc.TransactionType;
import java.util.Date;

public class MaxiSavingsAccountInterestCalculationStrategy implements InterestCalculationStrategy {

	private double maxiSavingsAnnualInterestRateLow = 0.001;
	private double maxiSavingsAnnualInterestRateHigh = 0.05;

	public double calculateInterest(Account account) {

		double accountBalance = account.getCurrentBalance();
		long calculationDays = account.getDaysSinceLastTransaction();

		DateProvider dp = new DateProvider();
		Transaction lastWithdrawal = account.getLastTransactionByType(TransactionType.WITHDRAWAL);

		long daysAppart = 0;

		if (lastWithdrawal.getType() != TransactionType.WITHDRAWAL) {

			daysAppart = 11;

		} else {

			Date lastWithdrawalDate = lastWithdrawal.getDate();

			daysAppart = dp.howManyDaysBeforeToday(lastWithdrawalDate);

		}

		if (daysAppart > 10) {

			accountBalance = accountBalance
					* Math.pow(1.0 + maxiSavingsAnnualInterestRateHigh, calculationDays / 365.0);

		} else {

			accountBalance = accountBalance
					* Math.pow(1.0 + maxiSavingsAnnualInterestRateLow, calculationDays / 365.0);

		}

		return accountBalance - account.getCurrentBalance();
	}

}
