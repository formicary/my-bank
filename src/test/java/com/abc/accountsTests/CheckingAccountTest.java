package com.abc.accountsTests;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.exceptions.*;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckingAccountTest {
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }

    @Test
    public void checkingAccountTotalInterestPaidTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Customer john = TestUtils.createCustomer(bank, "Lloyds");
        Account account = TestUtils.createCheckingAccount(john);
        TestUtils.depositMoney(account, 1000);

        assertTrue("Checking account interest rate is 1%", bank.getSystemManagement().totalInterestPaid() == 1.0);
    }
}
