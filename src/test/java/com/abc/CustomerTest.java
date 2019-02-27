package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private class DummyAccount extends Account {
        private double balance = 10.0;
        public void deposit(double amount) { this.balance += amount; }
        public void withdraw(double amount) { this.balance -= amount; }
        public double getBalance() { return this.balance; }
        public String getStatement() { return "Dummy Account Statement\n"; }
        public double interestEarned() { return 0.01; }
        public String accountType() { return "Dummy Account"; }
    }

    @Test
    public void testGetName( ){
        Customer annie = new Customer("Annie");

        assertEquals("Annie", annie.getName());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar")
            .openAccount(new DummyAccount());

        assertEquals(1, oscar.numberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount());

        assertEquals(3, oscar.numberOfAccounts());
    }

    @Test
    public void testGetAccount() {
        Account account = new DummyAccount();
        Customer edward = new Customer("Edward")
            .openAccount(account);

        assertEquals(account, edward.getAccount(0));
    }

    @Test
    public void testTransfer() {
        Account account0 = new DummyAccount();
        Account account1 = new DummyAccount();
        Customer felicia = new Customer("Felicia")
            .openAccount(account0)
            .openAccount(account1);

        felicia.transfer(0, 1, 10.0);

        assertEquals(0.0, account0.getBalance(), DOUBLE_DELTA);
        assertEquals(20.0, account1.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testInterest() {
        Customer james = new Customer("James")
            .openAccount(new DummyAccount());

        assertEquals(0.01, james.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestMultipleAccounts() {
        Customer james = new Customer("James")
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount());

        assertEquals(0.03, james.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testStatement() {
        Customer henry = new Customer("Henry")
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount());

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Dummy Account Statement\n" +
                "\n" +
                "Dummy Account Statement\n" +
                "\n" +
                "Total In All Accounts $20.00", henry.getStatement());
    }

    @Test
    public void testSummary() {
        Customer john = new Customer("John")
            .openAccount(new DummyAccount());

        assertEquals("John (1 account)", john.getSummary());
    }

    @Test
    public void testSummaryMultipleAccounts() {
        Customer jane = new Customer("Jane")
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount())
            .openAccount(new DummyAccount());

        assertEquals("Jane (3 accounts)", jane.getSummary());
    }
}
