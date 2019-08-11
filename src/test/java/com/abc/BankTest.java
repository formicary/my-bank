package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
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
        bank.accrueInterestDaily();

        //expected daily interest for this checking account would be;
        //deposit = 100
        //daily interest applied = 0.001(yearly) / 365(days) = 2.73972602739726e-6‬
        //deposit * daily interest applied = 2.7397260273972606E-4
        double testNum = 100 + 2.7397260273972606E-4;
        assertEquals(testNum, bill.getAccounts().get(bill.getNumberOfAccounts() - 1).sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(1500.0);
        bank.accrueInterestDaily();

        //expected daily interest for this savings account would be;
        //deposit = 1500
        //daily interest applied on $500 = 0.002(yearly) / 365(days) = 5.479452054794521e-6‬
        //daily interest applied on $1000 = 0.001(yearly) / 365(days) = 2.73972602739726e-6‬
        // $500 * daily interest applied = 0.0027397260273973‬
        //$1000 * daily interest applied = 0.0027397260273973‬
        //total interest added = 0.0054794520547945‬
        double testNum = 1500.0054794520547945;
        assertEquals(1500.0054794520547945, bill.getAccounts().get(bill.getNumberOfAccounts() - 1).sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);
        maxiSavingsAccount.deposit(3000.0);
        bank.accrueInterestDaily();


        //expected daily interest for this savings account would be;
        //deposit = 3000
        //deposit was made today, so there has been a "withdrawal in the past 10 days",
        // => the interest rate today will be form the 0.1% annual rate
        // daily rate = 0.001 / 365 days = 2.73972602739726e-6‬
        // interest added = 2.73972602739726e-6‬ * 3000
        // = 0.0082191780821918‬

        //assertEquals(3000.0082191780821918‬, bill.getAccounts().get(bill.getNumberOfAccounts() - 1).sumTransactions(), DOUBLE_DELTA);
        double testNum = 3000.0082191780821918;
        assertEquals(testNum, bill.getAccounts().get(bill.getNumberOfAccounts() - 1).sumTransactions(), DOUBLE_DELTA);
    }

}
