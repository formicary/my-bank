package com.abc;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 0.01;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void totalInterestPaid() throws NoSuchFieldException, IllegalAccessException {
        Bank bank = new Bank();
        Transaction t;
        Account account = new Account(Account.CHECKING);
        Account sAccount = new Account(Account.SAVINGS);
        Account mAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(account);
        bill.openAccount(sAccount);
        bill.openAccount(mAccount);
        bank.addCustomer(bill);
        t = account.deposit(100.0);
        final Field f = Transaction.class.getDeclaredField("transactionDate");
        f.setAccessible(true);
        f.set(t, DateProvider.getInstance().daysAgo(365));
        t = sAccount.deposit(1500.0);
        f.set(t, DateProvider.getInstance().daysAgo(365));
        t = mAccount.deposit(3000.0);
        f.set(t, DateProvider.getInstance().daysAgo(365));

        assertEquals(0.1 + 2 + 153.8, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
