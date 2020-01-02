package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfer1BetweenSameAccount() {
        Customer charlie = new Customer("Charlie");

        Account account = new Account(Account.SAVINGS);
        charlie.openAccount(account);
        account.deposit(1);

        charlie.transfer(account, account, 1);
    }

    @Test
    public void transfer1FromDifferentAccountsWithSufficientFunds() {
        Customer charlie = new Customer("Charlie");

        Account account1 = new Account(Account.SAVINGS);
        charlie.openAccount(account1);
        account1.deposit(1);

        Account account2 = new Account(Account.SAVINGS);
        charlie.openAccount(account2);

        charlie.transfer(account1, account2, 1);
        assertEquals(0, account1.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void transfer1ToDifferentAccountsWithSufficientFunds() {
        Customer charlie = new Customer("Charlie");

        Account account1 = new Account(Account.SAVINGS);
        charlie.openAccount(account1);
        account1.deposit(1);

        Account account2 = new Account(Account.SAVINGS);
        charlie.openAccount(account2);

        charlie.transfer(account1, account2, 1);
        assertEquals(1, account2.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfer1BetweenAccountsWithDifferentOwners() {
        Customer charlie = new Customer("Charlie");
        Account account1 = new Account(Account.SAVINGS);
        charlie.openAccount(account1);
        account1.deposit(1);

        Customer eve = new Customer("Eve");
        Account account2 = new Account(Account.SAVINGS);
        eve.openAccount(account2);

        charlie.transfer(account1, account2, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfer1BetweenAccountsWithSameOwnerByDifferentOwner() {
        Customer charlie = new Customer("Charlie");
        Account account1 = new Account(Account.SAVINGS);
        charlie.openAccount(account1);
        account1.deposit(1);

        Account account2 = new Account(Account.SAVINGS);
        charlie.openAccount(account2);

        Customer eve = new Customer("Eve");
        eve.transfer(account1, account2, 1);
    }
}
