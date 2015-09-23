package com.abc;

import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummary2Accounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        john.openAccount(new SavingsAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void customerSummary2() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);
        Customer bob = new Customer("Bob");
        bob.openAccount(new CheckingAccount());
        bank.addCustomer(bob);

        assertEquals("Customer Summary\n - John (1 account)\n - Bob (1 account)", bank.customerSummary());
    }


    @Test
    public void customerName() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("John", bank.getFirstCustomer());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account_a() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account_b() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }


    @Test
    public void maxi_savings_account_new() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_new_withdrawal() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        checkingAccount.withdraw(1000.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void total_interest() {
        Bank bank = new Bank();
        Account checkingAccount1 = new SavingsAccount();
        bank.addCustomer(new Customer("John").openAccount(checkingAccount1));
        checkingAccount1.deposit(1000.0);

        Account checkingAccount2 = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount2));
        checkingAccount2.deposit(1500.0);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}