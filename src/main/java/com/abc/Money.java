package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/* Bad practice to represent currency with double/float due to rounding accuracy
 * therefore replace with a Money class using industry best practices
 * 
 * This class loosely inspired by:
 * 	https://stackoverflow.com/questions/1359817/using-bigdecimal-to-work-with-currencies
 * 
 * Restricting the class solely to dollars since thats what the github for this challenge uses
 */


public class Money {
	// Get singleton for USD locale and set a default rounding mode
    private static final Currency USD = Currency.getInstance("USD");
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private final BigDecimal AMOUNT;
    private final Currency CURRENCY;
    static final BigDecimal ZERO_VALUE = new Money("0.00").getAmount();

    // simple public constructor with default rounding mode set
    public Money(String amount) {
        this.CURRENCY = USD;      
        this.AMOUNT = new BigDecimal(amount).setScale(CURRENCY.getDefaultFractionDigits(), DEFAULT_ROUNDING);
    }
    
    // Overloaded constructor to allow passing of BigDecimal as well as string
    public Money(BigDecimal amount) {
        this.CURRENCY = USD;      
        this.AMOUNT = amount.setScale(CURRENCY.getDefaultFractionDigits(), DEFAULT_ROUNDING);
    }

    public BigDecimal getAmount() {
        return AMOUNT;
    }

    public Currency getCurrency() {
        return CURRENCY;
    }

    @Override
    public String toString() {
        return getCurrency().getSymbol() + " " + getAmount();
    }
}