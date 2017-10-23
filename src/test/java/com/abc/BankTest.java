package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;

public class BankTest {

    @Test
    /**
     * Testing a customer summary with multiple customers
     */
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.AccountType.CHECKING));
        john.openAccount(null);
        Customer bob = new Customer("Bob");
        bob.openAccount(new Account(Account.AccountType.CHECKING));
        bob.openAccount(new Account(Account.AccountType.SAVINGS));
        bob.openAccount(new Account(Account.AccountType.MAXI_SAVINGS));
        
        bank.addCustomer(john).addCustomer(bob);

        String expected = "Customer Summary\n" +
        					" - John (1 account)\n" +
        					" - Bob (3 accounts)";
        
        assertEquals(expected, bank.customerSummary());
    }

    @Test
    /**
     * Testing a single checking account
     */
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100);

        assertTrue(bank.totalInterestPaid().compareTo(new BigDecimal("0.1")) == 0);
    }
    
    @Test
    /**
     * Testing multiple checking accounts
     */
    public void checkingAccounts() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        checkingAccount.deposit(1000000);
        checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bob = new Customer("Bob").openAccount(checkingAccount);
        checkingAccount.deposit(10);
        checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer jill = new Customer("Jill").openAccount(checkingAccount);
        checkingAccount.deposit(500);
        checkingAccount = new Account(Account.AccountType.CHECKING);
        jill.openAccount(checkingAccount);
        checkingAccount.deposit(475);
        
        bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

        assertTrue(bank.totalInterestPaid().compareTo(new BigDecimal("1000.985")) == 0);
    }

    @Test
    /**
     * Testing a single savings account in the max threshold
     */
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        savingsAccount.deposit(1500);

        bank.addCustomer(bill);
        
        assertTrue(bank.totalInterestPaid().compareTo(new BigDecimal("2")) == 0);
    }
    
    @Test
    /**
     * Testing multiple savings accounts in different thresholds
     */
    public void savings_accounts() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        savingsAccount.deposit(1500);
        savingsAccount = new Account(Account.AccountType.SAVINGS);
        Customer jill = new Customer("Jill").openAccount(savingsAccount);
        savingsAccount.deposit(8285);
        savingsAccount = new Account(Account.AccountType.SAVINGS);
        Customer bob = new Customer("Bob").openAccount(savingsAccount);
        savingsAccount.deposit(800);
        savingsAccount = new Account(Account.AccountType.SAVINGS);
        bob.openAccount(savingsAccount);
        savingsAccount.deposit(2500);

        bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);
        
        assertTrue(bank.totalInterestPaid().compareTo(new BigDecimal("22.37")) == 0);
    }

    @Test
    /**
     * Testing a single maxi account in the max threshold
     */
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000);

        assertTrue(bank.totalInterestPaid().compareTo(new BigDecimal("170")) == 0);
    }
    
    @Test
    /**
     * Testing multiple maxi accounts in different thresholds
     */
    public void maxi_savings_accounts() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        savingsAccount.deposit(3000);
        savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        Customer jill = new Customer("Jill").openAccount(savingsAccount);
        savingsAccount.deposit(5978246);
        savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        Customer bob = new Customer("Bob").openAccount(savingsAccount);
        savingsAccount.deposit(65.45);
        savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        bob.openAccount(savingsAccount);
        savingsAccount.deposit(1888);
        
        bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

        assertTrue(bank.totalInterestPaid().compareTo(new BigDecimal("597930.309")) == 0);
    }
    
    @Test
    /**
     * Testing correct customer is returned as first customer
     */
    public void fistCustomer() {
    	Bank bank = new Bank();
    	Customer bill = new Customer("Bill");
    	Customer bob = new Customer("Bob");
    	Customer jill = new Customer("Jill");
    	
    	bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);
    	
    	assertSame(bill, bank.getFirstCustomer());
    }
    
    @Test
    /**
     * Testing null is returned if there are no customers when requesting the first customer
     */
    public void fistCustomerNull() {
    	Bank bank = new Bank();
    	
    	assertSame(null, bank.getFirstCustomer());
    }
    
    @Test
    public void addInterest() {
    	Bank bank = new Bank();
    	Customer bill = new Customer("Bill");
    	Customer bob = new Customer("Bob");
    	
    	Account ac = new Account(Account.AccountType.CHECKING);
    	ac.deposit(100);
    	bill.openAccount(ac);
    	Account as = new Account(Account.AccountType.SAVINGS);
    	as.deposit(5000);
    	bob.openAccount(as);
    	
    	bank.addCustomer(bob).addCustomer(bill);
    	bank.applyInterest();
    	
    	assertTrue(bill.totalAccountHoldings().compareTo(new BigDecimal("100.1")) == 0 &&
    				bob.totalAccountHoldings().compareTo(new BigDecimal("5009")) == 0);
    }
    
    @Test
    public void addInterestDaily() {
    	Bank bank = new Bank();
    	Customer bill = new Customer("Bill");
    	Customer bob = new Customer("Bob");
    	
    	Account ac = new Account(Account.AccountType.CHECKING);
    	ac.deposit(100);
    	bill.openAccount(ac);
    	Account as = new Account(Account.AccountType.SAVINGS);
    	as.deposit(5000);
    	bob.openAccount(as);
    	
    	bank.addCustomer(bob).addCustomer(bill);
    	bank.applyDailyInterest();
    	    	
    	assertTrue(bill.totalAccountHoldings().compareTo(new BigDecimal("100.0002")) == 0 &&
    				bob.totalAccountHoldings().compareTo(new BigDecimal("5000.0243")) == 0);
    }
    
    @Test
    public void totalHoldings() {
    	Bank bank = new Bank();
    	Customer bill = new Customer("Bill");
    	Customer bob = new Customer("Bob");
    	
    	Account ac = new Account(Account.AccountType.CHECKING);
    	ac.deposit(100);
    	bill.openAccount(ac);
    	Account as = new Account(Account.AccountType.SAVINGS);
    	as.deposit(5000);
    	bob.openAccount(as);
    	
    	bank.addCustomer(bob).addCustomer(bill);
    	
    	assertTrue(bank.getTotalHoldings().compareTo(new BigDecimal("5100")) == 0);
    }

    @Test
    public void stringCreation() {
    	Bank bank = new Bank();
    	Customer bill = new Customer("Bill");
    	Customer bob = new Customer("Bob");
    	
    	Account ac = new Account(Account.AccountType.CHECKING);
    	ac.deposit(100);
    	bill.openAccount(ac);
    	bank.addCustomer(bob).addCustomer(bill);
    	
    	String expected = "Customers: 2  Total Holdings: $100.00";
    	assertEquals(expected, bank.toString());
    }
}
