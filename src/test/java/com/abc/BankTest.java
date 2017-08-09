package com.abc;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void addCustomer_Duplicate() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);
        bank.addCustomer(john);
        
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    
    @Test
    public void getFirstCustomerTests() {
        Bank bank = new Bank();
        
        assertEquals("None", bank.getFirstCustomer());
        
        Customer john = new Customer("John");
        Customer jane = new Customer("Jane");
        bank.addCustomer(john);
        bank.addCustomer(jane);

        assertEquals("John", bank.getFirstCustomer());
    }
    
    @Test
    public void totalInterestPaidTest(){
    	Bank bank = new Bank();
    	
    	assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    	
        Customer john = new Customer("John");
        Customer jane = new Customer("Jane");
        bank.addCustomer(john);
        bank.addCustomer(jane);
        
        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        Account acc1 = Mockito.mock(Account.class);
        Mockito.when(acc1.interestEarned()).thenReturn(38.00);
        
        Account acc2 = Mockito.mock(Account.class);
        Mockito.when(acc2.interestEarned()).thenReturn(38.00);
        
        Account acc3 = Mockito.mock(Account.class);
        Mockito.when(acc3.interestEarned()).thenReturn(38.00);
        
        Account acc4 = Mockito.mock(Account.class);
        Mockito.when(acc4.interestEarned()).thenReturn(38.00);
        
        Account acc5 = Mockito.mock(Account.class);
        Mockito.when(acc5.interestEarned()).thenReturn(38.00);
        
        john.openAccount(acc1);
        john.openAccount(acc2);
        jane.openAccount(acc3);
        jane.openAccount(acc4);
        jane.openAccount(acc5);
        
        assertEquals(5*38, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
