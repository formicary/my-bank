package com.abc;

import com.abc.types.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Bank class
 */
public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    /**
     * Test getting the summary of a customer
     */
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    /**
     * Test getting the interest earned from a checking account
     */
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    /**
     * Test getting the interest earned from a saving account
     */
    public void savingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    /**
     * Test getting the interest earned from a maxi saving account with no withdrawals
     */
    public void maxiSavingsAccountWithNoWithdrawals() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    /**
     * Test getting the interest earned from a maxi saving account with withdrawals older than ten days
     */
    public void maxiSavingsAccountWithWithdrawalsOlderThanTenDays() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        checkingAccount.withdraw(100.0);

        assertEquals(2.9, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
