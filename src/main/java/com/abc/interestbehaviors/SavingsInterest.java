package com.abc.interestbehaviors;

import com.abc.accounts.Account;
import com.abc.util.Money;
import com.abc.util.USDFactory;

import java.math.BigDecimal;

public class SavingsInterest implements InterestBehavior {

    private static Money ONE_THOUSAND_DOLLARS = USDFactory.DollarToMoney(1000L);
    private static Money ONE_DOLLAR = USDFactory.DollarToMoney(1L);

    private static double POINT_1_PERCENT = 0.001;
    private static double POINT_2_PERCENT = 0.002;

    public Money getInterest(Account account) {
        Money money = account.sumTransactions();

        if (money.lt(ONE_THOUSAND_DOLLARS)) {
            return money.times(POINT_1_PERCENT);
        }
        else {
            Money accuredInterst = money.minus(ONE_THOUSAND_DOLLARS).times(POINT_2_PERCENT);
            return ONE_DOLLAR.plus(accuredInterst);
        }
    }

}
