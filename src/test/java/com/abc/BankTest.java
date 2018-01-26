package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummaryWithOneAccountTest() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank.generateID());
        john.openAccount(new Account (Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John [000001] (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummaryWithSeveralAccountsTest() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank.generateID());
        john.openAccount(new Account (Account.CHECKING));
        john.openAccount(new Account (Account.SAVINGS));
        john.openAccount(new Account (Account.MAXI_SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John [000001] (3 accounts)", bank.customerSummary());
    }

    @Test
    public void customerSummaryWithSeveralCustomersTest() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank.generateID());
        Customer bill = new Customer("Bill", bank.generateID());
        john.openAccount(new Account (Account.CHECKING));
        john.openAccount(new Account (Account.SAVINGS));
        bill.openAccount(new Account (Account.SAVINGS));

        bank.addCustomer(john);
        bank.addCustomer(bill);

        assertEquals("Customer Summary\n - John [000001] (2 accounts)\n"+
                " - Bill [000002] (1 account)", bank.customerSummary());
    }

    @Test
    public void removeCustomerTest() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank.generateID());
        bank.addCustomer(john);

        bank.removeCustomer(john);

        assertEquals(0, bank.getNumberOfCustomers());
    }

    @Test
    public void totalInterestPaidTest () {
        double expectedTotalInterestPaid;

        Bank bank = new Bank();

        Customer john = new Customer("John", bank.generateID());
        Customer bill = new Customer("Bill", bank.generateID());

        Account checkingAccountJohn = new Account(Account.CHECKING);
        Account savingsAccountJohn = new Account(Account.SAVINGS);
        Account checkingAccountBill = new Account(Account.CHECKING);
        Account savingsAccountBill = new Account(Account.SAVINGS);

        john.openAccount(checkingAccountJohn);
        john.openAccount(savingsAccountJohn);
        bill.openAccount(checkingAccountBill);
        bill.openAccount(savingsAccountBill);

        bank.addCustomer(john);
        bank.addCustomer(bill);

        checkingAccountJohn.deposit(1000.0);
        savingsAccountJohn.deposit(1500);
        checkingAccountBill.deposit(1000.0);
        savingsAccountBill.deposit(500.0);

        expectedTotalInterestPaid = 1000.0 * 0.001 + //CHECKING account (John)
                                    1+(500 * 0.002) + //SAVINGS account (John)
                                    1000.0 * 0.001 + //CHECKING account (Bill)
                                    500.0 * 0.001; //SAVINGS account (Bill)

        assertEquals(expectedTotalInterestPaid, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
