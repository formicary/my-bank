package com.abc.entity;

import java.math.BigDecimal;

/**
 * Account type defines the different types of accounts that can be opened.
 * Each account type defines 3 savings rates that are applied by the {@Code InterestCalculator}
 * @author aneesh
 */
public enum AccountType {
    CURRENT(new BigDecimal("0.001"), new BigDecimal("0.001"), new BigDecimal("0.001")),
    SAVINGS(new BigDecimal("0.001"), new BigDecimal("0.002"), new BigDecimal("0.002")),
    MAXI_SAVINGS(new BigDecimal("0.02"), new BigDecimal("0.05"), new BigDecimal("0.1")),
    MAXI_SAVINGS_ADD(new BigDecimal("0.001"), new BigDecimal("0.05"), new BigDecimal("0.05"));

    AccountType(BigDecimal rate, BigDecimal secondRate, BigDecimal thirdRate){
        this.flatRate = rate;
        this.secondRate = secondRate;
        this.thirdRate = thirdRate;
    }

    private BigDecimal flatRate;
    private BigDecimal secondRate;
    private BigDecimal thirdRate;

    /**
     * Obtain the flat rate of interest for the account
     * @return flat rate percentage
     */
    public BigDecimal getFlatRate() {
        return flatRate;
    }

    /**
     * Obtain the second rate of interest for the account
     * @return second rate percentage
     */
    public BigDecimal getSecondRate() {
        return secondRate;
    }

    /**
     * Obtain the third rate of interest for the account
     * @return third rate percentage
     */
    public BigDecimal getThirdRate() {
        return thirdRate;
    }
}
