package com.abc.calculators;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abc.model.Account;
import com.abc.model.AccountType;
import com.abc.model.Money;
import com.abc.model.Transaction;
import com.abc.providers.DateProvider;

@Component("maxiSavingAccountInterestCalculator")
public class MaxiSavingAccountInterestCalculator implements IntrestCalculator{
	
	@Autowired
	private DateProvider dateProvider;
	
	private final static BigDecimal INTREST_RATE_AFTER_THRESHOLD = new BigDecimal("0.05");
	
	private final static BigDecimal INTREST_RATE_BEFORE_THRESHOLD = new BigDecimal("0.001");
	
	private final static int THRESHOLD_IN_DAYS = 5;
	
	
	
	/**
	 * Calculate interests for {@link AccountType#MAXI_SAVINGS}
	 * 
	 *  Maxi-Savings accounts has an interest calculated rate of 5% assuming 
	 *  no withdrawals in the past 10 days otherwise 0.1%
	 *  
	 * @param account
	 * @throws NullPointerException in case is account null
	 */
	public Money calculateInterestEarned(Account account) {
		Objects.requireNonNull(account);
		
		List<Transaction> transactinoList = account.getTransactionList();
		
		final Date dateThreshold = getDateTreshold();
		
		Money sumBeforeThreshold = Money.ZERO_USD;
		Money sumAfterThreshold = Money.ZERO_USD;
		
		for(final Transaction transaction : transactinoList){
			if(transaction.getTransactionDate().after(dateThreshold)){
				if(!transaction.isWithdrawal()){
					sumAfterThreshold = sumAfterThreshold.plus(transaction.getMoney());
				}
			}else{
				sumBeforeThreshold = sumBeforeThreshold.plus(transaction.getMoney());
			}
		}
		BigDecimal intrestAfter =  sumAfterThreshold.getAmount().multiply(INTREST_RATE_AFTER_THRESHOLD);
		BigDecimal intrestBefore =  sumBeforeThreshold.getAmount().multiply(INTREST_RATE_BEFORE_THRESHOLD);
		return new Money( intrestAfter.add(intrestBefore) );
	}
	
	
	private Date getDateTreshold(){
		Calendar calendar = dateProvider.getDate();
		calendar.add(Calendar.DAY_OF_MONTH, -THRESHOLD_IN_DAYS);
		return calendar.getTime();
	}

}
