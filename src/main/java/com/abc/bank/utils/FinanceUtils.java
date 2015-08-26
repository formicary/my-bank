package com.abc.bank.utils;


public enum FinanceUtils {
	INSTANCE;
	private static final int DAY_IN_YEAR = 365;
	public double calcInterestEarned(double principal,double perAnumRate, int numCompoundingPeriods){
			double dailyRate = Math.pow((1+perAnumRate),1.0/DAY_IN_YEAR)-1;
		
		return  principal * Math.pow(1 + dailyRate,
				numCompoundingPeriods)-principal;
		
	}

	
	
}
