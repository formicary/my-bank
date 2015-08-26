package com.abc.bank.admin.reports.interestaccumulator;

import com.abc.bank.utils.FinanceUtils;

public class SimpleInterestRateCalculator extends InterestCalculator {

	private double rate;
	public SimpleInterestRateCalculator(double rate) {
		this.rate =rate;
	}

	@Override
	protected double doCalcInterestEarned(Integer daysDiff, Double principal) {
		return FinanceUtils.INSTANCE.calcInterestEarned(principal, rate, daysDiff);
	}

		

		
}
