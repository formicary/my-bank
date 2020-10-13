package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BankTest {
    public static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;
    private Customer testCustomer;
    private Transaction t = new Transaction(-1000, Transaction.TransactionType.WITHDRAW);
    private CheckingAccount checkingAccount = new CheckingAccount();
    private SavingsAccount savingsAccount = new SavingsAccount();
    private MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();

    @Test
    public void checkingAccount() {
        createAccountForTest(checkingAccount, 3000, LocalDate.now().minusDays(0));
        assertEquals(0.00821917808219178, bank.totalInterestPaid(), DOUBLE_DELTA);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testLastInterestPaidTwoDaysAgo() {
        createAccountForTest(checkingAccount, 3000, LocalDate.now().minusDays(1));
        assertEquals(0.00821917808219178, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        createAccountForTest(savingsAccount, 3000, LocalDate.now().minusDays(0));
        assertEquals(5.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        savingsAccount = new SavingsAccount();
        createAccountForTest(savingsAccount, 1000, LocalDate.now().minusDays(0));
        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        createAccountForTest(maxiSavingsAccount, 4000, LocalDate.now().minusDays(0));
        t.setTransactionDate(LocalDate.of(2020, 9, 23));
        bank.getCustomers().get(0).getAccounts().get(0).addTransaction(t);
        assertEquals(0.547945205479452, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_with_withdraw_in_ten_days() {
        createAccountForTest(maxiSavingsAccount, 4000, LocalDate.now().minusDays(0));
        t.setTransactionDate(LocalDate.of(2020, 10, 9));
        bank.getCustomers().get(0).getAccounts().get(0).addTransaction(t);
        assertEquals(0.010958904109589041, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    private void createAccountForTest(Account accountType, double amount, LocalDate dateOfLastInterestEarned) {
        bank = new Bank();
        testCustomer = new Customer("John");
        bank.addCustomer(testCustomer.openAccount(accountType));
        accountType.setDateOfLastInterestsEarned(dateOfLastInterestEarned);
        accountType.deposit(amount);
    }
}
