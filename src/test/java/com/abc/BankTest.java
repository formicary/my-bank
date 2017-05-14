package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John").openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        Customer gray = new Customer("Gray").openAccount(new Account(Account.CHECKING));
        gray.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(gray);
        
        assertEquals("Customer Summary\n - John (1 account)"
        		+ "\n - Gray (2 accounts)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(3000.0);
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        maxiAccount.withdraw(1000.0);
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
