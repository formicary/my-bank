package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-10;

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
        DateProvider.getInstance().advanceDate(5);
        checkingAccount.compounding();

        assertEquals(0.00136893288945, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);
        DateProvider.getInstance().advanceDate(23);
        checkingAccount.withdraw(200.0);
        DateProvider.getInstance().advanceDate(12);
        checkingAccount.deposit(3000.0);
        DateProvider.getInstance().advanceDate(17);
        checkingAccount.compounding();
        
        assertEquals(0.5322877532681201, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        DateProvider.getInstance().advanceDate(7);
        checkingAccount.withdraw(300.0);
        DateProvider.getInstance().advanceDate(12);
        checkingAccount.compounding();
        
        assertEquals(3.690003820418042, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
