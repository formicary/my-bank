package com.abc.Customers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the CustomerManager class.
 */
public class CustomerManagerTests {
    /**
     * The dummy name.
     */
    private final String dummyName = "dummy-name";

    /**
     * The dummy statement.
     */
    private final String dummyStatement = "dummy-statement";

    /**
     * The dummy customer ID.
     */
    private final int dummyCustomerId = 1;

    /**
     * The dummy number of accounts.
     */
    private final int dummyNumberOfAccounts = 2;

    /**
     * The dummy total interest earned.
     */
    private final double dummyTotalInterestEarned = 4.0;

    /**
     *
     * The mock customer ID manager.
     */
    @Mock
    private ICustomerIdManager mockCustomerIdManager;

    /**
     * The mock customer factory.
     */
    @Mock
    private ICustomerFactory mockCustomerFactory;

    /**
     * The mock customer.
     */
    @Mock
    private ICustomer mockCustomer;


    /**
     * The customer manager.
     */
    private CustomerManager customerManager;

    /**
     * Sets up before each test.
     */
    @Before
    public void setUp() {
        this.mockCustomerIdManager = Mockito.mock(ICustomerIdManager.class);
        this.mockCustomerFactory = Mockito.mock(ICustomerFactory.class);
        this.mockCustomer = Mockito.mock(ICustomer.class);

        when(this.mockCustomerIdManager.generateCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockCustomerFactory.createCustomer(this.dummyName, this.dummyCustomerId)).thenReturn(this.mockCustomer);
        when(this.mockCustomer.getName()).thenReturn(this.dummyName);
        when(this.mockCustomer.getCustomerId()).thenReturn(this.dummyCustomerId);
        when(this.mockCustomer.getStatement()).thenReturn(this.dummyStatement);
        when(this.mockCustomer.getNumberOfAccounts()).thenReturn(this.dummyNumberOfAccounts);
        when(this.mockCustomer.calculateTotalInterestEarned()).thenReturn(this.dummyTotalInterestEarned);

        this.customerManager = new CustomerManager(this.mockCustomerIdManager, this.mockCustomerFactory);
    }

    /**
     * Tests that adding a customer adds the customer.
     */
    @Test
    public void addingCustomerAddsCustomer() {
        this.customerManager.addCustomer(this.dummyName);

        Mockito.verify(this.mockCustomerIdManager).generateCustomerId();
        Mockito.verify(this.mockCustomerFactory).createCustomer(this.dummyName, this.dummyCustomerId);

    }

    /**
     * Tests that getting a customer gets a customer.
     */
    @Test
    public void gettingCustomerGetsACustomer() {
        int customerId = this.customerManager.addCustomer(this.dummyName);

        ICustomer customer = this.customerManager.getCustomer(customerId);

        Mockito.verify(this.mockCustomer).getCustomerId();

        Assert.assertEquals(customer.getCustomerId(), customerId);
    }

    /**
     * Tests that getting a customer when no customer exists for the given customer ID returns null.
     */
    @Test
    public void gettingCustomerWhenNoCustomerExistsForGivenCustomerIdReturnsNull() {
        ICustomer customer = this.customerManager.getCustomer(this.dummyCustomerId);

        Assert.assertNull(customer);
    }

    /**
     * Tests that getting a customer statement gets a customer statement.
     */
    @Test
    public void gettingCustomerStatementGetsCustomerStatement() {
        int customerId = this.customerManager.addCustomer(this.dummyName);

        String statement = this.customerManager.getCustomerStatement(customerId);

        Mockito.verify(this.mockCustomer).getStatement();

        Assert.assertEquals(statement, this.dummyStatement);
    }

    /**
     * Tests that getting a customer statement when no customer exists for the given customer ID
     * throws a CustomerException.
     */
    @Test(expected = CustomerException.class)
    public void gettingCustomerStatementWhenNoCustomerExistsForGivenCustomerIdThrowsCustomerException() {
        this.customerManager.getCustomerStatement(this.dummyCustomerId);
    }

    /**
     * Tests that getting the customer summary gets the name and the number of accounts on the customer.
     */
    @Test
    public void gettingCustomerSummaryGetsNameAndGetsNumberOfAccountsOnCustomer() {
        this.customerManager.addCustomer(this.dummyName);

        String summary = this.customerManager.getCustomerSummary();

        String expectedCustomerSummary = " - " + this.dummyName + " (" + this.dummyNumberOfAccounts + " accounts)";

        Mockito.verify(this.mockCustomer).getName();
        Mockito.verify(this.mockCustomer).getNumberOfAccounts();

        Assert.assertTrue(summary.contains(expectedCustomerSummary));
    }

    /**
     * Tests that getting total interest paid gets total interest earned on customer.
     */
    @Test
    public void gettingTotalInterestPaidGetsTotalInterestEarnedOnCustomer() {
        this.customerManager.addCustomer(this.dummyName);

        double totalInterestEarned = this.customerManager.calculateTotalInterestPaid();

        Mockito.verify(this.mockCustomer).calculateTotalInterestEarned();

        Assert.assertEquals(totalInterestEarned, this.dummyTotalInterestEarned, 0.001);
    }
}
