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
        john.openAccount(new Account(Account.AccountType.CHECKING));
        bank.addCustomer(john);
        Customer bob = new Customer("Bob");
        bob.openAccount(new Account(Account.AccountType.CHECKING));
        bob.openAccount(new Account(Account.AccountType.CHECKING));
        bank.addCustomer(bob);
        bank.addCustomer(new Customer("Jim"));

        assertEquals("Customer Summary\n - John (1 account)\n - Bob (2 accounts)\n - Jim (0 accounts)", bank.customerSummary());
    }
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        Customer bob = new Customer("Bob");
        Account checkingAccount1 = new Account(Account.AccountType.CHECKING);
        Account checkingAccount2 = new Account(Account.AccountType.CHECKING);
        bob.openAccount(checkingAccount1);
        bob.openAccount(checkingAccount2);
        bank.addCustomer(bob);

        bill.deposit(100, checkingAccount);
        bob.deposit(101230, checkingAccount1);
        bob.deposit(0.01, checkingAccount2);
        assertTrue(bank.totalInterestPaid() == 0);
        // For reasons shown in the account test, the bank pays nothing because all deposits payed on the same day.

    }
}
