package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-10;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new AccountChecking());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());

        Customer emi = new Customer("Emi");
        emi.openAccount(new AccountChecking());
        emi.openAccount(new AccountSavings());
        bank.addCustomer(emi);

        assertEquals("Customer Summary\n" +
                " - John (1 account)" +
                "\n" +
                " - Emi (2 accounts)", bank.customerSummary());

        Bank bankEmpty = new Bank();

        assertEquals("No accounts in the bank.", bankEmpty.customerSummary());
    }

    @Test
    public void interestPaid() {
        Bank bank = new Bank();
        Account accountCheckingBill = new AccountChecking();
        Customer bill = new Customer("Bill");
        bill.openAccount(accountCheckingBill);
        Customer emi = new Customer("Emi");
        Account accountCheckingEmi = new AccountChecking();
        emi.openAccount(accountCheckingEmi);
        bank.addCustomer(bill);
        bank.addCustomer(emi);

        accountCheckingBill.deposit(100.0, DateProvider.getNow(1, 1, 13, 30));
        accountCheckingBill.deposit(50, DateProvider.getNow(1, 4, 17, 2));
        accountCheckingEmi.deposit(1000, DateProvider.getNow(2, 1, 13, 0));
        accountCheckingEmi.deposit(50, DateProvider.getNow(2, 3, 14, 0));
        assertEquals(0.0030137069, bank.totalInterestPaid(), DOUBLE_DELTA);


    }



}
