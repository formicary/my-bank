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
    public void totalInterestPaid() {
        Bank bank = new Bank();
        Account account = new Account(Account.CHECKING);
        Account sAccount = new Account(Account.SAVINGS);
        Account mAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(account);
        bill.openAccount(sAccount);
        bill.openAccount(mAccount);
        bank.addCustomer(bill);
        account.deposit(100.0);
        sAccount.deposit(1500.0);
        mAccount.deposit(3000.0);

        assertEquals(152.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
