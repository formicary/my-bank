package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import Banking.Account;
import Banking.CheckingAccount;
import Banking.Customer;
import Banking.MaxiSavingAccount;
import Banking.SavingAccount;


public class CustomerTest {

	@Test
	public void checkCustomerName(){
		
		String name = "Dave";
		
		Customer customer = new Customer(name);
		
		assertEquals(customer.getName(),name);
	}
	
	@Test
	public void canOpenAnAccounts(){
		
		Customer customer = new Customer("Dave");
		
		customer.openAccount(new CheckingAccount());
		assertEquals(customer.getNumberOfAccounts(),1);
		
		customer.openAccount(new SavingAccount());
		assertEquals(customer.getNumberOfAccounts(),2);
		
		customer.openAccount(new MaxiSavingAccount());
		assertEquals(customer.getNumberOfAccounts(),3);
	}
	

	@Test
	public void checkInterestEarnedChecking(){
		
		Account.resetAccountNumber();
		
		Customer customer = new Customer("Dave");
		customer.openAccount(new CheckingAccount());
		
		double checkAmount = 100;
		
		customer.getAccountByNumber(0).deposit(checkAmount);
				
		assertEquals(customer.totalInterestEarned(),checkAmount * CheckingAccount.getInterestRate(),0);
		
	}
	
	@Test
	public void checkInterestEarnedSaving(){
		
		Account.resetAccountNumber();
		
		Customer customer = new Customer("Dave");
		customer.openAccount(new SavingAccount());
		
		double savingLowAmount = 100, savingHighAmount = 1000;
		
		customer.getAccountByNumber(0).deposit(savingLowAmount);
				
		assertEquals(customer.totalInterestEarned(),savingLowAmount * SavingAccount.getLowerInterestRate(),0);
		
		customer.getAccountByNumber(0).deposit(savingHighAmount);
		
		double lowerInterest = SavingAccount.getUpperInterestThreshold() * SavingAccount.getLowerInterestRate();
		double upperInterest = (savingHighAmount + savingLowAmount - SavingAccount.getUpperInterestThreshold()) * SavingAccount.getUpperInterestRate();
		
		assertEquals(customer.totalInterestEarned(),lowerInterest + upperInterest,0);
		
	}
	
	@Test
	public void checkInterestEarnedMaxiSaving(){
		
		Account.resetAccountNumber();
		
		Customer customer = new Customer("Dave");
		customer.openAccount(new MaxiSavingAccount());
		
		double maxiSavingNoWithdrawalAmount = 100, maxiSavingWithdrawalAmount = 50;
		
		customer.getAccountByNumber(0).deposit(maxiSavingNoWithdrawalAmount);
		assertEquals(customer.totalInterestEarned(),maxiSavingNoWithdrawalAmount * MaxiSavingAccount.getUpperInterestRate(),0);
		
		customer.getAccountByNumber(0).withdraw(maxiSavingWithdrawalAmount);
		assertEquals(customer.totalInterestEarned(),(maxiSavingNoWithdrawalAmount - maxiSavingWithdrawalAmount) * MaxiSavingAccount.getLowerInterestRate(),0);
	}
	
	@Test
	public void checkThatMoneyCanBeTransfered(){
		
		Account.resetAccountNumber();
		
		Customer customer = new Customer("Dave");
		
		customer.openAccount(new CheckingAccount());
		customer.openAccount(new CheckingAccount());
		
		double depositAmount = 10, transferAmount = 5;
		
		customer.getAccountByNumber(0).deposit(depositAmount);
		
		customer.transferMoney(0, 1, transferAmount);
		
		assertEquals(customer.getAccountByNumber(0).sumTransactions(),depositAmount - transferAmount,0);
		assertEquals(customer.getAccountByNumber(1).sumTransactions(),transferAmount,0);
		
	}
}

