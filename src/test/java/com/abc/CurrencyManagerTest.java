package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CurrencyManagerTest {

    @Test
    public void toDollarsTest(){
        CurrencyManager manager = new CurrencyManager();
        assertEquals("$20.00",manager.toDollarsAbs(BigDecimal.valueOf(20)));
    }
}
