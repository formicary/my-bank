package com.abc.account;

import com.abc.customer.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    private final String TEST_CUSTOMER_NAME = "Josh";
    private final double DELTA = 1e-15;

    @Test
    public void testDeposit() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.deposit(150.0);
        account.deposit(350.0);
        account.deposit(100.0);
        assertEquals(600.0, account.getBalance(), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositWithZeroAmount() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.deposit(0.0);
    }

    @Test
    public void testWithdraw() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.deposit(600.0);
        account.withdraw(100.0);
        assertEquals(500.0, account.getBalance(), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawWithZeroAmount() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.deposit(100.0);
        account.withdraw(0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawIfAmountGreaterThanBalance() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.deposit(100.0);
        account.withdraw(2000.0);
    }

    @Test
    public void testSumTransactions() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.deposit(100.0);
        account.withdraw(20.0);
        account.deposit(200.0);
        account.withdraw(100.0);
        assertEquals(180.0, account.sumTransactions(), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferWhenTargetAccountIsNull() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.transfer(100.0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferWhenTargetAccountIsTheSameAccount() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.transfer(100.0, account);
    }

    @Test
    public void testTransfer() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        Account targetAccount = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.CHECKING);
        account.deposit(100.0);
        account.transfer(30.0, targetAccount);
        assertEquals(70.0, account.getBalance(), DELTA);
        assertEquals(30.0, targetAccount.getBalance(), DELTA);
    }
}