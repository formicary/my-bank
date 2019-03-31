package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummaryOnePersonOneAccount() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummaryOnePersonTwoAccounts(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void customerSummaryTwoPeople(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        Customer bob = new Customer("Bob");
        bank.addCustomer(bob);

        assertEquals("Customer Summary\n - John (1 account)\n - Bob (0 accounts)", bank.customerSummary());
    }

    @Test
    public void totalInterestOneAccount(){
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(10000.0);
        checkingAccount.addInterest();

        assertEquals(0.03, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestTwoAccounts(){
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account secondCheckingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bill.openAccount(secondCheckingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(10000.0);
        checkingAccount.addInterest();

        secondCheckingAccount.deposit(10000.0);
        secondCheckingAccount.addInterest();

        assertEquals(0.06, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    public void totalInterestTwoCusomtersOneAccount(){
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account secondCheckingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        Customer bob = new Customer("Bob").openAccount(secondCheckingAccount);
        bank.addCustomer(bill);
        bank.addCustomer(bob);

        checkingAccount.deposit(10000.0);
        checkingAccount.addInterest();

        secondCheckingAccount.deposit(10000.0);
        secondCheckingAccount.addInterest();

        assertEquals(0.06, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
