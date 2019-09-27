package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class accountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test public void defaultConstructor() {
        Account account = new Account();

        assertEquals(0, account.getAccountType());
    }

    @Test public void createAccountNotListed() {
        Account account = new Account(55);

        assertEquals(0, account.getAccountType());
    }

    @Test
    public void deposit() {
        Account account = new Account(Account.CHECKING);
        account.deposit(100);

        assertEquals(10000, account.getBalanceCents());
    }

    @Test
    public void depositThreeDecimals() {
        Account account = new Account(Account.CHECKING);
        account.deposit(100.125);

        assertEquals(10012, account.getBalanceCents());
    }

    @Test
    public void depositNegative() {
        Account account = new Account(Account.CHECKING);

        try {
            account.deposit(-100);
            fail("Expected illegal argument exception to be thrown");
        } catch (IllegalArgumentException e){
            assertEquals("amount must be greater than zero", e.getMessage());
        }
    }

    @Test
    public void withdraw() {
        Account account = new Account();
        account.withdraw(100);

        assertEquals(-10000, account.getBalanceCents());
    }

    @Test public void withdrawTwoDecimals() {
        Account account = new Account();
        account.withdraw(123.45);

        assertEquals(-12345, account.getBalanceCents());
    }

    @Test public void withdrawThreeDecimals() {
        Account account = new Account(Account.CHECKING);
        account.withdraw(100.255);

        assertEquals(-10025, account.getBalanceCents());
    }

    @Test
    public void withdrawNegative() {
        Account account = new Account(Account.CHECKING);

        try {
            account.withdraw(-100);
            fail("Expected illegal argument exception to be thrown");
        } catch (IllegalArgumentException e){
            assertEquals(e.getMessage(), "amount must be greater than zero");
        }
    }

    @Test public void interestCheckingAccount() {
        Account account = new Account();
        account.deposit(1000);

        assertEquals(1.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test public void interestSavingsAccount() {
        Account account = new Account(Account.SAVINGS);
        account.deposit(1000);

        assertEquals(1.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test public void higherInterestSavingsAccount() {
        Account account = new Account(Account.SAVINGS);
        account.deposit(2000);

        assertEquals(3.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test public void interestMaxi() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(1000);

        assertEquals(50.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test public void interestMaxiWithdraw() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(2000);
        account.withdraw(1000);

        assertEquals(1.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test public void negativeInterestChecking() {
        Account account = new Account(Account.CHECKING);
        account.withdraw(1500);

        assertEquals(-1.5, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test public void createStatementSavings() {
        Account account = new Account(Account.SAVINGS);
        account.deposit(1234.55);
        account.deposit(5678.44);
        account.deposit(9123.33);
        account.withdraw(4567.27);

        assertEquals("Savings Account\n" +
                "  deposit $1,234.55\n" +
                "  deposit $5,678.44\n" +
                "  deposit $9,123.33\n" +
                "  withdrawal $4,567.27\n" +
                "Total $11,469.05", account.createStatement());
    }

    @Test public void createStatementNegative() {
        Account account = new Account(Account.SAVINGS);
        account.withdraw(555.55);

        assertEquals("Savings Account\n" +
                "  withdrawal $555.55\n" +
                "Total -$555.55", account.createStatement());
    }

    @Test public void getBalanceDollars() {
        Account account = new Account(Account.SAVINGS);
        account.deposit(123.45);

        assertEquals(123.45, account.getBalanceDollars(), DOUBLE_DELTA);
    }

    @Test public void getBalanceDollarsNegative() {
        Account account = new Account(Account.SAVINGS);
        account.withdraw(123.45);

        assertEquals(-123.45, account.getBalanceDollars(), DOUBLE_DELTA);
    }

}
