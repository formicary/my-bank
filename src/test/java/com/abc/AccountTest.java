package com.abc;

import com.abc.MainClasses.Customer;
import com.abc.AccountTypes.CheckingAccount;
import com.abc.AccountTypes.MaxiSavingsAccount;
import com.abc.AccountTypes.SavingsAccount;
import com.abc.MainClasses.Account;
import com.abc.MainClasses.AccountType;
import com.abc.MainClasses.Bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//Refactored initialization of account types according to implemented inheritance
public class AccountTest {
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());

        Customer jack = new Customer("Jack").openAccount(new MaxiSavingsAccount());

        assertEquals(2, oscar.getNumberOfAccounts());
        assertEquals(1, jack.getNumberOfAccounts());

    }

    @Test
    public void testFiveAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());

        Customer bill = new Customer("Bill")
                .openAccount(new CheckingAccount());
        bill.openAccount(new SavingsAccount());
        
        assertEquals(3, oscar.getNumberOfAccounts());
        assertEquals(2, bill.getNumberOfAccounts());
    }

    //Transfer between accounts test
    @Test
    public void testTransferFunds() throws Exception {
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar");
        bank.addCustomer(oscar);

        Account oscarCheckingAccount = new CheckingAccount();
        Account oscarSavingsAccount = new SavingsAccount();
        Account oscarMaxiSavingsAccount = new MaxiSavingsAccount();

        oscar.openAccount(oscarCheckingAccount);
        oscar.openAccount(oscarSavingsAccount);
        oscar.openAccount(oscarMaxiSavingsAccount);

        oscarCheckingAccount.deposit(740.86);
        oscarSavingsAccount.deposit(300);
        oscarMaxiSavingsAccount.deposit(250);
        
        double[] amountInAccounts = oscar.transferFunds(80, oscarSavingsAccount, oscarCheckingAccount);

        assertEquals(220, amountInAccounts[0], 1e-15);
        assertEquals(820.86, amountInAccounts[1] , 1e-15);
    }
    
    @Test
    public void testCheckingAccount() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(100);
        checkingAccount.withdraw(59);

        assertEquals( 41.0, checkingAccount.getBalance(), 1e-15);
    }

     @Test
    public void testSavingsAccount() {
        Account savingsAccount = new SavingsAccount();
        savingsAccount.deposit(135.09);
        savingsAccount.withdraw(60);

        assertEquals(75.09, savingsAccount.getBalance(), 1e-15);
    }

     @Test
    public void testMaxiSavingsAccount() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(120);
        maxiSavingsAccount.withdraw(73.50);

        assertEquals(46.50, maxiSavingsAccount.getBalance(), 1e-15);
    }

    @Test
    public void testNewAccountInitialization() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);

        assertEquals(AccountType.CHECKING, checkingAccount.getAccountType());
        assertTrue("Unexpected account type", savingsAccount.getAccountType() == AccountType.SAVINGS);
        assertEquals(AccountType.MAXI_SAVINGS, maxiSavingsAccount.getAccountType());
    }
}