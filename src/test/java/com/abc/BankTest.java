/*Edited by: Foyaz Hasnath*/
package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerOneAccountSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCustomerTwoAccountsSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    /*below tests use the concept of rolling a "business date"
     1. bank is created and bankOpeningDate & businessDate are set to current time
     2. accounts are created and money deposited
     3. businessDate is moved/rolled forward to some point in time
     4. total interest paid on all accounts until this date is calculated */
    @Test
    public void testCheckingAccountTotalInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100000.0);
        bank.rollBusinessDate(30);//simulate account having being opened for x days

        assertEquals(8.22, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccountTotalInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(100000.0);
        bank.rollBusinessDate(60);

        assertEquals(32.71, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccountTotalInterest() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(100000.0);
        bank.rollBusinessDate(60);

        assertEquals(1635.59, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
