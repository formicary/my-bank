package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void TestOpenAccount_OneAccount_ShouldReturnOne() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void TestOpenAccount_TwoAccounts_ShouldReturnTwo(){
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));

        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void TestOpenAccount_ThreeAccounts_ShouldReturnThree() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));

        assertEquals(3, oscar.getNumberOfAccounts());
    }


    @Test (expected=UnsupportedOperationException.class)
    public void TestNumberOfCustomerAccounts_AddSameAccount() {
        Bank bank = new Bank();
        Customer sam = new Customer("Sam");
        Account account = new Account(Account.CHECKING);

        bank.addCustomer(sam);
        sam.openAccount(account);
        sam.openAccount(account);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_DifferentCustomerAccounts(){

        Bank bank = new Bank();

        Customer saul = new Customer("Saul");
        Customer kim = new Customer("Kim");
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        bank.addCustomer(saul);
        saul.openAccount(checkingAccount);
        kim.openAccount(savingsAccount);

        checkingAccount.deposit(1000);

        saul.transferBetweenAccount(checkingAccount, savingsAccount, 1000);
    }

    @Test
    public void TestTransferBetweenAccounts_SameCustomerAccount() {
        Bank bank = new Bank();
        Customer saul = new Customer("Saul");

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        bank.addCustomer(saul);
        saul.openAccount(checkingAccount);
        saul.openAccount(savingsAccount);

        checkingAccount.deposit(500);

        saul.transferBetweenAccount(checkingAccount, savingsAccount, 300);

        assertEquals(300, savingsAccount.getBalance(), 0);

    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_SameAccount(){

        Bank bank = new Bank();
        Customer henry = new Customer("Henry");
        Account account = new Account(Account.SAVINGS);

        bank.addCustomer(henry);
        henry.openAccount(account);
        account.deposit(150);

        henry.transferBetweenAccount(account, account, 100);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_NegativeAmount_ShouldThrowException(){

        Bank bank = new Bank();

        Customer barbara = new Customer("Barbara");

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        bank.addCustomer(barbara);
        barbara.openAccount(checkingAccount);
        barbara.openAccount(savingsAccount);

        checkingAccount.deposit(250);

        barbara.transferBetweenAccount(checkingAccount, savingsAccount, -250);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_AmountBiggerThanBalance(){

        Bank bank = new Bank();

        Customer murray = new Customer("Murray");

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        bank.addCustomer(murray);
        murray.openAccount(checkingAccount);
        murray.openAccount(savingsAccount);

        checkingAccount.deposit(500);

        murray.transferBetweenAccount(checkingAccount, savingsAccount, 1000);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransferBetweenAccounts_ValidAmount(){

        Bank bank = new Bank();

        Customer bob = new Customer("Bob");

        Account checkingAccount = new Account(Account.SAVINGS);
        Account savingsAccount = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(bob);
        bob.openAccount(checkingAccount);
        bob.openAccount(savingsAccount);

        checkingAccount.deposit(1750);

        bob.transferBetweenAccount(checkingAccount, savingsAccount, 1750);
    }

    @Test
    public void TestEarnedInterest_ShouldBeCorrect(){

        Account savingsAccount = new Account(Account.SAVINGS);

        Customer grigor = new Customer("Grigor");

        grigor.openAccount(savingsAccount);

        savingsAccount.deposit(4000.0);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        double expected = Double.valueOf(Account.decimalFormatter.format(7.0 / daysInYear * dayOfYear));

        assertEquals(expected, savingsAccount.getEarnedInterest(), 0D);
    }
}
