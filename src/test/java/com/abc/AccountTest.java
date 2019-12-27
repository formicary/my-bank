/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author batuh
 */
public class AccountTest {
    
    private Account checking;
    private Account saving;
    private Account maxiSaving;
    
    public AccountTest() {
    }
    
    @Before
    public void setUp() {
        checking = new Account(AccountType.CHECKING);
        saving = new Account(AccountType.SAVINGS);
        maxiSaving = new Account(AccountType.MAXI_SAVINGS);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void interestTest() {
       //checking
       checking.deposit(1000);
       assertEquals(1001,checking.calculateInterest(),0.1);
       checking.deposit(1000);
       assertEquals(2003,checking.calculateInterest(),0.1);
       
       //saving
       saving.deposit(1000);
       assertEquals(1001,saving.calculateInterest(),0.1);
       saving.deposit(1000);
       assertEquals(2004,saving.calculateInterest(),0.1);
       
       //maxisaving
       maxiSaving.deposit(1000);
       assertEquals(1020,maxiSaving.calculateInterest(),0.1);
       maxiSaving.deposit(1000);
       assertEquals(2092,maxiSaving.calculateInterest(),0.1);
       maxiSaving.deposit(1000);
       assertEquals(3271.2,maxiSaving.calculateInterest(),0.1);
    }
}
