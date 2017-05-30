package com.abc.transactionsTests;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.exceptions.ExceedsNegativeBalanceException;
import com.abc.exceptions.NonPositiveAmountException;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class WithdrawAmountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }

    @Test
    public void withdrawMoneyTest() throws ExceedsNegativeBalanceException, NonPositiveAmountException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account = TestUtils.createCheckingAccount(john);
        TestUtils.depositMoney(account, 1000);
        double balance = TestUtils.withdrawMoney(account, 400);
        assertEquals("The new balance account is 600$", 600, balance, DOUBLE_DELTA);
    }

    @Test(expected = NonPositiveAmountException.class)
    public void withdrawNegativeAmountTest() throws ExceedsNegativeBalanceException, NonPositiveAmountException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account, 1000);
        TestUtils.withdrawMoney(account, -400);
    }

    @Test(expected = NonPositiveAmountException.class)
    public void withdrawZeroAmountToAccountTest() throws ExceedsNegativeBalanceException, NonPositiveAmountException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account = TestUtils.createCheckingAccount(john);
        TestUtils.depositMoney(account, 1000);
        TestUtils.withdrawMoney(account, 0);
    }

    @Test(expected = ExceedsNegativeBalanceException.class)
    public void withdrawExceedsNegativeBalanceLimitTest() throws ExceedsNegativeBalanceException, NonPositiveAmountException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account = TestUtils.createSavingsAccount(john);
        TestUtils.withdrawMoney(account, 2000);
    }

}
