package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Random;

public class CustomerTest {
	
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testStatement(){

        Customer henry = new Customer("Henry");
        String checkingAccountID = henry.openAccount(Account.Type.CHECKING);
        String savingsAccountID = henry.openAccount(Account.Type.SAVINGS);

        henry.deposit(checkingAccountID, 100.0);
        henry.transfer(checkingAccountID, savingsAccountID, 50.0);
        henry.withdraw(savingsAccountID, 20.0);
        nextDayInterestIssue(henry);			// imitates a day passing to update interest rates
        
        LocalDate currentDate = DateProvider.getInstance().now().minusDays(1);

        assertEquals("Statement for Henry\n" +
                "\n" +
        		"Account " + checkingAccountID + "\n" + 
                "Checking Account\n" +
                "  "+ currentDate +": deposit $100.00\n" +
                "  "+ currentDate +": transfer $-50.00 (To " + savingsAccountID + ")\n" +
                "  "+ currentDate.plusDays(1) +": interests $0.05\n" +
                "Total $50.05\n" +
                "\n" +
                "Account " + savingsAccountID + "\n" + 
                "Savings Account\n" +
                "  "+ currentDate +": transfer $50.00 (From " + checkingAccountID + ")\n" +
                "  "+ currentDate +": withdrawal $-20.00\n" +
                "  "+ currentDate.plusDays(1) +": interests $0.03\n" +
                "Total $30.03\n" +
                "\n" +
                "Total In All Accounts $80.08", henry.getStatement());
    }

    // test number of accounts
    @Test
    public void testAccountNumber(){
        Customer oscar = new Customer("Oscar");
        int n = new Random().nextInt(10);
        for (int i=0; i<n; i++) {
        	oscar.openAccount(Account.Type.SAVINGS);
        }       
        assertEquals(n, oscar.getNumberOfAccounts());
    }
    
    // test transfer feature
    @Test
    public void testTransfer(){
        Customer henry = new Customer("Henry");
        String checkingAccountID = henry.openAccount(Account.Type.CHECKING);
        String savingsAccountID = henry.openAccount(Account.Type.SAVINGS);
        henry.deposit(checkingAccountID, 1000.0);
        henry.deposit(savingsAccountID, 1000.0);
        
        int n = new Random().nextInt(600)+1;
        henry.transfer(checkingAccountID, savingsAccountID, n);
        
        assertEquals(1000-n, henry.getAccountBalance(checkingAccountID), DOUBLE_DELTA);
        assertEquals(1000+n, henry.getAccountBalance(savingsAccountID), DOUBLE_DELTA);
    }
    
    // test that exceptions are thrown correctly for invalid input
    @Test
    public void testExceptions(){
    	Customer henry = new Customer("Henry");
        String checkingAccountID = henry.openAccount(Account.Type.CHECKING);
        String checkingAccountID2 = henry.openAccount(Account.Type.CHECKING);
        
        try {
        	henry.deposit(checkingAccountID, -3);
        	fail("Exception not thrown");
        } catch (IllegalArgumentException expectedException) { }
        try {
        	henry.deposit("", 3);
        	fail("Exception not thrown");
        } catch (IllegalArgumentException expectedException) { }
        try {
        	henry.withdraw(checkingAccountID, -3);
        	fail("Exception not thrown");
        } catch (IllegalArgumentException expectedException) { }
        try {
        	henry.withdraw("", 3);
        	fail("Exception not thrown");
        } catch (IllegalArgumentException expectedException) { }
        try {
        	henry.transfer("", "", 3);
        	fail("Exception not thrown");
        } catch (IllegalArgumentException expectedException) { }
        try {
        	henry.transfer(checkingAccountID, checkingAccountID2, 3);
        	fail("Exception not thrown");
        } catch (IllegalArgumentException expectedException) { }
        
    }     

    @Test
    public void testCheckingInterest() {
        Customer oscar = new Customer("Oscar");
        String id = oscar.openAccount(Account.Type.CHECKING);
        
        int n = new Random().nextInt(50000)+50000;
        oscar.deposit(id, n);
        
        double interest = twoDecimal(n*0.001);
        		
        assertTrue(interest==nextDayInterestIssue(oscar));
    }
    
    @Test
    public void testSavingsInterest() {
        Customer oscar = new Customer("Oscar");
        String id = oscar.openAccount(Account.Type.SAVINGS);
        oscar.deposit(id, 1000);
        
        assertTrue(1.0==nextDayInterestIssue(oscar));
        
        int n = new Random().nextInt(10000)+1000;
        oscar.deposit(id, n);
        
        double interest = twoDecimal((1+n)*0.002);
        assertEquals((1+interest), nextDayInterestIssue(oscar), DOUBLE_DELTA);      
    }
    
    @Test
    public void testMaxiSavingsInterest() {
        Customer oscar = new Customer("Oscar");
        String id = oscar.openAccount(Account.Type.MAXI_SAVINGS);
        
        int n = new Random().nextInt(50000)+50000;
        oscar.deposit(id, n);
        
        double interest = twoDecimal(n*0.05);
        assertTrue(interest==nextDayInterestIssue(oscar));
        
        int n1 = new Random().nextInt(50000);
        oscar.withdraw(id, n1);
        
        double interest1 = twoDecimal((n+interest-n1)*0.001);
        
        assertTrue(interest1==nextDayInterestIssue(oscar));  
    }
    
    // test that total account interest gain is calculated correctly
    @Test
    public void testTotalInterest() {
    	Customer henry = new Customer("Henry");
        String checkingAccountID = henry.openAccount(Account.Type.CHECKING);
        String savingsAccountID = henry.openAccount(Account.Type.SAVINGS);
        String maxisavingsAccountID = henry.openAccount(Account.Type.MAXI_SAVINGS);
        
        henry.deposit(checkingAccountID, 1000.0);
        henry.deposit(savingsAccountID, 1000.0);
        henry.deposit(maxisavingsAccountID, 1000.0);
        
        nextDayInterestIssue(henry);
        
        assertEquals(52.0, henry.getTotalInterestEarned(), DOUBLE_DELTA);
    }
    
    // helper methos to imitate date change and interest update
	private double nextDayInterestIssue(Customer c) {
		DateProvider.getInstance().nextDay();
		return c.earnDailyInterest();
	}
	
	// helper method for 2 decimal precision
    private double twoDecimal(double n) {
    	return Double.parseDouble(String.format("%.2f", n)); 
    }
}
