package com.abc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCustomerStatement() {
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();

        Customer oscarJohnson = new Customer("Oscar", "Johnson");
        oscarJohnson.openAccount(checkingAccount);
        oscarJohnson.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Oscar Johnson:\n\n" +
                "1: Checking Account\n" +
                "\tdeposit $100.00\n" +
                "\n\tTotal: $100.00\n\n" +
                "2: Savings Account\n" +
                "\tdeposit $4,000.00\n" +
                "\twithdrawal $200.00\n" +
                "\n\tTotal: $3,800.00\n\n" +
                "Total In All Accounts: $3,900.00", oscarJohnson.getStatement());
    }

    @Test
    public void testAccountNumber() {
        Customer oscarJohnson = new Customer("Oscar", "Johnson");
        oscarJohnson.openAccount(new SavingsAccount());
        oscarJohnson.openAccount(new CheckingAccount());
        oscarJohnson.openAccount(new SavingsAccount());
        assertEquals(3, oscarJohnson.getNumberOfAccounts());
    }

    @Test
    public void transferBetweenAccounts() {
        Customer oscarJohnson = new Customer("Oscar", "Johnson");
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        savingsAccount.deposit(250.0);
        checkingAccount.deposit(100.0);

        oscarJohnson.openAccount(savingsAccount);
        oscarJohnson.openAccount(checkingAccount);

        oscarJohnson.transferBetweenAccounts(1, 2, 50.0);
        String balances = "Savings: " + Utils.toDollars(savingsAccount.currentBalance()) +
                ", Current: " + Utils.toDollars(checkingAccount.currentBalance());

        assertEquals("Savings: $200.00, Current: $150.00", balances);
    }

    @Test
    public void transferBetweenAccountsInvalidAmount() {
        Customer oscarJohnson = new Customer("Oscar", "Johnson");
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        savingsAccount.deposit(250.0);
        checkingAccount.deposit(100.0);

        oscarJohnson.openAccount(savingsAccount);
        oscarJohnson.openAccount(checkingAccount);

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid withdrawal amount; " +
                "must be a positive value and funds " +
                "must be available in withdrawal account");
        oscarJohnson.transferBetweenAccounts(2, 1, 200.0);
    }

    @Test
    public void transferBetweenAccountsInvalidKey() {
        Customer oscarJohnson = new Customer("Oscar", "Johnson");
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();
        CheckingAccount checkingAccountTwo = new CheckingAccount();

        savingsAccount.deposit(250.0);
        checkingAccount.deposit(100.0);
        checkingAccountTwo.deposit(50.0);

        oscarJohnson.openAccount(savingsAccount);
        oscarJohnson.openAccount(checkingAccount);
        oscarJohnson.openAccount(checkingAccountTwo);

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid account key was provided");
        oscarJohnson.transferBetweenAccounts(1, 4, 200.0);
    }

    @Test
    public void totalInterestEarnedZeroAccounts() {
        Customer oscarJohnson = new Customer("Oscar", "Johnson");
        assertEquals(0.0, oscarJohnson.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestEarned() {
        Customer oscarJohnson = new Customer("Oscar", "Johnson");
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        savingsAccount.deposit(1300.0);
        checkingAccount.deposit(100.0);

        oscarJohnson.openAccount(savingsAccount);
        oscarJohnson.openAccount(checkingAccount);

        savingsAccount.addInterest();
        checkingAccount.addInterest();

        double savingsInterest = (1.0 / 365) + ((1300.0 - 1000) * (0.002 / 365));
        double checkingInterest = 100.0 * (0.001 / 365);

        assertEquals(savingsInterest + checkingInterest, oscarJohnson.totalInterestEarned(), DOUBLE_DELTA);
    }
}
