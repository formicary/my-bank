package com.abc;

import org.junit.Test;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    /*
    Testing customer summary for a customer with one account.
    */
    @Test
    public void customerSummary1() {
        Bank bank = new Bank();
        Customer conor = new Customer("Conor");
        conor.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(conor);

        assertEquals("Customer Summary\n - Conor (1 account)", bank.customerSummary());
    }

    /*
    Testing customer summary for a customer with two accounts.
   */
    @Test
    public void customerSummary2() {
        Bank bank = new Bank();
        Customer conor = new Customer("Conor");
        conor.openAccount(new Account(Account.CHECKING));
        conor.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(conor);

        assertEquals("Customer Summary\n - Conor (2 accounts)", bank.customerSummary());
    }

    /*
    Testing interest accrued on a checking account.
    */
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer conor = new Customer("Conor").openAccount(checkingAccount);
        bank.addCustomer(conor);

        checkingAccount.deposit(100.0);

        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DATE, -4); //4 days ago
        checkingAccount.getLastTransaction().setDate(pastDate.getTime());//Set the date to a past date for testing purposes

        assertEquals(0.001095894914627138, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
    Testing interest accrued on a savings account with a starting balance below $1000.
    */
    @Test
    public void savings_account1() {
        Bank bank = new Bank();
        Account savingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Conor").openAccount(savingAccount));

        savingAccount.deposit(900.0);

        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DATE, -2); //2 days ago
        savingAccount.getLastTransaction().setDate(pastDate.getTime());//Set the date to a past date for testing purposes

        assertEquals(0.0049315136047880515, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
    Testing interest accrued on a savings account with a starting balance above $1000.
    */
    @Test
    public void savings_account2() {
        Bank bank = new Bank();
        Account savingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Conor").openAccount(savingAccount));

        savingAccount.deposit(1500.0);

        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DATE, -2); //2 days ago
        savingAccount.getLastTransaction().setDate(pastDate.getTime()); //Set the date to a past date for testing purposes

        assertEquals(0.010958934133896037, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
    Testing interest accrued on a maxi savings account with the last transaction less than 10 days ago.
    */
    @Test
    public void maxi_savings_account1() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Conor").openAccount(checkingAccount));

        checkingAccount.deposit(4000.0);

        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DATE, -4); //4 days ago
        checkingAccount.getLastTransaction().setDate(pastDate.getTime());//Set the date to a past date for testing purposes

        assertEquals(0.04383579658497183, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
    Testing interest accrued on a maxi savings account with the last transaction more than 10 days ago.
    */
    @Test
    public void maxi_savings_account2() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Conor").openAccount(checkingAccount));

        checkingAccount.deposit(4000.0);

        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DATE, -11); //11 days ago
        checkingAccount.getLastTransaction().setDate(pastDate.getTime());//Set the date to a past date for testing purposes

        assertEquals(6.031527311611171, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
