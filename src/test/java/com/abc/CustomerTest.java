package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void simpleStatement(){
        /* In this case we assume that no interests have been paid in the account since all the funds were
           added in the accounts on the same day and that the the statement has been generated at the same
           day as well. */

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount);

        checkingAccount.deposit(100.0, LocalDate.of(2019,1,1));
        savingsAccount.deposit(4000.0, LocalDate.of(2019,1,1));
        savingsAccount.withdraw(200.0, LocalDate.of(2019,1,1));

        assertEquals("Statement for Henry on date 2019-01-01\n" +
                "\n" +
                "Checking Account\n" +
                "  2019-01-01 deposit $100.00\n" +
                "\n" +
                "  interests $0.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  2019-01-01 deposit $4,000.00\n" +
                "  2019-01-01 withdrawal $200.00\n" +
                "\n" +
                "  interests $0.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement(LocalDate.of(2019,1,1)));
    }


    @Test //Test customer statement generation
    public void complexStatement(){
        /* In this case we want to replicate a realistic scenario where a user may have multiple accounts
           and makes transactions through time. */

        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry")
                .openAccount(maxiSavingsAccount)
                .openAccount(savingsAccount);

        savingsAccount.deposit(4000.0, LocalDate.of(2019, 1, 1));
        savingsAccount.withdraw(3500.0, LocalDate.of(2019, 6, 12));
        maxiSavingsAccount.deposit(100.0, LocalDate.of(2019,1,1));
        maxiSavingsAccount.deposit(700.0, LocalDate.of(2019,1,4));
        maxiSavingsAccount.withdraw(700.0, LocalDate.of(2019,1,4));
        maxiSavingsAccount.deposit(700.0, LocalDate.of(2019,4,12));
        maxiSavingsAccount.withdraw(122, LocalDate.of(2019,5,15));


        assertEquals("Statement for Henry on date 2020-01-01\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  2019-01-01 deposit $100.00\n" +
                "  2019-01-04 deposit $700.00\n" +
                "  2019-01-04 withdrawal $700.00\n" +
                "  2019-04-12 deposit $700.00\n" +
                "  2019-05-15 withdrawal $122.00\n" +
                "\n" +
                "  interests $25.11\n" +
                "Total $703.11\n" +
                "\n" +
                "Savings Account\n" +
                "  2019-01-01 deposit $4,000.00\n" +
                "  2019-06-12 withdrawal $3,500.00\n" +
                "\n" +
                "  interests $3.83\n" +
                "Total $503.83\n" +
                "\n" +
                "Total In All Accounts $1,206.94", henry.getStatement(LocalDate.of(2020,1,1)));
    }

    @Test // Testing the generation of one account for a customer.
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test // Testing the generation of two accounts for a customer.
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS))
                .openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test // Testing the transfer of funds from one account to another.
    public void testTransfer(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer oscar = new Customer("oscar")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount);
        checkingAccount.deposit(2000.0);
        savingsAccount.deposit(100.0);
        oscar.transfer(checkingAccount, savingsAccount,500);
        assertEquals(1500, checkingAccount.getBalance(), DOUBLE_DELTA);
        assertEquals(600, savingsAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test // Testing the balance and interests functions.
    public void getBalance(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000, LocalDate.of(2020,1,1));
        checkingAccount.deposit(200, LocalDate.of(2020, 1, 4));
        checkingAccount.withdraw(200, LocalDate.of(2020, 1, 4));
        checkingAccount.deposit(300, LocalDate.of(2020, 1, 5));
    }

}
