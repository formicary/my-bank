package com.accenture;

import com.accenture.accounts.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;


public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;


    @Test
    public void testAccount() {
        Account account = new CheckingAccount(11111111);
        long accNum;
        accNum = 11111111;
        assertEquals(accNum, account.getAccountNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccountArgument() {
        new CheckingAccount(1234567);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccountArgument2() {
        new CheckingAccount(-12345678);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccountArgument3() {
        new CheckingAccount(123456789);
    }

    @Test
    public void testFailedWithdraw() {
        CheckingAccount acc = new CheckingAccount(12345678);
        assertFalse(acc.withdraw(1000));
        assertFalse(acc.withdraw(1));
    }

    @Test
    public void testSuccessfulWithdraw() {
        CheckingAccount acc = new CheckingAccount(12345678);
        acc.deposit(1001,"Regular Deposit");
        assertTrue(acc.withdraw(1000));
        assertTrue(acc.withdraw(1));
    }

    @Test
    public void testLinkCustomer(){
        Customer customer = new Customer("George");
        Account account = new SavingsAccount(12345678);
        account.linkCustomer(customer);
    }

    @Test
    public void testGetCustomers_1Account(){
        Customer customer = new Customer("George");
        Account account = new SavingsAccount(12345678);
        account.linkCustomer(customer);
        assertEquals(1,account.getCustomers().size());
    }

    @Test
    public void testGetCustomers_2Accounts(){
        Customer customer = new Customer("George");
        Customer customer2 = new Customer("Vouras");
        Account account = new SavingsAccount(12345678);
        account.linkCustomer(customer);
        account.linkCustomer(customer2);
        assertEquals(2,account.getCustomers().size());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount(11111111);
        bank.addAccount(checkingAccount);

        checkingAccount.deposit(100.0,"Regular Deposit");

        assertEquals(0.1, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Account savingsAccount = new SavingsAccount(11111111);

        savingsAccount.deposit(1500.0,"Regular Deposit");

        assertEquals(2.0, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Account maxiSavingsAccount = new MaxiSavingsAccount(11111111);

        maxiSavingsAccount.deposit(3000.0,"Regular Deposit");

        assertEquals(150.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    //Additional tests
    @Test
    public void accountTestTransactions() {
        Account maxiSavingsAccount = new MaxiSavingsAccount(11111111);
        assertEquals(new ArrayList<Transaction>(), maxiSavingsAccount.getTransactions());
        maxiSavingsAccount.deposit(100,"Regular Deposit");
        assertEquals(1, maxiSavingsAccount.getTransactions().size());
        maxiSavingsAccount.withdraw(100);
        assertEquals(2, maxiSavingsAccount.getTransactions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentException_deposit(){
        Account maxiSavingsAccount = new MaxiSavingsAccount(11111111);
        maxiSavingsAccount.deposit(0,"Regular Deposit");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentException_withdraw(){
        Account maxiSavingsAccount = new MaxiSavingsAccount(11111111);
        maxiSavingsAccount.withdraw(0);
    }

    @Test
    public void testAccountTypes(){
        Account checkingAccount = new CheckingAccount(11111111);
        Account savingsAccount = new SavingsAccount(22222222);
        Account maxiSavingsAccount = new MaxiSavingsAccount(33333333);
        assertEquals("Checking Account", checkingAccount.getAccountType());
        assertEquals("Savings Account", savingsAccount.getAccountType());
        assertEquals("Maxi Savings Account", maxiSavingsAccount.getAccountType());
    }

    @Test
    public void testSavingsInterest(){
        Account savingsAccount = new SavingsAccount(11111111);
        savingsAccount.deposit(100,"Regular Deposit");
        double expected,interest;
        expected = 0.1;
        interest = savingsAccount.interestEarned();
        assertEquals(expected, interest, DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsInterest(){
        Account maxiSavingsAccount = new MaxiSavingsAccount(11111111);
        maxiSavingsAccount.deposit(100,"Regular Deposit");
        double expected,interest;
        expected = 5.0;
        interest = maxiSavingsAccount.interestEarned();
        assertEquals(expected, interest, DOUBLE_DELTA);

        maxiSavingsAccount.deposit(1000,"Regular Deposit");
        expected = 55.0;
        interest = maxiSavingsAccount.interestEarned();
        assertEquals(expected, interest, DOUBLE_DELTA);
    }


    @Test
    public void testMaxiSavingsInterestRate_High(){
        Account maxiSavingsAccount = new MaxiSavingsAccount(11111111);
        maxiSavingsAccount.deposit(100,"Regular Deposit");
        maxiSavingsAccount.withdraw(50);
        double expected,interestRate;
        expected = 0.05;

        Date nowPlusTenDays = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowPlusTenDays);
        calendar.add(Calendar.DATE, 11);
        nowPlusTenDays = calendar.getTime();


        interestRate = ((MaxiSavingsAccount) maxiSavingsAccount).getInterestRate(nowPlusTenDays);
        assertEquals(expected, interestRate, DOUBLE_DELTA);

    }

    @Test
    public void testMaxiSavingsInterestRate_High2(){
        Account maxiSavingsAccount = new MaxiSavingsAccount(11111111);
        maxiSavingsAccount.deposit(100,"Regular Deposit");
        double expected,interestRate;
        expected = 0.05;

        Date nowPlusTenDays = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowPlusTenDays);
        calendar.add(Calendar.DATE, 11);
        nowPlusTenDays = calendar.getTime();


        interestRate = ((MaxiSavingsAccount) maxiSavingsAccount).getInterestRate(nowPlusTenDays);
        assertEquals(expected, interestRate, DOUBLE_DELTA);

    }


    @Test
    public void testMaxiSavingsInterestRate_Low(){
        Account maxiSavingsAccount = new MaxiSavingsAccount(11111111);
        maxiSavingsAccount.deposit(100,"Regular Deposit");
        maxiSavingsAccount.withdraw(50);
        double expected,interestRate;
        expected = 0.01;

        Date nowPlusTenDays = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowPlusTenDays);
        calendar.add(Calendar.DATE, 5);
        nowPlusTenDays = calendar.getTime();


        interestRate = ((MaxiSavingsAccount) maxiSavingsAccount).getInterestRate(nowPlusTenDays);
        assertEquals(expected, interestRate, DOUBLE_DELTA);

    }


    @Test
    public void testAccountStatement(){
        Account account = new CheckingAccount(12345678);
        account.deposit(1000,"Regular Deposit");
        account.withdraw(500);
        String expected = "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00";

        assertEquals(expected,account.statementForAccount());
    }

    @Test
    public void testToDollars(){
        String toDollarsValue,expected;
        toDollarsValue = new CheckingAccount(12345678).toDollars(1000);
        expected = "$1,000.00";
        assertEquals(expected,toDollarsValue);
    }
}
