package com.abc.account;

import com.abc.util.IDateProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MaxiSavingsAccountShould {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void CalculateInterestCorrectly_GivenWithdrawalWithinTheLast10Days() {
        IDateProvider mockDateProvider = Mockito.mock(IDateProvider.class);
        when(mockDateProvider.getCurrentDate()).thenReturn(Calendar.getInstance().getTime());

        Account account = new MaxiSavingsAccount(mockDateProvider);

        account.deposit(500);
        account.withdraw(100);
        assertEquals(0.4, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void CalculateInterestCorrectly_GivenNoWithdrawalsWithinTheLast10Days() {
        IDateProvider mockDateProvider = Mockito.mock(IDateProvider.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 0, 1);
        Date expectedDate = calendar.getTime();

        when(mockDateProvider.getCurrentDate()).thenReturn(expectedDate);

        Account account = new MaxiSavingsAccount(mockDateProvider);
        account.deposit(500);
        assertEquals(25, account.interestEarned(), DOUBLE_DELTA);
    }


    @Test
    public void ReturnCorrectPrettyAccountName() {
        IDateProvider mockDateProvider = Mockito.mock(IDateProvider.class);

        Account account = new MaxiSavingsAccount(mockDateProvider);

        assertEquals("Maxi Savings Account", account.getPrettyAccountType());
    }
}