package com.abc;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private static Bank bank;

    @BeforeClass
    public static void setup() {
        bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountTypes.CHECKING));
        john.getAccounts().get(0).deposit(800);
        bank.addCustomer(john);

        Customer henry = new Customer("Henry");
        henry.openAccount(new Account(AccountTypes.CHECKING));
        henry.getAccounts().get(0).deposit(1000);
        henry.openAccount(new Account(AccountTypes.SAVINGS));
        henry.getAccounts().get(1).deposit(1500);
        bank.addCustomer(henry);

        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountTypes.CHECKING));
        oscar.getAccounts().get(0).deposit(1200);
        oscar.openAccount(new Account(AccountTypes.SAVINGS));
        oscar.getAccounts().get(1).deposit(1800);
        oscar.openAccount(new Account(AccountTypes.MAXI_SAVINGS));
        oscar.getAccounts().get(2).deposit(2500);
        bank.addCustomer(oscar);
    }

    @Test
    public void customerSummary() {
        String expected = "Customer Summary";
        for (Customer c : bank.getCustomers()) {
            expected += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }

        assertEquals(expected, bank.customerSummary());
    }

    @Test
    public void totalInterestPaid() {
        assertEquals(127.6, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
}
