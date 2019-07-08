package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    
    //**Testing whether tiers of interest rates are calculating correctly**
    @Test
    public void testSavingsAccount_firstRate() {	//<= 1000 = 0.1%
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1000.0);

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testSavingsAccount_secondRate() {	// 0.2% for amounts over 1000
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);	//0.1% of 1000 + 0.2% of 500 = 2
    }

    @Test
    public void testMaxiSavingsAccount_maxiRate() {	//5% as no withdrawals
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(1000.0);

        assertEquals(50.0, bank.totalInterestPaid(), DOUBLE_DELTA);	//5% of 1000 = 50
    }
    
    @Test
    public void testMaxiSavingsAccount_baseRate() {	//0.1% as no withdrawals
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(2000.0);
        maxiAccount.withdraw(1000.0);

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);	//0.1% of 1000 = 1
    }
    
    //******
    
    @Test
    public void simulateDayPassing_oneDay() {	//Testing that interest accrues and compounds correctly by simulating a day passing
    	Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(1000.0);
        bank.dayRollover();

        assertEquals(1000 + (0.05 / 365) * 1000.0, maxiAccount.getAccountBalance(), DOUBLE_DELTA);	//(5% / 365) is the daily interest rate
    }
    
    //Testing that interest accrues and compounds correctly by simulating a day passing
    @Test
    public void simulateDayPassing_threeDays() {	
    	Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(1000.0);
        bank.dayRollover();
        bank.dayRollover();
        bank.dayRollover();
        
        double expectedBalance = 1000;
        
        for (int i = 0; i < 3; i++) {
        	expectedBalance += (0.05 / 365) * expectedBalance; //(5% / 365) is the daily interest rate
        }
        
        assertEquals(expectedBalance, maxiAccount.getAccountBalance(), DOUBLE_DELTA);	
    }
    
    //Testing that the number of days between two dates works correctly
    @Test
    public void testDateProvider_daysBetweenTwoDates() throws ParseException {
    	 
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date firstDate = sdf.parse("01/01/2000");
        Date secondDate = sdf.parse("01/07/2000");
    	
    	assertEquals(6, DateProvider.getInstance().daysBetweenTwoDates(firstDate, secondDate));
    	
    }
    
}
