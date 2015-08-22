package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Calendar;


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
        

        assertEquals(100*((1+0.001/365)^365), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(1500.0);
        

        assertEquals(1000*((1+0.001/365)^365)+500*((1+0.002/365)^365), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxi_savings_Account = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxi_savings_Account));

        maxi_savings_Account.deposit(3000.0);

        assertEquals(3000*((1+0.001/365)^10)*((1+0.05/365)^355), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
