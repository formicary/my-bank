package com.abc;

import com.abc.account.Account;
import com.abc.account.AccountFactory;
import com.abc.account.AccountType;

import com.abc.branch.Customer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sameen on 24/08/2018.
 */
public class AccountTest {

    private AccountFactory factory;

    @BeforeClass
    public void setUp() {
        factory = new AccountFactory();
    }

    @Test
    public void testCheckingAccount() {
        Account checkingAccount = factory.createAccount(new Customer("John"),
                AccountType.CHECKING, 50.00);

        assertEquals(50.00, checkingAccount.getBalance(), 0.001);
    }

    @Test
    public void testSavingsAccount() {
        Account savingsAccount = factory.createAccount(new Customer("John"),
                AccountType.SAVINGS, 50.00);

        assertEquals(50.00, savingsAccount.getBalance(), 0.001);
    }

    @Test
    public void testMaxiSavingsAccount() {
        Account maxiSavingsAccount = factory.createAccount(new Customer("John"),
                AccountType.MAXI_SAVINGS, 50.00);

        assertEquals(50.00, maxiSavingsAccount.getBalance(), 0.001);

    }
}
