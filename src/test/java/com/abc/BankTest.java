package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
        The following test is used to see if the correct interest rate is applied for a new account.
        The expected interest rate is of 0.1%.
     */
    @Test
    public void maxi_savings_newAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(2000.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
        The following test is used to see if the correct interest rate is applied for an account that was open for more than 10 days
        and has had no withdrawals.
        The expected interest rate is of 5%.
     */
    @Test
    public void maxi_savings_oldAccount() {
        DateProvider dateProvider = new DateProvider();
        Date oldDate = dateProvider.oldDate(dateProvider.now(), -12);

        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        checkingAccount.setAccountDate(oldDate);
        bank.addCustomer(new Customer("James").openAccount(checkingAccount));

        checkingAccount.deposit(2000.0);

        assertEquals(100.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
       The following test is used to see if the correct interest rate is applied for Maxi-Savings account when no withdrawals in the past 10 days.
       The expected interest rate is of 5%.
    */
    @Test
    public void maxi_savings_noWithdrawalAccount() {
        DateProvider dateProvider = new DateProvider();
        Date oldAccountDate = dateProvider.oldDate(dateProvider.now(), -18);
        Date oldDepositDate = dateProvider.oldDate(dateProvider.now(), -17);
        //Withdrawal date set 15 days before today's date
        Date oldWithdrawalDate = dateProvider.oldDate(dateProvider.now(), -15);

        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        checkingAccount.setAccountDate(oldAccountDate);
        bank.addCustomer(new Customer("Harry").openAccount(checkingAccount));

        checkingAccount.deposit(5000.0);
        checkingAccount.withdraw(500.0);

        checkingAccount.transactions.get(0).setTransactionDate(oldDepositDate);
        checkingAccount.transactions.get(1).setTransactionDate(oldWithdrawalDate);

        //Expected value was calculated by doing 5% of 4500 (5000 - 500)
        assertEquals(225.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /*
       The following test is used to see if the correct interest rate is applied for Maxi-Savings account when withdrawing within the past 10 days.
       The expected interest rate is of 1%.
    */
    @Test
    public void maxi_savings_WithdrawalAccount() {
        DateProvider dateProvider = new DateProvider();
        Date oldAccountDate = dateProvider.oldDate(dateProvider.now(), -18);
        Date oldDepositDate = dateProvider.oldDate(dateProvider.now(), -17);
        //Withdrawal date set 5 days before today's date
        Date oldWithdrawalDate = dateProvider.oldDate(dateProvider.now(), -5);

        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        checkingAccount.setAccountDate(oldAccountDate);
        bank.addCustomer(new Customer("Harry").openAccount(checkingAccount));

        checkingAccount.deposit(5000.0);
        checkingAccount.withdraw(500.0);

        checkingAccount.transactions.get(0).setTransactionDate(oldDepositDate);
        checkingAccount.transactions.get(1).setTransactionDate(oldWithdrawalDate);

        //Expected value was calculated by doing 0.1% of 4500 (5000 - 500)
        assertEquals(4.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
