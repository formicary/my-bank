package com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSuite {

    private static final double DOUBLE_DELTA = 1e-15;
    
    private Bank setUpTestBank() throws ParseException {
    	Bank bank = new Bank();
        
        Customer henry = bank.registerNewCustomer("Henry", 0);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
        
        henry.openAccount(Account.CHECKING, formatter.parse("2015-01-01-12.00.00"));
        henry.openAccount(Account.SAVINGS, formatter.parse("2015-01-01-12.00.00"));
        henry.openAccount(Account.MAXI_SAVINGS, formatter.parse("2015-01-01-12.00.00"));

        henry.deposit(0, 10000.0, formatter.parse("2015-01-01-12.00.00"));
        henry.withdraw(0, 50.0, formatter.parse("2015-01-02-12.00.00"));
        
        henry.deposit(1, 4000.0, formatter.parse("2015-01-03-12.00.00"));
        henry.withdraw(1, 200.0, formatter.parse("2015-01-04-12.00.00"));

        henry.deposit(2, 6000.0, formatter.parse("2015-01-05-12.00.00"));
        henry.withdraw(2, 2000.0, formatter.parse("2015-01-06-12.00.00"));
        henry.deposit(2, 6000.0, formatter.parse("2015-01-07-12.00.00"));
        
        return bank;
    }
    
    @Test
    public void getStatement() throws ParseException {
        Customer henry = setUpTestBank().getCustomerByUniqueID(0);
 
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  20150101 deposit $10,000.00\n" +
                "  20150102 withdrawal $50.00\n" +
                "Interest $3.71\n" + 
                "Total $9,953.71\n" +
                "\n" +
                "Savings Account\n" +
                "  20150103 deposit $4,000.00\n" +
                "  20150104 withdrawal $200.00\n" +
                "Interest $2.79\n" +
                "Total $3,802.79\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  20150105 deposit $6,000.00\n" +
                "  20150106 withdrawal $2,000.00\n" +
                "  20150107 deposit $6,000.00\n" + 
                "Interest $166.86\n" + 
                "Total $10,166.86\n" +
                "\n" +
                "Total In All Accounts $23,923.36", henry.getStatement());
    }
    
    @Test
	public void customerSummary() throws ParseException {
		Bank bank = setUpTestBank();
		Customer jill = bank.registerNewCustomer("Jill", 2);
	
		jill.openAccount(Account.CHECKING);
		
		jill.deposit(0, 150000.00);
		
		assertEquals(bank.customerSummary(), "Customer Summary\n - Henry (3 accounts)\n - Jill (1 account)");
	}

    @Test
    public void getNumberOfAccounts() {
        Bank bank = new Bank();
    	Customer oscar = bank.registerNewCustomer("Oscar", 0);
    	
    	oscar.openAccount(Account.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
        
        oscar.openAccount(Account.CHECKING);
        
        assertEquals(2, oscar.getNumberOfAccounts());
        
        oscar.openAccount(Account.MAXI_SAVINGS);
        
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void totalInterestPaid() throws ParseException {
        Bank bank = setUpTestBank();
        
        assertEquals(173.36, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void totalInterestEarned() throws ParseException {
    	Bank bank = setUpTestBank();
    	Customer henry = bank.getCustomerByUniqueID(0);
    	
    	assertEquals(173.36, henry.totalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
	public void validDeposit() {
		Bank bank = new Bank();
		Customer bill = bank.registerNewCustomer("Bill", 0);
		bill.openAccount(Account.CHECKING);
		
		assertEquals(bill.getAccountBalance(0), 0.0, DOUBLE_DELTA);
		
		bill.deposit(0, 100.0);
	
		assertEquals(bill.getAccountBalance(0), 100.0, DOUBLE_DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
	public void invalidDeposit() {
		Bank bank = new Bank();
		Customer bill = bank.registerNewCustomer("Bill", 0);
		bill.openAccount(Account.CHECKING);
		
		bill.deposit(0, -100.0);
	}
	
    @Test
	public void validWithdrawal() {
		Bank bank = new Bank();
		Customer bill = bank.registerNewCustomer("Bill", 0);
		bill.openAccount(Account.CHECKING);
		
		assertEquals(bill.getAccountBalance(0), 0.0, DOUBLE_DELTA);
		
		bill.deposit(0, 100.0);
		bill.withdraw(0, 50.0);
		
		assertEquals(bill.getAccountBalance(0), 50.0, DOUBLE_DELTA);
    }
    
	@Test(expected = IllegalArgumentException.class)
	public void invalidWithdrawal() {
		Bank bank = new Bank();
		Customer bill = bank.registerNewCustomer("Bill", 0);
		bill.openAccount(Account.CHECKING);
		
		bill.withdraw(0, -100.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insufficientFunds() {
		Bank bank = new Bank();
		Customer bill = bank.registerNewCustomer("Bill", 0);
		bill.openAccount(Account.CHECKING);
		
		bill.deposit(0, 100.0);
		bill.withdraw(0, 150.0);
	}
	
	@Test
	public void transferMoney() {
		Bank bank = new Bank();
		Customer bill = bank.registerNewCustomer("Bill", 0);
		bill.openAccount(Account.CHECKING);
		bill.openAccount(Account.SAVINGS);
		
		bill.deposit(0, 150.0);
		
		assertEquals(bill.getAccountBalance(0), 150.0, DOUBLE_DELTA);
		assertEquals(bill.getAccountBalance(1), 0.0, DOUBLE_DELTA);
		
		bill.transferMoney(0, 1, 50.0);
		
		assertEquals(bill.getAccountBalance(0), 100.0, DOUBLE_DELTA);
		assertEquals(bill.getAccountBalance(1), 50.0, DOUBLE_DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void invalidTransfer() {
		Bank bank = new Bank();
		Customer bill = bank.registerNewCustomer("Bill", 0);
		bill.openAccount(Account.CHECKING);
		bill.openAccount(Account.SAVINGS);
		
		bill.deposit(0, 150.0);
		
		assertEquals(bill.getAccountBalance(0), 150.0, DOUBLE_DELTA);
		assertEquals(bill.getAccountBalance(1), 0.0, DOUBLE_DELTA);
		
		bill.transferMoney(0, 1, 160.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidAccountIndex() {
		Bank bank = new Bank();
		Customer bill = bank.registerNewCustomer("Bill", 0);
		
		bill.getAccountBalance(0);
	}
	
	@Test
	public void getCustomerByUniqueID() {
		Bank bank = new Bank();
		bank.registerNewCustomer("Bill", 0);
		
		assertEquals(bank.getCustomerByUniqueID(2), null);
		assertEquals(bank.getCustomerByUniqueID(0).getName(), "Bill");
	}
}
