package com.abc.bank;

import com.abc.account.Account;
import com.abc.account.AccountType;
import com.abc.customer.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void When_CustomerIsAdded_Expect_BankStateToBeCorrect() {
        Bank bank = new Bank();
        Customer customer = new Customer("John");
        bank.addCustomer(customer);

        assertEquals(1, bank.getCustomers().size());
    }

    @Test
    public void When_BankHasMultipleCustomers_Expect_CustomerSummaryToBeCorrect() {
        Bank bank = new Bank();
        Customer jane = new Customer("Jane").openAccount(new Account(AccountType.MAXI_SAVINGS));
        jane.openAccount(new Account(AccountType.SAVINGS));
        bank.addCustomer(jane);
        Customer john = new Customer("John").openAccount(new Account(AccountType.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary" +
                "\n - Jane (2 accounts)" +
                "\n - John (1 account)", bank.summaryOfAllCustomers());
    }

    @Test
    public void When_BankHasNoCustomers_Expect_CustomerSummaryToBeCorrect() {
        Bank bank = new Bank();
        assertEquals("Customer Summary", bank.summaryOfAllCustomers());
    }

    @Test
    public void When_BankHasMultipleCustomers_Expect_TotalInterestPaidToBeCorrect() {
        Bank bank = new Bank();
        Customer jane = new Customer("Jane");
        bank.addCustomer(jane);
        Account janeSavings = new Account(AccountType.SAVINGS);
        jane.openAccount(janeSavings);
        janeSavings.deposit(1500.0);
        Account janeChecking = new Account(AccountType.CHECKING);
        jane.openAccount(janeChecking);
        janeChecking.deposit(500.0);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        Account johnMaxiSavings = new Account(AccountType.MAXI_SAVINGS);
        john.openAccount(johnMaxiSavings);
        johnMaxiSavings.deposit(2500.0);

        assertEquals(122.5 , bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
