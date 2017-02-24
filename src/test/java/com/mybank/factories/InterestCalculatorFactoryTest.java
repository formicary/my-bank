package com.mybank.factories;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.mybank.calculators.CheckingAccountCalculator;
import com.mybank.calculators.InterestCalculator;
import com.mybank.calculators.MaxiSavingsAccountCalculator;
import com.mybank.calculators.SavingsAccountCalculator;
import com.mybank.entities.Account;
import com.mybank.exceptions.UndefinedAccountTypeException;
import com.mybank.factories.InterestCalculatorFactory;

public class InterestCalculatorFactoryTest {

	@Mock
	Account mockCheckingAccount;
	@Mock
	Account mockSavingsAccount;
	@Mock
	Account mockMaxiSavingsAccount;
	@Mock
	Account mockExceptionAccount;

	private InterestCalculator calculator;

	@Before
	public void setUp() {

		mockCheckingAccount = Mockito.mock(Account.class);
		Mockito.when(mockCheckingAccount.getAccountType()).thenReturn(Account.CHECKING);

		mockSavingsAccount = Mockito.mock(Account.class);
		Mockito.when(mockSavingsAccount.getAccountType()).thenReturn(Account.SAVINGS);

		mockMaxiSavingsAccount = Mockito.mock(Account.class);
		Mockito.when(mockMaxiSavingsAccount.getAccountType()).thenReturn(Account.MAXI_SAVINGS);

		mockExceptionAccount = Mockito.mock(Account.class);
		Mockito.when(mockExceptionAccount.getAccountType()).thenReturn(3);

	}

	@Test
	public void getAccountCalculator_ShouldReturnCheckingAccountCalculator_GivenCheckingAccount()
			throws UndefinedAccountTypeException {

		calculator = InterestCalculatorFactory.getAccountCalculator(mockCheckingAccount);
		assertEquals(CheckingAccountCalculator.class, calculator.getClass());

	}

	@Test
	public void getAccountCalculator_ShouldReturnSavingsAccountCalculator_GivenSavingsAccount()
			throws UndefinedAccountTypeException {

		calculator = InterestCalculatorFactory.getAccountCalculator(mockSavingsAccount);
		assertEquals(SavingsAccountCalculator.class, calculator.getClass());

	}

	@Test
	public void getAccountCalculator_ShouldReturnMaxiSavingsAccountCalculator_GivenMaxiSavingsAccount()
			throws UndefinedAccountTypeException {

		calculator = InterestCalculatorFactory.getAccountCalculator(mockMaxiSavingsAccount);
		assertEquals(MaxiSavingsAccountCalculator.class, calculator.getClass());

	}

	@Test(expected = UndefinedAccountTypeException.class)
	public void getAccountCalculator_ShouldThrowUndefinedAccountTypeException_GivenUndefinedAccountType()
			throws UndefinedAccountTypeException {

		calculator = InterestCalculatorFactory.getAccountCalculator(mockExceptionAccount);

	}

}
