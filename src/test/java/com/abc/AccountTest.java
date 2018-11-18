package com.abc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mofiz
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    public AccountTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of deposit method, of class Account.
     */
    @Test
    public void testDeposit() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000);
        assertEquals(1000.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        
    }

    /**
     * Test of withdraw method, of class Account.
     */
    @Test
    public void testWithdraw() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000);
        checkingAccount.withdraw(500);
        assertEquals(500.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    /**
     * Test of interestEarned method, of class Account.
     */
    @Test
    public void testInterestEarned() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000);
        assertEquals(1.0, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

   
    /*
     * Test of compoundInterest method, of class Account.
     */
    @Test
    public void testCompoundInterest_double() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000);
        
        assertEquals(1010.0501532478214, checkingAccount.compoundInterest(10), DOUBLE_DELTA);
    }

    /**
     * Test of sumTransactions method, of class Account.
     */
    @Test
    public void testSumTransactions() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000);
        checkingAccount.withdraw(200);
        checkingAccount.deposit(100);
        checkingAccount.withdraw(200);
        
        assertEquals(700.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

}
