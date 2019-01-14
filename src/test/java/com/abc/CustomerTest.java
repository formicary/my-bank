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
        checkingAccount.compoundInterest();
        savingsAccount.compoundInterest();

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  Deposit $100.00\n" +
                "  Interest Payment $0.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  Deposit $4,000.00\n" +
                "  Withdrawal $200.00\n" +
                "  Interest Payment $0.02\n" +
                "Total $3,800.02\n" +
                "\n" +
                "Total In All Accounts $3,900.02", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testNoTransactions(){
        Account checkingAccount = new Account(Account.CHECKING);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);
        assertEquals(0, henry.totalInterestEarnedDaily(), DOUBLE_DELTA);
    }
}
