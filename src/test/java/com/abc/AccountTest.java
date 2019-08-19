package com.abc;

import com.abc.bank.CheckingAccount;
import com.abc.bank.MaxiSavingsAccount;
import com.abc.bank.SavingsAccount;
import com.abc.bank.Transaction;

import org.junit.Test;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.containsString;

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
    public void testSavingsAccountConstructor() {
        SavingsAccount savingsAccount = new SavingsAccount();

        assertEquals("Savings Account", savingsAccount.getAccountType());
    }

    @Test
    public void testMaxiSavingsAccountConstructor() {
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();

        assertEquals("Maxi Savings Account", maxiSavingsAccount.getAccountType());
    }

    @Test
    public void testOpenCheckingAccount() {
        CheckingAccount checkingAccount = new CheckingAccount();

        assertEquals("Checking Account", checkingAccount.getAccountType());
    }

    @Test
    public void testDepositLessThanZero() {
        SavingsAccount savingsAccount = new SavingsAccount();
        try {
            savingsAccount.deposit(-100);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero"));
        }

    }

    @Test
    public void testDepositZero() {
        SavingsAccount savingsAccount = new SavingsAccount();
        try {
            savingsAccount.deposit(0);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero"));
        }
    }

    @Test
    public void testDepositGreaterThanZero() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.deposit(100);

        assertEquals(100, savingsAccount.getAccountValue(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawLessThanZero() {
        SavingsAccount savingsAccount = new SavingsAccount();
        try {
            savingsAccount.deposit(100);
            savingsAccount.withdraw(-40);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero"));
        }
    }

    @Test
    public void testWithdrawZero() {
        SavingsAccount savingsAccount = new SavingsAccount();
        try {
            savingsAccount.deposit(10);
            savingsAccount.withdraw(0);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero"));
        }
    }

    @Test
    public void testWithdrawGreaterThanZeroLessThanAccountValue() {
        SavingsAccount savingsAccount = new SavingsAccount();
            savingsAccount.deposit(100);
            savingsAccount.withdraw(50);
            assertEquals(50, savingsAccount.getAccountValue(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawGreaterThanZeroGreaterThanAccountValue() {
        SavingsAccount savingsAccount = new SavingsAccount();
        try {
            savingsAccount.deposit(100);
            savingsAccount.withdraw(110);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("amount must be less than the total value of the account"));
        }
    }

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

    @Test
    public void testStatementGeneration() {
        SavingsAccount savingsAccount = new SavingsAccount();

        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals( "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00", savingsAccount.getAccountStatement());
    }
}
