package com.abc;

import org.junit.Test;

import com.abc.Account.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tester for the methods of the Account class.
 * @author Filippos Zofakis
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-10;

    @Test(expected = IllegalArgumentException.class)
    public void testDeposit() {
        Account checkingAccount = new Account(Type.CHECKING);
        checkingAccount.deposit(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawal() {
        Account checkingAccount = new Account(Type.CHECKING);
        checkingAccount.withdraw(0);
    }

    @Test // Test customer account type.
    public void testType() {
        Account account = new Account(Type.MAXI_SAVINGS);
        assertTrue(account.getType() == Type.MAXI_SAVINGS);
    }

    @Test // Test customer checking account interest.
    public void testCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Type.CHECKING);
        Customer bill = new Customer("Bill", "Gates");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        double depositAmount = 100;
        double checkingInterestRate = bank.getDailyInterestRate(checkingAccount.getType());
        checkingAccount.deposit(depositAmount);
        assertEquals(0, checkingAccount.getTotalInterestEarned(), DOUBLE_DELTA);

        bank.run();
        assertEquals(depositAmount * checkingInterestRate, checkingAccount.getTotalInterestEarned(), DOUBLE_DELTA);
        System.out.println("Checking account interest is calculated and accrues correctly.");
    }

    @Test // Test customer savings account interest.
    public void testSavingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Type.SAVINGS);
        Customer bill = new Customer("Bill", "Gates");
        bank.addCustomer(bill);
        bill.openAccount(savingsAccount);

        double depositAmount = 1500;
        savingsAccount.deposit(depositAmount);
        assertEquals(0, savingsAccount.getTotalInterestEarned(), DOUBLE_DELTA);

        bank.run();
        double checkingInterestRate = bank.getDailyInterestRate(Type.CHECKING);
        double savingsInterestRate = bank.getDailyInterestRate(savingsAccount.getType());
        assertEquals(1000 * checkingInterestRate + (depositAmount -1000) * savingsInterestRate, savingsAccount.getTotalInterestEarned(), DOUBLE_DELTA);
        System.out.println("Savings account interest is calculated and accrues correctly.");
    }

    @Test // Test customer maxi-savings account interest.
    public void testMaxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Type.MAXI_SAVINGS);
        Customer bill = new Customer("Bill", "Gates");
        bank.addCustomer(bill);
        bill.openAccount(maxiSavingsAccount);
  
        double depositAmount = 3000;
        maxiSavingsAccount.deposit(depositAmount);

        assertEquals(0, maxiSavingsAccount.getTotalInterestEarned(), DOUBLE_DELTA);

        bank.run();
        // Testing new interest of 0.05 for Maxi-Savings accounts.
        double maxiSavingsInterestRate = bank.getDailyInterestRate(maxiSavingsAccount.getType());
        assertEquals(depositAmount * maxiSavingsInterestRate, maxiSavingsAccount.getTotalInterestEarned(), DOUBLE_DELTA);
        
        // Testing interest on Maxi-Savings accounts after recent withdrawal.
        Account maxiSavingsAccount2 = new Account(Type.MAXI_SAVINGS);
        bill.openAccount(maxiSavingsAccount2);
        maxiSavingsAccount2.deposit(depositAmount);
        double withdrawalAmount = 100;
        maxiSavingsAccount2.withdraw(withdrawalAmount);
        
        assertEquals(0, maxiSavingsAccount2.getTotalInterestEarned(), DOUBLE_DELTA);

        double checkingInterestRate = bank.getDailyInterestRate(Type.CHECKING);
        bank.run();
        assertEquals((depositAmount - withdrawalAmount) * checkingInterestRate, maxiSavingsAccount2.getTotalInterestEarned(), DOUBLE_DELTA);

        System.out.println("Maxi-Savings account interest is calculated and accrues correctly.");
    }
}
