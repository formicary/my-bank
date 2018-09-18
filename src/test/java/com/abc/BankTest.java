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

        assertEquals(8541, bank.totalInterestPaid(), 1);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(999);
        savingsAccount.transactions.get(0)
    		.setTransactionDate(new GregorianCalendar(2017, Calendar.SEPTEMBER, 18).getTime());

        assertEquals(1068, bank.totalInterestPaid(), 1);
     
    }

}
