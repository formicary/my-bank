package com.abc.bank.admin.reports.interestaccumulator;

import java.util.Iterator;
import java.util.List;

import com.abc.bank.account.AccountType;
import com.abc.bank.bankops.Transaction;
import com.abc.bank.utils.DateProvider;
import com.google.common.collect.Lists;

public abstract class InterestCalculator {

	protected final static int DAYS_IN_YEAR = 365;
	private final static InterestCalculator CHEQUING = new SimpleInterestRateCalculator(0.001);
	private final static InterestCalculator SAVING_CALCULATOR = new LadderedInterestRateCalculator(
			Lists.newArrayList(new AccTreshold(0.001,1000),								
					new AccTreshold(0.002,Double.MAX_VALUE)));
	
	public double calcInterestEarned(List<Transaction> trans){
		Iterator<Transaction> it= trans.iterator();
		try {
			Transaction initT = it.next();
			double balance = initT.getAmount();
			double ret= 0D;
			int daysDiff;
			while (it.hasNext()){
				Transaction next= it.next();
				daysDiff = DateProvider.INSTANCE.getDaysDiff(next.getTransactionDate(),initT.getTransactionDate());
				ret += doCalcInterestEarned(daysDiff,balance);
				balance += next.getAmount();
				initT = next;
			}
			daysDiff = DateProvider.INSTANCE.getDaysDiff(DateProvider.INSTANCE.today(), initT.getTransactionDate());
			ret += doCalcInterestEarned(daysDiff,balance);
			return ret;
		} catch (Exception e) {
			return 0;
		}
	}
	
	protected abstract double doCalcInterestEarned(Integer daysDiff,Double prinipal);
	

	public static InterestCalculator getCalculator(AccountType type){
		switch (type){
		case  CHEQUING:
			return CHEQUING;
		case SAVINGS:
			return SAVING_CALCULATOR;
			case MAXI:
//			return new LadderedInterestRateCalculator(
//					Lists.newArrayList(new AccTreshold(0.02,1000),
//							new AccTreshold(0.05, 2000),
//							new AccTreshold(0.1,Double.MAX_VALUE)));

			return new NewrMaxiInterestCalculator();
			default:
				throw new IllegalArgumentException("Not Supported");
		}
	}
}
