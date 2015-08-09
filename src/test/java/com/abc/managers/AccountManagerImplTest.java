package com.abc.managers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.abc.model.Account;
import com.abc.model.AccountBuilder;
import com.abc.model.AccountType;
import com.abc.model.Customer;
import com.abc.providers.AccountNumberProvider;
import com.abc.reports.AccountStatementGenerator;

public class AccountManagerImplTest {

	private final static int ANY_ACCOUNT_NUMBER = 1;

	@Mock
	public AccountNumberProvider accountNumberProvider;

	@Mock
	private AccountStatementGenerator accountStatementGenerator;

	@InjectMocks
	public AccountManager accountManager = new AccountManagerImpl();

	public Account anyAccount;

	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(accountNumberProvider.getAccountNumber()).thenReturn(ANY_ACCOUNT_NUMBER);
		anyAccount = AccountBuilder.createSaving().get();
	}

	
	
	@Test
	public void shouldOpenCeckingAcountByType() {
		Customer customer = new Customer("ANY_NAME");

		Account account = accountManager.openAccount(customer, AccountType.CHECKING);

		assertEquals(AccountType.CHECKING, account.getAccountType());
	}
	
	

	@Test
	public void shouldOpenSavingAcountByType() {
		Customer customer = new Customer("ANY_NAME");

		Account account = accountManager.openAccount(customer, AccountType.SAVINGS);

		assertEquals(AccountType.SAVINGS, account.getAccountType());
	}

	
	
	@Test
	public void shouldOpenMaxiSavingAcountByType() {
		Customer customer = new Customer("ANY_NAME");

		Account account = accountManager.openAccount(customer, AccountType.MAXI_SAVINGS);

		assertEquals(AccountType.MAXI_SAVINGS, account.getAccountType());
	}

	
	
	@Test
	public void shouldGenerateAccountStatement() {
		accountManager.generateStatement(any(Account.class));
		verify(accountStatementGenerator).generate(any(Account.class));
	}
}
