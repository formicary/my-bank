package com.abc.account;

import com.abc.Bank;
import com.abc.Customer;
import com.abc.account.*;
import com.abc.account.Checking;
import com.abc.account.Saving;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

	@Test 
	public void DepositTest(){
		
		Bank bank = new Bank();
        AccountI Saving = new Saving();
        bank.addCustomer(new Customer("Reem").openAccount(Saving));

        Saving.deposit(15000.0);
        Saving.deposit(15000.0);
        assertEquals(30000,Saving.sumTransactions(),DOUBLE_DELTA);
	}
	@Test
	public void WithdrawTest(){
		Bank bank = new Bank();
        AccountI checking = new Checking();
        bank.addCustomer(new Customer("Reem").openAccount(checking));
        checking.deposit(3000);
        checking.withdraw(1000);
        checking.withdraw(1000);
        assertEquals(1000,checking.sumTransactions(),DOUBLE_DELTA);
		
	}
	/*
	 * testing the case when the fund available in the account is less than the requested amount*/
	@Test(expected=NegativeAmountException.class)
	public void NegativeAmountExceptionwithdrawtest(){
		Bank bank = new Bank();
        AccountI checking = new Checking();
        bank.addCustomer(new Customer("Reem").openAccount(checking));
        
        	checking.withdraw(1000);
        	
       
	}
	/*
	 * this test the case when the amount deposited is zero
	 * */
	@Test(expected=IllegalArgumentException.class)
	public void IllegalArgumentExceptiondeposittest(){
		Bank bank = new Bank();
        AccountI checking = new Checking();
        bank.addCustomer(new Customer("Reem").openAccount(checking));
        
        	checking.deposit(0);
        	
       
	}
	@Test
	public void sumTransactionTest(){
		Bank bank = new Bank();
        AccountI checking = new Checking();
        bank.addCustomer(new Customer("Reem").openAccount(checking));
        checking.deposit(1000);
        checking.deposit(2000);
        checking.withdraw(100);
        checking.withdraw(200);
        assertEquals(2700,checking.sumTransactions(),DOUBLE_DELTA);
        
	}
	
	
}
