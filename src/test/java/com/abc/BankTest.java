package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Account checkingAccount = new CheckingAccount();
        john.openAccount(checkingAccount);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(100.0);

        assertEquals(0.02, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new SavingsAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openAccount(savingsAccount);
        savingsAccount.deposit(1500.0);

        assertEquals(0.54, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxisavingsAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount(maxisavingsAccount);
        bank.addCustomer(bill);

        maxisavingsAccount.deposit(3000.0);

        assertEquals(41.97, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void firstCustomer(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer alice = new Customer("Alice");
        bank.addCustomer(john);
        bank.addCustomer(alice);

        assertEquals("John", bank.getFirstCustomer());
    }

    @Test
    public void nofirstCustomer(){
        Bank bank = new Bank();

        assertEquals("No Customers have joined the bank yet.", bank.getFirstCustomer());
    }

    @Test
    public void removeCustomer(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer alice = new Customer("Alice");
        bank.addCustomer(john);
        bank.addCustomer(alice);
        bank.removeCustomer(john);

        assertEquals("Alice", bank.getFirstCustomer());
    }
}
