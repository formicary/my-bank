package com.abc.reports;

import org.junit.Assert;
import org.junit.Test;

import com.abc.model.Account;
import com.abc.model.AccountBuilder;


public class AccountStatementGeneratorImplTest {
	
	private AccountStatementGenerator statementGenerator = new AccountStatementGeneratorImpl();
	
	
	
	@Test
	public void shouldGenerateStatementForOneAccount(){
		Account account = AccountBuilder.createChecking().withDeposit("100").get();
		
		String expectedResult =  "Checking Account\n  deposit $100.00\n Total $100.00";
		
		Assert.assertEquals(expectedResult, statementGenerator.generate(account));
	}
	
	
	@Test
	public void shouldGenerateStatementForAccountWithMultipleTransactions(){
		Account account = AccountBuilder
							.createChecking()
							.withDeposit("100")
							.withWithdrawal("50")
							.get();
		
		String expectedResult =  "Checking Account\n  deposit $100.00\n   withdrawal $50.00\n Total $50.00";
		
		Assert.assertEquals(expectedResult, statementGenerator.generate(account));
	}
}
