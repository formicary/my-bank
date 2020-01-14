package com.abc;

// import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertTrue;

// The test of the accounts class should:
// 1) Cover all public methods.
// 2) Verify that money can be withdrawn from an account.
// 3) Verify that money can be deposited to an account.
// 4) Verify that all the subclasses of accounts work properly; namely, that
// their interest rates are calculated properly. 
public class AccountsTests {
    private static final double DOUBLE_DELTA = 1e-15;
    // Check that an Account can withdraw and deposit money. 
    // Also test the sumTransactions method. 
    // This also tests points 2) and 3)
    @Test
    public void accountWithdrawDepositSumBalanceTest() {
        CheckingAccount testAccount = new CheckingAccount();

        double accountBalance = testAccount.sumTransactions();
        assertEquals("Account balance of a new account should be 0", 
                        0, accountBalance, DOUBLE_DELTA);

        testAccount.withdraw(50.0);
        accountBalance = testAccount.sumTransactions();
        assertEquals("Account balance of account should be -50 after withdrawal",
                        -50, accountBalance, DOUBLE_DELTA);

        testAccount.deposit(100.0);
        accountBalance = testAccount.sumTransactions();
        assertEquals("Account balance of account should be 50 after deposit",
                        50, accountBalance, DOUBLE_DELTA);

        testAccount.withdraw(200.0);
        testAccount.deposit(921);
        testAccount.deposit(32);
        accountBalance = testAccount.sumTransactions();
        assertEquals("sumTransactions should sum transaction history correctly",
                        803, accountBalance, DOUBLE_DELTA);
    }

    // Test that improper usage of withdraw/deposit throws an 
    // IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalWithdrawInput() {
        CheckingAccount testAccount = new CheckingAccount();
        testAccount.withdraw(-50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalDepositInput() {
        CheckingAccount testAccount = new CheckingAccount();
        testAccount.deposit(-50);
    }

    // Test getAccountType returns correct type
    @Test
    public void getAccountTypeTest() {
        CheckingAccount testCheckingAccount = new CheckingAccount();
        SavingsAccount testSavingsAccount = new SavingsAccount();
        MaxiSavingsAccount testMaxiSavingsAccount = new MaxiSavingsAccount();

        assertEquals("A checkingAccount should be of type CHECKING", 
                account_type.CHECKING, 
                testCheckingAccount.getAccountType());
        
        assertEquals("A savingsAccount should be of type SAVINGS", 
                account_type.SAVINGS, 
                testSavingsAccount.getAccountType());

        assertEquals("A MaxiSavingsAccount should be of type MAXI_SAVINGS",
                account_type.MAXI_SAVINGS, 
                testMaxiSavingsAccount.getAccountType());
    }

    // Test that the interest rate is calculated correctly for each account
    // type. Tests items 4)
    @Test 
    public void checkingAccountInterestRateTest() {
        CheckingAccount testCheckingAccount = new CheckingAccount();

        testCheckingAccount.deposit(1000);

        assertEquals("CheckingAccount containing 1000 should generate 1",
                    1, testCheckingAccount.interestEarned(), DOUBLE_DELTA);

        testCheckingAccount.withdraw(2000);

        assertEquals("CheckingAccount containing -1000 should generate -1",
                    -1, testCheckingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test 
    public void savingsAccountInterestRateTest() {
        SavingsAccount testSavingsAccount = new SavingsAccount();

        testSavingsAccount.deposit(1000);

        assertEquals("SavingsAccount containing 1000 should generate 1",
                    1, testSavingsAccount.interestEarned(), DOUBLE_DELTA);

                    testSavingsAccount.deposit(1000);

        assertEquals("SavingsAccount containing 2000 should generate 3",
                    3, testSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestRateTest() {
        MaxiSavingsAccount testMaxiSavingsAccount = new MaxiSavingsAccount();

        testMaxiSavingsAccount.deposit(1000);

        assertEquals("Maxi-Savings Account with no withdrawals should have 50 " + 
                     "interest on 1000", 
                     50, 
                     testMaxiSavingsAccount.interestEarned(),
                     DOUBLE_DELTA);

        testMaxiSavingsAccount.withdraw(1000);
        testMaxiSavingsAccount.deposit(1000);

        assertEquals("Maxi-Savings Account with a recent withdrawal should " + 
                     "have interest of 1",
                     1,
                     testMaxiSavingsAccount.interestEarned(),
                     DOUBLE_DELTA);

    }

    // Test to see that transfers work. Also tests edge case (amount less
    // than 0).
    @Test(expected = IllegalArgumentException.class) 
    public void testTransferIllegalArgument() {
        CheckingAccount account_1 = new CheckingAccount();
        CheckingAccount account_2 = new CheckingAccount();

        account_1.transferMoneyToAccount(-1, account_2);
    }

    @Test
    public void testMoneyTransfer() {
        CheckingAccount account_1 = new CheckingAccount();
        CheckingAccount account_2 = new CheckingAccount();

        account_1.transferMoneyToAccount(50, account_2);

        assertEquals("account_1 should have been deducted 50",
                    -50, account_1.sumTransactions(), DOUBLE_DELTA);
        assertEquals("account_2 should have been credited 50", 
                    50, account_2.sumTransactions(), DOUBLE_DELTA);
    }
}
