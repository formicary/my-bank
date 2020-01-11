package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.abc.accounttypes.*;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Bank bank = new Bank();
    private Customer bill = new Customer("Bill");

    @Test
    public void customerSummary() {
        bill.openAccount(new Account(new CheckingAccount()));
        bank.addCustomer(bill);

        assertEquals("Customer Summary\n - Bill (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Account checkingAccount = new Account(new CheckingAccount());
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Account savingsAccount = new Account(new SavingsAccount());
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Account checkingAccount = new Account(new MaxiSavingsAccount());
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Ignore
    public void getFirstCustomer(){
        
    }

}
