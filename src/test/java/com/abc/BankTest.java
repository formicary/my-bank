package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.text.DateFormat;

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

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        //old maxi_savings
        //assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertEquals(3, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_interest(){

        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Chungus").openAccount(checkingAccount));
        checkingAccount.deposit(100);

        //get present time
        DateProvider dp = new DateProvider();
        long t = dp.now().getTime();

        //construct past dates
        Date dateMinus5 = new Date(dp.now().getTime()-(5*24*60*60*1000));
        Date dateMinus15 = new Date(dp.now().getTime()-(15*24*60*60*1000));

        //set lastWithdraw to 5 days
        checkingAccount.setLastWithdraw(dateMinus5);

        assertEquals(0.1, bank.totalInterestPaid(),DOUBLE_DELTA);

        //set lastWithdraw to 15 days
        checkingAccount.setLastWithdraw(dateMinus15);

        assertEquals(0.5, bank.totalInterestPaid(),DOUBLE_DELTA);

    }

}
