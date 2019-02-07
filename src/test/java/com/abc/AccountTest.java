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
    public void TestWithdraw_NegativeAmount_ShouldThrowException() {
        Bank bank = new Bank();
        Customer daniel = new Customer("Daniel");
        Account account = new Account(Account.SAVINGS);

        bank.addCustomer(daniel);
        daniel.openAccount(account);
        account.deposit(5332);
        account.withdraw(-230);
    }

    @Test (expected = IllegalArgumentException.class)
    public void WithdrawAmountGreaterThanBalance_ShouldThrowException() {
        Bank bank = new Bank();
        Customer saul= new Customer("Saul");
        Account account = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(saul);
        saul.openAccount(account);
        account.deposit(1000);
        account.withdraw(1500);
    }

    @Test
    public void TestWithdraw_ValidAmount_ShouldUpdateBalanceCorrectly() {
        Bank bank = new Bank();
        Customer nick = new Customer("Nick");
        Account account = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(nick);
        nick.openAccount(account);
        account.deposit(2000);
        account.withdraw(500);

        assertEquals(1500.0, account.getBalance(), 0);
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

}