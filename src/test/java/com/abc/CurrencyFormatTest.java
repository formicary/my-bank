package com.abc;

import org.junit.*;

public class CurrencyFormatTest {

    @Test
    public void testToDollarsBelow1(){
        double amount = 0.50;

        Assert.assertEquals("$0.50", CurrencyFormat.toDollars(amount));
    }

    @Test
    public void testToDollarsBelow1000(){
        double amount = 450.00;

        Assert.assertEquals("$450.00", CurrencyFormat.toDollars(amount));
    }

    @Test
    public void testToDollarsAbove1000(){
        double amount = 1500.00;

        Assert.assertEquals("$1,500.00", CurrencyFormat.toDollars(amount));
    }

    @Test
    public void testToDollarsAboveMillion(){
        double amount = 2000000.00;

        Assert.assertEquals("$2,000,000.00", CurrencyFormat.toDollars(amount));
    }

}
