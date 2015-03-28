package com.abc;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.abc.exceptions.ExceededFundsException;
import com.abc.exceptions.InvalidAccountTypeException;
import com.abc.exceptions.InvalidCustomerAccount;
import com.abc.exceptions.InvalidCustomerName;

import static org.junit.Assert.*;
import static com.abc.accounts.AccountType.*;

public class CustomerTest {
	
	private Bank bank;
	private Customer customer;
	@Rule
	public ExpectedException exception;
	
	@Before
	public void setUp() {
		bank = new Bank();
		try {
			customer = bank.createCustomer("Oscar");
		} catch (InvalidCustomerName e) {
			e.printStackTrace();
		}
		exception = ExpectedException.none();
		
	}

    @Test 
    public void checkStatementGeneration() {

        try {
			Account checkingAccount = customer.openAccount(CHECKING);
			Account savingsAccount = customer.openAccount(SAVINGS);
			checkingAccount.deposit(new BigDecimal("100.0"));
			savingsAccount.deposit(new BigDecimal("4000.0"));
			savingsAccount.withdraw(new BigDecimal("200.0"));
		} catch (InvalidAccountTypeException | ExceededFundsException e) {
			e.printStackTrace();
		}
        
        assertEquals("Statement for Oscar\n" +
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
                "Total In All Accounts $3,900.00", customer.getStatement());
    }
    
    @Test
    public void checkEmptyStatement() {
    	assertEquals("Statement for Oscar\n" + 
    				 "There are no accounts associated with Oscar",
    				 customer.getStatement());
    }
    
   
    @Test
    public void testOneAccount() {
        try {
			customer.openAccount(SAVINGS);
		} catch (InvalidAccountTypeException e) {
			e.printStackTrace();
		}
        assertEquals(1, customer.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
       try {
		customer.openAccount(SAVINGS);
		customer.openAccount(CHECKING);
	} catch (InvalidAccountTypeException e) {
		e.printStackTrace();
	}
        assertEquals(2, customer.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        try {
			customer.openAccount(CHECKING);
			customer.openAccount(SAVINGS);
			customer.openAccount(MAXISAVINGS);
		} catch (InvalidAccountTypeException e) {
			e.printStackTrace();
		}
        assertEquals(3, customer.getNumberOfAccounts());
    }
    
    @Test
    public void contains() {
    	try {
			Account account = customer.openAccount(CHECKING);
			assertTrue(customer.contains(account));
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
    public void transfer() {
    	try {
			Account checking = customer.openAccount(CHECKING);
			Account savings = customer.openAccount(SAVINGS);
			checking.deposit(new BigDecimal("1500.00"));
			checking.transferTo(savings, customer, new BigDecimal("500.00"));
			assertEquals( new BigDecimal("1000.00"), checking.getBalance());
			assertEquals( new BigDecimal("500.00"), savings.getBalance());
			
		} catch (InvalidAccountTypeException | InvalidCustomerAccount | ExceededFundsException e) {
			e.printStackTrace();
		}  
    }
    
    @Test
    public void transferWrong() {
    	try {
			Account checking = customer.openAccount(CHECKING);
			Account savings = customer.openAccount(SAVINGS);
			checking.deposit(new BigDecimal("1500.00"));
			boolean success = savings.transferTo(savings, customer, new BigDecimal("500.00"));
			assertFalse(success);
			exception.expect(ExceededFundsException.class);
			boolean success2 = savings.transferTo(checking, customer, new BigDecimal("500.00"));
			assertFalse(success2);
			
			
		} catch (InvalidAccountTypeException | InvalidCustomerAccount | ExceededFundsException e) {}  
    }
    
    @Test
    public void transferWrongCustomer() {
    	try {
    		Customer bill = new Customer("Bill");
			Account checking = customer.openAccount(CHECKING);
			Account savings = customer.openAccount(SAVINGS);
			checking.deposit(new BigDecimal("1500.00"));
			exception.expect(InvalidCustomerAccount.class);
			checking.transferTo(savings, bill, new BigDecimal("500.00"));
		} catch (InvalidAccountTypeException | InvalidCustomerAccount | InvalidCustomerName | ExceededFundsException e) {}  
    }
}
