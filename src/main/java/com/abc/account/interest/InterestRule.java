package com.abc.account.interest;

import com.abc.Money;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Defines and interest rule and calculates amount of interest applied to a balance for that rule
 */
public class InterestRule {
    
    private Optional<Money> upperBoundary = Optional.empty();
    private Money lowerBoundary;
    private BigDecimal rate;

    /**
     * @param rate the rate of interest to apply
     * @param lowerBoundary the lower limit from which interest begins to be applied from
     */
    public InterestRule(BigDecimal rate, Money lowerBoundary){
        this.rate = rate;
        this.lowerBoundary = lowerBoundary;
    }

    /**
     * @param rate the rate of interest to apply
     * @param lowerBoundary the lower limit from which interest begins to be applied from
     * @param upperBoundary the upper limit from which point no further interest is added
     */
    public InterestRule(BigDecimal rate, Money lowerBoundary, Money upperBoundary) {
        this.rate = rate;
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = Optional.of(upperBoundary);
    }

    /**
     * Calculates interest according to rule for given balance
     * @param balance
     * @return
     */
    public Money calculateInterest(Money balance){
        if (balance.compareTo(lowerBoundary) < 1)
            return new Money("0.00");
        if (!upperBoundary.isPresent())
            return balance.subtract(lowerBoundary).multiply(rate);

        if (balance.compareTo(upperBoundary.get()) < 1)
            return balance.subtract(lowerBoundary).multiply(rate);
        return upperBoundary.get().subtract(lowerBoundary).multiply(rate);
    }
}
