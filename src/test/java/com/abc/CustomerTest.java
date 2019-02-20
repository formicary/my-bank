package com.abc;

import org.junit.Before;
import org.junit.Test;

import consts.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerTest {
	
	private static final String fullName = "Oscar Wilde";
	private Customer customer;
	private Account checkingAccount = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.UK);
	private Account savingsAccount = new Account(Constants.ACCOUNT_SAVINGS_ID, Locale.UK);
	private Account maxiSavingsAccount = new Account(Constants.ACCOUNT_MAXI_SAVINGS_ID, Locale.ITALY);
	
    @Before
    public void setUpCustomerWithMultipleAccounts(){
    	List<Account> accounts = new ArrayList<Account>(); 
    	accounts.add(checkingAccount); accounts.add(savingsAccount); accounts.add(maxiSavingsAccount);
    	customer = new Customer(fullName, accounts);
    	checkingAccount.deposit(1000);
    	checkingAccount.deposit(300);
    	savingsAccount.deposit(200);
    	maxiSavingsAccount.deposit(500);
    }
    
    @Test
    public void testCreatingCustomerAndOpeningAnAccount(){
        Customer oscar = new Customer(fullName, checkingAccount);
        assertEquals(1, oscar.getNumberOfAccounts());
        assertEquals(fullName, oscar.getFullName());
    }

    @Test
    public void testCreatingCustomerAndOpeningMultipleAccounts(){
        assertEquals(3, customer.getNumberOfAccounts());
    }
    
    @Test
    public void testAccountStatements(){
    	System.out.println(customer.getStatementForAllAccounts());
    	
    	String statement = "Statement for "+fullName + "\n\n" + 
    			checkingAccount.getAccountTypeName() + "\n" +
    			"\t" + Constants.DEPOSIT +" £1,000.00" + "\n" +
    			"\t" + Constants.DEPOSIT +" £300.00" + "\n" +
    			"Total: £1,300.00\n\n" +
    			savingsAccount.getAccountTypeName() + "\n" +
    			"\t" + Constants.DEPOSIT +" £200.00" + "\n" +
    			"Total: £200.00\n\n" +
    			maxiSavingsAccount.getAccountTypeName() + "\n" +
    			"\t" + Constants.DEPOSIT +" €500.00" + "\n" +
    			"Total: €500.00\n\n" +
    			"Total In All Accounts:\n"+
    			"- £1500.0\n" + 
    			"- €500.0\n";
    	
        assertEquals(statement, customer.getStatementForAllAccounts());
    }
    
    @Test
    public void testTransferBetweenTwoAccounts(){
    	double amountToTransfer = 50;
    	try {
    		customer.transferBetweenTwoAccounts(amountToTransfer, checkingAccount, checkingAccount);
    		fail();
    	} catch (UnsupportedOperationException e) { }
    	double senderAmount = checkingAccount.getTotalAmount();
    	double destinationAmount = savingsAccount.getTotalAmount();
    	customer.transferBetweenTwoAccounts(amountToTransfer, checkingAccount, savingsAccount);
    	assertEquals((senderAmount-amountToTransfer), checkingAccount.getTotalAmount(), 0);
    	assertEquals((destinationAmount+amountToTransfer), savingsAccount.getTotalAmount(), 0);
    }
    
}
