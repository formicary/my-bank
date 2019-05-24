package com.abc.interestbehaviors;

import com.abc.accounts.Account;
import com.abc.util.Money;
import com.abc.util.USDFactory;

import java.math.BigDecimal;

public class MaxiSavingsInterest implements InterestBehavior {

    // MAXI SAVINGS INTEREST CONSTANTS
    private final static Money ONE_THOUSAND_DOLLARS = USDFactory.DollarToMoney(1000L);
    private final static Money TWO_THOUSAND_DOLLARS = USDFactory.DollarToMoney(2000L);

    private final static Money TWENTY_DOLLARS = USDFactory.DollarToMoney(20L);
    private final static Money SEVENTY_DOLLARS = USDFactory.DollarToMoney(70L);

    private final static double TWO_PERCENT = 0.02;
    private final static double FIVE_PERCENT = 0.05;
    private final static double TEN_PERCENT = 0.10;


    public Money getInterest(Account account) {
        Money accountBalance = account.sumTransactions();

        if (accountBalance.lteq(ONE_THOUSAND_DOLLARS)) {
            return accountBalance.times(TWO_PERCENT);
        }
        else if (accountBalance.lteq(TWO_THOUSAND_DOLLARS)) {
            Money accuredInterest = accountBalance.minus(ONE_THOUSAND_DOLLARS).times(FIVE_PERCENT);
            return TWENTY_DOLLARS.plus(accountBalance);
        }
        else {
            Money accuredInterest = accountBalance.minus(TWO_THOUSAND_DOLLARS).times(TEN_PERCENT);
            return SEVENTY_DOLLARS.plus(accuredInterest);
        }
    }

}
