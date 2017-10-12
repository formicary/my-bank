package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void canCreateCustomerSummary() {
        Bank bank = new Bank();

        Customer john = new Customer("John");
        Customer dave = new Customer("Dave");
        Customer lydia = new Customer("Lydia");

        bank.addCustomer(john); bank.addCustomer(dave); bank.addCustomer(lydia);

        john.openAccount(new CheckingAccount()).openAccount(new SavingsAccount());
        dave.openAccount(new SavingsAccount()).openAccount(new CheckingAccount()).openAccount(new SavingsAccount());
        lydia.openAccount(new SavingsAccount());


        assertEquals("Customer Summary"+
                "\n - John (2 accounts)" +
                "\n - Dave (3 accounts)" +
                "\n - Lydia (1 account)", bank.customerSummary());
    }

    @Test
    public void canCalculateTotalInterestPaid() {
        Bank bank = new Bank();

        Customer john = new Customer("John");
        Customer dave = new Customer("Dave");
        Customer lydia = new Customer("Lydia");

        bank.addCustomer(john); bank.addCustomer(dave); bank.addCustomer(lydia);

        Account c1 = new CheckingAccount(); c1.deposit(1000); c1.deposit(250);
        Account c2 = new CheckingAccount(); c2.deposit(100); c2.deposit(2050);
        Account s1 = new SavingsAccount(); s1.deposit(3000); s1.deposit(50);
        Account s2 = new SavingsAccount(); s2.deposit(400); s2.deposit(550);
        Account m1 = new MaxiSavingsAccount(); m1.deposit(6000); m1.deposit(2);
        Account m2 = new MaxiSavingsAccount(); m2.deposit(10); m2.deposit(25000);

        john.openAccount(c1).openAccount(s1).openAccount(s2);
        dave.openAccount(m2);
        lydia.openAccount(m1).openAccount(c2);


        double totalInterestEarned = john.totalInterestEarned() + dave.totalInterestEarned() + lydia.totalInterestEarned();

        assertEquals(totalInterestEarned, bank.totalInterestPaid(), DOUBLE_DELTA);

    }

}
