package com.abc.interestbehaviors;

import com.abc.accounts.Account;
import com.abc.util.Money;

public class FlatRateInterest implements InterestBehavior {

    private double interestRate;

    public static FlatRateInterest getInstance(double interestRate) {
        return new FlatRateInterest(interestRate);
    }

    private FlatRateInterest(double interestRate) {
        this.interestRate = interestRate;
    }

    public Money getInterest(Account account) {
        Money times = account.sumTransactions().times(interestRate);
        Long d = times.getAmount().longValue();
        return times;
    }

}
