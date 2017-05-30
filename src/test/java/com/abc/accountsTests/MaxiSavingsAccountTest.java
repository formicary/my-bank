package com.abc.accountsTests;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.exceptions.*;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MaxiSavingsAccountTest {
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }

    @Test
    public void maxiSavingAccountTotalInterestPaidTest() throws IdenticalAccountIDException, CustomerNotExistException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        Customer john = TestUtils.createCustomer(bank, "Lloyds");
        Account account = TestUtils.createMaxiSavingsAccount(john);
        TestUtils.depositMoney(account, 10000);

        assertTrue("MaxiSavingsAccount interest rate 0.1%", bank.getSystemManagement().totalInterestPaid() == 10.0);
    }

}
