package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    // should use lower interest rate (0.1%)
    @Test
    public void testInterestRateUnder1000(){
        SavingsAccount saver = new SavingsAccount(new Customer("Fabio"));

        saver.deposit(500.00); // = $0.50 interest

        assertEquals(0.50, saver.interestEarned(), DOUBLE_DELTA);
    }

    // should use 0.1% for the first $1000, then 0.2% for anything afterwards
    @Test
    public void testInterestOver1000(){
        SavingsAccount saver = new SavingsAccount(new Customer("Christof"));

        saver.deposit(1500.00); // earns $2.00 interest

        assertEquals(2.00, saver.interestEarned(), DOUBLE_DELTA);
    }
}
