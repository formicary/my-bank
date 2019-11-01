package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Requirements Tested:
 *  A bank manager can get a report showing the list of customers and how many accounts they have.
 *  A bank manager can get a report showing the total interest paid by the bank on all accounts.
 */
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;


    /**
     * Customer summary
     */
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
        
        Customer bill = new Customer("Bill");
        bill.openAccount(new Account(Account.CHECKING));
        bill.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(bill);
        
        assertEquals("Customer Summary\n - John (1 account)\n - Bill (2 accounts)", bank.customerSummary());
    }
    
    /**
     * Total interest
     */
    @Test
    public void totalInterest(){
        Bank bank = new Bank();
        Customer john = new Customer("John").openAccount(new Account(Account.CHECKING));
        john.getAccount(Account.CHECKING).deposit(1000);
        
        Customer bill = new Customer("Bill").openAccount(new Account(Account.CHECKING));
        john.getAccount(Account.CHECKING).deposit(5000);
        
        bank.addCustomer(john);     
        bank.addCustomer(bill);
        
        assertEquals(6.0, bank.totalInterestPaid(),DOUBLE_DELTA);
    }

}
