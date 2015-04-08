package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.Type.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummary_multiple(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.Type.CHECKING));
        bank.addCustomer(john);
        Customer james = new Customer("James");
        james.openAccount(new Account(Account.Type.CHECKING));
        bank.addCustomer(james);

        assertEquals("Customer Summary" +
                "\n - John (1 account)" +
                "\n - James (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummary_multiple_second(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.Type.CHECKING));
        john.openAccount(new Account(Account.Type.SAVINGS));
        bank.addCustomer(john);
        Customer james = new Customer("James");
        james.openAccount(new Account(Account.Type.CHECKING));
        james.openAccount(new Account(Account.Type.CHECKING));
        james.openAccount(new Account(Account.Type.MAXI_SAVINGS));
        bank.addCustomer(james);

        assertEquals("Customer Summary" +
                "\n - John (2 accounts)" +
                "\n - James (3 accounts)", bank.customerSummary());
    }

    @Test
    public void customerSummaryHead(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer jack = new Customer("Jack");
        john.openAccount(new Account(Account.Type.CHECKING));
        john.openAccount(new Account(Account.Type.MAXI_SAVINGS));
        jack.openAccount(new Account(Account.Type.CHECKING));
        bank.addCustomer(john);
        bank.addCustomer(jack);

        assertEquals("John", bank.getFirstCustomer());
    }


    // NB: these test cases have fractional values due to assertEquals's accuracy measurements, making solid testing
    // values diffcult to ascertain.

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.Type.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals((0.1/365), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.Type.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals((1+1.0/365), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxi_account = new Account(Account.Type.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxi_account));

        maxi_account.deposit(1000000);
        maxi_account.transactions.get(0).setTime(new Date(100, 12, 25));

        assertEquals(1000000*(0.05/365), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_multiple(){
        Bank bank = new Bank();
        Account maxi_account = new Account(Account.Type.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxi_account));

        maxi_account.deposit(1000000);
        maxi_account.transactions.get(0).setTime(new Date(100, 12, 25));
        maxi_account.deposit(1000000);

        assertEquals(2000000*(0.05/365), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_withdraw(){
        Bank bank = new Bank();
        Account maxi_account = new Account(Account.Type.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxi_account));

        maxi_account.deposit(2000000);
        maxi_account.transactions.get(0).setTime(new Date(100, 12, 25));
        maxi_account.withdraw(1000000);

        assertEquals(1000000 * (0.001 / 365), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
