package com.abc.bank.admin.reports.interestaccumulator;

import java.util.List;

import com.abc.bank.utils.FinanceUtils;

public class LadderedInterestRateCalculator extends InterestCalculator {

	private List<AccTreshold> thresholds;

	public LadderedInterestRateCalculator(List<AccTreshold> thresholds) {
		this.thresholds = thresholds;
	}

	@Override
	protected double doCalcInterestEarned(Integer daysDiff, Double principal) {
		double ret = 0D;
		double balance = principal;;
		for (AccTreshold ac: thresholds){
			principal = Math.min(principal, ac.getThreshold());
			ret += FinanceUtils.INSTANCE.calcInterestEarned(principal, ac.getRate(), daysDiff);
			balance -= principal;
			if (balance <= 0){
				break;
			}
		}
		return ret;
	}

}
