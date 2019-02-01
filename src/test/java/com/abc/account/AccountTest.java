package com.abc.account;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

        //Test that the interest rate for each account type is accurate
        @Test
        public void testCheckingAccount() {
            CheckingAccount checkingAccount = new CheckingAccount();

            checkingAccount.deposit(3000.0);

            assertEquals(3.0, checkingAccount.interestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void testSavingsAccount() {
            SavingsAccount savingsAccount = new SavingsAccount();

            savingsAccount.deposit(3000.0);

            assertEquals(9.01, Math.round(savingsAccount.interestEarned()*100)/100.0, DOUBLE_DELTA);
        }

        @Test
        public void testMaxiSavingsAccount() {
            MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();

            maxiSavingsAccount.deposit(3000.0);

            assertEquals(3.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
        }

        //Test that negative amounts cannot be deposited or withdrawn
        @Test(expected = IllegalArgumentException.class)
        public void testDepositNegativeAmount() {
            CheckingAccount account = new CheckingAccount();
            account.deposit(-3000);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testWithdrawNegativeAmount() {
            CheckingAccount account = new CheckingAccount();
            account.withdraw(-3000);
        }

        //Test that the balance is stored and calculated correctly
        @Test
        public void testBalance() {
            SavingsAccount account = new SavingsAccount();
            account.deposit(1000.0);
            account.deposit(200.0);
            account.withdraw(600.0);
            assertEquals(600.0, account.balance(), DOUBLE_DELTA);
        }

        @Test
        public void testBalanceNoTransactions() {
          MaxiSavingsAccount account = new MaxiSavingsAccount();
          assertEquals(0.0, account.balance(), DOUBLE_DELTA);
        }
}
