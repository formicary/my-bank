package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry");
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

    @Test
    public void testNumberOfAccounts(){
        Customer oscar = new Customer("Oscar");

        for (int i = 0; i < 100; i++) {
            oscar.openAccount(new SavingsAccount());
            assertEquals(i+1, oscar.getNumberOfAccounts());
        }
    }

    @Test
    public void testTransferLessThanZero() {
        Customer john = new Customer("John");
        Account withdrawAccount = new SavingsAccount();
        Account depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        try {
            john.transferBetweenAccounts(withdrawAccount, depositAccount, (-0.0));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("amount must be greater than zero"))
                assert(true);
            else
                assert(false);
        }
    }

    @Test
    public void testTransferZero() {
        Customer john = new Customer("John");
        Account withdrawAccount = new SavingsAccount();
        Account depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        try {

            john.transferBetweenAccounts(withdrawAccount, depositAccount, 0.0);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("amount must be greater than zero"))
                assert(true);
            else
                assert(false);
        }
    }

    @Test
    public void testTransferGreaterThanZero() {
        Customer john = new Customer("John");
        Account withdrawAccount = new SavingsAccount();
        Account depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        john.transferBetweenAccounts(withdrawAccount, depositAccount, 1.0);

        assertEquals(withdrawAccount.getAccountValue(), 99.0, 0.001);
        assertEquals(depositAccount.getAccountValue(), 101.0, 0.001);
    }
}
