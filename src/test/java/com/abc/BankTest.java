package com.abc;

import org.junit.Test;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class BankTest {
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John", Locale.US);
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        
        Customer jill = new Customer("Jill", Locale.UK);
        jill.openAccount(new Account(Account.SAVINGS)).openAccount(new Account(Account.MAXI_SAVINGS));
        bank.addCustomer(jill);

        assertEquals("Customer Summary\n - John (1 account)" +
        "\n - Jill (2 accounts)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill", Locale.US).openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1000.0);

        assertEquals("=== Statement for Total Interest Earned Across all Accounts ===" +
        		"\nBill: $1.00" + 
        		"\n=== Total Interest Paid Out: $1.00", bank.totalInterestPaid());
    }

    @Test
    public void testSavingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill", Locale.US).openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals("=== Statement for Total Interest Earned Across all Accounts ===" +
        		"\nBill: $2.00" + 
        		"\n=== Total Interest Paid Out: $2.00", bank.totalInterestPaid());
    }

    @Test
    public void testMaxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill", Locale.US).openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals("=== Statement for Total Interest Earned Across all Accounts ===" +
        		"\nBill: $150.00" + 
        		"\n=== Total Interest Paid Out: $150.00", bank.totalInterestPaid());
    }
    
    @Test
    public void testMultipleAccounts() {
    	Bank bank = new Bank();
    	Account bobChecking = new Account(Account.CHECKING);
    	Account bobSavings = new Account(Account.SAVINGS);
    	Account janeSavings = new Account(Account.SAVINGS);
    	Account janeMaxiSavings = new Account(Account.MAXI_SAVINGS);
    	
    	bank.addCustomer(new Customer("Bob", Locale.UK).openAccount(bobChecking).openAccount(bobSavings));
    	bank.addCustomer(new Customer("Jane", Locale.UK).openAccount(janeSavings).openAccount(janeMaxiSavings));
    	
    	bobChecking.deposit(2000);
    	bobSavings.deposit(1000);
    	janeSavings.deposit(2000);
    	janeMaxiSavings.deposit(1000);
    	
    	assertEquals("=== Statement for Total Interest Earned Across all Accounts ===" +
        		"\nBob: £3.00" + 
    			"\nJane: £53.00" +
        		"\n=== Total Interest Paid Out: £56.00", bank.totalInterestPaid());
    }
    
    @Test
    public void testFirstCustomer() {
    	Bank bank = new Bank();
    	Account bobChecking = new Account(Account.CHECKING);
    	Account bobSavings = new Account(Account.SAVINGS);
    	Account janeSavings = new Account(Account.SAVINGS);
    	Account janeMaxiSavings = new Account(Account.MAXI_SAVINGS);
    	
    	bank.addCustomer(new Customer("Jane", Locale.UK).openAccount(janeSavings).openAccount(janeMaxiSavings));
    	bank.addCustomer(new Customer("Bob", Locale.UK).openAccount(bobChecking).openAccount(bobSavings));
    	
    	assertEquals("Jane", bank.getFirstCustomer());
    }
}
