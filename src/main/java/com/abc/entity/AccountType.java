package com.abc.entity;

import java.math.BigDecimal;

public enum AccountType {
    CURRENT(new BigDecimal("0.001"), new BigDecimal("0.001")),
    SAVINGS(new BigDecimal("0.001"), new BigDecimal("0.002")),
    MAXI_SAVINGS(new BigDecimal("0.02"), new BigDecimal("0.05"));

    AccountType(BigDecimal rate, BigDecimal additionalRate){
        this.flatRate = rate;
        this.additionalRate = additionalRate;
    }
    private BigDecimal flatRate;
    private BigDecimal additionalRate;

    public BigDecimal getFlatRate() {
        return flatRate;
    }

    public BigDecimal getAdditionalRate() {
        return additionalRate;
    }
}
