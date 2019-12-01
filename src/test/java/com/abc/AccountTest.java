package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;
    Account checking = new CheckingAccount();
    Account savings = new SavingsAccount();
    Account maxiSavings = new MaxiSavingsAccount();

    @Test
    public void testDeposit() {
        checking.deposit(200);

        assertEquals(200, checking.getBalance(), DOUBLE_DELTA);
        assertFalse(1000 == checking.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDeposit() throws Exception {
        checking.deposit(-200);
    }

    @Test
    public void testWithdraw() {
        checking.deposit(2000);
        checking.withdraw(800);

        assertEquals(1200, checking.getBalance(), DOUBLE_DELTA);
        assertFalse(1000 == checking.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeWithdraw() throws Exception {
        checking.withdraw(-200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotWithdrawMoreThanWhatYouHave() throws Exception {
        checking.deposit(2000);
        checking.withdraw(2200);
    }

    @Test
    public void testSumTransactions() {
        checking.deposit(2000);
        checking.withdraw(800);
        checking.deposit(1000);
        checking.withdraw(800);

        assertEquals(1400, checking.sumTransactions(), DOUBLE_DELTA);
        assertFalse(1000 == checking.sumTransactions());
    }

    @Test
    public void interestEarnedCheckingAccount() {
        checking.deposit(2000);
        checking.withdraw(1000);

        assertEquals(1.00, checking.interestEarned(), DOUBLE_DELTA);
        assertFalse(1000 == checking.interestEarned());
    }


    @Test
    public void interestEarnedSavingsAccount() {
        savings.deposit(2000);
        savings.withdraw(1000);

        assertEquals(1.00, savings.interestEarned(), DOUBLE_DELTA);
        assertFalse(1000 == savings.interestEarned());

        savings.deposit(2000);

        assertEquals(5.00, savings.interestEarned(), DOUBLE_DELTA);
        assertFalse(1000 == savings.interestEarned());
    }

    @Test
    public void interestEarnedMaxiSavingsAccount() {
        maxiSavings.deposit(2000);
        maxiSavings.withdraw(1000);

        assertEquals(20.00, maxiSavings.interestEarned(), DOUBLE_DELTA);
        assertFalse(1000 == maxiSavings.interestEarned());

        maxiSavings.deposit(1000);

        assertEquals(70.00, maxiSavings.interestEarned(), DOUBLE_DELTA);
        assertFalse(1000 == maxiSavings.interestEarned());


        maxiSavings.deposit(1000);

        assertEquals(170.00, maxiSavings.interestEarned(), DOUBLE_DELTA);
        assertFalse(1000 == maxiSavings.interestEarned());
    }
}