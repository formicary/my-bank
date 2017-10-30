package com.abc;

import java.math.BigDecimal;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() throws IllegalAccountException {

        Customer henry = new Customer("Henry");
        henry.openAccount(Account.AccountType.CHECKING);
        henry.openAccount(Account.AccountType.SAVINGS);

        henry.getAccount(Account.AccountType.CHECKING).deposit(new BigDecimal(100));
        henry.getAccount(Account.AccountType.SAVINGS).deposit(new BigDecimal(4000));
        henry.getAccount(Account.AccountType.SAVINGS).withdraw(new BigDecimal(200));
        
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() throws IllegalAccountException {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.SAVINGS);
        oscar.openAccount(Account.AccountType.CHECKING);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.SAVINGS);
        oscar.openAccount(Account.AccountType.CHECKING);
        oscar.openAccount(Account.AccountType.MAXI_SAVINGS);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testInterestEarnedChecking() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.CHECKING);
        
        // Deposit 1000.00
        oscar.getAccount(Account.AccountType.CHECKING).deposit(new BigDecimal("1000.00"));
        
        // Should gain 0.1% interest
        BigDecimal d = new BigDecimal("1.0");
        assertTrue(d.compareTo(oscar.totalInterestEarned()) == 0);
    }
    
    @Test
    public void testInterestEarnedSavingsOne() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.SAVINGS);
        
        // Deposit 1000.00
        oscar.getAccount(Account.AccountType.SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Should gain 0.1% interest
        BigDecimal d = new BigDecimal("1.0");
        assertTrue(d.compareTo(oscar.totalInterestEarned()) == 0);
    }
    
    @Test
    public void testInterestEarnedSavingsTwo() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.SAVINGS);
        
        // Deposit 1000.00
        oscar.getAccount(Account.AccountType.SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Deposit another 1000.00
        oscar.getAccount(Account.AccountType.SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Should gain 0.1% for first 1000.00, then 0.2 for remaining 1000.00 
        BigDecimal d = new BigDecimal("3.0");
        assertTrue(d.compareTo(oscar.totalInterestEarned()) == 0);
    }
    
    @Test
    public void testInterestEarnedMaxiSavingsOne() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.MAXI_SAVINGS);
        
        // Deposit 1000.00
        oscar.getAccount(Account.AccountType.MAXI_SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Should gain 2.0% interest
        BigDecimal d = new BigDecimal("20.0");
        assertTrue(d.compareTo(oscar.totalInterestEarned()) == 0);
    }
    
    @Test
    public void testInterestEarnedMaxiSavingsTwo() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.MAXI_SAVINGS);
        
        // Deposit 1000.00
        oscar.getAccount(Account.AccountType.MAXI_SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Deposit another 1000.00
        oscar.getAccount(Account.AccountType.MAXI_SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Should gain 2.0% for first 1000.00, then 5.0 for remaining 1000.00
        BigDecimal d = new BigDecimal("70.0");
        assertTrue(d.compareTo(oscar.totalInterestEarned()) == 0);
    }
    
        @Test
    public void testInterestEarnedMaxiSavingsThree() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.MAXI_SAVINGS);
        
        // Deposit 1000.00
        oscar.getAccount(Account.AccountType.MAXI_SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Deposit another 1000.00
        oscar.getAccount(Account.AccountType.MAXI_SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Deposit another 1000.00
        oscar.getAccount(Account.AccountType.MAXI_SAVINGS).deposit(new BigDecimal("1000.00"));
        
        // Should gain 2.0% for first 1000.00, then 5.0% for next 1000.00, then 10.0% for remaining 1000.00
        BigDecimal d = new BigDecimal("170.0");
        assertTrue(d.compareTo(oscar.totalInterestEarned()) == 0);
    }
    
    @Test(expected=IllegalAccountException.class)
    public void testDuplicateAccountsSavings() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.SAVINGS);
        oscar.openAccount(Account.AccountType.SAVINGS);
    }
    
    @Test(expected=IllegalAccountException.class)
    public void testDuplicateAccountsMaxiSavings() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.MAXI_SAVINGS);
        oscar.openAccount(Account.AccountType.MAXI_SAVINGS);
    }
    
    @Test(expected=IllegalAccountException.class)
    public void testDuplicateAccountsChecking() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.CHECKING);
        oscar.openAccount(Account.AccountType.CHECKING);
    }
    
    @Test
    public void testNonExistentAccountAccess() throws IllegalAccountException {
        
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(Account.AccountType.CHECKING);
        Account a = oscar.getAccount(Account.AccountType.SAVINGS);
        
        assertEquals(null, a);
    }
    
    
}
