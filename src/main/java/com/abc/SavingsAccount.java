package com.abc;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    private BigDecimal yearlyInterestRateTier1;
    private BigDecimal yearlyInterestRateTier2;
    private BigDecimal tier2Cutoff;

    SavingsAccount() {
        this.yearlyInterestRateTier1 = BigDecimal.valueOf(0.001);
        this.yearlyInterestRateTier2 = BigDecimal.valueOf(0.002);
        this.tier2Cutoff = BigDecimal.valueOf(1000);
    }
    @Override

    public BigDecimal interestEarnedDaily() {
        BigDecimal amountToAccrueInterestOn = this.getAccountBalance();
        BigDecimal tier1Interest = dailyCompoundInterestRate(yearlyInterestRateTier1).multiply(tier2Cutoff);
        BigDecimal tier2Interest = dailyCompoundInterestRate(yearlyInterestRateTier2).
                multiply(amountToAccrueInterestOn.subtract(tier2Cutoff));
        return tier1Interest.add(tier2Interest);
        }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}
