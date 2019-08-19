package com.abc;

import com.abc.bank.Bank;
import com.abc.bank.CheckingAccount;
import com.abc.bank.Customer;
import com.abc.bank.SavingsAccount;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    public class TestBank extends Bank {
        public String getFirstCustomerName() {
            if (customers != null) {
                return customers.get(0).getName();
            }
            else return null;
        }
    }

    @Test
    public void testAddCustomer() {
        TestBank bank = new TestBank();
        Customer customer = new Customer("John");
        bank.addCustomer(customer);

        assertThat("John", containsString(bank.getFirstCustomerName()));
    }

    @Test
    public void testGenerateCustomerSummaryOneAccount() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testGenerateCustomerSummaryMultipleOneAccount() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        john.openAccount(new SavingsAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void testMultipleCustomerTotalInterest() {
        Bank bank = new Bank();

        SavingsAccount savingsAccount = new SavingsAccount();
        Customer john = new Customer("John");
        john.openAccount(savingsAccount);
        savingsAccount.deposit(100);
        bank.addCustomer(john);

        CheckingAccount checkingAccount = new CheckingAccount();
        Customer eve = new Customer("Eve");
        eve.openAccount(checkingAccount);
        checkingAccount.deposit(100);
        bank.addCustomer(eve);

        assertEquals(0.2, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
