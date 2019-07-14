package com.abc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer johnSmith = new Customer("John", "Smith");
        johnSmith.openAccount(new CheckingAccount());
        bank.addCustomer(johnSmith);

        assertEquals("Customer Summary:\n - John Smith (1 account)", bank.customerSummary());
    }

    @Test
    public void totalInterestPaidZeroCustomers() {
        Bank bank = new Bank();
        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaid() {
        Bank bank = new Bank();

        Customer johnSmith = new Customer("John", "Smith");
        Customer henryJameson = new Customer("Henry", "Jameson");
        bank.addCustomer(johnSmith);
        bank.addCustomer(henryJameson);

        CheckingAccount checkingAccount = new CheckingAccount();
        johnSmith.openAccount(checkingAccount);
        checkingAccount.deposit(500.0);
        SavingsAccount savingsAccount = new SavingsAccount();
        henryJameson.openAccount(savingsAccount);
        savingsAccount.deposit(1300.0);

        checkingAccount.addInterest();
        savingsAccount.addInterest();

        double checkingInterest = 500.0 * (0.001 / 365);
        double savingsInterest = (1.0 / 365) + ((1300.0 - 1000) * (0.002 / 365));

        assertEquals(savingsInterest + checkingInterest, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void getFirstCustomerEmptyList() {
        Bank bank = new Bank();

        exceptionRule.expect(IndexOutOfBoundsException.class);
        exceptionRule.expectMessage("No customers found");
        bank.getFirstCustomer();
    }

    @Test
    public void getFirstCustomer() {
        Bank bank = new Bank();
        Customer johnSmith = new Customer("John", "Smith");

        bank.addCustomer(johnSmith);
        Customer firstCustomer = bank.getFirstCustomer();

        assertEquals("John Smith (0 accounts)", firstCustomer.toString());
    }
}
