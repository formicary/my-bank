package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void TestOpenAccount_OneAccount() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        Account account = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(sarah);
        sarah.openAccount(account);
        account.deposit(800);

        assertEquals(1, sarah.getNumberOfAccounts());
    }

    @Test
    public void TestOpenTwoAccounts() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        sarah.openAccount(new Account(1));
        sarah.openAccount(new Account(2));

        assertEquals(2, sarah.getNumberOfAccounts());
    }


    @Test
    public void TestOpenThreeAccounts() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        sarah.openAccount(new Account(1));
        sarah.openAccount(new Account(2));
        sarah.openAccount(new Account(3));

        assertEquals(3, sarah.getNumberOfAccounts());
    }

    @Test (expected = IllegalArgumentException.class)
    public void DepositNegativeAmountTest_ShouldThrowException() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account account = new Account(Account.SAVINGS);

        bank.addCustomer(bill);
        bill.openAccount(account);
        account.deposit(-300);
    }

    @Test
    public void DepositValidAmount_ShouldUpdateBalanceCorrectly() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        Account account = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(sarah);
        sarah.openAccount(account);
        account.deposit(100);

        assertEquals(100, account.getBalance(), 0);
    }

    @Test
    public void TestCompleteTransaction_TwoTransactions_ShouldAddTransactionsToList() {
        Bank bank = new Bank();

        Customer walter = new Customer("Walter");
        Account account = new Account(Account.CHECKING);

        bank.addCustomer(walter);
        walter.openAccount(account);

        account.deposit(2500);
        account.withdraw(350);

        assertEquals(2, account.getTransactions().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestWithdraw_NegativeAmount() {
        Bank bank = new Bank();
        Customer daniel = new Customer("Daniel");
        Account account = new Account(Account.SAVINGS);

        bank.addCustomer(daniel);
        daniel.openAccount(account);
        account.deposit(5332);
        account.withdraw(-230);
    }

    @Test (expected = IllegalArgumentException.class)
    public void WithdrawAmountGreaterThanBalance() {
        Bank bank = new Bank();
        Customer saul= new Customer("Saul");
        Account account = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(saul);
        saul.openAccount(account);
        account.deposit(1000);
        account.withdraw(1500);
    }


    @Test
    public void TestTwoTransactions_ShouldAddTransactionsToAccount() {
        Bank bank = new Bank();

        Customer walter = new Customer("Walter");
        Account account = new Account(Account.CHECKING);

        bank.addCustomer(walter);
        walter.openAccount(account);

        account.deposit(2500);
        account.withdraw(350);

        assertEquals(2, account.getTransactions().size());
    }


    @Test (expected=IllegalArgumentException.class)
    public void TestWithdraw_AmountGreaterThanBalance() {
        Bank bank = new Bank();
        Customer kim = new Customer("Kim");
        Account account = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(kim);
        kim.openAccount(account);
        account.deposit(1000);
        account.withdraw(1500);
    }

    @Test
    public void TestWithdraw_ValidAmount_ShouldUpdateBalanceCorrectly() {
        Bank bank = new Bank();
        Customer kim = new Customer("Kim");
        Account account = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(kim);
        kim.openAccount(account);
        account.deposit(2000);
        account.withdraw(500);

        assertEquals(1500.0, account.getBalance(), 0);
    }

    @Test
    public void TestCheckingAccountEarnedInterest_ShouldBeCorrect() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill");

        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1500.0);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        double expected = Double.valueOf(Account.decimalFormatter.format(1.5 / daysInYear * dayOfYear));

        assertEquals(expected, checkingAccount.getEarnedInterest(), 0D);
    }

    @Test
    public void TestSavingsAccountEarnedInterest_ShouldBeCorrect() {
        Bank bank = new Bank();
        Customer saul = new Customer("Saul");
        Account savingsAccount = new Account(Account.SAVINGS);

        bank.addCustomer(saul);
        saul.openAccount(savingsAccount);

        savingsAccount.deposit(2000.0);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        double expected = Double.valueOf(Account.decimalFormatter.format(3.0 / daysInYear * dayOfYear));

        assertEquals(expected, savingsAccount.getEarnedInterest(), 0D);
    }

    @Test
    public void TestMaxiSavingsAccountEarnedInterest_ShouldBeCorrect() {
        Bank bank = new Bank();
        Customer saul = new Customer("Saul");
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(saul);
        saul.openAccount(maxiSavingsAccount);

        maxiSavingsAccount.deposit(3000.0);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        double expected = Double.valueOf(Account.decimalFormatter.format(150.0 / daysInYear * dayOfYear));

        assertEquals(expected, maxiSavingsAccount.getEarnedInterest(), 0D);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestAccountHasWithdrawalInPastDays_NegativeNumberOfDays() {
        Account account = new Account(Account.SAVINGS);
        account.hasWithdrawInLastNDays((short) -3);
    }

    @Test
    public void TestAccountHasWithdrawalInPast10Days_TransactionMadeToday_ShouldReturnTrue() {
        Customer chuck = new Customer("Chuck");
        Account account = new Account(Account.SAVINGS);

        chuck.openAccount(account);
        account.deposit(120);
        account.withdraw(50);

        assertTrue(account.hasWithdrawInLastNDays((short) 10));
    }

    @Test
    public void TestAccountHasWithdrawalInPast10Days_TransactionMade12DaysAgo_ShouldReturnTrue() {
        Customer jessie = new Customer("Jessie");
        Account account = new Account(Account.SAVINGS);

        jessie.openAccount(account);
        account.deposit(120);
        account.withdraw(50);

        account.getTransactions().get(1).setDateToNDaysAgo((short) 12);
        assertEquals(false, account.hasWithdrawInLastNDays((short) 10));
    }

}