package com.abc;

import com.abc.account.Account;
import com.abc.account.AccountFactory;
import com.abc.account.AccountType;

import com.abc.branch.Customer;
import com.abc.util.ZeroAmountException;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by sameen on 24/08/2018.
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private AccountFactory factory;

    @Before
    public void setUp() {
        this.factory = new AccountFactory();
    }

    @After
    public void tearDown() {
        this.factory = null;
    }

    @Test
    public void testCheckingAccount() {
        Account checkingAccount = null;
        checkingAccount = factory.createAccount(new Customer("John"),
                AccountType.CHECKING, 50.00);

        assertEquals(50.00, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccount() {
        Account savingsAccount = null;
        savingsAccount = factory.createAccount(new Customer("John"),
                AccountType.SAVINGS, 50.00);

        assertEquals(50.00, savingsAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccount() {
        Account maxiSavingsAccount = null;
        maxiSavingsAccount = factory.createAccount(new Customer("John"),
                AccountType.MAXI_SAVINGS, 50.00);

        assertEquals(50.00, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testId() {
        Customer bill = new Customer("Bill");
        Account checkingAccountA = factory.createAccount(bill, AccountType.CHECKING, 250.00);
        Account checkingAccountB = factory.createAccount(bill, AccountType.CHECKING, 250.00);

        assertNotEquals(checkingAccountA.getId(), checkingAccountB.getId());
    }
}
