package com.abc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Customer henry;
    private Account savingsAccount;
    private Account checkingAccount;

    @Before
    public void createCustomer() {
        henry = new Customer("Henry");


        savingsAccount = new Account(AccountType.SAVINGS);
        checkingAccount = new Account(AccountType.CHECKING);

        savingsAccount.deposit(1000);
        checkingAccount.deposit(1000);

        henry.openAccount(savingsAccount);
        henry.openAccount(checkingAccount);
    }

    @Test
    public void customerSummaryIsCorrect() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);
        bank.addCustomer(henry);

        Assert.assertEquals("Customer Summary\n - John (1 account)\n - Henry (2 accounts)", bank.customerSummary());
    }

    @Test
    public void totalInterestPaidIsCorrect() {
        Bank bank = new Bank();
        Account johnAccount = new Account(AccountType.CHECKING);
        Customer john = new Customer("John");
        john.openAccount(johnAccount);
        bank.addCustomer(john);
        bank.addCustomer(henry);

        johnAccount.deposit(100);

        Assert.assertEquals(2.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
