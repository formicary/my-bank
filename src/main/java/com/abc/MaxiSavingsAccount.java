package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MaxiSavingsAccount extends Account {
    private BigDecimal yearlyInterestRateNoWithdrawal = BigDecimal.valueOf(0.05);
    private BigDecimal yearlyInterestRateAfterWithdrawal = BigDecimal.valueOf(0.001);
    private BigDecimal currentYearlyInterestRate;

    MaxiSavingsAccount() {
        super();
        this.currentYearlyInterestRate = yearlyInterestRateNoWithdrawal; //default - no withdrawals, so starting interest rate of 5%.
    }
    @Override
    public BigDecimal interestEarnedDaily() {
        this.toggleInterestRate(DateProvider.now());
        BigDecimal amountToAccrueInterestOn = this.getAccountBalance();
        return amountToAccrueInterestOn.multiply(this.dailyCompoundInterestRate(currentYearlyInterestRate));
    }
    public String getAccountType(){
        return "Maxi-Savings Account";
    }
    BigDecimal getCurrentYearlyInterest(){
        return this.currentYearlyInterestRate;
    }
    void toggleInterestRate(LocalDate currentDate){
        if(this.withdrawalExists()) {
            LocalDate withdrawalDate = this.getLatestWithdrawalDate();
            int daysSinceWithdrawal = (int) java.time.temporal.ChronoUnit.DAYS.between(withdrawalDate, currentDate);
            if (daysSinceWithdrawal <= 10) {
                currentYearlyInterestRate = yearlyInterestRateAfterWithdrawal;
            } else {
                currentYearlyInterestRate = yearlyInterestRateNoWithdrawal;
            }
        }
        else{
                currentYearlyInterestRate = yearlyInterestRateNoWithdrawal;
            }
        }
}
