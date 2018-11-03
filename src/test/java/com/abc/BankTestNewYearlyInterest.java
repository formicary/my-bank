package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BankTestNewYearlyInterest {
    private static final double DOUBLE_DELTA = 1e-15;
    private InterestCalculator calculator = new NewYearlyInterestCalculator();
    Bank bank;
    Account maxiAccount;
    Customer bill;
    
    @Before
    public void init() {
    	bank = new Bank();
    	maxiAccount = new Account(AccountType.MAXI_SAVINGS);
    	bill = (new Customer("Bill").openAccount(maxiAccount));
    	bank.addCustomer(bill);
    }
    
	
	 @Test
	    public void totalInterestPaid_maxiSavings_lessThanTenDays() {
	        maxiAccount.deposit(3000.0);
	        maxiAccount.withdraw(2000);

	        assertEquals(1.0, bank.totalInterestPaid(calculator), DOUBLE_DELTA);
	    }
	    
	    
	    @Test
	    public void totalInterestPaid_maxiSavings_greaterThanTenDays() {
	            maxiAccount.deposit(3500.0);
	            maxiAccount.withdraw(500);
	            DateProvider.getInstance().addFifthteenDays();
	            
	            assertEquals(150, bank.totalInterestPaid(calculator), DOUBLE_DELTA);
	    }
	    
	    @Test
	    public void totalInterestPaid_maxiSavingsNoWithdrawls_lessThanTenDays() {
	        maxiAccount.deposit(1000.0);

	        assertEquals(1.0, bank.totalInterestPaid(calculator), DOUBLE_DELTA);
	    }
	    
	    
	    @Test
	    public void totalInterestPaid_maxiSavingsNoWithdrawls_greaterThanTenDays() {
	            maxiAccount.deposit(3000.0);
	            DateProvider.getInstance().addFifthteenDays();
	            
	            assertEquals(150, bank.totalInterestPaid(calculator), DOUBLE_DELTA);
	    	
	    }

}
