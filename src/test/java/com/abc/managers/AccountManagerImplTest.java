package com.abc.managers;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.abc.model.Account;
import com.abc.model.AccountBuilder;
import com.abc.model.AccountType;
import com.abc.model.Customer;
import com.abc.model.Money;
import com.abc.reports.AccountStatementGenerator;


public class AccountManagerImplTest {
	
	@Mock
	private AccountStatementGenerator accountStatementGenerator;
	
	@InjectMocks
	public AccountManager accountManager = new AccountManagerImpl();
	
	public Account anyAccount;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		anyAccount = AccountBuilder.createSaving().get();
	}
	
	
	
	@Test
	public void shouldOpenCeckingAcountByType(){
		Customer customer = new Customer("ANY_NAME");
		
		Account account = accountManager.openAccount(customer, AccountType.CHECKING);
		
		assertEquals(AccountType.CHECKING, account.getAccountType());
	}
	
	@Test
	public void shouldOpenSavingAcountByType(){
		Customer customer = new Customer("ANY_NAME");
		
		Account account = accountManager.openAccount(customer, AccountType.SAVINGS);
		
		assertEquals(AccountType.SAVINGS, account.getAccountType());
	}
	
	@Test
	public void shouldOpenMaxiSavingAcountByType(){
		Customer customer = new Customer("ANY_NAME");
		
		Account account = accountManager.openAccount(customer, AccountType.MAXI_SAVINGS);
		
		assertEquals(AccountType.MAXI_SAVINGS, account.getAccountType());
	}
	
	@Test
	public void shouldAddDepositToAccount(){
		Money amount = new Money("150");
		
		accountManager.addDeposit(anyAccount, amount);
		
		assertEquals(1, anyAccount.getTransactionList().size() );
		assertEquals(new BigDecimal("150.0"), anyAccount.getBalance().getAmount() );
	}
	
	
	
	@Test
	public void shouldAddWithdrawalToAccount(){
		Money amount = new Money("150");
		
		accountManager.addWithdrawal(anyAccount, amount);
		
		assertEquals(1, anyAccount.getTransactionList().size() );
		assertEquals(new BigDecimal("-150.0"), anyAccount.getBalance().getAmount() );
	}
	
	
	@Test
	public void shouldAddInterestToAccount(){
		Money amount = new Money("150");
		
		accountManager.addIntrest(anyAccount, amount);
		
		assertEquals(1, anyAccount.getTransactionList().size() );
		assertEquals(new BigDecimal("150.0"), anyAccount.getBalance().getAmount() );
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldFaildIfAmountOfMoneyIsLowerThenZero(){
		Money amount = new Money("-150");
		accountManager.addIntrest(anyAccount, amount);
	}
	
	
	@Test
	public void shouldGenerateAccountStatement(){
		accountManager.generateStatement(any(Account.class));
		verify(accountStatementGenerator).generate(any(Account.class));
	}
}
