package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testStatementGeneration(){

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
        Customer oscar = new Customer("Matthew").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts(){
        Customer oscar = new Customer("James")
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

    @Test
    public void testTransferBetweenOwnedAccountsSuccess(){
        Customer dwayne = new Customer("Dwayne");
        Account savings = new Account(Account.SAVINGS);
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);

        dwayne.openAccount(savings);
        dwayne.openAccount(maxiSavings);

        savings.deposit(1000.0);

        dwayne.transferBetweenAccounts(savings, maxiSavings, 500.0);

        assertEquals(500.0, savings.sumTransactions(), DOUBLE_DELTA);
        assertEquals(500.0, maxiSavings.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferBetweenUnownedAccountsException(){
        Customer barry = new Customer("Barry");
        Customer eobard = new Customer("Eobard");

        Account savings = new Account(Account.SAVINGS);
        Account checking = new Account(Account.CHECKING);

        barry.openAccount(savings);
        eobard.openAccount(checking);

        savings.deposit(1000.0);
        checking.deposit(1000.0);

        barry.transferBetweenAccounts(savings, checking, 500.0);
     }
}
