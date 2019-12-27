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
public class BankTest {
    
    private Customer henry;
    private Customer lucy;
    private Account checking;
    private Account saving;
    private Account maxiSaving;
    private Account checking2;
    private Account saving2;
    private Account maxiSaving2;
    private Bank bank;
    private BankManager bankManager;
    
    public BankTest() {
    }
    
    @Before
    public void setUp() {
        checking = new Account(AccountType.CHECKING);
        saving = new Account(AccountType.SAVINGS);
        maxiSaving = new Account(AccountType.MAXI_SAVINGS);
        checking2 = new Account(AccountType.CHECKING);
        saving2 = new Account(AccountType.SAVINGS);
        maxiSaving2 = new Account(AccountType.MAXI_SAVINGS);
        henry = new Customer("Henry");
        lucy = new Customer("Lucy");
        henry.createAccount(checking).createAccount(saving).createAccount(maxiSaving);
        lucy.createAccount(checking2).createAccount(saving2).createAccount(maxiSaving2);
        bank = new Bank();
        bankManager = new BankManager("John");
    }
    
    @After
    public void tearDown() {
    }
    
    
    @Test
    public void testCustomerCreation() {
        bankManager.addCustomer(lucy, bank);
        assertEquals(1, bank.getCustomers().size());
        
        bankManager.addCustomer(henry, bank);
        assertEquals(2, bank.getCustomers().size());
        
        assertEquals("Customer name Lucy\n" + "Number of Accounts 3\n\n" +
                "Customer name Henry\n" + "Number of Accounts 3\n\n" 
                , bankManager.getAllCustomers(bank));
        
        
    }
    
    @Test
    public void testInterestPaidOut() {
        bankManager.addCustomer(lucy, bank);
        bankManager.addCustomer(henry, bank);
        
        henry.deposit(checking.getUniqueID(), 1000);
        henry.deposit(saving.getUniqueID(), 300);
        henry.deposit(maxiSaving.getUniqueID(), 200);
        
        lucy.deposit(checking2.getUniqueID(), 1000);
        lucy.deposit(saving2.getUniqueID(), 300);
        lucy.deposit(maxiSaving2.getUniqueID(), 200);
        
        assertEquals("Total paid by the bank is $3010.6", bankManager.getTotalInterestPaid(bank));
        
        
        
    }

    
    
}
