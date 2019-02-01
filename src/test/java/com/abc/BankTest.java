package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-10;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.addAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    // Testing interest earned for individual accounts

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill");
        bill.addAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals((100 * 0.001) / 365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.addAccount(checkingAccount);

        checkingAccount.deposit(1500.0);

        assertEquals( ((1000 * 0.001) + (500 *0.002)) / 365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bill.addAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(3000.0);

        assertEquals((3000 * 0.05) / 365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    // Testing interest earned for multiple customers with multiple accounts

    @Test
    public void MultipleCustomersAndAccountInterestEarnedTest(){
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.addAccount(checkingAccount);
        bill.addAccount(savingAccount);
        bill.addAccount(maxiAccount);

        checkingAccount.deposit(1000.0);
        maxiAccount.deposit(1000);
        savingAccount.deposit(1000);

        Account checkingAccount2 = new Account(Account.CHECKING);
        Account savingAccount2 = new Account(Account.SAVINGS);
        Account maxiAccount2 = new Account(Account.MAXI_SAVINGS);
        Customer bill2 = new Customer("Bill");
        bank.addCustomer(bill2);
        bill2.addAccount(checkingAccount2);
        bill2.addAccount(savingAccount2);
        bill2.addAccount(maxiAccount2);

        checkingAccount2.deposit(500.0);
        maxiAccount2.deposit(500);
        savingAccount2.deposit(500);

        double interest1 = ((1000 * 0.05) + (1000 * 0.001) + (1000 * 0.001)) / 365;
        double interest2 = ((500 * 0.05) + (500 * 0.001) + (500 * 0.001)) / 365;
        double actualInterest = interest2 + interest1;
        assertEquals(actualInterest, bank.totalInterestPaid(), DOUBLE_DELTA);

    }
}
