package com.abc;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;
import com.abc.Account.MaxiSavingsAccount;
import com.abc.Account.SavingsAccount;
import com.abc.Exception.IllegalInterestException;
import com.abc.Utilities.TestingDateProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    TestingDateProvider provider;

    private static final double DOUBLE_DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {
        provider = TestingDateProvider.getInstance();
        DateProvider.setInstance(provider);
    }

    @Test
    public void depositTest(){
        Account account = new CheckingAccount();
        account.deposit(100.0);
        Transaction t = account.getLastTransaction();
        assertEquals(100.0, account.getBalance(), DOUBLE_DELTA);
        assertEquals(100.0, t.amount, DOUBLE_DELTA);
        assertEquals(Transaction.TransctionType.DEPOSIT, t.type);
        assertEquals(1, account.getNumberOfTransactions());
    }

    @Test
    public void depositFailTest(){
        Account account = new CheckingAccount();
        boolean failed = false;
        try{
            account.deposit(0);
        } catch (IllegalArgumentException e){
            assertEquals("amount must be greater than zero", e.getMessage());
            failed = true;
        }
        assertTrue(failed);
        assertEquals(0, account.getBalance(), DOUBLE_DELTA);
        assertEquals(0, account.getNumberOfTransactions());
    }

    @Test
    public void withdrawTest(){
        Account account = new CheckingAccount();
        account.deposit(100.0);
        assertEquals(100.0, account.getBalance(), DOUBLE_DELTA);
        account.withdraw(75);
        Transaction t = account.getLastTransaction();
        assertEquals(25.0, account.getBalance(), DOUBLE_DELTA);
        assertEquals(-75.0, t.amount, DOUBLE_DELTA);
        assertEquals(Transaction.TransctionType.WITHDRAWAL, t.type);
    }

    @Test
    public void withdrawFailTest(){
        Account account = new CheckingAccount();
        account.deposit(100.0);
        boolean failed = false;
        assertEquals(100.0, account.getBalance(), DOUBLE_DELTA);
        try {
            account.withdraw(0);
        } catch (IllegalArgumentException e){
            assertEquals("amount must be greater than zero", e.getMessage());
            failed = true;
        }
        assertTrue(failed);
        assertEquals(100.0, account.getBalance(), DOUBLE_DELTA);
        assertEquals(1, account.getNumberOfTransactions());
    }

    @Test
    public void transferTest(){
        Account account = new CheckingAccount();
        account.recieveTransfer(123.0);
        Transaction t = account.getLastTransaction();
        assertEquals(123.0, account.getBalance(), DOUBLE_DELTA);
        assertEquals(123.0, t.amount, DOUBLE_DELTA);
        assertEquals(Transaction.TransctionType.TRANSFER, t.type);
        assertEquals(1, account.getNumberOfTransactions());
    }

    @Test
    public void transferFailTest(){
        Account account = new CheckingAccount();
        boolean failed = false;
        try{
            account.recieveTransfer(0);
        } catch (IllegalArgumentException e){
            assertEquals("amount must be greater than zero", e.getMessage());
            failed = true;
        }
        assertTrue(failed);
        assertEquals(0, account.getBalance(), DOUBLE_DELTA);
        assertEquals(0, account.getNumberOfTransactions());
    }

    @Test
    public void dailyInterestTest(){
        Account account = new CheckingAccount();
        boolean failed = false;
        try {
            account.accrueDailyInterest();
        } catch (IllegalInterestException e){
            failed = true;
            assertEquals("No balance to calculate interest", e.getMessage());
        }
        assertTrue(failed);
        TestingDateProvider.getInstance().setTestingModeOn(80);
        DateProvider.setInstance(TestingDateProvider.getInstance());
        for(int i = 1; i < 91; i++){
            account.deposit(i*10);
        }
        account.withdraw(950.0);
        TestingDateProvider.getInstance().setTestingModeOff();
        assertEquals(91, account.getNumberOfTransactions());
        assertEquals(40000.0, account.getBalance(), DOUBLE_DELTA);

        account.accrueDailyInterest();
        Transaction t = account.getLastTransaction();
        assertEquals(92, account.getNumberOfTransactions());
        assertEquals(40000.0 + (40.0/365.0), account.getBalance(), DOUBLE_DELTA);
        assertEquals(40.0/365.0, t.amount, DOUBLE_DELTA);

        failed = false;
        try{
            account.accrueDailyInterest();
        } catch (IllegalInterestException e){
            failed = true;
            assertEquals("Interest already accrued today", e.getMessage());
        }
        assertTrue(failed);
    }

    @Test
    public void checkingAccountInterestTest() {
        Account account = new CheckingAccount();
        account.deposit(100.0);
        account.accrueDailyInterest();
        assertEquals(0.1/365, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountInterestTest() {
        Account account = new SavingsAccount();
        account.deposit(1500.0);
        account.accrueDailyInterest();
        assertEquals(2.0/365, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestTest() {
        Account account = new MaxiSavingsAccount();
        provider.setTestingModeOn(10);
        account.deposit(3000.0);
        provider.setTestingModeOff();
        account.accrueDailyInterest();
        assertEquals(150.0/365, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestLowerRateTest() {
        Account account = new MaxiSavingsAccount();
        provider.setTestingModeOn(9);
        account.deposit(3000.0);
        provider.setTestingModeOff();
        account.accrueDailyInterest();
        assertEquals(3.0/365, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void getLastTransactionTest(){
        Account account = new SavingsAccount();
        account.deposit(100);
        account.deposit(102);
        account.withdraw(12);
        Transaction t = account.getLastTransaction();
        assertEquals(Transaction.TransctionType.WITHDRAWAL, t.type);
        assertEquals(-12, t.amount, DOUBLE_DELTA);
    }

    @Test
    public void getTransactionTest(){
        Account account = new CheckingAccount();
        account.deposit(235);
        account.deposit(2134);
        account.withdraw(122);
        Transaction t = account.getTransaction(1);
        assertEquals(Transaction.TransctionType.DEPOSIT, t.type);
        assertEquals(2134, t.amount, DOUBLE_DELTA);
    }

    @Test
    public void getBalanceTest(){
        Account account = new CheckingAccount();
        account.deposit(123);
        account.deposit(1234);
        account.withdraw(357);
        assertEquals(1000.0, account.getBalance(), DOUBLE_DELTA);
        assertEquals(account.getBalance(), account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void getAccountTypeTest(){
        Account account = new CheckingAccount();
        assertEquals(Account.AccountType.CHECKING, account.getAccountType());
        account = new SavingsAccount();
        assertEquals(Account.AccountType.SAVINGS, account.getAccountType());
        account = new MaxiSavingsAccount();
        assertEquals(Account.AccountType.MAXI_SAVINGS, account.getAccountType());
    }


}