package com.abc;

import com.abc.account.AccountFactory;
import com.abc.account.AccountType;
import com.abc.branch.Bank;
import com.abc.branch.Customer;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;
    private AccountFactory accountFactory;

    @Before
    public void setUp() {
        bank = new Bank();
        accountFactory = new AccountFactory();
    }

    @After
    public void tearDown() {
        bank = null;
        accountFactory = null;
    }

    @Test
    public void customerSummary() {
        Customer john = new Customer("John");
        accountFactory.createAccount(john, AccountType.CHECKING, DOUBLE_DELTA);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Customer bill = new Customer("Bill");
        accountFactory.createAccount(bill, AccountType.CHECKING, 100.0);
        bank.addCustomer(bill);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Customer bill = new Customer("Bill");
        accountFactory.createAccount(bill, AccountType.SAVINGS, 1500.0);
        bank.addCustomer(bill);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Customer bill = new Customer("Bill");
        accountFactory.createAccount(bill, AccountType.MAXI_SAVINGS, 3000.0);
        bank.addCustomer(bill);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
