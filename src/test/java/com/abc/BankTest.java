package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(AccountType.CHECKING);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerMultipleAccountSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(AccountType.CHECKING);
        john.openAccount(AccountType.MAXI_SAVINGS);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void totalInterestPaidTest(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);
        int accountNo =  john.openAccount(AccountType.CHECKING);
        john.getAccount(accountNo).deposit(500.0);

        Customer mike = new Customer("Mike");
        bank.addCustomer(mike);
        accountNo =  mike.openAccount(AccountType.CHECKING);
        mike.getAccount(accountNo).deposit(400.0);
        mike.getAccount(accountNo).withdraw(100.0);

        assertEquals(0.8, bank.totalInterestPaid(), DOUBLE_DELTA);

    }

}
