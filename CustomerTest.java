package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class CustomerTest {
    private Bank bank;
    private Customer customer;
    private Account account;

    @org.junit.Before
    public void setup() {
        bank = new Bank();
        customer = new Customer("FirstName LastName");
        bank.getCustomers().add(customer);
    }

    // open account, invalid account type
    @Test
    public void openAccount_invalidAccountType() {
        Assert.assertEquals(0, customer.getAccounts().size(),0);
        Assert.assertNull(customer.openAccount(4));
        Assert.assertEquals(0, customer.getAccounts().size(),0);
    }

    // open account, Checking account, first customer's account
    @Test
    public void openAccount_Checking() {
        Assert.assertEquals(0, customer.getAccounts().size(),0);
        Assert.assertEquals("Checking", customer.openAccount(1).getType());
        Assert.assertEquals(1, customer.getAccounts().size(),0);
        Assert.assertEquals(1, customer.getAccounts().get(0).getNumber());
        Assert.assertEquals(0, customer.getAccounts().get(0).getTransactions().size());
    }

    // open account, Savings account, first customer's account
    @Test
    public void openAccount_Savings() {
        Assert.assertEquals(0, customer.getAccounts().size(),0);
        Assert.assertEquals("Savings", customer.openAccount(2).getType());
        Assert.assertEquals(1, customer.getAccounts().size(),0);
        Assert.assertEquals(1, customer.getAccounts().get(0).getNumber());
        Assert.assertEquals(0, customer.getAccounts().get(0).getTransactions().size());
    }

    // open account, Maxi-Savings account, first customer's account
    @Test
    public void openAccount_MaxiSavings() {
        Assert.assertEquals(0, customer.getAccounts().size(),0);
        Assert.assertEquals("Maxi-Savings", customer.openAccount(3).getType());
        Assert.assertEquals(1, customer.getAccounts().size(),0);
        Assert.assertEquals(1, customer.getAccounts().get(0).getNumber());
        Assert.assertEquals(0, customer.getAccounts().get(0).getTransactions().size());
    }

    // deposit on non-existing account
    @Test
    public void deposit_nonExistingAccount() {
        customer.getAccounts().add(new CheckingAccount());
        Assert.assertEquals(0, customer.getAccounts().get(0).getTransactions().size(),0);
        Assert.assertFalse(customer.deposit(2, 100d));
        Assert.assertEquals(0, customer.getAccounts().get(0).getTransactions().size());
    }

    // deposit on existing account
    @Test
    public void deposit_existingAccount() {
        customer.getAccounts().add(new CheckingAccount());
        Assert.assertEquals(0, customer.getAccounts().get(0).getTransactions().size(),0);
        Assert.assertTrue(customer.deposit(1, 100d));
        Assert.assertEquals(1, customer.getAccounts().get(0).getTransactions().size(),0);
        Assert.assertEquals(100d, customer.getAccounts().get(0).getTransactions().get(0).getAmount(),0);
    }

    // withdrawal on non-existing account
    @Test
    public void withdraw_nonExistingAccount() {
        customer.getAccounts().add(new CheckingAccount());
        customer.deposit(1,100d);
        Assert.assertEquals(1, customer.getAccounts().get(0).getTransactions().size(),0);
        Assert.assertFalse(customer.withdraw(2, 100d));
        Assert.assertEquals(1, customer.getAccounts().get(0).getTransactions().size());
    }

    // withdrawal on existing account
    @Test
    public void withdraw_existingAccount() {
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(0).getTransactions().add(new Transaction(100d));
        Assert.assertEquals(1, customer.getAccounts().get(0).getTransactions().size(),0);
        Assert.assertTrue(customer.withdraw(1, 100d));
        Assert.assertEquals(2, customer.getAccounts().get(0).getTransactions().size());
        Assert.assertEquals(-100d, customer.getAccounts().get(0).getTransactions().get(1).getAmount(),0);
    }


    // all transaction as Arralist of Strings, non-existing account
    @Test
    public void getAllTransactions_nonExistingAccount() {
        Assert.assertNull(customer.getAllTransactions(2));
    }

    // all transaction as Arralist of Strings, existing account
    @Test
    public void getAllTransactions_existingAccount() {
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(0).getTransactions().add(new Transaction(1000d, LocalDate.now()));
        double amount = 1000d;
        LocalDate date = LocalDate.now();
        Assert.assertEquals(amount, customer.getAllTransactions(1).get(0).getAmount(),0);
        Assert.assertEquals(date, customer.getAllTransactions(1).get(0).getDate());
    }

    // get current balance as double, non existing account
    @Test
    public void getTotal_nonExistingAccount() {
        Assert.assertEquals(-1d, customer.getTotal(2),0);
    }

    // get current balance as double, non existing account
    @Test
    public void getTotal_existingAccount() {
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(0).getTransactions().add(new Transaction(100d, LocalDate.now()));
        Assert.assertEquals(100d, customer.getTotal(1),0);
    }

    // transfer between non-existing accounts, 1st (withdrawal) account is invalid
    @Test
    public void makeTransfer_nonExistingWithdrawalAccount() {
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(0).getTransactions().add(new Transaction(100d, LocalDate.now()));
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(1).getTransactions().add(new Transaction(100d, LocalDate.now()));
        Assert.assertFalse(customer.makeTransfer(3,1,50d));
    }

    // transfer between non-existing accounts, 2nd (deposit) account is invalid
    @Test
    public void makeTransfer_nonExistingDepositAccount() {
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(0).getTransactions().add(new Transaction(100d, LocalDate.now()));
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(1).getTransactions().add(new Transaction(100d, LocalDate.now()));
        Assert.assertFalse(customer.makeTransfer(1,3,50d));
    }

    // transfer between non-existing accounts, both accounts are invalid
    @Test
    public void makeTransfer_nonExistingBothAccounts() {
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(0).getTransactions().add(new Transaction(100d, LocalDate.now()));
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(1).getTransactions().add(new Transaction(100d, LocalDate.now()));
        Assert.assertFalse(customer.makeTransfer(3,3,50d));
    }

    // transfer between existing accounts
    @Test
    public void makeTransfer_existingAccounts() {
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(0).getTransactions().add(new Transaction(100d, LocalDate.now()));
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().get(1).getTransactions().add(new Transaction(100d, LocalDate.now()));
        Assert.assertTrue(customer.makeTransfer(1,2,50d));
        Assert.assertEquals(-50d, customer.getAccounts().get(0).getTransactions().get(1).getAmount(),0);
        Assert.assertEquals(50d, customer.getAccounts().get(1).getTransactions().get(1).getAmount(),0);
    }

    // number of accounts, no accounts created so far
    @Test
    public void getNumberOfAccounts_noAccountsSoFar() {
        Assert.assertEquals(0, customer.getNumberOfAccounts(),0);
    }

    // number of accounts
    @Test
    public void getNumberOfAccounts_exisitngAccounts() {
        customer.getAccounts().add(new CheckingAccount());
        customer.getAccounts().add(new SavingsAccount());
        customer.getAccounts().add(new MaxiSavingsAccount());
        customer.getAccounts().add(new CheckingAccount());
        Assert.assertEquals(4, customer.getNumberOfAccounts(),0);
    }
}