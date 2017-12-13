package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.abc.utils.TestingUtils;

public class BankTest {
    
    @Test
    public void newCustomerShallAssignNewCustomerName() throws IllegalAccessException, InstantiationException  {
        Bank bank = new Bank();
        
        Customer bill = bank.newCustomer("Bill");
        
        Assert.assertEquals("Bill", bill.getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void newCustomerShallThrowExceptionOnNullArgument() {
    	Bank bank = new Bank();
    	bank.newCustomer(null);
    }

    @Test
    public void getCustomersShallReturnCustomerCollection() throws IllegalAccessException, InstantiationException  {
        Bank bank = new Bank();
        
        Customer bill = bank.newCustomer("Bill");
        Customer ben = bank.newCustomer("Ben");
        
        Collection<Customer> customers = bank.getCustomers();
        
        Assert.assertTrue(customers.contains(bill));
        Assert.assertTrue(customers.contains(ben));
        Assert.assertEquals(2, customers.size());
    }
    
    @Test
    public void totalInterestPaidShallReturnZeroIfNoCustomers() {
    	Bank bank = new Bank();
    	
    	TestingUtils.assertEqualsBigDecimal(BigDecimal.ZERO, bank.totalInterestPaid());
    }

    @Test
    public void totalInterestPaidShallReturnZeroIfNoTransactions() {
    	Bank bank = new Bank();
    	bank.newCustomer("John");
    	bank.newCustomer("Mike");
    	
    	TestingUtils.assertEqualsBigDecimal(BigDecimal.ZERO, bank.totalInterestPaid());
    }
    
    @Test
    public void totalInterestPaidShallReturnZeroIfNoInterestAccrued() throws Exception {
    	Bank bank = new Bank();
    	bank.newCustomer("John").openAccount(SavingsAccount.class).deposit(new BigDecimal("100.00"));
    	bank.newCustomer("Mike").openAccount(SavingsAccount.class).deposit(new BigDecimal("190.00"));
    	
    	TestingUtils.assertEqualsBigDecimal(BigDecimal.ZERO, bank.totalInterestPaid());
    }
    
    @Test
    public void totalInterestPaidShallReturnCorrectValue() throws Exception {
    	Bank bank = new Bank();
    	Account accountJohn = bank.newCustomer("John").openAccount(CheckingAccount.class);
    	Account accountMike = bank.newCustomer("Mike").openAccount(CheckingAccount.class);
    	
    	TestingUtils.changePrivateField(
    			accountJohn.deposit(new BigDecimal("365000.00")),
    			"transactionDate",
    			LocalDate.now().minusDays(1));
    	
    	TestingUtils.changePrivateField(
    			accountMike.deposit(new BigDecimal("730000.00")),
    			"transactionDate",
    			LocalDate.now().minusDays(1));
    	
    	accountJohn.ensureInterestAccrued();
    	accountMike.ensureInterestAccrued();

    	TestingUtils.assertEqualsBigDecimal(new BigDecimal("30.00"), bank.totalInterestPaid());
    }

    @Test
    public void totalInterestPaidShallReturnValueUpToToday() throws Exception {
    	Bank bank = new Bank();
    	Account accountJohn = bank.newCustomer("John").openAccount(CheckingAccount.class);
    	Account accountMike = bank.newCustomer("Mike").openAccount(CheckingAccount.class);
    	
    	TestingUtils.changePrivateField(
    			accountJohn.deposit(new BigDecimal("365000.00")),
    			"transactionDate",
    			LocalDate.now().plusDays(365));
    	
    	TestingUtils.changePrivateField(
    			accountMike.deposit(new BigDecimal("730000.00")),
    			"transactionDate",
    			LocalDate.now().minusDays(1));
    	
    	accountJohn.ensureInterestAccrued();
    	accountMike.ensureInterestAccrued();

    	TestingUtils.assertEqualsBigDecimal(new BigDecimal("20.00"), bank.totalInterestPaid());
    }
}
