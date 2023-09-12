package com.mybank.Accounts;


import com.mybank.Bank;
import com.mybank.Customer;
import com.mybank.Transaction;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {
    private Bank bank;
    private Account checkingAccount;
    private Account savingAccount;
    private Account maxiSaving;
    private Exception exception;

    @Test
    public void testDepositIntoChecking() {
        givenBank();
        givenCheckingAccountIsCreated();
        givenCustomerOpensAccount(checkingAccount);

        whenMoneyIsDepositedIntoChecking();

        thenMoneyIsDeposited(checkingAccount, 7000.0);

    }

    @Test
    public void testDepositIntoSaving() {
        givenBank();
        givenSavingAccountIsCreated();
        givenCustomerOpensAccount(savingAccount);

        whenMoneyIsDepositedIntoSaving();

        thenMoneyIsDeposited(savingAccount, 300.0);
    }

    @Test
    public void testDepositIntoMaxiSaving() {
        givenBank();
        givenMaxiSavingAccountIsCreated();
        givenCustomerOpensAccount(maxiSaving);

        whenMoneyIsDepositedIntoMaxiSaving();

        thenMoneyIsDeposited(maxiSaving, 90.0);
    }

    @Test
    public void testSuccessfulWithdrawalFromChecking() {
        givenBank();
        givenCheckingAccountIsCreated();
        givenCustomerOpensAccount(checkingAccount);

        whenMoneyIsDepositedIntoChecking();
        whenMoneyIsWithdrawnFromChecking(200);

        thenTotalBalanceIsCorrect(checkingAccount, 6800.0);
    }

    @Test
    public void testSuccessfulWithdrawalFromSavings() {
        givenBank();
        givenSavingAccountIsCreated();
        givenCustomerOpensAccount(savingAccount);

        whenMoneyIsDepositedIntoSaving();
        whenMoneyIsWithdrawnFromSaving();

        thenTotalBalanceIsCorrect(savingAccount, 250.0);
    }

    @Test
    public void testSuccessfulWithdrawalFromMaxiSavings() {
        givenBank();
        givenMaxiSavingAccountIsCreated();
        givenCustomerOpensAccount(maxiSaving);

        whenMoneyIsDepositedIntoMaxiSaving();
        whenMoneyIsWithdrawnFromMaxiSaving();

        thenTotalBalanceIsCorrect(maxiSaving, 0.0);
    }

    @Test
    public void testUnsuccessfulWithdrawalFromChecking() {
        givenBank();
        givenCheckingAccountIsCreated();
        givenCustomerOpensAccount(checkingAccount);

        whenMoneyIsDepositedIntoChecking();
        whenMoneyIsWithdrawnFromChecking(8000.0);

        thenErrorIsLogged(checkingAccount);
    }

    //TODO: UnsuccessfulWithdrawalFromSavings
    //TODO: UnsuccessfulWithdrawalFromMaxiSavings
    //TODO: Move interestCalculationTestsToAccountTest
    //TODO: test other methods to ensure complete code coverage


    private void givenCustomerOpensAccount(Account checkingAccount) {
        Customer customer = new Customer("Sandra");
        bank.addCustomer(customer);
        customer.openAccount(checkingAccount);
    }

    private void givenCheckingAccountIsCreated() {
        checkingAccount = new CheckingAccount();
    }

    private void givenSavingAccountIsCreated() {
        savingAccount = new SavingsAccount();
    }

    private void givenMaxiSavingAccountIsCreated() {
        maxiSaving = new MaxiSavingsAccount();
    }

    private void givenBank() {
        bank = new Bank();
    }

    private void whenMoneyIsDepositedIntoChecking() {
        checkingAccount.deposit(7000.0);
    }

    private void whenMoneyIsDepositedIntoSaving() {
        savingAccount.deposit(300.0);
    }

    private void whenMoneyIsDepositedIntoMaxiSaving() {
        maxiSaving.deposit(90.0);
    }

    private void whenMoneyIsWithdrawnFromChecking(double amount) {
        try {
            checkingAccount.withdraw(amount);
        } catch (Exception e) {
            exception = e;
        }
    }

    private void whenMoneyIsWithdrawnFromSaving() {
        savingAccount.withdraw(50);
    }

    private void whenMoneyIsWithdrawnFromMaxiSaving() {
        maxiSaving.withdraw(90);
    }

    private void thenMoneyIsDeposited(Account accountType, double expectedAmount) {
        assertThat(accountType.transactions).size().isEqualTo(1);

        for (Transaction transaction : accountType.transactions) {
            double amount = transaction.amount;
            assertThat(amount).isEqualTo(expectedAmount);
        }
    }

    private void thenTotalBalanceIsCorrect(Account accountType, double expectedAmount) {
        assertThat(accountType.transactions).size().isEqualTo(2);
        assertThat(accountType.sumTransactions()).isEqualTo(expectedAmount);
    }

    private void thenErrorIsLogged(Account accountType) {
        assertThat(accountType.transactions).size().isEqualTo(1);
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }
}