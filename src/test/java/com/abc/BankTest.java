package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.AccountCreator;
import com.abc.Accounts.AccountType;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummarySingleAccount() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);
        john.openAccount(checkingAccount);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummaryReport());
    }

    @Test
    public void testCustomerSummaryTwoAccounts() {
        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(checkingAccount).openAccount(savingsAccount);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummaryReport());
    }

    @Test
    public void testTotalInterestPaidCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidSavingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidMaxiSavingsAccountWithoutWithdrawals() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = AccountCreator.createAccount(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidMaxiSavingsAccountNoWithdrawalsWithinTenDays() {
        // Override the date set on the transaction object to test interest (Maxi Savings Account)
        Bank bank = new Bank();
        Account maxiSavingsAccount = AccountCreator.createAccount(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
        Instant date = Instant.parse("2019-01-01T00:00:01.00Z");//DateProvider.generateDate(2019, 0, 1);
        maxiSavingsAccount.deposit(2000.0);
        maxiSavingsAccount.getTransactions().get(0).setTransactionDate(date);
        maxiSavingsAccount.withdraw(1000.0);
        maxiSavingsAccount.getTransactions().get(1).setTransactionDate(date);
        assertEquals(50.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidMaxiSavingsAccountWithdrawalsWithinTenDays() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = AccountCreator.createAccount(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(1000.0);
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testReportTotalInterestPaidAllAccounts() {
        Bank bank = new Bank();
        Account savingsAccountBill = AccountCreator.createAccount(AccountType.SAVINGS);
        Account savingsAccountJohn = AccountCreator.createAccount(AccountType.SAVINGS);

        savingsAccountBill.deposit(1000.0);
        savingsAccountJohn.deposit(1000.0);

        Account checkingAccountBill = AccountCreator.createAccount(AccountType.CHECKING);
        Account checkingAccount_John = AccountCreator.createAccount(AccountType.CHECKING);

        checkingAccountBill.deposit(1000.0);
        checkingAccount_John.deposit(1000.0);

        bank.addCustomer(new Customer("Bill").openAccount(savingsAccountBill).openAccount(checkingAccountBill));
        bank.addCustomer(new Customer("John").openAccount(savingsAccountJohn).openAccount(checkingAccount_John));

        assertEquals(4.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testReportTotalCustomersAndAccounts() {
        Bank bank = new Bank();
        Account savingAccount_Bill = AccountCreator.createAccount(AccountType.SAVINGS);

        Account savingAccount_John = AccountCreator.createAccount(AccountType.SAVINGS);
        Account checkingAccount_John = AccountCreator.createAccount(AccountType.CHECKING);

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
