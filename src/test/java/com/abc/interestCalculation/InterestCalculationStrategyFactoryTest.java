package com.abc.interestCalculation;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.abc.Account;
import com.abc.AccountType;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.interestCalculation.interestCalculationStrategies.CheckingAccountInterestCalculationStrategy;
import com.abc.interestCalculation.interestCalculationStrategies.MaxiSavingsAccountInterestCalculationStrategy;
import com.abc.interestCalculation.interestCalculationStrategies.SavingsAccountInterestCalculationStrategy;


public class InterestCalculationStrategyFactoryTest {
	
	private static final double DOUBLE_DELTA = 1e-10;
	
	
    @Test
    public void strategyFactoryTest(){
    	
    	InterestCalculationStrategyFactory factory = new InterestCalculationStrategyFactory();
        
        Account acc1 = Mockito.mock(Account.class);
        Mockito.when(acc1.getAccountType()).thenReturn(AccountType.CHECKING);
        
        Account acc2 = Mockito.mock(Account.class);
        Mockito.when(acc2.getAccountType()).thenReturn(AccountType.SAVINGS);
        
        Account acc3 = Mockito.mock(Account.class);
        Mockito.when(acc3.getAccountType()).thenReturn(AccountType.MAXI_SAVINGS);
        
        assertEquals(CheckingAccountInterestCalculationStrategy.class, (factory.getInterestCalculationStrategy(acc1.getAccountType())).getClass());
        
        assertEquals(SavingsAccountInterestCalculationStrategy.class, (factory.getInterestCalculationStrategy(acc2.getAccountType())).getClass());
        
        assertEquals(MaxiSavingsAccountInterestCalculationStrategy.class, (factory.getInterestCalculationStrategy(acc3.getAccountType())).getClass());

    }
    
    @Test (expected = IllegalArgumentException.class)
    public void strategyFactoryTest_EmptyAccountType(){
    	
    	InterestCalculationStrategyFactory factory = new InterestCalculationStrategyFactory();
        
        Account acc1 = Mockito.mock(Account.class);
        Mockito.when(acc1.getAccountType()).thenReturn(AccountType.EMPTY);
     
        factory.getInterestCalculationStrategy(acc1.getAccountType()).getClass();
 
    }
}