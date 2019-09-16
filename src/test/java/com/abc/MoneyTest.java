package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MoneyTest {

    @Test
    public void add() {
        Money x = new Money("10");
        Money y = new Money("5.50");
        assertEquals(new Money("15.50"), x.add(y));
    }

    @Test
    public void subtract() {
        Money x = new Money("10");
        Money y = new Money("5.50");
        assertEquals(new Money("4.50"), x.subtract(y));
    }

    @Test
    public void multiply() {
        Money x = new Money("10");
        BigDecimal y = new BigDecimal("0.1");
        assertEquals(new Money("1"), x.multiply(y));
    }

    @Test
    public void compareTo() {
        Money x = new Money("10");
        Money y = new Money("5.50");
        assertEquals(1, x.compareTo(y));
        assertEquals(-1, y.compareTo(x));
        assertEquals(0, x.compareTo(x));
    }

    @Test
    public void equals() {
        Money x = new Money("10");
        Money y = new Money("5.50");
        assertEquals(true, x.equals(x));
        assertEquals(false, x.equals(y));
        assertEquals(false, y.equals(x));


    }

    @Test
    public void stringConversion() {
        assertEquals("$2.00", new Money("2").toString());
        assertEquals("$2.00", new Money("2.0").toString());
        assertEquals("$2.00", new Money("2.00").toString());
        assertEquals("$2.00", new Money("2.000").toString());
    }
}