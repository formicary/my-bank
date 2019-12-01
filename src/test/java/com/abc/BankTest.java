package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;
    Bank bank = new Bank();

    @Test
    public void shouldReportCustomerListAndAccountSummary() {
        Customer john = new Customer("John");
        Customer jane = new Customer("Jane");

        bank.addCustomer(john);
        bank.accountSetup(new CheckingAccount(), john);
        bank.accountSetup(new CheckingAccount(), john);

        bank.addCustomer(jane);
        bank.accountSetup(new CheckingAccount(), jane);

        assertFalse("Customer Summary\n - John (1 account)" == bank.customerSummary());
        assertEquals("Customer Summary\n - John (2 accounts)\n - Jane (1 account)", bank.customerSummary());
    }

    @Test
    public void checkBankTotalInterestsOfFlatRate() {
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bank.accountSetup(checkingAccount, bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertFalse(300.0 == bank.totalInterestPaid());
    }

    @Test
    public void checkBankTotalInterestsForSavingsAccount() {
        Account savingsAccount = new SavingsAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bank.accountSetup(savingsAccount, bill);
        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertFalse(300.0 == bank.totalInterestPaid());
    }

    @Test
    public void checkBankTotalInterestsForMaxiSavingsAccount() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bank.accountSetup(maxiSavingsAccount, bill);
        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertFalse(300.0 == bank.totalInterestPaid());
    }

    @Test
    public void checkTotalInterestPaid() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Account maxiSavingsAccount2 = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill");
        Customer harry = new Customer("Harry");
        bank.addCustomer(bill);
        bank.accountSetup(maxiSavingsAccount, bill);
        bank.addCustomer(harry);
        bank.accountSetup(maxiSavingsAccount2, harry);

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount2.deposit(3000.0);

        assertEquals(340.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertFalse(300.0 == bank.totalInterestPaid());
    }
}
