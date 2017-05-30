package com.abc.accountsTests;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.exceptions.*;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class SavingsAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }

    @Test
    public void savingsAccountHighTotalInterestPaidTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Customer john = TestUtils.createCustomer(bank, "Lloyds");
        Account account = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account, 2000);

        assertTrue(bank.getSystemManagement().totalInterestPaid() == 3.0);
    }

    @Test
    public void savingsAccountLowTotalInterestPaidTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Customer john = TestUtils.createCustomer(bank, "Lloyds");
        Account account = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account, 500);

        assertTrue(bank.getSystemManagement().totalInterestPaid() == 0.5);
    }
}
