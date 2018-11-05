package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test                                                                       // TESTS NEED TO BE IN PLACE TO RUN IF NO MAIN FUNCTION.
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        try {
            checkingAccount.deposit(100.0);
        }
        catch (InvalidAmountException iae) {
            System.out.println(iae.getMessage());
        }

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);   // DETERMINES WHETHER OUR TESTS PASS!
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        try {
            checkingAccount.deposit(1500.0);
        }
        catch (InvalidAmountException iae) {
            System.out.println(iae.getMessage());
        }

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        try {
            checkingAccount.deposit(3000.0);
        }
        catch (InvalidAmountException iae) {
            System.out.println(iae.getMessage());
        }

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
