package com.abc.managers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.abc.model.Account;
import com.abc.model.AccountBuilder;
import com.abc.model.Customer;
import com.abc.model.CustomerTransactionRequest;
import com.abc.model.Money;
import com.abc.providers.DateProvider;
import com.abc.validators.TransactionRequestValidator;


public class TransactionManagerImplTest {
	
	private final static int ACCOUNT_NO_FROM = 999;
	private final static int ACCOUNT_NO_TO = 888;
	
	@Mock
	private DateProvider dateProvider;
	
	@Mock
	private TransactionRequestValidator validator;
	
	@InjectMocks
	public TransactionManager transactionManager = new TransactionManagerImpl();
	

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(dateProvider.getDate()).thenReturn(new GregorianCalendar());
	}
	
	
	
	
	@Test
	public void shouldAddDepositToAccount(){
		Account anyAcount = AccountBuilder.createSaving().get();
		Money amount = new Money("100");
		
		transactionManager.addDeposit(anyAcount, amount);
		
		assertEquals(1, anyAcount.getTransactionList().size() );
		assertEquals(amount, anyAcount.getBalance() );
	}
	
	
	
	@Test
	public void shouldAddWithdrawalToAccount(){
		Money amount = new Money("150");
		Account account = AccountBuilder.createSaving().withDeposit("1000").get();
		
		transactionManager.addWithdrawal(account, amount);
		
		assertEquals(2, account.getTransactionList().size() );
		assertEquals(new Money("850"), account.getBalance() );
	}
	
	
	
	
	@Test
	public void shouldAddInterestToAccount(){
		Account anyAccount = AccountBuilder.createSaving().get();
		Money amount = new Money("150");
		
		transactionManager.addIntrest(anyAccount, amount);
		
		assertEquals(1, anyAccount.getTransactionList().size() );
		assertEquals(amount, anyAccount.getBalance());
	}
	
	
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldFaildIfAmountOfMoneyIsLowerThenZero(){
		Money amount = new Money("-150");
		transactionManager.addIntrest(AccountBuilder.createSaving().get(), amount);
	}
	
	
	
	@Test
	public void shouldMoveCustomerMoney(){
		Customer customer = new Customer("ANY_NAME");
		Account accountFrom = AccountBuilder.createSaving(ACCOUNT_NO_FROM).withDeposit("1000").get();
		Account accountTo = AccountBuilder.createSaving(ACCOUNT_NO_TO).withDeposit("50").get();
		
		customer.addAccount(accountTo);
		customer.addAccount(accountFrom);
		
		CustomerTransactionRequest request = createCustomerTransactionRequest(customer, "500");
		
		transactionManager.moveCustomerMoney(request);
		
		assertEquals( new Money("500"), accountFrom.getBalance());
		assertEquals( new Money("550"), accountTo.getBalance());
		
	}
	
	
	
	private CustomerTransactionRequest createCustomerTransactionRequest(Customer customer, String amount){
		return new CustomerTransactionRequest(customer, ACCOUNT_NO_FROM, ACCOUNT_NO_TO, new Money(amount));
	}
}
