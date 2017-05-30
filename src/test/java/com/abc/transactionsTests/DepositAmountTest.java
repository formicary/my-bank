package com.abc.transactionsTests;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.exceptions.NonPositiveAmountException;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DepositAmountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }

    @Test
    public void depositMoneyToAccountTest() throws NonPositiveAmountException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account = TestUtils.createCheckingAccount(john);
        double balance = TestUtils.depositMoney(account, 1000);
        assertEquals("The new balance account is 1000$", 1000, balance, DOUBLE_DELTA);
    }

    @Test(expected = NonPositiveAmountException.class)
    public void depositNegativeAmountTest() throws NonPositiveAmountException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account, -1000);
    }

    @Test(expected = NonPositiveAmountException.class)
    public void depositZeroAmountTest() throws NonPositiveAmountException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account = TestUtils.createCheckingAccount(john);
        TestUtils.depositMoney(account, 0);
    }

}
