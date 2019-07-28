package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-10;
    private Customer customer;

    /**
     * Create new customer before each test
     */
    @Before
    public void init(){
        customer = new Customer("Oscar");
    }


    /**
     * Check that opening a new account adds an account to the user's list of accounts
     */
    @Test
    public void openAccount(){
        assertEquals(0, customer.getNumberOfAccounts());

        customer.openAccount(new Account(Account.CHECKING));
        assertEquals(1, customer.getNumberOfAccounts());

        customer.openAccount(new Account(Account.SAVINGS));
        assertEquals(2, customer.getNumberOfAccounts());
    }


    /**
     * Check that the correct amount of interest is calculated for each account type
     */
    @Test
    public void interestEarned(){
        // Open one of each account
        Account checking = new Account(Account.CHECKING);
        Account savings = new Account(Account.SAVINGS);
        Account maxi = new Account(Account.MAXI_SAVINGS);
        customer.openAccount(checking).openAccount(savings).openAccount(maxi);

        checking.deposit(200); // $0.2 interest
        assertEquals(0.2, customer.totalInterestEarned(), DOUBLE_DELTA);

        savings.deposit(2000); // $1 for first £1000 + $2 for second $1000 = $3 total
        assertEquals(0.2 + 3, customer.totalInterestEarned(), DOUBLE_DELTA);

        maxi.deposit(100); // $5
        assertEquals(5 + 3 + 0.2, customer.totalInterestEarned(), DOUBLE_DELTA);
    }

}
