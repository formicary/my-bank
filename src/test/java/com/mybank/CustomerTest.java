package com.mybank;

import com.mybank.Accounts.Account;
import com.mybank.Accounts.CheckingAccount;
import com.mybank.Accounts.MaxiSavingsAccount;
import com.mybank.Accounts.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSaving;
    private Customer henry;
    private Customer oscar;
    private Customer layla;

    private Exception exception;

    @Test //Test customer statement generation
    public void testApp() {
        givenBankAccounts();
        givenAccountOpening();

        whenTransactionsArePerformed();

        thenStatementsAreProduced();
    }

    @Test
    public void testOneAccount() {
        whenOneAccountOpened();

        thenCheckNoOfAccounts(1);
    }

    @Test
    public void testTwoAccount() {
        whenTwoAccountsOpened();

        thenCheckNoOfAccounts(2);

    }

    @Test
    public void testThreeAccounts() {
        whenThreeAccountsOpened();

        thenCheckNoOfAccounts(3);
    }

    @Test
    public void testAccountTransferringFromCheckingToSaving() {
        givenBankAccountsForCustomer();
        whenThreeAccountsOpenedForCustomer();
        givenDeposit();

        whenTransferringBetweenCheckingToSaving(100.0);

        thenAmountIsSuccessfullyTransferred();

    }

    @Test
    public void testAccountTransferNotEnoughBalance() {
        givenBankAccountsForCustomer();
        whenThreeAccountsOpenedForCustomer();
        givenDeposit();

        whenTransferringBetweenCheckingToSaving(500.0);

        thenExceptionIsThrown();
    }

    private void givenDeposit() {
        checkingAccount.deposit(100.0);
    }

    private void givenAccountOpening() {
        henry = new Customer("Henry")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount);
    }

    private void givenBankAccounts() {
        checkingAccount = new CheckingAccount();
        savingsAccount = new SavingsAccount();
    }

    private void givenBankAccountsForCustomer() {
        checkingAccount = new CheckingAccount();
        savingsAccount = new SavingsAccount();
        maxiSaving = new MaxiSavingsAccount();
    }

    private void whenTransferringBetweenCheckingToSaving(Double amount) {
        try {
            layla.transferBetweenAccounts(checkingAccount, savingsAccount, amount);
        } catch (Exception e) {
            exception = e;
        }
    }

    private void whenThreeAccountsOpenedForCustomer() {
        layla = new Customer("Layla")
                .openAccount(savingsAccount)
                .openAccount(checkingAccount)
                .openAccount(maxiSaving);
    }

    private void whenTransactionsArePerformed() {
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
    }

    private void whenOneAccountOpened() {
        oscar = new Customer("Oscar").openAccount(new SavingsAccount());
    }

    private void whenTwoAccountsOpened() {
        oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount())
                .openAccount(new CheckingAccount());
    }

    private void whenThreeAccountsOpened() {
        oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount())
                .openAccount(new CheckingAccount())
                .openAccount(new MaxiSavingsAccount());
    }

    private void thenStatementsAreProduced() {
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    private void thenCheckNoOfAccounts(int noOfAccounts) {
        assertEquals(noOfAccounts, oscar.getNumberOfAccounts());
    }

    private void thenExceptionIsThrown() {
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    private void thenAmountIsSuccessfullyTransferred() {
        assertThat(savingsAccount.sumTransactions()).isEqualTo(100.0);
    }
}
