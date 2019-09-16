package com.abc.account.interest;

import com.abc.DateProvider;
import com.abc.Money;
import com.abc.Transaction;
import com.abc.account.Account;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Calculates interest based on the number of days since the last transaction
 */
public class TimeBasedInterestRule extends InterestRule {

    private int daysLimit;
    private BigDecimal lowerRate;
    private BigDecimal higherRate;

    public TimeBasedInterestRule(BigDecimal lowerRate, BigDecimal higherRate, int daysLimit){
        this.daysLimit = daysLimit;
        this.higherRate = higherRate;
        this.lowerRate = lowerRate;
    }

    @Override
    public Money calculateInterest(Account account) {
        int numberOfTransactions = account.getTransactions().size();
        Transaction lastTransaction = account.getTransactions().get(numberOfTransactions - 1);
        long diff = DateProvider.getInstance().now().getTime() - lastTransaction.getTransactionDate().getTime();
        if(Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))>daysLimit){
            return account.getBalance().multiply(higherRate);
        }
        return account.getBalance().multiply(lowerRate);
    }
}
