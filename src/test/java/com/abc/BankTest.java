package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

       // assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void firstCustomer(){
        Bank bank = new Bank();
        Customer dan = new Customer("Dan").openAccount(new Account(Account.SAVINGS));
        Customer peter = new Customer("Peter").openAccount(new Account(Account.CHECKING));
        bank.addCustomer(dan);
        bank.addCustomer(peter);

        assertEquals("Dan", bank.getFirstCustomer());
    }

    @Test
    public void twocustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        Customer mark = new Customer("Mark");
        mark.openAccount(new Account(Account.CHECKING));
        mark.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(mark);

        assertEquals("Customer Summary\n - John (1 account)\n - Mark (2 accounts)", bank.customerSummary());
    }

    @Test
    public void InterestsInAccounts(){
        Bank bank = new Bank();
        Customer mark = new Customer("Mark");
        Account checkingAccount = new Account(Account.CHECKING);
        Account saveingAccount = new Account(Account.SAVINGS);
        mark.openAccount(checkingAccount);
        mark.openAccount(saveingAccount);
        bank.addCustomer(mark);

        checkingAccount.deposit(1000);
        saveingAccount.deposit(1500);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }


}
