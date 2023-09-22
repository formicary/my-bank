package com.abc.Account;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.abc.Utilities.Enums.AccountType;

public class MaxiSavingsAccount extends Account {
    private final BigDecimal LOW_INTEREST_RATE = BigDecimal.valueOf(0.02);
    private final BigDecimal MID_INTEREST_RATE = BigDecimal.valueOf(0.05);
    private final BigDecimal HIGH_INTEREST_RATE = BigDecimal.valueOf(0.1);

    private final BigDecimal MID_INTEREST_RATE_THRESHOLD = BigDecimal.valueOf(1000.00);
    private final BigDecimal HIGH_INTEREST_RATE_THRESHOLD = BigDecimal.valueOf(2000.00);

    private final BigDecimal MAX_LOW_INTEREST = BigDecimal.valueOf(20);
    private final BigDecimal MAX_MID_INTEREST = BigDecimal.valueOf(70);

    public MaxiSavingsAccount() {
        super(AccountType.MAXI_SAVINGS);
    }

    @Override
    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        
        if (amount.compareTo(MID_INTEREST_RATE_THRESHOLD) <= 0) {
            amount = amount.multiply(LOW_INTEREST_RATE);
        } else if (amount.compareTo(HIGH_INTEREST_RATE_THRESHOLD) <= 0) {
            amount = amount.subtract(MID_INTEREST_RATE_THRESHOLD).multiply(MID_INTEREST_RATE).add(MAX_LOW_INTEREST);
        } else {
            amount = amount.subtract(HIGH_INTEREST_RATE_THRESHOLD).multiply(HIGH_INTEREST_RATE).add(MAX_MID_INTEREST);
        }
        
        return amount.setScale(2, RoundingMode.CEILING);
    };

}
