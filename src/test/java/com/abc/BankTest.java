package com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        CheckingAccount checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(100*(Math.pow(1+0.001/365, 365)-1), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        SavingsAccount checkingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals((1000*(Math.pow(1+0.001/365, 365)-1)+((500*(Math.pow(1+0.002/365, 365))-1))), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_no_withdrawal() {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date transDate = null;
    	try 
    	{
    		transDate = sdf.parse("04/01/2016");
		} 
    	catch (ParseException e) 
    	{
			e.printStackTrace();
		}
    	
        Bank bank = new Bank();
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));     
        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(2000.0);
        maxiSavingsAccount.transactions.get(1).transactionDate = transDate;

        assertEquals(1000*(Math.pow(1+0.05/365, 365)-1), bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_withdrawal() {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date transDate = null;
    	try 
    	{
    		transDate = sdf.parse("16/01/2016");
		} 
    	catch (ParseException e) 
    	{
			e.printStackTrace();
		}
    	
        Bank bank = new Bank();
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount)); 
        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(2000.0);
        maxiSavingsAccount.transactions.get(1).transactionDate = transDate;

        assertEquals(1000*(Math.pow(1+0.001/365, 365)-1), bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void getFirstCustomer()
    {
    	Bank bank = new Bank();
    	Customer richard = new Customer("Richard");
    	Customer bill = new Customer("Bill");    	
    	bank.addCustomer(richard);
    	bank.addCustomer(bill);
    	
    	assertEquals("Richard", bank.getFirstCustomer());    	
    }
}
