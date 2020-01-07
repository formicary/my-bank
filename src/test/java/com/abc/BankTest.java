package com.abc;

import java.util.Calendar;
import java.util.Date;
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
        checkingAccount.deposit(100.0);

        checkingAccount.transactions.get(0).transactionDate.setMonth(Calendar.JANUARY);
        checkingAccount.transactions.get(1).transactionDate.setMonth(Calendar.MARCH);

        System.out.println(bank.totalInterestPaid());

        assertEquals(0.0175357, bank.totalInterestPaid(), 1e-5);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        checkingAccount.withdraw(500.0);

        // Testing for no date change, so should be zero interest
        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        final Date currentDate = DateProvider.getInstance().now();

        // Moving 10 days ahead...

        checkingAccount.deposit(500.0);
        checkingAccount.transactions.get(2).transactionDate.setDate(currentDate.getDate()+10);

        assertEquals(0.02465778, bank.totalInterestPaid(), 1e-5);


    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        checkingAccount.withdraw(1000.0);

        final Date currentDate = DateProvider.getInstance().now();

        checkingAccount.transactions.get(1).transactionDate.setDate(currentDate.getDate()+2);

        assertEquals(0.0328768, bank.totalInterestPaid(), 1e-5);

        checkingAccount.withdraw(500.0);
        checkingAccount.transactions.get(2).transactionDate.setDate(currentDate.getDate()+11);

        assertEquals(1.123528, bank.totalInterestPaid(), 1e-5);

    }

}
