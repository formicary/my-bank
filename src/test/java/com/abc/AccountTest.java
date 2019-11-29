package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    @Test
    public void checkingAccount() {
        Account account = new CheckingAccount();
        assertTrue(account instanceof Account);
    }
    @Test
    public void savingsAccount() {
        Account account = new SavingsAccount();
        assertTrue(account instanceof Account);
    }
    @Test
    public void maxiSavingsAccount() {
        Account account = new MaxiSavingsAccount();
        assertTrue(account instanceof Account);
    }

    // Check retrieving account type correctly
    @Test
    public void testGetAccountType() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        assertEquals("Checking Account", checkingAccount.getAccountType());
        assertEquals("Savings Account", savingsAccount.getAccountType());
        assertEquals("Maxi-Savings Account", maxiSavingsAccount.getAccountType());
    }

    // Check retrieving balance correctly
    @Test
    public void testGetBalance() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(1000);
        checkingAccount.withdraw(300);
        checkingAccount.withdraw(100);
        checkingAccount.deposit(600);

        assertEquals("1200", checkingAccount.getBalance().stripTrailingZeros().toPlainString());
    }

    // Check retrieving interest earned correctly
    @Test
    public void testGetInterest() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        checkingAccount.deposit(1000);
        savingsAccount.deposit(1600);
        maxiSavingsAccount.deposit(3200);

        assertEquals("1", checkingAccount.interestEarned().stripTrailingZeros().toPlainString());
        assertEquals("2.2", savingsAccount.interestEarned().stripTrailingZeros().toPlainString());
        assertEquals("3.2", maxiSavingsAccount.interestEarned().stripTrailingZeros().toPlainString());
    }

    // Check deposit/withdrawing
    @Test
    public void testDepositAndWithdraw() {
        Account savingsAccount = new SavingsAccount();

        savingsAccount.deposit(1000);
        assertEquals("1000", savingsAccount.getBalance().stripTrailingZeros().toPlainString());
        savingsAccount.withdraw(500);
        assertEquals("500", savingsAccount.getBalance().stripTrailingZeros().toPlainString());
    }

    // Check exception thrown when trying to withdraw more money than customer currently has
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalWithdraw() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(500);
        maxiSavingsAccount.withdraw(600);
    }

    // Check getting a list of transactions
    @Test
    public void getTransactionList() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(1000);
        checkingAccount.withdraw(100);
        checkingAccount.withdraw(300);

        List<Transaction> transactions = checkingAccount.getTransactions();
        assertEquals("Deposit", transactions.get(0).getTransactionType());
        assertEquals("Withdrawal", transactions.get(1).getTransactionType());
        assertEquals("Withdrawal", transactions.get(2).getTransactionType());
    }

    // Add new transaction to an account
    @Test
    public void addNewTransaction() {
        Account checkingAccount = new CheckingAccount();
        Transaction transaction = new Transaction(300);

        checkingAccount.newTransaction(transaction);
        assertEquals("300", checkingAccount.getBalance().stripTrailingZeros().toPlainString());
        List<Transaction> transactions = checkingAccount.getTransactions();
        assertEquals("Deposit", transactions.get(0).getTransactionType());
    }
    // Test checking time between last withdrawal
    @Test
    public void testMaxiSavingsInterest() {
        Account maxiSavings = new MaxiSavingsAccount();
        maxiSavings.deposit(1200);
        Calendar calendar = Calendar.getInstance();

        // TEST FOR LAST WITHDRAW 10 DAYS AGO
        calendar.add(Calendar.DATE, -10);
        Date dummyDate = calendar.getTime();
        maxiSavings.setLastWithdrawal(dummyDate);
        assertEquals("60", maxiSavings.interestEarned().stripTrailingZeros().toPlainString());
        // TEST FOR LAST WITHDRAW LESS THAN 10 DAYS AGO
        calendar.add(Calendar.DATE, 5);
        dummyDate = calendar.getTime();
        maxiSavings.setLastWithdrawal(dummyDate);
        assertEquals("1.2", maxiSavings.interestEarned().stripTrailingZeros().toPlainString());
    }
}
