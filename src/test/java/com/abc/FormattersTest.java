package com.abc;

import org.junit.*;

public class FormattersTest {

    @Test
    public void testFormatOne(){
        Assert.assertEquals("1 account", Formatters.format(1, "account"));
    }

    @Test
    public void testFormatMultiple(){
        Assert.assertEquals("3 accounts", Formatters.format(3, "account"));
    }

    @Test
    public void testToDollarsBelow1(){
        double amount = 0.50;

        Assert.assertEquals("$0.50", Formatters.toDollars(amount));
    }

    @Test
    public void testToDollarsBelow1000(){
        double amount = 450.00;

        Assert.assertEquals("$450.00", Formatters.toDollars(amount));
    }

    @Test
    public void testToDollarsAbove1000(){
        double amount = 1500.00;

        Assert.assertEquals("$1,500.00", Formatters.toDollars(amount));
    }

    @Test
    public void testToDollarsAboveMillion(){
        double amount = 2000000.00;

        Assert.assertEquals("$2,000,000.00", Formatters.toDollars(amount));
    }

}
