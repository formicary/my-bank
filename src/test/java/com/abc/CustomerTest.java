package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() {

        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

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
    }

    @Test
    public void transferFromCheckingToSavings() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        bank.addCustomer(henry);

        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(1000.0);

        henry.transfer(checkingAccount, savingsAccount, 500.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $500.00\n" +
                "Total $1,500.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00", henry.getStatement());
    }

    @Test
    public void transferFromSavingsToChecking() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        bank.addCustomer(henry);

        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(1000.0);

        henry.transfer(savingsAccount, checkingAccount, 500.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $500.00\n" +
                "Total $1,500.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00", henry.getStatement());
    }

    @Test
    public void transferFromSavingsToMaxiSavings() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(maxiSavingsAccount).openAccount(savingsAccount);

        bank.addCustomer(henry);

        savingsAccount.deposit(1000.0);
        maxiSavingsAccount.deposit(1000.0);

        henry.transfer(savingsAccount, maxiSavingsAccount, 500.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $500.00\n" +
                "Total $1,500.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00", henry.getStatement());
    }

    @Test
    public void transferFromMaxiSavingsToSavings() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(maxiSavingsAccount).openAccount(savingsAccount);

        bank.addCustomer(henry);

        savingsAccount.deposit(1000.0);
        maxiSavingsAccount.deposit(1000.0);

        henry.transfer(maxiSavingsAccount, savingsAccount, 500.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $500.00\n" +
                "Total $1,500.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00", henry.getStatement());
    }

    @Test
    public void transferFromMaxiSavingsToChecking() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Account checkingAccount = new CheckingAccount();

        Customer henry = new Customer("Henry").openAccount(maxiSavingsAccount).openAccount(checkingAccount);

        bank.addCustomer(henry);

        checkingAccount.deposit(1000.0);
        maxiSavingsAccount.deposit(1000.0);

        henry.transfer(maxiSavingsAccount, checkingAccount, 500.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $500.00\n" +
                "Total $1,500.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00", henry.getStatement());
    }

    @Test
    public void transferFromCheckingToMaxiSavings() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Account checkingAccount = new CheckingAccount();

        Customer henry = new Customer("Henry").openAccount(maxiSavingsAccount).openAccount(checkingAccount);

        bank.addCustomer(henry);

        checkingAccount.deposit(1000.0);
        maxiSavingsAccount.deposit(1000.0);

        henry.transfer(checkingAccount, maxiSavingsAccount, 500.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $500.00\n" +
                "Total $1,500.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
