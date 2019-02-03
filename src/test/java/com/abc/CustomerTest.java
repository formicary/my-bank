package com.abc;

import org.junit.Test;

import com.abc.Account.ACCOUNT_TYPE;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

public class CustomerTest {

    @Test
    public void testCustomerStatement(){
        Account checkingAccount = new Account(ACCOUNT_TYPE.CHECKING);
        Account savingsAccount = new Account(ACCOUNT_TYPE.SAVINGS);

        Customer henry = new Customer("Henry");
        henry.addAccount(checkingAccount);
        henry.addAccount(savingsAccount);

        checkingAccount.deposit(BigDecimal.valueOf(100.0));
        savingsAccount.deposit(BigDecimal.valueOf(4000.0));
        savingsAccount.withdraw(BigDecimal.valueOf(200.0));

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }
    
    @Test
    public void testCustomerStatementNoAccounts(){
        Customer henry = new Customer("Henry");

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Total In All Accounts $0.00", henry.getStatement());
    }

    @Test
    public void testOpeningOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.addAccount(new Account(ACCOUNT_TYPE.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testOpeningTwoAccounts(){
        Customer oscar = new Customer("Oscar");
        oscar.addAccount(new Account(ACCOUNT_TYPE.SAVINGS));
        oscar.addAccount(new Account(ACCOUNT_TYPE.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testOpeningThreeAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.addAccount(new Account(ACCOUNT_TYPE.MAXI_SAVINGS));
        oscar.addAccount(new Account(ACCOUNT_TYPE.SAVINGS));
        oscar.addAccount(new Account(ACCOUNT_TYPE.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testFundTransfer() {
    	Customer oscar = new Customer("Oscar");
    	Account fromAccount = new Account(ACCOUNT_TYPE.CHECKING);
    	oscar.addAccount(fromAccount);
    	Account toAccount = new Account(ACCOUNT_TYPE.CHECKING);
    	oscar.addAccount(toAccount);
    	
    	fromAccount.deposit(new BigDecimal("500"));
    	oscar.transferAccountFunds(new BigDecimal("200"), fromAccount, toAccount);
    	
    	assertEquals(fromAccount.getBalance().compareTo(new BigDecimal("300")), 0);
    	assertEquals(toAccount.getBalance().compareTo(new BigDecimal("200")), 0);
    }
    
    @Test
    public void testFundTransferNotEnoughFunds() {
    	Customer oscar = new Customer("Oscar");
    	Account fromAccount = new Account(ACCOUNT_TYPE.CHECKING);
    	oscar.addAccount(fromAccount);
    	Account toAccount = new Account(ACCOUNT_TYPE.CHECKING);
    	oscar.addAccount(toAccount);
    	
    	try {
    		oscar.transferAccountFunds(new BigDecimal("200"), fromAccount, toAccount);		
    	} catch (IllegalArgumentException e){
    		assertEquals(e.getMessage(), "From account balance must be greater or equal to the amount");
    	}
    }
    
    @Test
    public void testFundTransferZeroAmount() {
    	Customer oscar = new Customer("Oscar");
    	Account fromAccount = new Account(ACCOUNT_TYPE.CHECKING);
    	oscar.addAccount(fromAccount);
    	Account toAccount = new Account(ACCOUNT_TYPE.CHECKING);
    	oscar.addAccount(toAccount);
    	
    	try {
    		oscar.transferAccountFunds(new BigDecimal("0"), fromAccount, toAccount);		
    	} catch (IllegalArgumentException e){
    		assertEquals(e.getMessage(), "Amount must be greater than zero");
    	}		
    }
}
