package com.abc;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testSingleAccountCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.accountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void testMultipleAccountCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.accountType.CHECKING));
        john.openAccount(new Account(Account.accountType.SAVINGS));

        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccountInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccountLessOneThousandInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.accountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testSavingsAccountMoreOneThousandInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.accountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccountLessOneThousandInterest() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.accountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(100.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testMaxiSavingsAccountLessTwoThousandInterest() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.accountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(1500.0);

        assertEquals(45.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void testMaxiSavingsAccountMoreTwoThousandInterest() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.accountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testFirstCustomer() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("Bill").openAccount(new Account(Account.accountType.SAVINGS)));
        bank.addCustomer(new Customer("Tom").openAccount(new Account(Account.accountType.SAVINGS)));
        bank.addCustomer(new Customer("Fred").openAccount(new Account(Account.accountType.SAVINGS)));

        assertEquals("Bill", bank.getFirstCustomer());
    }
    
    @Test
    public void testFirstCustomerWithNoCustomers() {
        Bank bank = new Bank();

        assertEquals("Error", bank.getFirstCustomer());
    }

}
