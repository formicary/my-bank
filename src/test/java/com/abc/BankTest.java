package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummaryForOneAccount() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Account checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        john.openAccount(checkingAccount);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummaryForTwoAccounts() {
        Account checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        Account savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(checkingAccount).openAccount(savingsAccount);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void testTotalInterestPaidOnCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidOnSavingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

       savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidOnMaxiSavingsAccountWithoutWithdrawals() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = AccountFactory.createAccount(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidOnMaxiSavingsAccountWithWithdrawalsWithinTenDays() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = AccountFactory.createAccount(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(1000.0);
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testReportOnTotalInterestPaidByBankOnAllAccounts() {
        Bank bank = new Bank();
        Account savingAccount_Bill = AccountFactory.createAccount(AccountType.SAVINGS);
        Account savingAccount_John = AccountFactory.createAccount(AccountType.SAVINGS);

        savingAccount_Bill.deposit(1000.0);
        savingAccount_John.deposit(1000.0);

        Account checkingAccount_Bill = AccountFactory.createAccount(AccountType.CHECKING);
        Account checkingAccount_John = AccountFactory.createAccount(AccountType.CHECKING);

        checkingAccount_Bill.deposit(1000.0);
        checkingAccount_John.deposit(1000.0);

        bank.addCustomer(new Customer("Bill").openAccount(savingAccount_Bill).openAccount(checkingAccount_Bill));
        bank.addCustomer(new Customer("John").openAccount(savingAccount_John).openAccount(checkingAccount_John));

        assertEquals(4.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testReportOnTotalCustomersAndTheirAccounts() {
        Bank bank = new Bank();
        Account savingAccount_Bill = AccountFactory.createAccount(AccountType.SAVINGS);

        Account savingAccount_John = AccountFactory.createAccount(AccountType.SAVINGS);
        Account checkingAccount_John = AccountFactory.createAccount(AccountType.CHECKING);

        bank.addCustomer(new Customer("Bill").openAccount(savingAccount_Bill));
        bank.addCustomer(new Customer("John").openAccount(savingAccount_John).openAccount(checkingAccount_John));

        assertEquals(2, bank.getCustomers().size());
        assertEquals(1, bank.getCustomers().get(0).getAccounts().size());
        assertEquals(2, bank.getCustomers().get(1).getAccounts().size());
    }

    @Test
    public void testFirstCustomer() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Customer john = new Customer("John");

        bank.addCustomer(bill);
        bank.addCustomer(john);

        assertEquals("Bill", bank.getFirstCustomer());
    }

}
