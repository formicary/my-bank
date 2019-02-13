package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-2;
    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));

        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccountInterest() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Account checkingAccount = new Account(Account.CHECKING);

        john.openAccount(checkingAccount);
        bank.addCustomer(john);

        checkingAccount.deposit(5000.0);
        assertEquals(0.01, bank.totalInterestPaid(false), DOUBLE_DELTA);

        //Checking Works for year
        assertEquals(5.0, bank.totalInterestPaid(true), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccountInterest() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Account savingsAccount = new Account(Account.SAVINGS);

        bank.addCustomer(john);
        john.openAccount(savingsAccount);

        savingsAccount.deposit(3000.0);
        assertEquals(0.01, bank.totalInterestPaid(false), DOUBLE_DELTA);

        //Checking Works for year
        assertEquals(5.0, bank.totalInterestPaid(true), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccountInterest() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Account maxSaveAccount = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(john);
        john.openAccount(maxSaveAccount);

        maxSaveAccount.deposit(3000.0);
        assertEquals(0.41, bank.totalInterestPaid(false), DOUBLE_DELTA);

        //Checking Works for year
        assertEquals(153.8, bank.totalInterestPaid(true), DOUBLE_DELTA);

    }


    @Test
    public void testFirstCustomer(){
        Bank bank = new Bank();
        assertEquals("No Customers", bank.getFirstCustomer());

        Customer john = new Customer("John");
        bank.addCustomer(john);

        assertEquals("John", bank.getFirstCustomer());
    }
}
