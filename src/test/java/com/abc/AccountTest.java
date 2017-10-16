package com.abc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transactionSummary() {
        Account checkingAccount1 = new Account(Account.CHECKING);
        Account checkingAccount2 = new Account(Account.CHECKING);

        Customer dan = new Customer("Dan").openAccount(checkingAccount1);
        dan.openAccount(checkingAccount2);

        checkingAccount1.deposit(100.0);
        checkingAccount1.transferToAccount(checkingAccount2, 20);

        assertEquals("Statement for Dan\n"
                + "\n"
                + "Checking Account\n"
                + "  deposit $100.00\n"
                + "  withdrawal $20.00\n"
                + "Total $80.00\n"
                + "\n"
                + "Checking Account\n"
                + "  deposit $20.00\n"
                + "Total $20.00\n"
                + "\n"
                + "Total In All Accounts $100.00", dan.getStatement());
        //Check balances update on both accounts       
    }

    @Test
    public void maxiInterestRate() {
        Account maxiAccount1 = new Account(Account.MAXI_SAVINGS);
        Customer dan = new Customer("Dan").openAccount(maxiAccount1);

        maxiAccount1.deposit(100.0);
        assertEquals(1.0, maxiAccount1.annualInterestEarned(), DOUBLE_DELTA);
        //Check interest for new accounts

        maxiAccount1.updateYear(1990);
        assertEquals(5.0, maxiAccount1.annualInterestEarned(), DOUBLE_DELTA);
        //Check extra interest for old accounts

        maxiAccount1.withdraw(10);
        assertEquals(0.9, maxiAccount1.annualInterestEarned(), DOUBLE_DELTA);
        //Check interest reduced upon withdrawal
    }

    @Test
    public void dailyInterestRate() {
        Account maxiAccount1 = new Account(Account.MAXI_SAVINGS);
        Account savingsAccount1 = new Account(Account.SAVINGS);
        Account checkingAccount1 = new Account(Account.CHECKING);

        Customer dan = new Customer("Dan").openAccount(maxiAccount1);
        dan.openAccount(savingsAccount1);
        dan.openAccount(checkingAccount1);

        maxiAccount1.deposit(100.0);
        savingsAccount1.deposit(10000.0);
        checkingAccount1.deposit(100000.0);

        assertEquals(maxiAccount1.daysInterestEarned(365), maxiAccount1.annualInterestEarned(), DOUBLE_DELTA);
        assertEquals(savingsAccount1.daysInterestEarned(365), savingsAccount1.annualInterestEarned(), DOUBLE_DELTA);
        //Check annual rate is same as daily rate over 365 days

        assert (maxiAccount1.daysInterestEarned(360) < maxiAccount1.daysInterestEarned(370));
        assert (savingsAccount1.daysInterestEarned(360) < savingsAccount1.daysInterestEarned(370));
        assert (checkingAccount1.daysInterestEarned(360) < checkingAccount1.daysInterestEarned(370));

        assert (maxiAccount1.daysInterestEarned(360) > maxiAccount1.daysInterestEarned(350));
        assert (savingsAccount1.daysInterestEarned(360) > savingsAccount1.daysInterestEarned(350));
        assert (maxiAccount1.daysInterestEarned(360) > maxiAccount1.daysInterestEarned(350));

        //Check that interest is added daily, ie money on day 370 > day 360 > day 350
    }
}
