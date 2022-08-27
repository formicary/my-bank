package com.abc.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MaxiSavingsAccountTest {

    @Mock
    Transaction TRANSACTION;

    @Mock
    MaxiSavingsAccount ACCOUNT;

    private final double DELTA = 1e-15;

    @Test
    public void testCalcInterestEarnedWhenLastTransactionInLessThan10Days() {
        when(ACCOUNT.getLastTransaction()).thenReturn(TRANSACTION);
        when(ACCOUNT.getBalance()).thenReturn(900.0);
        when(TRANSACTION.getTransactionDate()).thenReturn(
                new Date());
        when(ACCOUNT.calcInterestEarned()).thenCallRealMethod();
        assertEquals(0.9, ACCOUNT.calcInterestEarned(), DELTA);
    }

    @Test
    public void testCalcInterestEarnedWhenLastTransactionInMoreThan10Days() throws ParseException {
        when(ACCOUNT.getLastTransaction()).thenReturn(TRANSACTION);
        when(ACCOUNT.getBalance()).thenReturn(900.0);
        when(TRANSACTION.getTransactionDate()).thenReturn(
                new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse("08/16/2022"));
        when(ACCOUNT.calcInterestEarned()).thenCallRealMethod();
        assertEquals(45, ACCOUNT.calcInterestEarned(), DELTA);
    }

    @Test
    public void testCalcInterestEarnedWithNoTransactions() {
        when(ACCOUNT.getLastTransaction()).thenReturn(null);
        when(ACCOUNT.getBalance()).thenCallRealMethod();
        when(ACCOUNT.calcInterestEarned()).thenCallRealMethod();
        assertEquals(0, ACCOUNT.calcInterestEarned(), DELTA);
    }

}