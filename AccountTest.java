package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountTest {

    private Bank bank;
    private Customer customer;
    private Account account;

    @org.junit.Before
    public void setup() {
        bank = new Bank();
        customer = new Customer("FirstName LastName");
        bank.getCustomers().add(customer);
        account = new CheckingAccount();
        customer.getAccounts().add(account);
    }

    // deposit of negative amount
    @Test
    public void deposit_negativeAmount() {
        Assert.assertFalse(account.deposit(-1d));
        Assert.assertEquals(0, account.getTransactions().size(),0);
    }

    // deposit of amount = 0
    @Test
    public void deposit_zeroAmount() {
        Assert.assertFalse(account.deposit(0d));
        Assert.assertEquals(0, account.getTransactions().size(),0);
    }

    // deposit of positive amount
    @Test
    public void deposit_positiveAmount() {
        Assert.assertTrue(account.deposit(1d));
        Assert.assertEquals(1d, account.getTransactions().get(0).getAmount(),0);
        Assert.assertEquals(1, account.getTransactions().size(),0);
    }

    // withdrawal of amount = 0
    @Test
    public void withdraw_zeroAmount() {
        Assert.assertFalse(account.withdraw(0d));
        Assert.assertEquals(0, account.getTransactions().size(),0);
    }

    // withdrawal of negative amount, sufficient money
    @Test
    public void withdraw_negativeAmount_sufficientMoney() {
        account.getTransactions().add(new Transaction(1d, LocalDate.now()));
        Assert.assertEquals(1, account.getTransactions().size(),0);
        Assert.assertTrue(account.withdraw(-1d));
        Assert.assertEquals(-1d, account.getTransactions().get(1).getAmount(),0);
        Assert.assertEquals(2, account.getTransactions().size(),0);
    }

    // withdrawal of positive amount, sufficient money
    @Test
    public void withdraw_positiveAmount_sufficientMoney() {
        account.getTransactions().add(new Transaction(1d, LocalDate.now()));
        Assert.assertEquals(1, account.getTransactions().size(),0);
        Assert.assertTrue(account.withdraw(1d));
        Assert.assertEquals(-1d, account.getTransactions().get(1).getAmount(),0);
        Assert.assertEquals(2, account.getTransactions().size(),0);
    }

    // withdrawal of negative amount, insufficient money
    @Test
    public void withdraw_negativeAmount_insufficientMoney() {
        account.getTransactions().add(new Transaction(1d, LocalDate.now()));
        Assert.assertEquals(1, account.getTransactions().size(),0);
        Assert.assertFalse(account.withdraw(-2d));
        Assert.assertEquals(1, account.getTransactions().size(),0);
    }

    // withdrawal of positive amount, insufficient money
    @Test
    public void withdraw_positiveAmount_insufficientMoney() {
        account.getTransactions().add(new Transaction(1d, LocalDate.now()));
        Assert.assertEquals(1, account.getTransactions().size(),0);
        Assert.assertFalse(account.withdraw(2d));
        Assert.assertEquals(1, account.getTransactions().size(),0);
    }

    // current balance, no transactions made so far
    @Test
    public void makeTotal_noTransactionsSoFar() {
        Assert.assertEquals(0d, account.makeTotal(),0);
    }

    // current balance with no interest earned so far, one deposit made
    @Test
    public void makeTotal_noInterestEarnedSoFar_oneTransaction() {
        account.getTransactions().add(new Transaction(1d, LocalDate.now()));
        Assert.assertEquals(1d, account.makeTotal(),0);
    }

    // current balance with no interest earned so far, one deposit and one withdrawal made
    // this is an indirect test of private method sumCustomersTransactions()
    @Test
    public void makeTotal_noInterestEarnedSoFar_depositAndWithdrawal() {
        account.getTransactions().add(new Transaction(2d, LocalDate.now()));
        account.getTransactions().add(new Transaction(-1d, LocalDate.now()));
        Assert.assertEquals(1d, account.makeTotal(),0);
    }

    // all transactions as formatted String, no transactions so far
    @Test
    public void showAllTransactions_noTransactionsSoFar() {
        StringBuilder result = new StringBuilder("Account no.: 1");
        result.append("\n");
        result.append("Account type: Checking");
        result.append("\n");
        result.append("List of transactions:");
        result.append("\nNo transactions made on this account so far.");
        Assert.assertEquals(result.toString(), account.showAllTransactions());
    }

    // all transactions as formatted String, one deposit and one withdrawal made, no interest earnings
    @Test
    public void showAllTransactions_noInterestEarnings_depositAndWithdrawal() {
        account.getTransactions().add(new Transaction(2d, LocalDate.now()));
        account.getTransactions().add(new Transaction(-2d, LocalDate.now()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM yyyy");
        StringBuilder result = new StringBuilder("Account no.: 1");
        result.append("\n");
        result.append("Account type: Checking");
        result.append("\n");
        result.append("List of transactions:");
        result.append("\ndeposit of 2.0 on "+LocalDate.now().format(formatter));
        result.append("\nwithdrawal of 2.0 on "+LocalDate.now().format(formatter));
        Assert.assertEquals(result.toString(), account.showAllTransactions());
    }

    // all transactions as formatted String, one deposit and interest earnings for one day
    @Test
    public void showAllTransactions_InterestEarnings_deposit() {
        account.getTransactions().add(new Transaction(1000d, LocalDate.now().minusDays(1)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM yyyy");
        StringBuilder result = new StringBuilder("Account no.: 1");
        result.append("\n");
        result.append("Account type: Checking");
        result.append("\n");
        result.append("List of transactions:");
        result.append("\ndeposit of 1000.0 on "+LocalDate.now().minusDays(1).format(formatter));
        result.append("\ndeposit of "+(1000d*(0.001/(LocalDate.now().minusDays(1).isLeapYear() ? 366 : 365))+" on "+LocalDate.now().format(formatter)));
        Assert.assertEquals(result.toString(), account.showAllTransactions());
    }
}