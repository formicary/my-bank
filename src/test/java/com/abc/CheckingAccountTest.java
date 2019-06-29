package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void interestRatesTest(){
        CheckingAccount check = new CheckingAccount(new Customer("Bill"));

        check.deposit(1250.00); // = $1.25 interest for checking account

        assertEquals(1.25, check.interestEarned(), DOUBLE_DELTA);
    }

}
