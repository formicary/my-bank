package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for various aspects of the "Bank" class.
 * @author Daniel Seymour
 * @version 1.0
 */
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private String[] testCustomers = {"John", "Jill", "Bill"};

    /**
     * Tests whether the "customerSummary" method from the "Bank" class works for a single customer.
     * 
     * Changed:
     * Opened Savings and Maxi-Savings account for the test customer, and tested each
     * to see whether the "format" method from the "Bank" class works as intended. 
     */
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Checking());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
        
        john.openAccount(new Savings());
        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
        
        john.openAccount(new MaxiSavings());
        assertEquals("Customer Summary\n - John (3 accounts)", bank.customerSummary());
    }
    
    /**
     * Tests whether the "customerSummary" method works for multiple accounts.
     */
    @Test
    public void multipleCustomerSummary() {
    	Bank bank = new Bank();
    	for (String n : testCustomers) {
    		Customer customer = new Customer(n);
    		customer.openAccount(new Checking()).openAccount(new Savings());
    		bank.addCustomer(customer);
    	}
    	assertEquals("Customer Summary\n - John (2 accounts)" + 
    	"\n - Jill (2 accounts)" + "\n - Bill (2 accounts)", bank.customerSummary());
    }

    /**
     * Tests the interest paid to a single customer with a Checking account.
     * 
     * Changed:
     * Instantiates an "Account" object with dynamic type "Checking" as this subclass
     * now exists.
     */
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Checking();
        Customer customer = new Customer("John").openAccount(checkingAccount);
        bank.addCustomer(customer);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Tests the interest paid to a single customer with a Savings account.
     * 
     * Changed:
     * Instantiates an "Account" object with dynamic type "Savings" as this subclass
     * now exists.
     * Changed method name to cameCase for consistency.
     */
    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Savings();
        Customer customer = new Customer("John").openAccount(savingsAccount);
        bank.addCustomer(customer);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Tests the interest paid to a single customer with a Maxi-Savings account.
     * 
     * Changed:
     * Instantiates an "Account" object with dynamic type "MaxiSavings" as this subclass
     * now exists.
     * Changed method name to cameCase for consistency.
     */
    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavings();
        Customer customer = new Customer("John").openAccount(maxiSavingsAccount);
        bank.addCustomer(customer);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    
    /**
     * Tests the interest paid to a single customer with multiple accounts.
     * 
     */
    @Test
    public void multipleAccounts() {
    	Bank bank = new Bank();
    	Account checkingAccount = new Checking();
    	Account savingsAccount = new Savings();
    	Account maxiSavingsAccount = new MaxiSavings();
    	Customer customer = new Customer("John").openAccount(checkingAccount)
    			.openAccount(savingsAccount).openAccount(maxiSavingsAccount);
    	bank.addCustomer(customer);
    	
    	checkingAccount.deposit(100.0);
    	savingsAccount.deposit(1500.0);
    	maxiSavingsAccount.deposit(3000.0);
    	
    	assertEquals(0.1 + 2.0 + 170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    /**
     * Tests the total interest paid by the bank for any number of customers listed in the
     * "testCustomers" array, in this case three. 
     * 
     */
    @Test
    public void multipleCustomersAccounts() {
    	Bank bank = new Bank();
    	for (String n : testCustomers) {
    		Account checkingAccount = new Checking();
    		Account savingsAccount = new Savings();
    		Account maxiSavingsAccount = new MaxiSavings();
    		Customer customer = new Customer(n);
    		bank.addCustomer(customer.openAccount(checkingAccount)
    			.openAccount(savingsAccount).openAccount(maxiSavingsAccount));
    		checkingAccount.deposit(100.0);
    		savingsAccount.deposit(1500.0);
    		maxiSavingsAccount.deposit(3000.0);
    	}
    	assertEquals((0.1 + 2.0 + 170.0) * testCustomers.length, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    

}