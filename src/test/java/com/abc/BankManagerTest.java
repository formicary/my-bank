package com.abc;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class BankManagerTest {
	
	private Bank bank = new Bank();
	private Account checkingAccount = new Account(Account.CHECKING);
    private Customer john = new Customer("John", bank, checkingAccount);
    private BankManager steve = new BankManager("Steve", bank);

    @Test
    public void bankManagerCanGetCustomerReport() {
        assertEquals("Customer Summary\n - John (1 account)", steve.customerSummaryReport());
    }
    
    @Test
    public void bankManagerCanGetInterestReport() {
    	
    	int accountId = checkingAccount.getAccountId();
        john.deposit(accountId,new BigDecimal("1000.00"));
       
        assertEquals("Total interest paid by the bank: $1.00", steve.totalInterestPaidReport());
    }


}
