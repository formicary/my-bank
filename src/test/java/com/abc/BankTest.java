package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
        
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savings = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(savings);
        bank.addCustomer(bill);

        savings.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxi_savings = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(maxi_savings);
        bank.addCustomer(bill);

        maxi_savings.deposit(3000.0);
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        maxi_savings.withdraw(1000);
        assertEquals(2, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
