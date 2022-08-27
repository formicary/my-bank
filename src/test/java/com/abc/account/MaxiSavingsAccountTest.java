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
    private Transaction mockTransaction;

    @Mock
    private MaxiSavingsAccount mockAccount;

    private final double DELTA = 1e-15;

    @Test
    public void testCalcInterestEarnedWhenLastTransactionInLessThan10Days() {
        when(mockAccount.getLastTransaction()).thenReturn(mockTransaction);
        when(mockAccount.getBalance()).thenReturn(900.0);
        when(mockTransaction.getTransactionDate()).thenReturn(
                new Date());
        when(mockAccount.calcInterestEarned()).thenCallRealMethod();
        assertEquals(0.9, mockAccount.calcInterestEarned(), DELTA);
    }

    @Test
    public void testCalcInterestEarnedWhenLastTransactionInMoreThan10Days() throws ParseException {
        when(mockAccount.getLastTransaction()).thenReturn(mockTransaction);
        when(mockAccount.getBalance()).thenReturn(900.0);
        when(mockTransaction.getTransactionDate()).thenReturn(
                new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2022"));
        when(mockAccount.calcInterestEarned()).thenCallRealMethod();
        assertEquals(45, mockAccount.calcInterestEarned(), DELTA);
    }

    @Test
    public void testCalcInterestEarnedWithNoTransactions() {
        when(mockAccount.getLastTransaction()).thenReturn(null);
        when(mockAccount.getBalance()).thenCallRealMethod();
        when(mockAccount.calcInterestEarned()).thenCallRealMethod();
        assertEquals(0, mockAccount.calcInterestEarned(), DELTA);
    }

}