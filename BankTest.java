package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private Bank bank;
    private static final double DOUBLE_DELTA = 1e-15;

    @Before public void initialize() {
        bank = new Bank();
    }

    @Test
    public void customerCanBeAdded() {
        bank.addCustomer(new Customer("Javier"));

        Customer javier = bank.getCustomers().get(0);

        assertEquals("Javier", javier.getName());
    }

    @Test
    public void summaryFunctionPrintRightInformationForSingleResult() {
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.getCustomerSummary());
    }

    @Test
    public void summaryFunctionPrintRightInformationWithMultipleCustomersAndAccounts() {
        Customer javier = new Customer("Javier");
        Customer daniel = new Customer("Daniel");

        javier.openAccount(new Account(Account.CHECKING));
        daniel.openAccount(new Account(Account.CHECKING));
        daniel.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(javier);
        bank.addCustomer(daniel);

        String expectedSummary =
                "Customer Summary" +
                        "\n - Javier (1 account)" +
                        "\n - Daniel (2 accounts)";

        assertEquals(expectedSummary, bank.getCustomerSummary());
    }

    @Test
    public void checkInterestsInCheckingAccount() {
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(3000.0);

        assertEquals(3, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void checkInterestsInSavingAccount() {
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000);

        assertEquals(5.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void checkInterestsInMaxiSavingAccountNoWithdrawalsInLastTenDays() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        checkingAccount.withdraw(500);

        bank.getCustomers().get(0)
                .getAccounts().get(0)
                .getTransactions().get(1)
                .setTransactionDate(LocalDate.of(2017,5,10));

        assertEquals(2.5, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void checkInterestsInMaxiSavingAccountWithWithdrawalsInLastTenDays() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        checkingAccount.withdraw(500);

        assertEquals(125.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }


}
