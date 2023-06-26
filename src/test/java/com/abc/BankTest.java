package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void should_returnSummaryWithThreeAccounts_when_customerWithThreeAccountsAreAdded() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        john.openAccount(new SavingsAccount());
        john.openAccount(new MaxiSavingsAccount());
        bank.addCustomer(john);
        assertEquals("Customer Summary\n - John (3 accounts)", bank.customerSummary());
    }
    @Test
    public void should_throwError_when_noCustomerIsAdded() {
        Bank bank = new Bank();
        try {
            bank.customerSummary();
        } catch (Error e) {
            assertEquals(e.getMessage(), "there is no customer, get your salesman working");
        }
    }

    @Test
    public void should_returnCorrectTotalInterestAmount_when_twoAccountsAreOpened() {
        Bank bank = new Bank();
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);

        checkingAccount.transact(100.0);
        savingsAccount.transact(2000);
        assertEquals(3.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void should_returnInterestAsZero_when_noAccountIsOpened() {
        Bank bank = new Bank();
        assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
