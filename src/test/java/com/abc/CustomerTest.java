package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void canOpenAccounts() {
        Customer dave = new Customer("Dave");
        assertEquals("Dave", dave.getName());

        dave.openAccount(new CheckingAccount()).openAccount(new SavingsAccount()).openAccount(new MaxiSavingsAccount());
        assertEquals(3, dave.getNumberOfAccounts());
    }

    @Test
    public void canGenerateStatement(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

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

    @Test
    public void canCalculateTotalInterest() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        maxiSavingsAccount.deposit(1500.0);
        maxiSavingsAccount.withdraw(500.0);
        savingsAccount.withdraw(200.0);

        double totalInterest = savingsAccount.interestEarned() + checkingAccount.interestEarned() + maxiSavingsAccount.interestEarned();

        assertEquals(totalInterest, henry.totalInterestEarned(), DOUBLE_DELTA);

    }

    @Test
    public void canTransferBetweenAccounts() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        maxiSavingsAccount.deposit(1500.0);
        maxiSavingsAccount.withdraw(500.0);
        savingsAccount.withdraw(200.0);

        henry.transferBetweenAccounts(checkingAccount, savingsAccount, 100);
        assertEquals(0.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(3900, savingsAccount.sumTransactions(), DOUBLE_DELTA);

    }

    @Test (expected = Exception.class)
    public void cantTransferBetweenAccounts() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        maxiSavingsAccount.deposit(1500.0);
        maxiSavingsAccount.withdraw(500.0);
        savingsAccount.withdraw(200.0);

        henry.transferBetweenAccounts(checkingAccount, savingsAccount, 100);
        fail();
    }
}
