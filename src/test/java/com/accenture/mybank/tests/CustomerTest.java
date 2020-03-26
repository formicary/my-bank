package com.accenture.mybank.tests;

import org.junit.Ignore;
import org.junit.Test;

import com.accenture.mybank.Customer;
import com.accenture.mybank.accounts.Account;
import com.accenture.mybank.accounts.CheckingAccount;
import com.accenture.mybank.accounts.SavingsAccount;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test 
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer anusha = new Customer("Anusha").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        assertEquals("Statement for Anusha\n" +
                "\n" +
                "Checking Account" +
                "  deposit 100.0\n" +
                "Total 100.0\n" +
                "\n" +
                "Savings Account" +
                "  deposit 4000.0\n" +
                "  withdrawal -200.0\n" +
                "Total 3800.0\n" +
                "\n" +
                "Total: 3900.0", anusha.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer anusha = new Customer("Akshaya").openAccount(new SavingsAccount());
        assertEquals(1, anusha.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer anu = new Customer("Anu")
                .openAccount(new SavingsAccount());
        anu.openAccount(new CheckingAccount());
        assertEquals(2, anu.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer akhil = new Customer("Akhil")
                .openAccount(new SavingsAccount());
        akhil.openAccount(new CheckingAccount());
        assertEquals(3, akhil.getNumberOfAccounts());
    }

    @Test
    public void testTransferBetweenAccounts(){
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        Customer anu = new Customer("Anu")
                .openAccount(savingsAccount);
        anu.openAccount(checkingAccount);

        savingsAccount.deposit(5000);
        anu.transferMoneyTo(savingsAccount, checkingAccount, 2000);

        assertEquals(2000.0, checkingAccount.getTotalAmount(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferBetweenAccountsThrowsException(){
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        Customer anusha = new Customer("Anusha")
                .openAccount(savingsAccount);

        savingsAccount.deposit(5000);
        anusha.transferMoneyTo(savingsAccount, checkingAccount, 2000);
    }
}