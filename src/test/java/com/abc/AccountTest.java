/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yusri
 */
public class AccountTest {
    
        private static final double DOUBLE_DELTA = 1e-15;
    
    /**
     * Test of deposit method, of class Account.
     */
    @Test
    public void testDeposit() {
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        checkingAccount.deposit(100.0);

        assertEquals(100, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        checkingAccount.deposit(50.0);
        assertEquals(150, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        checkingAccount.deposit(37);
        assertEquals(187, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        
    }

    /**
     * Test of withdraw method, of class Account.
     */
    @Test
    public void testWithdraw() {
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        checkingAccount.deposit(300.0);

        assertEquals(300, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        checkingAccount.withdraw(50.0);
        assertEquals(250, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        checkingAccount.withdraw(37);
        assertEquals(213, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

 }