package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void closeAccountTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry", "000001")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount);

        henry.closeAccount(checkingAccount);

        assertEquals(1, henry.getNumberOfAccounts());
    }

    @Test
    public void allAccountsStatementTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry", "000001")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount);

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
    public void numberOfAccountsTest(){
        Customer oscar = new Customer("Oscar", "aa0001")
                .openAccount(new Account(Account.SAVINGS))
                .openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void transferBetweenAccountsTest() {
        Bank bank = new Bank();

        Account savingsAccount = new Account(Account.SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);

        Customer bill = new Customer("Bill", bank.generateID())
                .openAccount(savingsAccount)
                .openAccount(checkingAccount);

        bank.addCustomer(bill);

        savingsAccount.deposit(1000.0);
        checkingAccount.deposit(1000.0);

        bill.transfer(savingsAccount,checkingAccount, 451.0);

        assertEquals(1451.0, checkingAccount.getBalance(), DOUBLE_DELTA);
    }
}
