package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void oneCustomerWithZeroAccountsSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (0 accounts)", bank.customerSummary());
    }

    @Test
    public void oneCustomerWithOneAccountSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void oneCustomerWithTwoAccountsSummary() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.CHECKING));
        alice.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(alice);

        assertEquals("Customer Summary\n - Alice (2 accounts)", bank.customerSummary());
    }

    @Test
    public void twoCustomersWithOneAccountEachSummary() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(alice);

        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - Alice (1 account)\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void twoCustomersWithOneAccountAndTwoAccountsSummary() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(alice);

        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - Alice (1 account)\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void twoCustomersWithTwoAccountsEachSummary() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.CHECKING));
        alice.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(alice);

        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - Alice (2 accounts)\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void interestPaidOnNewBankIs0() {
        Bank bank = new Bank();
        assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void interestPaidOnBankWith1NewCustomerIs0() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("Alice"));
        assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void interestPaidOnBankWith1CustomerWith1NewAccountIs0() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(alice);
        assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void interestPaidOnBankWith1CustomerWith1AccountIsEqualToInterestOnAccount() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        Account account = new Account(Account.SAVINGS);
        alice.openAccount(account);
        account.deposit(1000);
        bank.addCustomer(alice);

        double accountInterest = account.interestEarned();
        assertEquals(accountInterest, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void interestPaidOnBankWith2CustomersWith2AccountsIsEqualToTotalInterestOnAccounts() {
        Bank bank = new Bank();
        double totalInterest = 0;

        Customer alice = new Customer("Alice");
        Account aliceAccount1 = new Account(Account.SAVINGS);
        alice.openAccount(aliceAccount1);
        aliceAccount1.deposit(5000);
        totalInterest += aliceAccount1.interestEarned();

        Account aliceAccount2 = new Account(Account.CHECKING);
        alice.openAccount(aliceAccount2);
        aliceAccount2.deposit(2000);
        totalInterest += aliceAccount2.interestEarned();

        bank.addCustomer(alice);

        Customer bob = new Customer("Bob");
        Account bobAccount1 = new Account(Account.SAVINGS);
        alice.openAccount(bobAccount1);
        bobAccount1.deposit(1000);
        totalInterest += bobAccount1.interestEarned();

        Account bobAccount2 = new Account(Account.MAXI_SAVINGS);
        alice.openAccount(bobAccount2);
        bobAccount2.deposit(5000);
        totalInterest += bobAccount2.interestEarned();

        bank.addCustomer(bob);

        double accountInterest = totalInterest;
        assertEquals(accountInterest, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
