package com.accenture.mybank.tests;

import org.junit.Test;
import com.accenture.mybank.Bank;
import com.accenture.mybank.Customer;
import com.accenture.mybank.accounts.Account;
import com.accenture.mybank.accounts.CheckingAccount;
import com.accenture.mybank.accounts.MaxiSavingsAccount;
import com.accenture.mybank.accounts.SavingsAccount;
import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import java.util.Date;


public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer anusha = new Customer("Anusha");
        anusha.openAccount(new CheckingAccount());
        bank.addCustomer(anusha);
        assertEquals("Customer Summary\n - Anusha (1 account)", bank.getCustomerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -1);
        date = c.getTime();

        Account checkingAccount = new CheckingAccount();
        Customer anu = new Customer("Anu").openAccount(checkingAccount);
        bank.addCustomer(anu);

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
        bank.addCustomer(new Customer("Anu").openAccount(savingsAccount));

        savingsAccount.deposit(1000.0, date);
        assertEquals(1.9992506476781955, bank.totalInterestPaid(), DOUBLE_DELTA);
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
        bank.addCustomer(new Customer("Anu").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0, date);
        maxiSavingsAccount.deposit(3000.0, date);
        assertEquals(307.7777920305598, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccounWithWd() {
        Date date = new Date();
        Bank bank = new Bank();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -1);
        c.add(Calendar.DATE, -10);
        date = c.getTime();

        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Anu").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0, date);
        c.add(Calendar.YEAR, 1);
        c.add(Calendar.DATE, 10);
        c.add(Calendar.DATE, -8);
        date = c.getTime();
        maxiSavingsAccount.deposit(3000.0 ,date);
        assertEquals(150.5612950558725, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}