package com.abc;

import java.time.LocalDate;

public class MaxiSavingsAccount extends Account {
    private double yearlyInterestRateNoWithdrawal = 0.05;
    private double yearlyInterestRateAfterWithdrawal = 0.001;
    private double currentYearlyInterestRate;

    MaxiSavingsAccount() {
        super();
        this.currentYearlyInterestRate = yearlyInterestRateNoWithdrawal; //default - no withdrawals, so starting interest rate of 5%.
    }
    @Override
    public double interestEarnedDaily() {
        this.toggleInterestRate(DateProvider.now());
        double amountToAccrueInterestOn = this.getAccountBalance();
        return amountToAccrueInterestOn * this.dailyCompoundInterestRate(currentYearlyInterestRate);
    }
    public String getAccountType(){
        return "Maxi-Savings Account";
    }
    double getCurrentYearlyInterest(){
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
