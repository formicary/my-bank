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
/*expected case deposit amount*/
	@Test 
	public void DepositTest(){
		
		Bank bank = new Bank();
        Account Saving = new Saving();
        bank.addCustomer(new Customer("Reem").openAccount(Saving));

        Saving.deposit(15000.0);
        Saving.deposit(15000.0);
        assertEquals(30000,Saving.getBalance(),DOUBLE_DELTA);
	}
	/*Testing expected case withdrawal with available amount in account*/
	@Test
	public void WithdrawTest(){
		Bank bank = new Bank();
        Account checking = new Checking();
        bank.addCustomer(new Customer("Reem").openAccount(checking));
        checking.deposit(3000);
        checking.withdraw(1000);
        checking.withdraw(1000);
        assertEquals(1000,checking.getBalance(),DOUBLE_DELTA);
		
	}
	/*
	 * Testing the case when the fund available in the account is less than the requested amount
	 * */
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
	/*testing the calculation of the account balance available */
	@Test
	public void sumTransactionTest(){
		Bank bank = new Bank();
        Account checking = new Checking();
        bank.addCustomer(new Customer("Reem").openAccount(checking));
        checking.deposit(1000);
        checking.deposit(2000);
        checking.withdraw(100);
        checking.withdraw(200);
        assertEquals(2700,checking.getBalance(),DOUBLE_DELTA);
        
	}
	/*
	 * this test the case when the amount to withdraw is zero
	 * */
	@Test(expected=IllegalArgumentException.class)
	public void IllegalArgumentExceptionWithdrawtest(){
		Bank bank = new Bank();
        AccountI checking = new Checking();
        bank.addCustomer(new Customer("Reem").openAccount(checking));
        	checking.withdraw(0);
        	
       
	}
	/*testing transfers between accounts expected case */
	@Test
	public void TrasferFundTest(){
		
		Account checking = new Checking();
        Customer bill = new Customer("Bill").openAccount(checking);
        Account saving = new Saving();
        bill.openAccount( saving);
        saving.deposit(2000);
        saving.transferFund(saving, checking, 100.0);
       
        assertEquals(saving.getBalance(),1900,DOUBLE_DELTA);
        
		
	}
	/*testing transfers between accounts exception case */
	@Test(expected=NegativeAmountException.class)
	public void TrasferFundTestNoFund(){
		
		Account checking = new Checking();
        Customer bill = new Customer("Bill").openAccount(checking);
        Account saving = new Saving();
        bill.openAccount( saving);
        saving.deposit(50);
        
        saving.transferFund(saving, checking, 100.0);
       
        
		
	}
	
	
}
