package com.abc.managementsTests;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.exceptions.*;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountManagementTests {
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }

    @Test
    public void openAccount() {
        Customer john = TestUtils.createCustomer(bank, "Lloyds");
        TestUtils.createCheckingAccount(john);

        assertEquals(1, john.getAccountManagement().getNumberOfAccounts());
    }

    @Test
    public void showTransactionAndTotalBalanceTest() throws NonPositiveAmountException, ExceedsNegativeBalanceException {
        Customer john = TestUtils.createCustomer(bank, "Lloyds");
        Account account = TestUtils.createCheckingAccount(john);
        TestUtils.depositMoney(account, 100);

        Account account2 = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account2, 4000);

        assertEquals("Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "Total $4,000.00\n" +
                "\n" +
                "Total In All Accounts $4,100.00", john.getAccountManagement().getStatement());
    }


    @Test
    public void interestAccrual() throws NonPositiveAmountException, AccountNotExistException, CustomerNotExistException, IdenticalAccountIDException, ExceedsNegativeBalanceException {
        Customer john = TestUtils.createCustomer(bank, "John");
        Account account = TestUtils.createSavingsAccount(john);
        TestUtils.depositMoney(account, 1000);

        assertTrue(account.interestEarned() == 1.0);
        double DAILY_INTEREST = 0.0027397260273972603;
        assertTrue(account.accrueInterestDaily() == DAILY_INTEREST);
    }
}
