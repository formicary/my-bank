package com.abc.Customers;

import com.abc.Accounts.AccountType;
import com.abc.Accounts.IAccount;
import com.abc.Accounts.IAccountManager;
import com.abc.Utils.BankUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Tests for the Customer class.
 */
public class CustomerTests {
    /**
     * The dummy name.
     */
    private final String dummyName = "dummy-name";

    /**
     * The dummy customer ID.
     */
    private final int dummyCustomerId = 1;

    /**
     * The dummy number of accounts.
     */
    private final int dummyNumberOfAccounts = 4;

    /**
     * The dummy total interest
     */
    private final double dummyTotalInterestEarned = 9.7;

    /**
     * The dummy account statement.
     */
    private final String dummyAccountStatement = "dummy-account-statement";

    /**
     * The dummy account type.
     */
    private final AccountType dummyAccountType = AccountType.CHECKING;

    /**
     * The dummy transaction sum.
     */
    private final double dummyTransactionSum = 20.0;

    /**
     * The mock account.
     */
    @Mock
    private IAccount mockAccount;

    /**
     * The mock account manager.
     */
    @Mock
    private IAccountManager mockAccountManager;

    /**
     * The customer.
     */
    private Customer customer;

    /**
     * Sets up before each test.
     */
    @Before
    public void setUp() {
        this.mockAccount = Mockito.mock(IAccount.class);
        this.mockAccountManager = Mockito.mock(IAccountManager.class);

        when(this.mockAccount.getAccountStatement()).thenReturn(dummyAccountStatement);
        when(this.mockAccount.sumTransactions()).thenReturn(dummyTransactionSum);

        List<IAccount> dummyAccounts = new ArrayList<IAccount>();
        dummyAccounts.add(this.mockAccount);

        when(this.mockAccountManager.getNumberOfAccounts()).thenReturn(dummyNumberOfAccounts);
        when(this.mockAccountManager.calculateTotalInterestEarned(dummyCustomerId)).thenReturn(dummyTotalInterestEarned);
        when(this.mockAccountManager.getAccounts(this.dummyCustomerId))
            .thenReturn(dummyAccounts);

        this.customer = new Customer(this.mockAccountManager, dummyName, dummyCustomerId);
    }

    /**
     * Tests that opening an account opens an account on the account manager.
     */
    @Test
    public void openingAccountOpensAccountOnAccountManager() {
        this.customer.openAccount(dummyAccountType);

        Mockito.verify(this.mockAccountManager).openAccount(dummyCustomerId, dummyAccountType);
    }

    /**
     * Tests that getting the number of accounts get the number of accounts on the account manager.
     */
    @Test
    public void gettingNumberOfAccountsGetsNumberOfAccountsOnAccountManager() {
        int numberOfTheAccounts = this.customer.getNumberOfAccounts();

        Mockito.verify(this.mockAccountManager).getNumberOfAccounts();

        Assert.assertEquals(numberOfTheAccounts, this.dummyNumberOfAccounts);
    }

    /**
     * Getting the total interest earned gets the total interest earned on the account manager.
     */
    @Test
    public void gettingTotalInterestEarnedGetsTotalInterestEarnedOnAccountManager() {
        double totalInterestEarned = this.customer.calculateTotalInterestEarned();

        Mockito.verify(this.mockAccountManager).calculateTotalInterestEarned(this.dummyCustomerId);

        Assert.assertEquals(totalInterestEarned, this.dummyTotalInterestEarned, 0.001);
    }

    /**
     * Getting the statements gets accounts on account manager.
     */
    @Test
    public void gettingStatementGetsAccountsOnAccountManager() {
        String statement = this.customer.getStatement();

        Mockito.verify(this.mockAccountManager).getAccounts(this.dummyCustomerId);

        String expectedOpeningLine = "Statement for " + dummyName;
        String expectedTotal = "Total In All Accounts " + BankUtils.toDollars(dummyTransactionSum);

        Assert.assertTrue(statement.contains(dummyAccountStatement));
        Assert.assertTrue(statement.contains(expectedOpeningLine));
        Assert.assertTrue(statement.contains(expectedTotal));
    }
}
