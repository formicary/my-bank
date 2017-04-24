package com.abc;

import org.junit.Test;

import com.abc.implementation.Bank;
import com.abc.interfaces.ICustomer;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        ICustomer john = mock(ICustomer.class);
        when(john.getNumberOfAccounts()).thenReturn(1);
        when(john.getName()).thenReturn("John");
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void totalInterestPaid()
    {
    	Bank bank = new Bank();
    	ICustomer bill = mock(ICustomer.class);
        when(bill.totalInterestEarned()).thenReturn(50.0);
        ICustomer josh = mock(ICustomer.class);
        when(josh.totalInterestEarned()).thenReturn(100.0);
        bank.addCustomer(bill);
        bank.addCustomer(josh);
        
        assertEquals(150.0, bank.totalInterestPaid(),DOUBLE_DELTA);
    }

}
