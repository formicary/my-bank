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
     * same type to be created for the same customer.
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
        Account savingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(new BigDecimal(1500));

        assertEquals(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    /**
     * The test is for the maxi saving account. This test was created mainly to test the outcome
     * of the interest rate calculation when the period of time for interest accruing is zero.
     */
    public void maxi_savings_account1() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(new BigDecimal(42000));
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(maxiAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, 0);

        maxiAccount.getTransactions().get(0).setTransactionDate(cal.getTime());

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
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        GregorianCalendar cal = new GregorianCalendar();
        maxiAccount.deposit(new BigDecimal(20000));
        maxiAccount.deposit(new BigDecimal(1000));

        cal.setTime(maxiAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -360);

        maxiAccount.getTransactions().get(0).setTransactionDate(cal.getTime());

        assertEquals(new BigDecimal(1025.35).setScale(2, BigDecimal.ROUND_HALF_UP), bank.totalInterestPaid());
    }

    @Test
    /**
     * The test is for the maxi saving account. The aim is to test the outcome when two
     * consecutive transactions are more than 10 days apart from each other.
     * The first transaction is deposited 1000000 dollars 11 days ago from now.
     * THe second transaction is withdrawal of 1 dollars now.
     * Half-even rounding mode is chosen.
     */
    public void maxi_savings_account_change_date2() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();

        maxiAccount.deposit(new BigDecimal(1000000));
        maxiAccount.withdraw(new BigDecimal(1));

        cal.setTime(maxiAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -11);
        cal2.setTime(maxiAccount.getTransactions().get(1).getTransactionDate());
        cal2.add(Calendar.DATE, -0);

        maxiAccount.getTransactions().get(0).setTransactionDate(cal.getTime());
        maxiAccount.getTransactions().get(1).setTransactionDate(cal2.getTime());

        assertEquals(new BigDecimal(1528.84).setScale(2, BigDecimal.ROUND_HALF_EVEN), bank.totalInterestPaid());
    }

    @Test
    /**
     * The test is for the maxi saving account. The aim is to test the outcome when two
     * consecutive transactions are within 10 or equal to 10 days.
     * The first transaction is deposited 1000000 dollars 11 days ago from now.
     * THe second transaction is withdrawal of 1 dollars 1 day ago from now.
     * Half-even rounding mode is chosen.
     */
    public void maxi_savings_account_change_date3() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();

        maxiAccount.deposit(new BigDecimal(1000000));
        maxiAccount.withdraw(new BigDecimal(1));

        cal.setTime(maxiAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -11);
        cal2.setTime(maxiAccount.getTransactions().get(1).getTransactionDate());
        cal2.add(Calendar.DATE, -1);

        maxiAccount.getTransactions().get(0).setTransactionDate(cal.getTime());
        maxiAccount.getTransactions().get(1).setTransactionDate(cal2.getTime());

        assertEquals(new BigDecimal(30.56).setScale(2, BigDecimal.ROUND_HALF_EVEN), bank.totalInterestPaid());
    }

    @Test
    /**
     * The test is for the checking saving account.
     * The first transaction is deposited 42000 dollars 360 days ago from now.
     * THe second transaction is deposited now.
     * Half-even rounding mode is chosen.
     */
    public void checking_account_change_date() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        checkingAccount.deposit(new BigDecimal(42000.0));
        checkingAccount.deposit(new BigDecimal(0.1));

        cal.setTime(checkingAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -360);

        checkingAccount.getTransactions().get(0).setTransactionDate(cal.getTime());

        assertEquals(new BigDecimal(42.02).setScale(2, BigDecimal.ROUND_HALF_EVEN), bank.totalInterestPaid());
    }

    @Test
    /**
     * The test is for the saving saving account.
     * The first transaction is deposited 900 dollars 720 days ago from now.
     * The second transaction is deposited 360 days ago from now.
     * The third transaction is withdrawal of 1100 dollars 180 days ago from now.
     * Half-even rounding mode is chosen.
     */
    public void saving_account_change_date() {
        Bank bank = new Bank();
        Account savingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        GregorianCalendar cal3 = new GregorianCalendar();

        savingAccount.deposit(new BigDecimal(900));
        savingAccount.deposit(new BigDecimal(1100));
        savingAccount.withdraw(new BigDecimal(1100));

        cal.setTime(savingAccount.getTransactions().get(0).getTransactionDate());
        cal.add(Calendar.DATE, -720);
        cal2.setTime(savingAccount.getTransactions().get(1).getTransactionDate());
        cal2.add(Calendar.DATE, -360);
        cal3.setTime(savingAccount.getTransactions().get(2).getTransactionDate());
        cal3.add(Calendar.DATE, -180);

        savingAccount.getTransactions().get(0).setTransactionDate(cal.getTime());
        savingAccount.getTransactions().get(1).setTransactionDate(cal2.getTime());
        savingAccount.getTransactions().get(2).setTransactionDate(cal3.getTime());

        assertEquals(new BigDecimal(3.35).setScale(2, BigDecimal.ROUND_HALF_EVEN), bank.totalInterestPaid());
    }

}
