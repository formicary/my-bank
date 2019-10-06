package com.abc;

public class SavingsAccount extends Account {
    private double yearlyInterestRateTier1;
    private double yearlyInterestRateTier2;
    private double tier2Cutoff;

    public SavingsAccount() {
        this.yearlyInterestRateTier1 = 0.001;
        this.yearlyInterestRateTier2 = 0.002;
        this.tier2Cutoff = 1000;
    }
    @Override

    //TODO fix rates and get rid of hard coding
    public double interestEarnedDaily() {
        double amountToAccrueInterestOn = this.getAccountBalance();
        if (amountToAccrueInterestOn <= tier2Cutoff) {
            return amountToAccrueInterestOn * dailyCompoundInterestRate(yearlyInterestRateTier1);
        } else {
            double tier1Interest = dailyCompoundInterestRate(yearlyInterestRateTier1) * tier2Cutoff;
            double tier2Interest = dailyCompoundInterestRate(yearlyInterestRateTier2) *
                    (amountToAccrueInterestOn - tier2Cutoff);
            return tier1Interest + tier2Interest;
        }
    }
    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}
