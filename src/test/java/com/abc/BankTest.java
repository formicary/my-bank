package com.abc;

import com.abc.account.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customers and their accounts are being accurately added to the bank's customers List and that the summary is correctly outputted
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        Customer ann = new Customer("Ann");
        ann.openAccount(new MaxiSavingsAccount());
        ann.openAccount(new CheckingAccount());
        ann.openAccount(new SavingsAccount());
        bank.addCustomer(ann);

        Customer phillip = new Customer("Phillip");
        bank.addCustomer(phillip);

        assertEquals("Customer Summary:\n - John (1 account)"
                  +  "\n - Ann (3 accounts)"
                  +  "\n - Phillip (0 accounts)", bank.customerSummary());
    }

    @Test //Test that the summary shows their being no customers
    public void testCustomerSummaryNoCustomers() {
        String emptySummary = new Bank().customerSummary();

        assertEquals("Customer Summary:\nThere are no customers.", emptySummary);
    }

    @Test //Test that the bank sums the interest payments to every customer correctly
    public void testTotalInterestPaid() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        CheckingAccount checkAcc = new CheckingAccount();
        john.openAccount(checkAcc);
        checkAcc.deposit(3000.0);
        Customer phillip = new Customer("Phillip");
        SavingsAccount savAcc = new SavingsAccount();
        phillip.openAccount(savAcc);
        savAcc.deposit(3000.0);
        bank.addCustomer(john);
        bank.addCustomer(phillip);

        assertEquals(Math.round(bank.totalInterestPaid()*100)/100.0, 12.01, DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidNoCustomers() {
        Bank bank = new Bank();

        assertEquals(bank.totalInterestPaid(), 0.0, DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidNoDeposits() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new MaxiSavingsAccount());

        assertEquals(bank.totalInterestPaid(), 0.0, DOUBLE_DELTA);
    }
}
