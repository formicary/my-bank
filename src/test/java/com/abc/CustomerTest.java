package com.abc;

import org.junit.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {
    private static final double DELTA = 1e-15;

    @Test
    public void customer() {
        Customer john = new Customer("John", "Doe");
        assertTrue(john instanceof Customer);
    }
    // Customer can open an account
    @Test
    public void testOpenAccount() {
        Customer john = new Customer("John", "Doe");
        john.openAccount(new CheckingAccount());
        assertEquals(1, john.getNumberOfAccounts());
    }
    // Customer can retrieve the total amount of interest they earned
    @Test
    public void testDepositAndWithdraw() {
        Customer john = new Customer("John", "Doe");
        Account checkingAccount = new CheckingAccount();
        john.openAccount(checkingAccount);

        checkingAccount.deposit(1200);
        assertEquals(BigDecimal.valueOf(1.2), john.totalInterestEarned().stripTrailingZeros());
    }
    // Customer can retrieve a statement a statement that shows transactions
    // and totals for each of their accounts
    @Test
    public void testStatement() {
        Customer john = new Customer("John", "Doe");
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        john.openAccount(checkingAccount);
        john.openAccount(savingsAccount);

        checkingAccount.deposit(1200);
        checkingAccount.withdraw(300);

        savingsAccount.deposit(600);
        savingsAccount.withdraw(200);

        // Testing individual parts of statement
        List <Account> accounts = john.getCustomerAccounts();
        // Account Type
        assertEquals("Checking Account", accounts.get(0).getAccountType());
        assertEquals("Savings Account", accounts.get(1).getAccountType());
        // ID Retrieval - id may differ on instantiated accounts so can't test on specific values
        // Transaction Type
        // Checking Account
        assertEquals("Deposit", accounts.get(0).getTransactions().get(0).getTransactionType());
        assertEquals("Withdrawal", accounts.get(0).getTransactions().get(1).getTransactionType());
        // Savings Account
        assertEquals("Deposit", accounts.get(1).getTransactions().get(0).getTransactionType());
        assertEquals("Withdrawal", accounts.get(1).getTransactions().get(1).getTransactionType());
        // Amount
        // Checking Account
        assertEquals(1200, accounts.get(0).getTransactions().get(0).getAmount(), DELTA);
        assertEquals(-300, accounts.get(0).getTransactions().get(1).getAmount(), DELTA);
        // Savings Account
        assertEquals(600, accounts.get(1).getTransactions().get(0).getAmount(), DELTA);
        assertEquals(-200, accounts.get(1).getTransactions().get(1).getAmount(), DELTA);
        // Parts combined form the statement
    }
    // Test opening multiple accounts
    @Test
    public void testMultipleAccounts() {
        Customer john = new Customer("John", "Doe");
        Account test1 = new CheckingAccount();
        Account test2 = new CheckingAccount();
        Account test3 = new MaxiSavingsAccount();
        Account test4 = new SavingsAccount();

        john.openAccount(test1);
        john.openAccount(test2);
        john.openAccount(test3);
        john.openAccount(test4);

        assertEquals(4, john.getNumberOfAccounts());
    }
}
