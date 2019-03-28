package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer jessica = new Customer("Jessica");
        jessica.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(jessica);

        assertEquals("Customer Summary\n - Jessica (1 account)", bank.getCustomerSummary(jessica));
    }

    @Test (expected=IllegalArgumentException.class)
    public void testCustomerSummaryShouldThrowIfCustomerDoesntExist() {
        Bank bank = new Bank();
        Customer jessica = new Customer("Jessica");
        bank.getCustomerSummary(jessica);
    }

    @Test
    public void testAllCustomersSummary() {
        Bank bank = new Bank();

        Customer john = TestHelper.createCustomerWithAccounts("John", 1);
        Customer sam = TestHelper.createCustomerWithAccounts("Sam", 2);

        bank.addCustomer(john);
        bank.addCustomer(sam);

        assertEquals("Customer Summary\n - John (1 account)\n - Sam (2 accounts)", bank.getAllCustomersSummary());
    }

    @Test (expected=NullPointerException.class)
    public void testGetFirstCustomerShouldThrow() {
        Bank bank = new Bank();
        bank.getFirstCustomer();
    }

    @Test
    public void testGetFirstCustomer() {
        Bank bank = new Bank();
        Customer customer = TestHelper.createCustomerWithAccounts("Sam", 1);
        bank.addCustomer(customer);
        assertEquals(customer, bank.getFirstCustomer());
    }

    @Test
    public void testCheckInterestPaidOnCheckingAccount() {
        Bank bank = new Bank();
        Customer sarah = TestHelper.createCustomerWithAccounts("Sarah", 1);
        Account account1 = sarah.getAllCustomerAccounts().get(0);
        bank.addCustomer(sarah);

        account1.deposit(10000.0);
        Date date = new Date(DateProvider.getInstance().now().getTime() - (5*24*60*60*1000));
        account1.getAllTransactions().get(account1.getAllTransactions().size()-1).setTransactionDate(date);

        assertEquals(10/365*5, bank.totalInterestPaid(false), DOUBLE_DELTA);
    }

    @Test
    public void testCheckYearlyInterestPaidOnCheckingAccount() {
        Bank bank = new Bank();
        Customer olivia = TestHelper.createCustomerWithAccounts("Olivia", 1);
        Account account1 = olivia.getAllCustomerAccounts().get(0);
        bank.addCustomer(olivia);

        account1.deposit(1000.0);
        assertEquals(1, bank.totalInterestPaid(true), DOUBLE_DELTA);
    }

    @Test
    public void testCheckInterestPaidOnSavingsAccount() {
        Bank bank = new Bank();
        Account savingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(1500.0);

        Date date = new Date(DateProvider.getInstance().now().getTime() - (5*24*60*60*1000));
        savingAccount.getAllTransactions().get(savingAccount.getAllTransactions().size()-1).setTransactionDate(date);

        assertEquals(2/365*5, bank.totalInterestPaid(false), DOUBLE_DELTA);
    }

    @Test
    public void testCheckYearlyInterestPaidOnSavingsAccount() {
        Bank bank = new Bank();

        Account savingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(1500.0);

        assertEquals(2, bank.totalInterestPaid(true), DOUBLE_DELTA);
    }

    @Test
    public void testCheckInterestPaidOnMaxiSavingAccount() {
        Bank bank = new Bank();

        Account maxisavingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Sarah").openAccount(maxisavingAccount));

        maxisavingAccount.deposit(4000.0);
        maxisavingAccount.withdraw(1000.0, false);

        Date date = new Date(DateProvider.getInstance().now().getTime() - (15*24*60*60*1000));
        maxisavingAccount.getAllTransactions().get(maxisavingAccount.getAllTransactions().size()-1).setTransactionDate(date);

        assertEquals(150/365*15, bank.totalInterestPaid(false), DOUBLE_DELTA);

        date = new Date(DateProvider.getInstance().now().getTime() - (9*24*60*60*1000));
        maxisavingAccount.getAllTransactions().get(maxisavingAccount.getAllTransactions().size()-1).setTransactionDate(date);

        assertEquals(3/365*9, bank.totalInterestPaid(false), DOUBLE_DELTA);

    }

    @Test
    public void testCheckYearlyInterestPaidOnMaxiSavingAccount() {
        Bank bank = new Bank();

        Account maxisavingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Olivia").openAccount(maxisavingAccount));

        maxisavingAccount.deposit(4000.0);
        maxisavingAccount.withdraw(1000.0, false);

        Date date = new Date(DateProvider.getInstance().now().getTime() - (11*24*60*60*1000));
        maxisavingAccount.getAllTransactions().get(maxisavingAccount.getAllTransactions().size()-1).setTransactionDate(date);

        assertEquals(150, bank.totalInterestPaid(true), DOUBLE_DELTA);

        date = new Date(DateProvider.getInstance().now().getTime() - (7*24*60*60*1000));
        maxisavingAccount.getAllTransactions().get(maxisavingAccount.getAllTransactions().size()-1).setTransactionDate(date);

        assertEquals(3, bank.totalInterestPaid(true), DOUBLE_DELTA);

    }


}
