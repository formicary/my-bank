package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.CheckingAccount;
import com.abc.accounts.MaxiSavingsAccount;
import com.abc.accounts.SavingsAccount;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -1);
        date = c.getTime();

        System.out.println(date);
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(10000.0, date);

        assertEquals(10.004987954684111, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -1);
        date = c.getTime();

        Account savingsAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1000.0, date);

        assertEquals(2.2710250423102707, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountNoWd() {
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -1);
        c.add(Calendar.DATE, -10);
        date = c.getTime();

        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0, date);
        maxiSavingsAccount.deposit(3000.0, date);

        System.out.println("Date = " + date);
        assertEquals(307.7777920305598, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccounWithWd() {
        Date date = new Date();
        Bank bank = new Bank();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //c.add(Calendar.DATE, -8);
        c.add(Calendar.YEAR, -1);
        c.add(Calendar.DATE, -10);
        date = c.getTime();

        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0, date);
        c.add(Calendar.YEAR, 1);
        c.add(Calendar.DATE, 10);
        c.add(Calendar.DATE, -8);
        date = c.getTime();
        maxiSavingsAccount.deposit(3000.0 ,date);

        System.out.println("Date = " + date);
        assertEquals(150.5612950558725, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
