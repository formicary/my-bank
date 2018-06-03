package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * This class is for testing the methods in the Bank class.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version  03/05/2018
 */
public class BankTest {

    @Test
    /**
     * This test is created to test if the program allows two accounts of the
     * same type to be created for same customer.
     * In this program, it is designed that such operation is allowed.
     */
    public void customerSummary1() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.SAVINGS));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    /**
     * This test is created to test if the program allows two accounts of
     * different types to be created for same customer.
     */
    public void customerSummary2() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    /**
     * This test is created to test if the output is singular when
     * the customer only has one account.
     */
    public void customerSummary3() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    /**
     * The test is for the checking account. This test was created mainly to test the outcome
     * of the interest rate calculation when the period of time for interest accruing is zero.
     */
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(new BigDecimal(100));

        assertEquals(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    /**
     * The test is for the saving account. This test was created mainly to test the outcome
     * of the interest rate calculation when the period of time for interest accruing is zero.
     */
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(new BigDecimal(1500));

        assertEquals(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    /**
     * The test is for the maxi saving account. This test was created mainly to test the outcome
     * of the interest rate calculation when the period of time for interest accruing is zero.
     */
    public void maxi_savings_account1() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(new BigDecimal(3000));

        assertEquals(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    /**
     * The test is for the maxi saving account.
     * The first transaction is deposited 2000 dollars 360 days ago from now.
     * THe second transaction is deposited now.
     */
    public void maxi_savings_account2() {
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
    /**
     * The test is for the maxi saving account.
     * The first transaction is deposited 2000 dollars 369 days ago from now.
     * THe second transaction is withdrawal of 1000 dollars 9 days ago from now.
     * Half-even rounding mode is chosen.
     */
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

        assertEquals(new BigDecimal(100.02).setScale(2, BigDecimal.ROUND_HALF_EVEN), bank.totalInterestPaid());
    }


    @Test
    /**
     * The test is for the checking saving account.
     * The first transaction is deposited 5000 dollars 360 days ago from now.
     * THe second transaction is deposited now.
     * Half-even rounding mode is chosen.
     */
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

        assertEquals(new BigDecimal(5).setScale(2, BigDecimal.ROUND_HALF_EVEN), bank.totalInterestPaid());
    }


    @Test
    /**
     * The test is for the saving saving account.
     * The first transaction is deposited 900 dollars 720 days ago from now.
     * The second transaction is deposited 360 days ago from now.
     * The third transaction is withdrawal of 1000 dollars 180 days ago from now.
     * Half-even rounding mode is chosen.
     */
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

        assertEquals(new BigDecimal(3.4).setScale(2, BigDecimal.ROUND_HALF_EVEN), bank.totalInterestPaid());
    }

}
