package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;


    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount("Checking Account"));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        CheckingAccount checkingAccount = new CheckingAccount("Checking Account");
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        SavingAccount savingAccount = new SavingAccount("Savings Account");
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        MaxiSavingAccount maxiSavingAccount = new MaxiSavingAccount("Maxi-Savings Account");
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingAccount));

        // total interest when no withdrawal
        maxiSavingAccount.deposit(3000.0);
        assertEquals(150, bank.totalInterestPaid(), DOUBLE_DELTA);

        // checking total interest paid when the last withdrawal happened on the same day
        maxiSavingAccount.withdraw(1000.0);
        assertEquals(20, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
