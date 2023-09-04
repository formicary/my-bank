package com.abc;

import com.util.BigDecimalProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountCheckingTest {
    Account account;

    @Before
    public void setup() {
        account = new AccountChecking();
        account.deposit(BigDecimalProvider.format(100));
        account.deposit(BigDecimalProvider.format(4000));
        account.withdraw(BigDecimalProvider.format(200));
    }

    @Test
    public void getStatementTest() {
        assertEquals(
                "Checking Account\n deposit $100.00\n deposit $4,000.00\n withdrawal -$200.00\nTotal $3,900.00",
                account.getStatement());
    }

    @Test
    public void interestEarnedTest() {
        assertEquals(BigDecimalProvider.format(3.9), account.getInterestEarned());
    }
}