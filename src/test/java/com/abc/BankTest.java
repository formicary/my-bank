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
        john.openAccount(new AccountChecking());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new AccountChecking();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new AccountSavings();
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new AccountMaxiSavings();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(2000.0);

        assertEquals(100.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        maxiSavingsAccount.withdraw(1000.0);

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_dates() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new AccountMaxiSavings();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        Date longAgo = new Date(0);
        Date quiteLongAgo = new Date(86400); // Day after epoch

        Calendar cal = Calendar.getInstance();
        cal.setTime(DateProvider.INSTANCE.now());
        cal.add(Calendar.DAY_OF_MONTH, -1);

        Date yesterday = cal.getTime();

        maxiSavingsAccount.deposit(2100.0, longAgo);
        maxiSavingsAccount.withdraw(100.0, quiteLongAgo);

        assertEquals(100.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        maxiSavingsAccount.withdraw(1000.0, yesterday);

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test(expected=IllegalArgumentException.class)
    public void negative_deposit(){
        Bank bank = new Bank();
        Account checkingAccount = new AccountChecking();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(-100.0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void negative_withdrawal(){
        Bank bank = new Bank();
        Account checkingAccount = new AccountChecking();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.withdraw(-100.0);
    }

}
