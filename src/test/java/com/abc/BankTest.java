package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class BankTest {

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

        checkingAccount.deposit(new BigDecimal(100));

        assertEquals(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(new BigDecimal(1500));

        assertEquals(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(new BigDecimal(3000));

        assertEquals(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    public void maxi_savings_account_change_date() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        checkingAccount.deposit(new BigDecimal(2000));
        checkingAccount.deposit(new BigDecimal(1000));

        cal.setTime(checkingAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -360);

        checkingAccount.getTransactions().get(0).setTransactionDate(cal.getTime());

        assertEquals(new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    public void maxi_savings_account_change_date2() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();

        checkingAccount.deposit(new BigDecimal(2000));
        checkingAccount.withdraw(new BigDecimal(1000));

        cal.setTime(checkingAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -369);
        cal2.setTime(checkingAccount.getTransactions().get(1).getTransactionDate());
        cal2.add(Calendar.DATE, -9);

        checkingAccount.getTransactions().get(0).setTransactionDate(cal.getTime());
        checkingAccount.getTransactions().get(1).setTransactionDate(cal2.getTime());

        assertEquals(new BigDecimal(100.02).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }


    @Test
    public void checking_account_change_date() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        checkingAccount.deposit(new BigDecimal(5000.0));
        checkingAccount.deposit(new BigDecimal(0.1));

        cal.setTime(checkingAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -360);

        checkingAccount.getTransactions().get(0).setTransactionDate(cal.getTime());

        assertEquals(new BigDecimal(5).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }


    @Test
    public void saving_account_change_date() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        GregorianCalendar cal3 = new GregorianCalendar();

        checkingAccount.deposit(new BigDecimal(900));
        checkingAccount.deposit(new BigDecimal(1100));
        checkingAccount.withdraw(new BigDecimal(1000));

        cal.setTime(checkingAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -720);
        cal2.setTime(checkingAccount.getTransactions().get(1).getTransactionDate());
        cal2.add(Calendar.DATE, -360);
        cal3.setTime(checkingAccount.getTransactions().get(2).getTransactionDate());
        cal3.add(Calendar.DATE, -180);

        checkingAccount.getTransactions().get(0).setTransactionDate(cal.getTime());
        checkingAccount.getTransactions().get(1).setTransactionDate(cal2.getTime());
        checkingAccount.getTransactions().get(2).setTransactionDate(cal3.getTime());

        assertEquals(new BigDecimal(3.4).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

}
