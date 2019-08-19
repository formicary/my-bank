package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class AccountTest {
    public class TestMaxiSavingsAccount extends MaxiSavingsAccount {
        public TestMaxiSavingsAccount() {
            super();
        }

        @Override
        public void deposit(double amount) {
            transactions.add(new TestTransaction(amount));
            accountValue += amount;
        }
    }

    public class TestTransaction extends Transaction {
        public TestTransaction(double amount) {
            super(amount);

            Calendar newDate = Calendar.getInstance();
            newDate.add(Calendar.DAY_OF_MONTH, -11);

            super.transactionDate = newDate.getTime();
        }

    }

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testTotalInterestEarnedSavingsAccountUnderOneThousand() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.deposit(100);

        assertEquals(0.1, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestEarnedSavingsAccountOverOneThousand() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.deposit(1100);

        assertEquals(1.2, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestEarnedMaxiSavingsWithinTenDays() {
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(100);

        assertEquals(0.1, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedMaxiSavingsNotWithinTenDays() {
        TestMaxiSavingsAccount testMaxiSavingsAccount = new TestMaxiSavingsAccount();
        testMaxiSavingsAccount.deposit(100);

        assertEquals(5, testMaxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedCheckingAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.deposit(100);

        assertEquals(0.1, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }
}
