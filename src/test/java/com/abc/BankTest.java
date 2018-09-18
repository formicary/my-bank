package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BankTest {

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
    	Bank bank = new Bank();
    	Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        
        checkingAccount.deposit(10000.0);
        checkingAccount.transactions.get(0)
        	.setTransactionDate(new GregorianCalendar(2016, Calendar.SEPTEMBER, 18).getTime());
        checkingAccount.withdraw(5000.0);
        checkingAccount.transactions.get(1)
    		.setTransactionDate(new GregorianCalendar(2017, Calendar.SEPTEMBER, 18).getTime());

        assertEquals(15.0, bank.totalInterestPaid(), 1);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(999.9);
        savingsAccount.transactions.get(0)
    		.setTransactionDate(new GregorianCalendar(2017, Calendar.SEPTEMBER, 18).getTime());

        assertEquals(1.9, bank.totalInterestPaid(), 0.1);
     
    }
    
    @Test
    public void maxiSavingsAccountNoWithdrawal() {
    	Bank bank = new Bank();
    	Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
    	bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
    	
    	maxiSavingsAccount.deposit(1000.0);
        maxiSavingsAccount.transactions.get(0)
    		.setTransactionDate(new GregorianCalendar(2017, Calendar.SEPTEMBER, 18).getTime());
        
        assertEquals(51.27, bank.totalInterestPaid(), 0.1);
    }
    
    @Test
    public void maxiSavingsAccountWithdrawal() {
    	Bank bank = new Bank();
    	Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
    	bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
    	
    	maxiSavingsAccount.deposit(1000.0);
        maxiSavingsAccount.transactions.get(0)
    		.setTransactionDate(new GregorianCalendar(2017, Calendar.SEPTEMBER, 18).getTime());
        maxiSavingsAccount.withdraw(100.0);
        maxiSavingsAccount.transactions.get(1)
			.setTransactionDate(new GregorianCalendar(2018, Calendar.SEPTEMBER, 12).getTime());
        
        assertEquals(50.42, bank.totalInterestPaid(), 0.1);
    }

}
