package com.abc;

import org.junit.Test;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    @Test
    public void TestConstructor_ShouldReturnAccountInstance() {
        Account account = new Account(Account.ACCOUNT_TYPE.CHECKING);
        assertTrue(account instanceof Account);
    }

    @Test (expected=UnsupportedOperationException.class)
    public void TestDeposit_CustomerAccountNotLinked_ShouldThrowException() {
        Account account = new Account(Account.ACCOUNT_TYPE.MAXI_SAVINGS);
        account.deposit(3000);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestDeposit_NegativeAmount_ShouldThrowException() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account account = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        bank.addCustomer(bill);
        bill.openAccount(account);
        account.deposit(-300);
    }

    @Test
    public void TestDeposit_ValidAmount_ShouldUpdateBalanceCorrectly() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        Account account = new Account(Account.ACCOUNT_TYPE.MAXI_SAVINGS);

        bank.addCustomer(sarah);
        sarah.openAccount(account);
        account.deposit(800);

        assertEquals(800.0, account.getBalance());
    }

    @Test
    public void TestPrivateCompleteTransaction_TwoTransactions_ShouldAddTransactionsToAccount() {
        Bank bank = new Bank();

        Customer walter = new Customer("Walter");
        Account account = new Account(Account.ACCOUNT_TYPE.CHECKING);

        bank.addCustomer(walter);
        walter.openAccount(account);

        account.deposit(2500);
        account.withdraw(350);

        assertEquals(2, account.getTransactions().size());
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestWithdraw_NegativeAmount_ShouldThrowException() {
        Bank bank = new Bank();
        Customer daniel = new Customer("Daniel");
        Account account = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        bank.addCustomer(daniel);
        daniel.openAccount(account);
        account.deposit(630);
        account.withdraw(-230);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestWithdraw_AmountGreaterThanBalance_ShouldThrowException() {
        Bank bank = new Bank();
        Customer kim = new Customer("Kim");
        Account account = new Account(Account.ACCOUNT_TYPE.MAXI_SAVINGS);

        bank.addCustomer(kim);
        kim.openAccount(account);
        account.deposit(1000);
        account.withdraw(1500);
    }

    @Test
    public void TestWithdraw_ValidAmount_ShouldUpdateBalanceCorrectly() {
        Bank bank = new Bank();
        Customer nick = new Customer("Nick");
        Account account = new Account(Account.ACCOUNT_TYPE.MAXI_SAVINGS);

        bank.addCustomer(nick);
        nick.openAccount(account);
        account.deposit(2000);
        account.withdraw(500);

        assertEquals(1500.0, account.getBalance());
    }

    @Test
    public void TestCheckingAccountEarnedInterest_ShouldBeCorrect() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.ACCOUNT_TYPE.CHECKING);
        Customer bill = new Customer("Bill");

        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1500.0);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        double expected = Double.valueOf(ReportFormatter.decimalFormatter.format(1.5 / daysInYear * dayOfYear));

        assertEquals(expected, checkingAccount.getEarnedInterest(), 0D);
    }

    @Test
    public void TestSavingsAccountEarnedInterest_ShouldBeCorrect() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account savingsAccount = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        bank.addCustomer(bill);
        bill.openAccount(savingsAccount);

        savingsAccount.deposit(2000.0);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        double expected = Double.valueOf(ReportFormatter.decimalFormatter.format(3.0 / daysInYear * dayOfYear));

        assertEquals(expected, savingsAccount.getEarnedInterest(), 0D);
    }

    @Test
    public void TestMaxiSavingsAccountEarnedInterest_ShouldBeCorrect() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account maxiSavingsAccount = new Account(Account.ACCOUNT_TYPE.MAXI_SAVINGS);

        bank.addCustomer(bill);
        bill.openAccount(maxiSavingsAccount);

        maxiSavingsAccount.deposit(3000.0);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        double expected = Double.valueOf(ReportFormatter.decimalFormatter.format(150.0 / daysInYear * dayOfYear));

        assertEquals(expected, maxiSavingsAccount.getEarnedInterest(), 0D);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestAccountHasWithdrawalInPastDays_NegativeNumberOfDays_ShouldThrowException() {
        Account account = new Account(Account.ACCOUNT_TYPE.SAVINGS);
        account.hasWithdrawalInTheLastNDays((short) -3);
    }

    @Test
    public void TestAccountHasWithdrawalInPast10Days_TransactionMadeToday_ShouldReturnTrue() {
        Customer edward = new Customer("Edward");
        Account account = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        edward.openAccount(account);
        account.deposit(120);
        account.withdraw(50);

        assertEquals(true, account.hasWithdrawalInTheLastNDays((short) 10));
    }

    @Test
    public void TestAccountHasWithdrawalInPast10Days_TransactionMade12DaysAgo_ShouldReturnTrue() {
        Customer victoria = new Customer("Victoria");
        Account account = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        victoria.openAccount(account);
        account.deposit(120);
        account.withdraw(50);

        account.getTransactions().get(1).setDateToNDaysAgo((short) 12);
        assertEquals(false, account.hasWithdrawalInTheLastNDays((short) 10));
    }
}
