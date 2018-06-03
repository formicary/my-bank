package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_change_date() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        checkingAccount.deposit(2000);
        checkingAccount.deposit(1000);

        cal.setTime(checkingAccount.transactions.get(0).getTransactionDate());
        cal.add(Calendar.DATE, -360);

        checkingAccount.transactions.get(0).setTransactionDate(cal.getTime());

        assertEquals(100, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_change_date2() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();

        checkingAccount.deposit(2000);
        checkingAccount.withdraw(1000);

        cal.setTime(checkingAccount.transactions.get(0).getTransactionDate());
        cal.add(Calendar.DATE, -369);
        cal2.setTime(checkingAccount.transactions.get(1).getTransactionDate());
        cal2.add(Calendar.DATE, -9);

        checkingAccount.transactions.get(0).setTransactionDate(cal.getTime());
        checkingAccount.transactions.get(1).setTransactionDate(cal2.getTime());

        assertEquals(100.025, bank.totalInterestPaid(), DOUBLE_DELTA);
    }


    @Test
    public void checking_account_change_date() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        checkingAccount.deposit(5000.0);
        checkingAccount.deposit(0.1);

        cal.setTime(checkingAccount.transactions.get(0).getTransactionDate());
        cal.add(Calendar.DATE, -360);

        checkingAccount.transactions.get(0).setTransactionDate(cal.getTime());

        assertEquals(5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }


    @Test
    public void saving_account_change_date() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        GregorianCalendar cal3 = new GregorianCalendar();

        checkingAccount.deposit(900.0);
        checkingAccount.deposit(1100.0);
        checkingAccount.withdraw(1000.0);

        cal.setTime(checkingAccount.transactions.get(0).getTransactionDate());
        cal.add(Calendar.DATE, -720);
        cal2.setTime(checkingAccount.transactions.get(1).getTransactionDate());
        cal2.add(Calendar.DATE, -360);
        cal3.setTime(checkingAccount.transactions.get(2).getTransactionDate());
        cal3.add(Calendar.DATE, -180);

        System.out.println(cal.getTime());
        System.out.println(cal2.getTime());
        System.out.println(cal3.getTime());
        System.out.println(DateProvider.getInstance().now());

        checkingAccount.transactions.get(0).setTransactionDate(cal.getTime());
        checkingAccount.transactions.get(1).setTransactionDate(cal2.getTime());
        checkingAccount.transactions.get(2).setTransactionDate(cal3.getTime());

        assertEquals(3.4, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
