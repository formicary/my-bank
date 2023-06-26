package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void should_returnCorrectStatement_when_TransactionsAreMade() {

        Account savingsAccount = new SavingsAccount();
        Account checkingAccount = new CheckingAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.transact(100.0);
        savingsAccount.transact(4000.0);
        savingsAccount.transact(-200.0);

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
    public void should_returnAccountNumberAsTwo_when_twoAccountsAreOpened() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void should_returnInterestAsTwo_when_twoAccountsAreOpenedWithDeposit() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Customer sam = new Customer("Sam")
                .openAccount(savingsAccount).openAccount(checkingAccount);
        savingsAccount.transact(2000);
        checkingAccount.transact(1000);
        assertEquals(4, sam.totalInterestEarned(), DOUBLE_DELTA);
    }

}
