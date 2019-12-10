package com.abc;

import com.abc.shared.Constants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTests {
    Bank bank;

    @Before
    public void initEach(){
        bank = new Bank();
    }

    @Test
    public void getCustomerSummary_WhenCalledWithNoCustomers_ReturnsEmptySummary() {
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

        bank.addCustomer(customer);
        bank.addCustomer(customer2);

        String result = bank.getCustomerSummary();

        assertEquals("Customer Summary\n - John (2 accounts)\n - Tom (1 account)", result);
    }

    @Test
    public void getInterestSummary_WhenCalledWithNoCustomers_ReturnsEmptySummary(){
        String result = bank.getInterestSummary();

        assertEquals("Interest Summary (0 accounts)\nInterest Paid: $0.00", result);
    }

    @Test
    public void getInterestSummary_WhenCalledWithTwoCustomers_ReturnsCorrectInterestSummary(){
        Customer customer = new Customer("John");
        customer.openAccount(Constants.AccountTypes.CheckingAccount).deposit(100);

        Customer customer2 = new Customer("Tom");
        customer2.openAccount(Constants.AccountTypes.CheckingAccount).deposit(400);

        bank.addCustomer(customer);
        bank.addCustomer(customer2);

        String result = bank.getInterestSummary();

        assertEquals("Interest Summary (2 accounts)\nInterest Paid: $0.05", result);
    }
}
