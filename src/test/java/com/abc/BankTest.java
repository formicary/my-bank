package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


import java.math.BigDecimal;
import java.math.RoundingMode;

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
    public void getFirstCustomer() {
        Bank bank = new Bank();
        Bank emptyBank = new Bank();
        Customer john = new Customer("John");
        Customer sarah = new Customer("Sarah");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals(john.getName(), bank.getFirstCustomer());
        bank.addCustomer(sarah);
        assertEquals(john.getName(), bank.getFirstCustomer());
        assertEquals("No customer found", emptyBank.getFirstCustomer());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        System.out.println(bank.totalInterestPaid().toString());
        assertEquals(BigDecimal.valueOf(0.10).setScale(2, RoundingMode.HALF_EVEN), bank.totalInterestPaid());
        assertFalse(BigDecimal.valueOf(0.20).setScale(2, RoundingMode.HALF_EVEN) == bank.totalInterestPaid());
        assertNotNull("should not be null", checkingAccount);
        assertNotNull("should not be null", bank);
        assertNotNull("should not be null", bill);
        
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        System.out.println(bank.totalInterestPaid().toString());
        checkingAccount.deposit(1500);
        System.out.println(bank.totalInterestPaid());
        assertEquals(BigDecimal.valueOf(2.00).setScale(2, RoundingMode.HALF_EVEN), bank.totalInterestPaid());
     
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        System.out.println(bank.totalInterestPaid().toString());

        assertEquals(BigDecimal.valueOf(3.00).setScale(2, RoundingMode.HALF_EVEN), bank.totalInterestPaid());
    }

}
