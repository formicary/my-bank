package com.abc.reports;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.abc.model.Account;
import com.abc.model.AccountBuilder;
import com.abc.model.Customer;

public class CustomerStatementGeneratorImplTest {
	
	private static final String ANY_ACCOUNT_STATEMETN = "ANY_STATEMENT";
	
	@Mock
	private AccountStatementGenerator accountStatementGenerator;
	
	@InjectMocks
	public CustomerStatementGenerator customerStatementGenerator = new CustomerStatementGeneratorImpl();

	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(accountStatementGenerator.generate(any(Account.class))).thenReturn(ANY_ACCOUNT_STATEMETN);
		
	}
	
	
	
	@Test
	public void shouldGenerateStatementForCustomerWithOneAccount(){
		Customer customer = new Customer("ANY_NAME");
		customer.addAccount(AccountBuilder.createChecking().withDeposit("100").get());
		
		String expectedRes = "Statement for ANY_NAME\n"+
								"\n"+ ANY_ACCOUNT_STATEMETN+"\n" +
								"\nTotal In All Accounts $100.00";
		
		Assert.assertEquals(expectedRes, customerStatementGenerator.generate(customer));
		
	}
	
	
	
	
	@Test
	public void shouldGenerateStatementForCustomerWithMultipleAccounts(){
		Customer customer = new Customer("ANY_NAME");
		customer.addAccount(AccountBuilder.createChecking().withDeposit("100").get());
		customer.addAccount(AccountBuilder.createSaving().withDeposit("4000").withWithdrawal("200").get());
		
		String expectedRes = "Statement for ANY_NAME\n"+
								"\n"+ ANY_ACCOUNT_STATEMETN+"\n" +
								"\n"+ ANY_ACCOUNT_STATEMETN+"\n" +
								"\nTotal In All Accounts $3900.00";
		
		
		Assert.assertEquals(expectedRes, customerStatementGenerator.generate(customer));
		
	}
}
