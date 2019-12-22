package com.abc;

import org.junit.Test;

import java.util.Date;

import static com.abc.Utils.DOUBLE_DELTA;
import static com.abc.Utils.getDate;
import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void accountDeposit(){
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        Date now = DateProvider.getInstance().now();

        checkingAccount.deposit(100, now);

        assertEquals(100, checkingAccount.getBalance(now), DOUBLE_DELTA);

        checkingAccount.deposit(123.4, now);

        assertEquals(223.4, checkingAccount.getBalance(now), DOUBLE_DELTA);
    }

    @Test
    public void accountWithdrawal(){
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);
        Date now = DateProvider.getInstance().now();

        checkingAccount.deposit(100, now);
        checkingAccount.withdraw(50, now);
        assertEquals(50, checkingAccount.getBalance(now), DOUBLE_DELTA);

        checkingAccount.withdraw(60.5, DateProvider.getInstance().now());

        assertEquals(-10.5, checkingAccount.getBalance(now), DOUBLE_DELTA);
    }

    @Test
    public void checkingAccountYearDailyCompound() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/12/2018");

        checkingAccount.deposit(100.0, fromDate);

        assertEquals(0.1, checkingAccount.getCompoundInterest(toDate), DOUBLE_DELTA);
        assertEquals(100.1, checkingAccount.getBalance(toDate), DOUBLE_DELTA);
    }

    @Test
    public void checkingAccount6MonthsDailyCompound() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/6/2018");

        checkingAccount.deposit(100.0, fromDate);

        assertEquals(0.0496, checkingAccount.getCompoundInterest(toDate), DOUBLE_DELTA);
        assertEquals(100.0496, checkingAccount.getBalance(toDate), DOUBLE_DELTA);
    }

    @Test
    public void checkingAccountInterestAfterWithdrawal() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/6/2018");

        checkingAccount.deposit(100.0, fromDate);

        Date withdrawDate = getDate("21/6/2019");
        checkingAccount.withdraw(50, withdrawDate);

        assertEquals(0.1497, checkingAccount.getCompoundInterest(withdrawDate), DOUBLE_DELTA);
        assertEquals(50.1497, checkingAccount.getBalance(withdrawDate), DOUBLE_DELTA);

        //Interest after 6 months
        assertEquals(50.1748, checkingAccount.getBalance(getDate("21/12/2019")), DOUBLE_DELTA);

    }

    @Test
    public void savingsAccountYearDailyCompound() {
        Account checkingAccount = new Account(AccountType.SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/12/2018");

        checkingAccount.deposit(1000.0, fromDate);

        assertEquals(1.0004, checkingAccount.getCompoundInterest(toDate), DOUBLE_DELTA);
        assertEquals(1001.0004, checkingAccount.getBalance(toDate), DOUBLE_DELTA);
    }


    @Test
    public void savingsAccountYearMoreThanThousand() {
        Account checkingAccount = new Account(AccountType.SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/12/2018");

        checkingAccount.deposit(2000.0, fromDate);

        assertEquals(3.0024, checkingAccount.getCompoundInterest(toDate), DOUBLE_DELTA);
        assertEquals(2003.0024, checkingAccount.getBalance(toDate), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount6MonthsDailyCompound() {
        Account checkingAccount = new Account(AccountType.SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/6/2018");

        checkingAccount.deposit(2000.0, fromDate);

        assertEquals(1.4882, checkingAccount.getCompoundInterest(toDate), DOUBLE_DELTA);
        assertEquals(2001.4882, checkingAccount.getBalance(toDate), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountInterestAfterWithdrawal() {
        Account checkingAccount = new Account(AccountType.SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/6/2018");

        checkingAccount.deposit(2000.0, fromDate);

        Date withdrawDate = getDate("21/6/2019");
        checkingAccount.withdraw(1500, withdrawDate);

        assertEquals(4.4932, checkingAccount.getCompoundInterest(withdrawDate), DOUBLE_DELTA);
        assertEquals(504.4932, checkingAccount.getBalance(withdrawDate), DOUBLE_DELTA);

        //Interest after 6 months
        assertEquals(504.9993, checkingAccount.getBalance(getDate("21/12/2019")), DOUBLE_DELTA);

    }

    @Test
    public void maxSavingsAccountYearDailyCompound() {
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/12/2018");

        checkingAccount.deposit(1000.0, fromDate);

        assertEquals(51.2674, checkingAccount.getCompoundInterest(toDate), DOUBLE_DELTA);
        assertEquals(1051.2674, checkingAccount.getBalance(toDate), DOUBLE_DELTA);
    }

    @Test
    public void maxSavingsAccount6MonthsDailyCompound() {
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/6/2018");

        checkingAccount.deposit(2000.0, fromDate);

        assertEquals(50.2054, checkingAccount.getCompoundInterest(toDate), DOUBLE_DELTA);
        assertEquals(2050.2054, checkingAccount.getBalance(toDate), DOUBLE_DELTA);
    }

    @Test
    public void maxSavingsAccountInterestAfterWithdrawal() {
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/6/2018");

        checkingAccount.deposit(2000.0, fromDate);

        Date withdrawDate = getDate("21/6/2019");
        checkingAccount.withdraw(1500, withdrawDate);

        assertEquals(155.3143, checkingAccount.getCompoundInterest(withdrawDate), DOUBLE_DELTA);
        assertEquals(655.3143, checkingAccount.getBalance(withdrawDate), DOUBLE_DELTA);

        Date soonWithdrawDate = getDate("26/6/2019");
        checkingAccount.withdraw(300, soonWithdrawDate);

        //Interest after 5 days, now the interest is 0.1%
        assertEquals(355.7633, checkingAccount.getBalance(soonWithdrawDate), DOUBLE_DELTA);

        assertEquals(364.5441, checkingAccount.getBalance(getDate("21/12/2019")), DOUBLE_DELTA);
    }

    @Test
    public void maxSavingsAccountMoreWithdrawals() {
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");

        checkingAccount.deposit(1000.0, fromDate);

        Date withdrawDate = getDate("21/12/2018");
        checkingAccount.withdraw(900, withdrawDate);

        assertEquals(51.2674, checkingAccount.getCompoundInterest(withdrawDate), DOUBLE_DELTA);
        assertEquals(151.2674, checkingAccount.getBalance(withdrawDate), DOUBLE_DELTA);

        Date soonWithdrawDate = getDate("24/12/2018");
        checkingAccount.withdraw(300, soonWithdrawDate);

        //No interest amounting when negative balance
        assertEquals(-148.6703, checkingAccount.getBalance(soonWithdrawDate), DOUBLE_DELTA);

        checkingAccount.deposit(300, getDate("26/12/2018"));
        checkingAccount.withdraw(100, getDate("29/12/2018"));
        assertEquals(51.3989, checkingAccount.getBalance(getDate("30/12/2018")), DOUBLE_DELTA);
    }

    @Test
    public void checkingAccount3Months() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);
        bank.addCustomer(customer);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/12/2018");

        checkingAccount.deposit(100.0, fromDate);

        assertEquals(0.1, bank.totalInterestPaid(toDate), DOUBLE_DELTA);

        //Test
    }

    @Test
    public void checkingAccountWeeklyInterest(){
        Account checkingAccount = new Account(AccountType.CHECKING, CompoundInterestPeriod.WEEKLY);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/12/2018");

        checkingAccount.deposit(100.0, fromDate);

        assertEquals(0.1, checkingAccount.getCompoundInterest(toDate), DOUBLE_DELTA);
        assertEquals(100.1, checkingAccount.getBalance(toDate), DOUBLE_DELTA);
    }


    @Test
    public void TestSimpleInterest(){
        Account checkingAccount = new Account(AccountType.CHECKING, CompoundInterestPeriod.WEEKLY);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);

        //Set the period for which the interest is earned
        Date fromDate = getDate("21/12/2017");
        Date toDate = getDate("21/12/2018");

        checkingAccount.deposit(100.0, fromDate);

        assertEquals(0.1, checkingAccount.simpleInterestEarned(1), DOUBLE_DELTA);
    }


}
