package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BankTest {
    public static final double DOUBLE_DELTA = 1e-15;
    private Bank bank = new Bank();
    private Customer testCustomer = new Customer("John");
    private Transaction t = new Transaction(-1000,"withdraw");

    @Test
    public void checkingAccount() {
        createAccountForTest(Account.AccountType.CHECKING,3000,LocalDate.now().minusDays(1));
        assertEquals(3, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testLastInterestPaidTwoDaysAgo()
    {
        createAccountForTest(Account.AccountType.CHECKING,3000,LocalDate.now().minusDays(2));
        assertEquals(6.003, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        createAccountForTest(Account.AccountType.SAVINGS,3000,LocalDate.now().minusDays(1));
        assertEquals(5.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        bank.getCustomers().get(0).getAccounts().clear();
        createAccountForTest(Account.AccountType.SAVINGS,1000,LocalDate.now().minusDays(1));
        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        createAccountForTest(Account.AccountType.MAXI_SAVINGS,4000,LocalDate.now().minusDays(1));
        t.setTransactionDate(LocalDate.of(2020,9,23));
        bank.getCustomers().get(0).getAccounts().get(0).addTransaction(t);
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void maxi_savings_account_with_withdraw_in_ten_days() {
        createAccountForTest(Account.AccountType.MAXI_SAVINGS,4000,LocalDate.now().minusDays(1));
        t.setTransactionDate(LocalDate.of(2020,10,9));
        bank.getCustomers().get(0).getAccounts().get(0).addTransaction(t);
        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    private void createAccountForTest(Account.AccountType accountType, double amount, LocalDate dateOfLastInterestEarned)
    {
        Account checkingAccount = new Account(accountType);
        bank.getCustomers().clear();
        bank.addCustomer(testCustomer.openAccount(checkingAccount));
        checkingAccount.setDateOfLastInterestsEarned(dateOfLastInterestEarned);
        checkingAccount.deposit(amount);
    }
}
