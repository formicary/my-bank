package com.abc;

import org.junit.Ignore;
import org.junit.Test;

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
}
