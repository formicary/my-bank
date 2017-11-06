package com.abc;

//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.text.NumberFormat;
import java.util.Locale;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry", Locale.US).openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("=== Statement for Henry ===\n" +
        		"\n" +
                "Checking Account\n" +
                "\tDeposit: $100.00\n" +
                "Total: $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "\tDeposit: $4,000.00\n" +
                "\tWithdrawal: $200.00\n" +
                "Total: $3,800.00\n" +
                "\n" +
                "=== Total In All Accounts: $3,900.00\n", henry.getStatement());
    }
    
    @Test
    public void testGetFullName() {
    	Customer brian = new Customer("Brian Example", Locale.UK);
    	assertEquals("Brian Example", brian.getFullName());
    }
    
    @Test
    public void testSetFullName() {
    	Customer brian = new Customer("Brian Example", Locale.UK);
    	brian.setFullName("Brian Newlastname");
    	assertEquals("Brian Newlastname", brian.getFullName());
    }
    
    @Test
    public void testGetLocale() {
    	Customer jill = new Customer("Jill", Locale.UK);
    	assertEquals(Locale.UK, jill.getLocale());
    }
    
    @Test
    public void testSetLocale() {
    	Customer jill = new Customer("Jill", Locale.UK);
    	jill.setLocale(Locale.US);
    	assertEquals(Locale.US, jill.getLocale());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar", Locale.US).openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar", Locale.US)
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar", Locale.US)
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testTotalInterestEarned() {
    	Account checking = new Account(Account.CHECKING);
    	Account maxiSavings = new Account(Account.MAXI_SAVINGS);
    	Customer bill = new Customer("Bill", Locale.US)
    			.openAccount(checking);
    	bill.openAccount(maxiSavings);
    	checking.deposit(1000.0);
    	maxiSavings.deposit(5000);
    	checking.withdraw(50.0);
    	maxiSavings.deposit(10.0);
    	
    	assertEquals("$0.69", NumberFormat.getCurrencyInstance(bill.getLocale()).format(bill.totalInterestEarned()));
    }

}
