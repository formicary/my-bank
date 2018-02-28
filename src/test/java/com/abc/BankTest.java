package com.abc;

import org.junit.Test;

import static com.abc.AccountType.*;
import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-9;

    @Test
    public void shouldGenerateCustomerSummary() {
        Bank bank = new Bank();
        assertEquals("Customer Summary", bank.customerSummary());
        Customer john = new Customer("John");
        john.openAccount(new Account(CHECKING));
        bank.addCustomer(john);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
        Customer peter = new Customer("Peter");
        peter.openAccount(new Account(CHECKING));
        peter.openAccount(new Account(SAVINGS));
        peter.openAccount(new Account(MAXI_SAVINGS));
        bank.addCustomer(peter);
        assertEquals("Customer Summary\n - John (1 account)\n - Peter (3 accounts)", bank.customerSummary());
    }

    @Test
    public void shouldCorrectlyCountInterestPaid() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(CHECKING);
        Account savingsAccount = new Account(SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bill.openAccount(savingsAccount);
        checkingAccount.deposit(45.55);
        savingsAccount.deposit(55.55);
        bank.addCustomer(bill);
        assertEquals(savingsAccount.getInterestEarned() + checkingAccount.getInterestEarned(),
                bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void shouldGetFirstCustomerIfOneExists() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        assertEquals(bill, bank.getFirstCustomer());
    }

    @Test
    public void shouldReturnNullIfNoCustomersExist() {
        Bank bank = new Bank();
        assertEquals(null, bank.getFirstCustomer());
    }

}
