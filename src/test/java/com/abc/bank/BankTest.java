package com.abc.bank;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.abc.bank.account.Account;
import com.abc.bank.account.AccountType;
import com.abc.bank.admin.Customer;
import com.abc.bank.bankops.AuthorizationException;
import com.abc.bank.bankops.TransactionType;


public class BankTest {

	private Bank bank;
	private Customer bob;
	@Before
	public void before(){
		this.bank = new Bank();
		bob = new Customer("Bob",1);
		bank.addAccount(bob, AccountType.CHEQUING);	
		bank.addAccount(bob, AccountType.SAVINGS);
		bank.addAccount(bob, AccountType.MAXI);
	}


	@Test
	public void testAddCustomer() throws AuthorizationException{
		Bank bank = new Bank();
		Customer john = new Customer("John",123);
		Integer accNo = bank.addAccount(john, AccountType.CHEQUING);
		assertNotNull(accNo); 	       

		Account acc = bank.getAccount(john, accNo);
		assertTrue(acc.getAccNo() == accNo);
		assertTrue(acc.getAccType()== AccountType.CHEQUING);
	}

	@Test
	public void testCreateAccountSummary(){
		
		String base = "Customer Summary:\nBob (3 accounts)\n";
		assertEquals("Diff Detected", base,bank.createCustomerSummary(null));

		bank.addAccount(new Customer("John",123),AccountType.CHEQUING);
		String base2 = base + "John (1 account)\n";
		assertEquals("Diff Detected", base2,bank.createCustomerSummary(null));

	}

	@Test
	public void testCreateStatement(){
		bank.doBanking(bob,TransactionType.DEPOSIT,1,50D);
		bank.doBanking(bob,TransactionType.DEPOSIT,1,45.5D);
		
		String base = "Statement for Bob:\n"+
					  "Checking AccountNo.0\n"+
					  "Total: $0.00\n"+
					  "Savings AccountNo.1\n"+
					  "Transaction:1 deposit  $50.00\n"+
					  "Transaction:2 deposit  $45.50\n"+
					  "Total: $95.50\n"+
					  "Maxi Savings AccountNo.2\n"+
					  "Total: $0.00\n";
		
		assertEquals("Diff Detected", base,bank.createStatement(bob));
		
		bank.doBanking(bob,TransactionType.WITHDRAWL,1,	10D);

		String base2 = "Statement for Bob:\n"+
				  "Checking AccountNo.0\n"+
				  "Total: $0.00\n"+
				  "Savings AccountNo.1\n"+
				  "Transaction:1 deposit  $50.00\n"+
				  "Transaction:2 deposit  $45.50\n"+
				  "Transaction:3 withdrawl  $10.00\n"+
				  "Total: $85.50\n"+
				  "Maxi Savings AccountNo.2\n"+
				  "Total: $0.00\n";
	
	assertEquals("Diff Detected", base2,bank.createStatement(bob));

	}
	
	@Test
	public void testCreateIntereesstPaid(){
		String base = "Interest Paid:\n"+
			"AccNo:0:$0.00\n"+
			 "AccNo:1:$0.00\n"+
			 "AccNo:2:$0.00\n"+
			 "Total: $0.00";
		System.out.println(bank.createInterestPaidReport(null));		
		assertEquals("Diff Detected", base,bank.createInterestPaidReport(null));
		
	}

}


