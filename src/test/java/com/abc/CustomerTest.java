/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author batuhan yilmaz
 */

public class CustomerTest {
    private Customer henry;
    private Account checking;
    private Account saving;
    private Account maxiSaving;
    
    public CustomerTest() {
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
    public void testAccountCreation(){
        henry = new Customer("Henry").createAccount(checking);
        assertEquals(1, henry.getAccounts().size());
        henry.createAccount(saving);
        assertEquals(2, henry.getAccounts().size());
        henry.createAccount(maxiSaving);
        assertEquals(3, henry.getAccounts().size());
    }
    
    @Test
    public void testFindUID(){
        henry = new Customer("Henry").createAccount(checking);
        assertEquals(checking, henry.findAccount(checking.getUniqueID()));
        henry.createAccount(saving);
        assertEquals(saving, henry.findAccount(saving.getUniqueID()));
        henry.createAccount(maxiSaving);
        assertEquals(maxiSaving, henry.findAccount(maxiSaving.getUniqueID()));
    }
    
    @Test
    public void testDepositWithdraw(){
        henry = new Customer("Henry").createAccount(checking);
        henry.deposit(checking.getUniqueID(), 1000);
        assertEquals(1000.0, checking.getBalance(),0.1);
        henry.withdraw(checking.getUniqueID(), 250);
        assertEquals(750.0, checking.getBalance(),0.1);
    }
    
    @Test
    public void testStatements(){
        henry = new Customer("Henry").createAccount(checking).createAccount(saving).createAccount(maxiSaving);
        henry.deposit(checking.getUniqueID(), 1000);
        henry.withdraw(checking.getUniqueID(), 250);
        henry.deposit(saving.getUniqueID(), 300);
        henry.deposit(maxiSaving.getUniqueID(), 200);

        System.out.println(henry.getAllStatements());
        
        assertEquals("Statement for Henry\n\n" + "Checking Account\n"
                + "Deposit $1000.0\n\n" + "Withdrawal $250.0\n\n" +
                "Total $750.00\n\n" + "Savings Account\n" + "Deposit $300.0\n\n" +
                "Total $300.00\n\n" + "Maxi Savings Account\n" + "Deposit $200.0\n\n"+
                "Total $200.00\n\n" + "TotalBalance $1250"
                , henry.getAllStatements());
    }
    
    @Test
    public void testTransferFunds(){
        henry = new Customer("Henry").createAccount(checking).createAccount(saving).createAccount(maxiSaving);
        henry.transferFunds(checking.getUniqueID(), 500, saving.getUniqueID());
        assertEquals(-500, checking.getBalance(),0.1);
        assertEquals(500, saving.getBalance(),0.1);
    }
    
    
    @Test
    public void testTotalInterest(){
        henry = new Customer("Henry").createAccount(checking).createAccount(saving).createAccount(maxiSaving);
        henry.deposit(checking.getUniqueID(), 1000);
        henry.deposit(saving.getUniqueID(), 300);
        henry.deposit(maxiSaving.getUniqueID(), 200);
        assertEquals(1505.3,henry.totalInterestEarned(),0.1);
        
    }
    
    
    


}
