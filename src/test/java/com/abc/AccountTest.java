package com.abc;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.abc.exceptions.ExceededFundsException;
import com.abc.exceptions.InvalidAccountTypeException;
import com.abc.exceptions.InvalidCustomerName;
import com.abc.interests.CheckingStrategy;

import static org.junit.Assert.*;
import static com.abc.accounts.AccountType.*;

public class AccountTest {
	
	private Customer customer;
	@Rule
	public ExpectedException exception;

	
	@Before
	public void setUp() {
		try {
			customer = new Bank().createCustomer("John");
		} catch (InvalidCustomerName e) {
			e.printStackTrace();
		}
		exception = ExpectedException.none();
	}
	
	@Test
	public void depositWrongBigDecimal() {
		try {
			Account savings = customer.openAccount(SAVINGS);
			exception.expect(NumberFormatException.class);
			BigDecimal amount = new BigDecimal("");
			savings.deposit(amount);
		} catch (InvalidAccountTypeException | NumberFormatException e) {}
		assertTrue(true);
	}
	
	 @Test
    public void manipulateBalance() {

      try {
			Account checkingAccount = customer.openAccount(CHECKING);
			checkingAccount.deposit(new BigDecimal("100.00"));
			BigDecimal c = checkingAccount.getBalance();
			assertTrue(c.equals(checkingAccount.getBalance()));
			c = BigDecimal.ZERO;
			assertFalse(c.equals(checkingAccount.getBalance()));
			checkingAccount.deposit(new BigDecimal("500.00"));
			assertEquals(new BigDecimal("600.00"),checkingAccount.getBalance());
			
		} catch (InvalidAccountTypeException e) {
			e.printStackTrace();
		}
    }
	 @Test
    public void containsEmpty() {
    	try {
    		Customer bill = new Customer("Bill");
			Account account = customer.openAccount(CHECKING);
			assertFalse(bill.contains(account));
		} catch (InvalidAccountTypeException | InvalidCustomerName e) {
			e.printStackTrace();
		}  
    }
	 
	 @Test
	 public void negativeDeposit() {
		 try {
	    		Account account = customer.openAccount(CHECKING);
	    		exception.expect(IllegalArgumentException.class);
	    		account.deposit(new BigDecimal("-300.00"));
			} catch (InvalidAccountTypeException | IllegalArgumentException e) {}  
	 }
	 
	 @Test
	 public void negativeWithdrawal() {
		 try {
	    		Account account = customer.openAccount(CHECKING);
	    		exception.expect(IllegalArgumentException.class);
	    		account.withdraw(new BigDecimal("-300.00"));
			} catch (InvalidAccountTypeException | IllegalArgumentException | ExceededFundsException e) {}  
	 }
	 
	 @Test
	 public void setWrongStrategy() {
		 try {
	    		Account account = customer.openAccount(CHECKING);
	    		exception.expect(IllegalArgumentException.class);
	    		account.setInterestRateStrategy(new CheckingStrategy());
			} catch (InvalidAccountTypeException | IllegalArgumentException e) {}  
	 }
	 
	

}
