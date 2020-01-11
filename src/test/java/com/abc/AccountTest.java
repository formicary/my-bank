package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Account class
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    /**
     * Test to calculate the total of an account
     */
    @Test
    public void sumTransactions(){
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING, "checking");
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(100);
        checkingAccount.deposit(250);
        checkingAccount.deposit(150);
        checkingAccount.deposit(1000);

        assertEquals(1500,checkingAccount.sumTransactions(), DOUBLE_DELTA );
    }

    /**
     * Test transferring money between two accounts
     */
    @Test
    public void moneyTransfer() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING, "checking");
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1000.0);

        Account savingsAccount = new Account(Account.SAVINGS, "savings");
        bill.openAccount(savingsAccount);

        checkingAccount.transfer(bill, "checking", "savings", 400);

        assertEquals(600, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(400, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    }

    /**
     * Test transferring money from an account with no money
     */
    @Test(expected = IllegalArgumentException.class)
    public void moneyTransferWithNoMoney() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING, "checking");
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        Account savingsAccount = new Account(Account.SAVINGS, "savings");
        bill.openAccount(savingsAccount);

        checkingAccount.transfer(bill, "checking", "savings", 400);
    }

    /**
     * Test checking interest of a checking account with money
     */
    @Test
    public void interestOfCheckingAccountWithMoney() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING, "checking");
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Test checking interest of a checking account with no money
     */
    @Test
    public void interestOfCheckingAccountWithNoMoney() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING, "checking");
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Test checking interest of a savings account with money
     */
    @Test
    public void interestOfSavingsAccountWithMoney() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS, "savings");
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Test checking interest of a savings account with no money
     */
    @Test
    public void interestOfSavingsAccountWithNoMoney() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS, "savings");
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Test checking interest of a maxi savings account with no withdrawals in the past 10 days
     */
    @Test
    public void interestOfMaxiSavingsAccountNoWithdrawals() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS, "maxi savings");
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Test checking interest of a maxi savings account with a withdrawal in the past 10 days
     */
    @Test
    public void interestOfMaxiSavingsAccountWithdrawalWithinTenDays() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS, "maxi savings");
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(1000.0);
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Test checking interest of a maxi saving account with no money
     */
    @Test
    public void maxiSavingsAccountWithNoMoney() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS, "maxi savings");
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Test depositing legal amount
     */
    @Test
    public void depositMoney() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING, "checking");
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(100);

        assertEquals(100, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    /**
     * Test depositing an illegal amount
     */
    @Test(expected = IllegalArgumentException.class)
    public void depositZeroMoney() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING, "checking");
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(0);
    }

    /**
     * Test withdrawing legal amount
     */
    @Test
    public void withdrawMoney() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS, "savings");
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.withdraw(100);

        assertEquals(-100, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    }

    /**
     * Test withdrawing illegal amount
     */
    @Test(expected = IllegalArgumentException.class)
    public void withdrawZeroMoney() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING, "checking");
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.withdraw(0);
    }
}
