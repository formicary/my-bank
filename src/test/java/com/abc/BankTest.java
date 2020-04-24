package com.abc;

import org.junit.Test;

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
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingAccount = new SavingAccount();
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(30000.0);
        maxiSavingsAccount.withdraw(200.0);
        maxiSavingsAccount.withdraw(300.0);
        maxiSavingsAccount.withdraw(400.0);
        maxiSavingsAccount.withdraw(500.0);
        maxiSavingsAccount.withdraw(600.0);
        maxiSavingsAccount.withdraw(700.0);
        maxiSavingsAccount.withdraw(800.0);
        maxiSavingsAccount.withdraw(900.0);
        maxiSavingsAccount.withdraw(1000.0);
        maxiSavingsAccount.withdraw(2000.0);

        assertEquals(2376.5206645268554, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
