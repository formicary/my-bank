package com.abc;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.abc.implementation.CheckingAccount;
import com.abc.implementation.MaxiSavingsAccount;
import com.abc.implementation.RateHelper;
import com.abc.implementation.SavingsAccount;
import com.abc.implementation.Transaction;
import com.abc.implementation.TransactionHelper;
import com.abc.interfaces.IAccount;
import com.abc.interfaces.IRateHelper;
import com.abc.interfaces.ITransaction;
import com.abc.interfaces.ITransactionHelper;

public class AccountTest {
	private static final double DOUBLE_DELTA = 1e-10;
	
	@Test
    public void checkingAccountCompoundInterestEarned() {
    	ITransactionHelper transactionHelper = mock(ITransactionHelper.class);
    	IRateHelper rateHelper = mock(IRateHelper.class);
    	when(transactionHelper.getDaysDifference(any(ITransaction.class), any(ITransaction.class))).thenReturn(5);
    	when(rateHelper.getEarnedInterest(any(double.class), any(int.class))).thenReturn(0.1);
        IAccount checkingAccount = new CheckingAccount(rateHelper, transactionHelper);

        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(20.0);
        checkingAccount.deposit(200.0);
        
        double result = checkingAccount.compoundInterestEarned();

        assertEquals(48.9, result, DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountCompoundInterestEarned() {
    	ITransactionHelper transactionHelper = mock(ITransactionHelper.class);
    	IRateHelper rateHelper = mock(IRateHelper.class);
    	when(transactionHelper.getDaysDifference(any(ITransaction.class), any(ITransaction.class))).thenReturn(5);
    	when(rateHelper.getEarnedInterest(0.1, 5)).thenReturn(0.1);
    	when(rateHelper.getEarnedInterest(0.1, 0)).thenReturn(0.1);
    	when(rateHelper.getEarnedInterest(0.2, 5)).thenReturn(0.2);
    	when(rateHelper.getEarnedInterest(0.2, 0)).thenReturn(0.2);
    	when(rateHelper.getDailyRate(0.001)).thenReturn(0.1);
    	when(rateHelper.getDailyRate(0.002)).thenReturn(0.2);
        IAccount savingsAccount = new SavingsAccount(rateHelper, transactionHelper);

        savingsAccount.deposit(700.0);
        savingsAccount.deposit(800.0);

        assertEquals(284.0, savingsAccount.compoundInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountCompoundInterestEarned() {
    	IRateHelper rateHelper = mock(IRateHelper.class);
    	when(rateHelper.getEarnedInterest(0.1, 5)).thenReturn(0.1);
    	when(rateHelper.getEarnedInterest(0.1, 0)).thenReturn(0.1);
    	when(rateHelper.getEarnedInterest(0.5, 4)).thenReturn(0.5);
    	when(rateHelper.getEarnedInterest(0.5, 10)).thenReturn(0.5);
    	
    	when(rateHelper.getDailyRate(0.001)).thenReturn(0.1);
    	when(rateHelper.getDailyRate(0.05)).thenReturn(0.5);
    	
    	ITransaction transaction1 = new Transaction(100, new LocalDate(2017, 4, 1));
    	ITransaction transaction2 = new Transaction(20, new LocalDate(2017, 4, 5));
    	transaction1.setNextTransaction(transaction2);
    	ITransaction transaction3 = new Transaction(-50, new LocalDate(2017, 4, 10));
    	transaction2.setNextTransaction(transaction3);
    	
        IAccount maxiAccount = new MaxiSavingsAccount(transaction1, rateHelper, new TransactionHelper());

        assertEquals(135.5, maxiAccount.compoundInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test 
    public void depositSuccessful()
    {
    	IAccount account = new CheckingAccount(new RateHelper(), new TransactionHelper());
    	account.deposit(200);
    	account.deposit(300);
    	assertEquals(500, account.getTotalBalance(), DOUBLE_DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeAmount()
    {
    	IAccount account = new CheckingAccount(new RateHelper(), new TransactionHelper());
    	account.deposit(-200);
    }
    
    @Test 
    public void withdrawSuccessful()
    {
    	IAccount account = new CheckingAccount(new RateHelper(), new TransactionHelper());
    	account.deposit(200);
    	account.deposit(300);
    	account.withdraw(100);
    	assertEquals(400, account.getTotalBalance(), DOUBLE_DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegative()
    {
    	IAccount account = new CheckingAccount(new RateHelper(), new TransactionHelper());
    	account.deposit(200);
    	account.deposit(300);
    	account.withdraw(-100);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void withdrawInsufficientBalance()
    {
    	IAccount account = new CheckingAccount(new RateHelper(), new TransactionHelper());
    	account.deposit(200);
    	account.deposit(300);
    	account.withdraw(1000);
    }
}
