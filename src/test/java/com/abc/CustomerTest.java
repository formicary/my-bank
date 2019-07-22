package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() {

        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavingsAccount);

        bank.addCustomer(henry);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        maxiSavingsAccount.deposit(1000.0);

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
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Total In All Accounts $4,900.00", henry.getStatement());

//      Transfer 50$ from checkingAccount to savingsAccount
        henry.transfer(checkingAccount, savingsAccount, 50.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "Total $50.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $50.00\n" +
                "Total $3,850.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Total In All Accounts $4,900.00", henry.getStatement());

//      Transfer 50$ from savingsAccount to checkingAccount
        henry.transfer(savingsAccount, checkingAccount, 50.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "  deposit $50.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $50.00\n" +
                "  withdrawal $50.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Total In All Accounts $4,900.00", henry.getStatement());

//      Transfer 150$ from savingsAccount to maxiSavingsAccount
        henry.transfer(savingsAccount, maxiSavingsAccount, 150.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "  deposit $50.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $50.00\n" +
                "  withdrawal $50.00\n" +
                "  withdrawal $150.00\n" +
                "Total $3,650.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $150.00\n" +
                "Total $1,150.00\n" +
                "\n" +
                "Total In All Accounts $4,900.00", henry.getStatement());

//      Transfer 150$ from maxiSavingsAccount to savingsAccount
        henry.transfer(maxiSavingsAccount, savingsAccount, 150.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "  deposit $50.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $50.00\n" +
                "  withdrawal $50.00\n" +
                "  withdrawal $150.00\n" +
                "  deposit $150.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $150.00\n" +
                "  withdrawal $150.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Total In All Accounts $4,900.00", henry.getStatement());

//      Transfer 300$ from maxiSavingsAccount to checkingAccount
        henry.transfer(maxiSavingsAccount, checkingAccount, 300.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "  deposit $50.00\n" +
                "  deposit $300.00\n" +
                "Total $400.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $50.00\n" +
                "  withdrawal $50.00\n" +
                "  withdrawal $150.00\n" +
                "  deposit $150.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $150.00\n" +
                "  withdrawal $150.00\n" +
                "  withdrawal $300.00\n" +
                "Total $700.00\n" +
                "\n" +
                "Total In All Accounts $4,900.00", henry.getStatement());

//      Transfer 300$ from checkingAccount to maxiSavingsAccount
        henry.transfer(checkingAccount, maxiSavingsAccount, 300.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "  deposit $50.00\n" +
                "  deposit $300.00\n" +
                "  withdrawal $300.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $50.00\n" +
                "  withdrawal $50.00\n" +
                "  withdrawal $150.00\n" +
                "  deposit $150.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $150.00\n" +
                "  withdrawal $150.00\n" +
                "  withdrawal $300.00\n" +
                "  deposit $300.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Total In All Accounts $4,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
