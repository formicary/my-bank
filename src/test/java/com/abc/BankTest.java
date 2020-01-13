package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class BankTest {
    public static final LocalDate START = LocalDate.of(2020,1,1);
    public static final LocalDate END = LocalDate.of(2020,1,12);

    @Test
    public void addCustomer() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        assertTrue(bank.getCustomers().contains(john));
    }

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void totalInterestPaid() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        Customer jeff = new Customer("Jeff").openAccount(savingsAccount);
        Customer harry = new Customer("Harry").openAccount(maxiSavingsAccount);

        bank.addCustomer(bill);
        bank.addCustomer(jeff);
        bank.addCustomer(harry);

        checkingAccount.deposit(20000.0, START);
        savingsAccount.deposit(20000.0, START);
        maxiSavingsAccount.deposit(20000.0, START);
        maxiSavingsAccount.withdraw(10000, LocalDate.of(2020,1,6));

        assertEquals(15.64, bank.totalInterestPaid(END), 0.01);
    }
}
