package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;
    private Customer john;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSaving;
    private Exception exception;

    @Test
    public void successfulCustomerSummary() {
        givenBank();

        whenCustomerOpensAccount();

        thenCustomerIsSuccessfullyAdded();
        thenNoExceptionsAreThrown();
    }

    @Test
    public void unsuccessfulCustomerSummary() {
        givenBank();

        whenCustomerUnableToOpenAccount();

        thenExceptionsAreThrown();
    }

    @Test
    public void checkingAccount() {
        givenBank();
        givenCheckingAccountIsCreated();
        givenCustomerOpensAccount(checkingAccount);

        whenMoneyIsDepositedIntoChecking();

        thenCorrectAmountOfInterest(0.1);
    }


    @Test
    public void savings_account() {
        givenBank();
        givenSavingAccountIsCreated();
        givenCustomerOpensAccount(savingsAccount);

        whenMoneyIsDepositedIntoSaving();

        thenCorrectAmountOfInterest(1.0);
    }


    @Test
    public void maxi_savings_accountNoWithdrawalBefore10Days() {
        givenBank();
        givenMaxiSavingAccountIsCreated();
        givenCustomerOpensAccount(maxiSaving);

        whenMoneyIsDepositedIntoMaxiSaving();

        thenCorrectAmountOfInterest(300.0);
    }

    private void whenMoneyIsDepositedIntoMaxiSaving() {
        maxiSaving.deposit(6000.0);
    }

    @Test
    public void maxi_savings_accountWithdrawalWithin10Days() {
        givenBank();
        givenMaxiSavingAccountIsCreated();
        givenCustomerOpensAccount(maxiSaving);

        whenMoneyIsDepositedIntoMaxiSavingWithWithdrawal();

        thenCorrectAmountOfInterest(2.80);
    }

    //TODO: Test case for getting first customer

    private void givenBank() {
        bank = new Bank();
    }

    private void givenCheckingAccountIsCreated() {
        checkingAccount = new Account(Account.CHECKING);
    }

    private void givenMaxiSavingAccountIsCreated() {
        maxiSaving = new Account(Account.MAXI_SAVINGS);
    }

    private void givenSavingAccountIsCreated() {
        savingsAccount = new Account(Account.SAVINGS);
    }

    private void givenCustomerOpensAccount(Account accountType) {
        bank.addCustomer(new Customer("Bill").openAccount(accountType));
    }

    private void whenCustomerOpensAccount() {
        try {
            john = new Customer("John").openAccount(new Account(Account.CHECKING));
            bank.addCustomer(john);
        } catch (Exception e) {
            exception = e;
            // do nothing further
        }
    }

    private void whenCustomerUnableToOpenAccount() {
        try {
            Customer john = null;
            john.openAccount(new Account(Account.CHECKING));
            bank.addCustomer(john);
        } catch (Exception e) {
            exception = e;
            // do nothing as we don't want test classes to throw exceptions
        }
    }

    private void whenMoneyIsDepositedIntoChecking() {
        checkingAccount.deposit(100.0);
    }

    private void whenMoneyIsDepositedIntoSaving() {
        savingsAccount.deposit(1500.0);
    }
    private void whenMoneyIsDepositedIntoMaxiSavingWithWithdrawal() {
        maxiSaving.deposit(3000.0);
        maxiSaving.withdraw(200.0);
    }

    private void thenNoExceptionsAreThrown() {
        assertThat(exception).isNull();
    }

    private void thenExceptionsAreThrown() {
        assertThat(exception).isNotNull();
    }

    private void thenCustomerIsSuccessfullyAdded() {
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    private void thenCorrectAmountOfInterest(Double interest) {
        assertEquals(interest, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
