package com.abc;

import com.util.BigDecimalProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountSavingsTest {
    Account account;

    @Before
    public void setup() {
        account = new AccountSavings();
        account.deposit(BigDecimalProvider.format(400));
        account.deposit(BigDecimalProvider.format(200));
        account.withdraw(BigDecimalProvider.format(100));
    }

    @Test
    public void getStatement() {
        assertEquals(
                "Savings Account\n deposit $400.00\n deposit $200.00\n withdrawal -$100.00\nTotal $500.00",
                account.getStatement());
    }

    @Test
    public void getInterestEarnedTest_LOW() {
        account.deposit(BigDecimalProvider.format(298));
        assertEquals(BigDecimalProvider.format(0.80), account.getInterestEarned());
    }

    @Test
    public void getInterestEarnedTest_HIGH() {
        account.deposit(BigDecimalProvider.format(3265));
        assertEquals(BigDecimalProvider.format(6.53), account.getInterestEarned());
    }

}