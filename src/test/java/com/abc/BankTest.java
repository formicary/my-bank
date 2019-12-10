package com.abc;

import com.abc.account_types.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    // - List format?
    @Test
    public void getCustomerSummary_WhenCalledWithNoCustomers_ReturnsEmptySummary() {
        Bank bank = new Bank();

        String result = bank.getCustomerSummary();

        assertEquals("Customer Summary\n- No customer accounts!", result);
    }

    @Test
    public void getCustomerSummary_WhenCalledWithTwoCustomers_ReturnsCorrectSummary() {
        Customer customer = new Customer("John");
        customer.openAccount(Constants.AccountTypes.CheckingAccount);
        customer.openAccount(Constants.AccountTypes.SavingsAccount);

        Customer customer2 = new Customer("Tom");
        customer2.openAccount(Constants.AccountTypes.SavingsAccount);

        Bank bank = new Bank();
        bank.addCustomer(customer);
        bank.addCustomer(customer2);

        String result = bank.getCustomerSummary();

        assertEquals("Customer Summary\n - John (2 accounts)\n - Tom (1 account)", result);
    }

    @Test
    public void getInterestSummary_WhenCalledWithNoCustomers_ReturnsEmptySummary(){
        Bank bank = new Bank();

        String result = bank.getInterestSummary();

        assertEquals("Interest Summary (0 accounts)\nInterest Paid: $0.00", result);
    }

    @Test
    public void getInterestSummary_WhenCalledWithTwoCustomers_ReturnsCorrectInterestSummary(){
        Customer customer = new Customer("John");
        customer.openAccount(Constants.AccountTypes.CheckingAccount).deposit(100);

        Customer customer2 = new Customer("Tom");
        customer2.openAccount(Constants.AccountTypes.CheckingAccount).deposit(400);

        Bank bank = new Bank();
        bank.addCustomer(customer);
        bank.addCustomer(customer2);

        String result = bank.getInterestSummary();

        assertEquals("Interest Summary (2 accounts)\nInterest Paid: $0.05", result);
    }
}
