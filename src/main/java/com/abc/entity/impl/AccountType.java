package com.abc.entity.impl;

import java.math.BigDecimal;

/**
 * Account type defines the different types of accounts that can be opened.
 * Each account type defines savings rates that are applied by the {@Code InterestCalculator}
 * @author aneesh
 */
public enum AccountType {
    CURRENT(new BigDecimal("0.001"), new BigDecimal("0.001"), new BigDecimal("0.001")),
    SAVINGS(new BigDecimal("0.001"), new BigDecimal("0.002"), new BigDecimal("0.002")),
    MAXI_SAVINGS(new BigDecimal("0.02"), new BigDecimal("0.05"), new BigDecimal("0.1"));

    AccountType(BigDecimal rate, BigDecimal secondRate, BigDecimal thirdRate){
        this.flatRate = rate;
        this.secondRate = secondRate;
        this.thirdRate = thirdRate;
    }
    private BigDecimal flatRate;
    private BigDecimal secondRate;
    private BigDecimal thirdRate;

    public BigDecimal getFlatRate() {
        return flatRate;
    }

    public BigDecimal getSecondRate() {
        return secondRate;
    }

    public BigDecimal getThirdRate() {
        return thirdRate;
    }
}
