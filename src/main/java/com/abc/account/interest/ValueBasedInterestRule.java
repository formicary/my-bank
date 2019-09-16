package com.abc.account.interest;

import com.abc.Money;
import com.abc.account.Account;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Calculates interest on an account for a balance between a given range
 */
public class ValueBasedInterestRule extends InterestRule {

    private Optional<Money> upperBoundary = Optional.empty();
    private Money lowerBoundary;
    private BigDecimal rate;

    /**
     * @param rate the rate of interest to apply
     * @param lowerBoundary the lower limit from which interest begins to be applied from
     */
    public ValueBasedInterestRule(BigDecimal rate, Money lowerBoundary){
        this.rate = rate;
        this.lowerBoundary = lowerBoundary;
    }

    /**
     * @param rate the rate of interest to apply
     * @param lowerBoundary the lower limit from which interest begins to be applied from
     * @param upperBoundary the upper limit from which point no further interest is added
     */
    public ValueBasedInterestRule(BigDecimal rate, Money lowerBoundary, Money upperBoundary) {
        this.rate = rate;
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = Optional.of(upperBoundary);
    }

    /**
     * Calculates interest according to rule for given account
     * @param account
     * @return
     */
    @Override
    public Money calculateInterest(Account account){
        Money balance = account.getBalance();
        if (balance.compareTo(lowerBoundary) < 1)
            return new Money("0.00");
        if (!upperBoundary.isPresent())
            return balance.subtract(lowerBoundary).multiply(rate);

        if (balance.compareTo(upperBoundary.get()) < 1)
            return balance.subtract(lowerBoundary).multiply(rate);
        return upperBoundary.get().subtract(lowerBoundary).multiply(rate);
    }
}
