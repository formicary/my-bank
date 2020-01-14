package com.abc;

import com.abc.util.IDateProvider;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class TransactionShould {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void CreateATransactionWithTheExpectedValueAndDare() {
        int transactionAccount = 5;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 0, 14);
        Date expectedDate = calendar.getTime();


        IDateProvider mockDateProvider = Mockito.mock(IDateProvider.class);
        when(mockDateProvider.getCurrentDate()).thenReturn(expectedDate);

        Transaction transaction = new Transaction(transactionAccount, mockDateProvider);

        assertEquals(transactionAccount, transaction.getAmount(), DOUBLE_DELTA);
        assertEquals(expectedDate, transaction.getTransactionDate());
    }
}
