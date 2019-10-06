package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
    private BigDecimal yearlyInterestRate;

    CheckingAccount(){
        super();
        this.yearlyInterestRate = BigDecimal.valueOf(0.001);
    }
    @Override
    public BigDecimal interestEarnedDaily(){
        BigDecimal amountToAccrueInterestOn = this.getAccountBalance();
        return amountToAccrueInterestOn.multiply(dailyCompoundInterestRate(yearlyInterestRate));
    }
    @Override
    public String getAccountType() {
        return "Checking Account";
    }
}
