package com.abc;

import com.abc.account.*;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testGetStatement(){

        Customer henry = new Customer("Henry");

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

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

    @Test //Test customer's transactions List is being appended
    public void testOpenAccount() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new SavingsAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test //Test the interest from all accounts is correct
    public void testTotalInterestEarned() {
        Customer lee = new Customer("Lee");
        CheckingAccount checkAcc = new CheckingAccount();
        checkAcc.deposit(3000.0);
        SavingsAccount savAcc = new SavingsAccount();
        savAcc.deposit(3000.0);
        MaxiSavingsAccount maxSavAcc = new MaxiSavingsAccount();
        maxSavAcc.deposit(3000.0);
        lee.openAccount(checkAcc);
        lee.openAccount(savAcc);
        lee.openAccount(maxSavAcc);

        assertEquals(Math.round(lee.totalInterestEarned()*100)/100.0, 15.01, DOUBLE_DELTA);
    }
}
