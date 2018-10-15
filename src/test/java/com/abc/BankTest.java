package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.junit.Ignore;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.accountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.accountType.SAVINGS);
        bank.addCustomer(new Customer("Henry").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

       assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //default interest rate if no withdrawal made 
    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.accountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Dave").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(4000.0);

        assertEquals(4.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    //test for checking high interest rate if a withdrawal made in the past 10 days
    @Test
    public void maxi_savings_account_10days_withdrawal() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.accountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Dave").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(4000.0);
        maxiSavingsAccount.withdraw(1000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    

}
