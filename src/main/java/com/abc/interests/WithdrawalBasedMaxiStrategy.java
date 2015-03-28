package com.abc.interests;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.abc.interests.RateConstants.*;

import com.abc.Account;
import com.abc.DateProvider;
import com.abc.Transaction;

/**
 * This class implements a strategy to have an interest rate of 5% assuming no withdrawals in the past 10 days 
 * otherwise 0.1%. Interest rates accrue daily (including weekends), rates above are per-annum.
 * It depends on fact that transactions are kept in a sorted list in ascending order.
 */

public class WithdrawalBasedMaxiStrategy implements InterestRateStrategy {
	
	final private Account account;
	final static private int TENDAYS = 10;
	final static private BigDecimal MINRATE = new BigDecimal("0.001").divide(YEAR, AFTERCOMMA, ROUNDINGMODE);
	final static private BigDecimal MAXRATE = new BigDecimal("0.05").divide(YEAR, AFTERCOMMA, ROUNDINGMODE);
	
	public WithdrawalBasedMaxiStrategy(final Account account) {
		this.account = account;
	}

	/**
	 * This method computes an interest that can be earned with a given amount for a given strategy
	 * for Maxi-Savings Account.
	 * @return amount earned.
	 */
	@Override
	public BigDecimal computeInterestEarned(BigDecimal amount) {
		
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			if (isLastWithdrawalExpired()) {
				return amount.multiply(MAXRATE);
			} else {
				return amount.multiply(MINRATE);
			}			
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * This method tests transactions. For testing purposes it is left public, it should become private later.  
	 * @return true if there were no withdrawal transactions in the last 10 days, otherwise false.
	 */
	public boolean isLastWithdrawalExpired () {
		Date now = DateProvider.INSTANCE.now();
		List<Transaction> list = account.getTransactions();
		for (int i = list.size()-1; i>=0; i--) {
			Transaction t = list.get(i);
			Date date = t.getTransactionDate();
			long duration = TimeUnit.MILLISECONDS.toDays(now.getTime()-date.getTime());
			if (duration<=TENDAYS) {
				if (t.getAmount().compareTo(BigDecimal.ZERO) < 0) {
					return false;
				} 
			} else {
				return true;
			}
		}
		//if there were no transactions
		//assume MAXRATE should be used
		return true;
	}

}
