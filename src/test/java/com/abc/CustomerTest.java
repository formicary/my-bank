package com.abc;

import org.junit.Test;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void TestOpenAccount_OneAccount_ShouldReturnOne(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.ACCOUNT_TYPE.SAVINGS));

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void TestOpenAccount_TwoAccounts_ShouldReturnTwo(){
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(new Account(Account.ACCOUNT_TYPE.SAVINGS));
        oscar.openAccount(new Account(Account.ACCOUNT_TYPE.CHECKING));

        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void TestOpenAccount_ThreeAccounts_ShouldReturnThree() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(new Account(Account.ACCOUNT_TYPE.SAVINGS));
        oscar.openAccount(new Account(Account.ACCOUNT_TYPE.CHECKING));
        oscar.openAccount(new Account(Account.ACCOUNT_TYPE.MAXI_SAVINGS));

        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test (expected=UnsupportedOperationException.class)
    public void TestNumberOfCustomerAccounts_AddSameAccount_ShouldThrowException() {
        Bank bank = new Bank();
        Customer sam = new Customer("Sam");
        Account account = new Account(Account.ACCOUNT_TYPE.CHECKING);

        bank.addCustomer(sam);
        sam.openAccount(account);
        sam.openAccount(account);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_SameAccount_ShouldThrowException(){

        Bank bank = new Bank();
        Customer henry = new Customer("Henry");
        Account account = new Account(Account.ACCOUNT_TYPE.CHECKING);

        bank.addCustomer(henry);
        henry.openAccount(account);
        account.deposit(150);

        henry.transferBetweenAccounts(account, account, 50);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_DifferentCustomerAccounts_ShouldThrowException(){

        Bank bank = new Bank();

        Customer kevin = new Customer("Kevin");
        Customer jamie = new Customer("Jamie");
        Account checkingAccount = new Account(Account.ACCOUNT_TYPE.CHECKING);
        Account savingsAccount = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        bank.addCustomer(kevin);
        kevin.openAccount(checkingAccount);
        jamie.openAccount(savingsAccount);

        checkingAccount.deposit(380);

        kevin.transferBetweenAccounts(checkingAccount, savingsAccount, 380);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_NegativeAmount_ShouldThrowException(){

        Bank bank = new Bank();

        Customer stuart = new Customer("Stuart");

        Account checkingAccount = new Account(Account.ACCOUNT_TYPE.CHECKING);
        Account savingsAccount = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        bank.addCustomer(stuart);
        stuart.openAccount(checkingAccount);
        stuart.openAccount(savingsAccount);

        checkingAccount.deposit(250);

        stuart.transferBetweenAccounts(checkingAccount, savingsAccount, -250);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_AmountBiggerThanBalance_ShouldThrowException(){

        Bank bank = new Bank();

        Customer robert = new Customer("Robert");

        Account checkingAccount = new Account(Account.ACCOUNT_TYPE.CHECKING);
        Account savingsAccount = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        bank.addCustomer(robert);
        robert.openAccount(checkingAccount);
        robert.openAccount(savingsAccount);

        checkingAccount.deposit(500);

        robert.transferBetweenAccounts(checkingAccount, savingsAccount, 1000);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_ValidAmount_ShouldThrowException(){

        Bank bank = new Bank();

        Customer richard = new Customer("Richard");

        Account checkingAccount = new Account(Account.ACCOUNT_TYPE.SAVINGS);
        Account savingsAccount = new Account(Account.ACCOUNT_TYPE.MAXI_SAVINGS);

        bank.addCustomer(richard);
        richard.openAccount(checkingAccount);
        richard.openAccount(savingsAccount);

        checkingAccount.deposit(1750);

        richard.transferBetweenAccounts(checkingAccount, savingsAccount, 1750);
    }

    @Test
    public void TestEarnedInterest_ShouldBeCorrect(){

        Account savingsAccount = new Account(Account.ACCOUNT_TYPE.SAVINGS);

        Customer nicole = new Customer("Nicole");

        nicole.openAccount(savingsAccount);

        savingsAccount.deposit(4000.0);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        double expected = Double.valueOf(ReportFormatter.decimalFormatter.format(7.0 / daysInYear * dayOfYear));

        assertEquals(expected, savingsAccount.getEarnedInterest(), 0D);
    }
}
