package com.abc;

import com.util.BigDecimalProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountMaxiSavingsTest {
    Account account;

    @Before
    public void setUp() throws Exception {
        account = new AccountMaxiSavings();
        account.deposit(BigDecimalProvider.format(400));
        account.deposit(BigDecimalProvider.format(200));
        account.withdraw(BigDecimalProvider.format(100));
    }

    @Test
    public void getStatement() {
        assertEquals(
                "Maxi Savings Account\n deposit $400.00\n deposit $200.00\n withdrawal -$100.00\nTotal $500.00",
                account.getStatement());
    }

    @Test
    public void interestEarned() {

    }

    @Test
    public void getInterestsEarned_LOW() {
        account.deposit(BigDecimalProvider.format(87));
        assertEquals(BigDecimalProvider.format(11.74), account.getInterestEarned());
    }

    @Test
    public void getInterestsEarned_MID() {
        account.deposit(BigDecimalProvider.format(1087));
        assertEquals(BigDecimalProvider.format(49.35), account.getInterestEarned());
    }

    @Test
    public void getInterestsEarned_HIGH() {
        account.deposit(BigDecimalProvider.format(2087));
        assertEquals(BigDecimalProvider.format(128.70), account.getInterestEarned());
    }

}