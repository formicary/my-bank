package com.abc;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class AccountTest {

	// General Account tests
	
	@Test
	public void testAccountType(){
		Account checking = new CheckingAccount();
		Account savings = new SavingsAccount();
		Account maxiSavings = new MaxiSavingsAccount();
		
		assertEquals(AccountType.CHECKING, checking.getAccountType());
		assertEquals(AccountType.SAVINGS, savings.getAccountType());
		assertEquals(AccountType.MAXI_SAVINGS, maxiSavings.getAccountType());
	}
	
	// Unneccesary to check for every type of account since deposit, withdrawal and sumTrans are all in
	// parent class - behaviour is the same.
	@Test
	public void testDepositAndWithdrawalAndSum(){
		Money M1 = new Money("30.00");
		Money M2 = new Money("15.45");
		
		Account checking = new CheckingAccount();
		
		checking.deposit(M1);
		checking.withdraw(M2);
		
		assertEquals(checking.sumTransactions().getAmount(), M1.getAmount().subtract(M2.getAmount()));
		
	}
	
	@Test
	public void testTransfer(){
		Customer c = new Customer("Tom Sturgeon");
		Account a = new SavingsAccount();
		a.deposit(new Money("100.00"));
		Account b = new SavingsAccount();
		c.openAccount(a);
		c.openAccount(b);
		
		Account.transfer(c, a, b, new Money("100.00"));
		
		// Check the transfer was a success!
		assertEquals(a.sumTransactions().getAmount(), new Money("0").getAmount());
		assertEquals(b.sumTransactions().getAmount(), new Money("100.00").getAmount());
	}
	
	
	@Test
	public void testInterest(){
		
		// CHECKING ACCOUNT
		Account checking = new CheckingAccount();
		checking.deposit(new Money("999"));
		assertEquals(new Money("1.00").getAmount(), checking.interestEarned().getAmount());
		
		
		// NORMAL SAVINGS
		Account savings = new SavingsAccount();
		savings.deposit(new Money("888"));
		assertEquals(new Money("0.89").getAmount(), savings.interestEarned().getAmount());
		savings.deposit(new Money("1001"));
		//3.78 as adding the 1001 onto the current balance of 888 (interest rate of 0.2)
		assertEquals(new Money("3.78").getAmount(), savings.interestEarned().getAmount());
		
		
		
		// MAXI_SAVINGS
		
		Account maxiSavings = new MaxiSavingsAccount();
		maxiSavings.deposit(new Money("100"));
		assertEquals(new Money("2").getAmount(), maxiSavings.interestEarned().getAmount());
		//put value into next threshold for 5% interest on 1100
		maxiSavings.deposit(new Money("1000"));
		assertEquals(new Money("55").getAmount(), maxiSavings.interestEarned().getAmount());
		maxiSavings.deposit(new Money("3000"));
		//moves over next threshold for 10% interest on 4100
		assertEquals(new Money("410").getAmount(), maxiSavings.interestEarned().getAmount());
	}
	
	
	

}
