package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(100, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(1500, savingsAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(3000, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void addingInterestTest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bob").openAccount(checkingAccount));

        checkingAccount.deposit(100.0);
        assertEquals(100.0, checkingAccount.getBalance(), DOUBLE_DELTA);

        bank.dailyInterest();
        assertEquals(100.00027777777778, checkingAccount.getBalance(), DOUBLE_DELTA);

        bank.dailyInterest();
        assertEquals(100.00055555632717, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void annualInterestTest() {
        Bank bank = new Bank();
        Customer customer = new Customer("Bob");
        Account account = new Account(Account.CHECKING);
        bank.addCustomer(customer);
        customer.openAccount(account);

        account.deposit(100.0);

        for (int i = 0; i < 360; i++) {
            bank.dailyInterest();
        }

        assertEquals(0.1, Math.round(bank.totalInterestPaid()*10)/10.0, DOUBLE_DELTA);
    }

    @Test
    public void noCustomerCheck() {
        Bank bank = new Bank();
        assertTrue("No Customers Found".matches(bank.getFirstCustomer()));
    }

}
