package com.abc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Before
    @After
    public void reset() {
        DateProvider.setForcedDate(null);
    }

    @Test
    public void maxiSavingInterestWhenLastWithdrawalIsToday() {
        Account account = new Account("iban", Account.MAXI_SAVINGS);

        DateProvider.setForcedDate(LocalDate.now().minus(5, ChronoUnit.DAYS));
        account.deposit(BigDecimal.valueOf(600));
        account.withdraw(BigDecimal.valueOf(100));
        account.deposit(BigDecimal.valueOf(600));
        DateProvider.setForcedDate(LocalDate.now());
        account.withdraw(BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(1).compareTo(account.interestEarned()));
    }

    @Test
    public void maxiSavingInterestWhenLast10daysIsWithoutWithdrawal() {
        Account account = new Account("iban", Account.MAXI_SAVINGS);

        DateProvider.setForcedDate(LocalDate.now().minus(45, ChronoUnit.DAYS));
        account.deposit(BigDecimal.valueOf(500));
        account.withdraw(BigDecimal.valueOf(100));
        DateProvider.setForcedDate(LocalDate.now().minus(10, ChronoUnit.DAYS));
        account.withdraw(BigDecimal.valueOf(100));
        DateProvider.setForcedDate(LocalDate.now());
        account.deposit(BigDecimal.valueOf(700));

        assertEquals(0, BigDecimal.valueOf(50).compareTo(account.interestEarned()));
    }

    @Test
    public void maxiSavingInterestWhenIsWithoutWithdrawal() {
        Account account = new Account("iban", Account.MAXI_SAVINGS);

        DateProvider.setForcedDate(LocalDate.now());
        account.deposit(BigDecimal.valueOf(1000));

        assertEquals(0, BigDecimal.valueOf(50).compareTo(account.interestEarned()));
    }

}
