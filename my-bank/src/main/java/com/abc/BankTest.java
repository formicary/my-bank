package com.abc;

import org.junit.Test;
import static org.junit.Assert.*;



public class BankTest {
    @Test
    public void bank() {
        Bank bank = new Bank(0);
        Customer john = new Customer(0,"John Smith");
        bank.addCustomer(john);

        assertEquals(bank.getFirstCustomer(),"John Smith");
        assertEquals(bank.totalInterestPaid(),0.0);

        Account acc = new Account(0);
        john.openAccount(acc);
        acc.deposit(1000);

        assertEquals(bank.totalInterestPaid(),1.0);

        
    }

}