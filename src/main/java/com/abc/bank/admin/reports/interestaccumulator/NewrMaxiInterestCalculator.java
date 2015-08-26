package com.abc.bank.admin.reports.interestaccumulator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.abc.bank.bankops.Transaction;
import com.abc.bank.bankops.TransactionType;
import com.abc.bank.utils.DateProvider;
import com.abc.bank.utils.FinanceUtils;

public class NewrMaxiInterestCalculator extends InterestCalculator {

	private Date lastWithdrawl;
	private int timeSinceLastWithdrawl = Integer.MAX_VALUE;


	@Override
	public double calcInterestEarned(List<Transaction> trans){
		Iterator<Transaction> it= trans.iterator();
		try {
			Transaction initT = it.next();
			double balance = initT.getAmount();
			double ret= 0D;
			int daysDiff;
			while (it.hasNext()){
				Transaction next= it.next();
				if (lastWithdrawl != null){
					timeSinceLastWithdrawl = DateProvider.INSTANCE.getDaysDiff(next.getTransactionDate(),lastWithdrawl);
				}
				daysDiff = DateProvider.INSTANCE.getDaysDiff(next.getTransactionDate(),initT.getTransactionDate());
				ret += doCalcInterestEarned(daysDiff,balance);
				if (next.getTransactionType() == TransactionType.WITHDRAWL){
					lastWithdrawl = next.getTransactionDate();
				} 
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


	@Override
	protected double doCalcInterestEarned(Integer daysDiff, Double principal) {
		if (lastWithdrawl == null){
			return FinanceUtils.INSTANCE.calcInterestEarned(principal, 0.05, daysDiff);
		}else if(timeSinceLastWithdrawl < 10) {
			return FinanceUtils.INSTANCE.calcInterestEarned(principal, 0.001,daysDiff);
		}else {
			return FinanceUtils.INSTANCE.calcInterestEarned(principal,.001, 10) + 
					FinanceUtils.INSTANCE.calcInterestEarned(principal,0.05, daysDiff-10);
		}

	}
}