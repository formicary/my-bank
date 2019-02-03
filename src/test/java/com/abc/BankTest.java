package com.abc;

import org.junit.Test;

import com.abc.Account.ACCOUNT_TYPE;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

public class BankTest {
	
    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.addAccount(new Account(ACCOUNT_TYPE.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void testCustomerSummaryNoCustomers() {
        Bank bank = new Bank();

        assertEquals("Customer Summary", bank.customerSummary());
    }
    
    
    @Test
    public void testTotalAccountInterestOneCustomer() {
    	Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(ACCOUNT_TYPE.MAXI_SAVINGS);
        Account savingsAccount = new Account(ACCOUNT_TYPE.SAVINGS);
        Account checkingAccount = new Account(ACCOUNT_TYPE.CHECKING);
        Customer bill = new Customer("Bill");
        bill.addAccount(maxiSavingsAccount);
        bill.addAccount(savingsAccount);
        bill.addAccount(checkingAccount);
        maxiSavingsAccount.deposit(new BigDecimal("500"));
        savingsAccount.deposit(new BigDecimal("2000"));
        checkingAccount.deposit(new BigDecimal("5000"));
        bank.addCustomer(bill);
        
        assertEquals(bank.totalInterestPaid().compareTo(new BigDecimal("33")), 0);
    }
    
    @Test
    public void testTotalAccountInterestMultipleCustomers() {
    	Bank bank = new Bank();
    	
        Customer bill = new Customer("Bill");
        Account maxiSavingsAccount = new Account(ACCOUNT_TYPE.MAXI_SAVINGS);
        Account checkingAccount = new Account(ACCOUNT_TYPE.CHECKING);
        bill.addAccount(maxiSavingsAccount);
        bill.addAccount(checkingAccount);
        maxiSavingsAccount.deposit(new BigDecimal("500"));
        checkingAccount.deposit(new BigDecimal("5000"));
        bank.addCustomer(bill);
        
        Customer ben = new Customer("Ben");
        Account savingsAccount = new Account(ACCOUNT_TYPE.SAVINGS);
        ben.addAccount(savingsAccount);
        savingsAccount.deposit(new BigDecimal("50000"));
        bank.addCustomer(ben);

        assertEquals(bank.totalInterestPaid().compareTo(new BigDecimal("129")), 0);
    }
    
    @Test
    public void testTotalAccountInterestNoBalance() {
    	Bank bank = new Bank();
        Account account = new Account(ACCOUNT_TYPE.CHECKING);
        Customer bill = new Customer("Bill");
        bill.addAccount(account);
        bank.addCustomer(bill);
        
    	
        assertEquals(bank.totalInterestPaid().compareTo(new BigDecimal("0")), 0);
    }

}
