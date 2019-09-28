package com.abc.accounts;

import java.time.LocalDateTime;

/**
 * @project MyBank
 */
public class Savings extends Account {

    protected double secIntRate;
    protected double secAccrueRate;

    public Savings() {
        super();
        init();
    }

    public Savings(LocalDateTime date) {
        super(date);
        init();
    }

    private void init() {
        intRate = 0.001;
        secIntRate = 0.002;
        accrueRate = intRate / 365;
        secAccrueRate = secIntRate / 365;
    }

    @Override
    protected void compoundInterest() {

        double earnedInt;

        if (balance <= 1000) {
            earnedInt = balance * intRate;
            balance += earnedInt;
            totalEarnedInt += earnedInt;
        } else {
            earnedInt = (1000 * intRate) + ((balance - 1000) * (secIntRate));
            balance += earnedInt;
            totalEarnedInt += earnedInt;
        }
    }

    @Override
    protected void accrueInterest() {
        intRate += accrueRate;
        secIntRate += secAccrueRate;
    }

    @Override
    public String toString() {
        return "Savings Account";
    }
}
