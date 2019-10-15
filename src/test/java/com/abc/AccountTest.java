package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void deposit() {
        Account checking = new CheckingAccount();
        checking.deposit(new BigDecimal("10"));
        checking.deposit(new BigDecimal("40"));
        checking.deposit(new BigDecimal("19.99"));

        assertEquals("69.99", checking.getBalance().toString());
        checking.deposit(new BigDecimal("23.43"));
        assertEquals("93.42", checking.getBalance().toString());
    }

    @Test (expected = IllegalArgumentException.class)
    public void negativeDeposit(){
        Account saving = new SavingAccount();
        saving.deposit(Transaction.FIFTY);

        assertEquals("50.00", saving.getBalance().toString());
        saving.deposit(new BigDecimal("-10"));
    }

    @Test
    public void withdraw() {
        Account checking = new CheckingAccount();
        checking.deposit(new BigDecimal("101.50"));
        checking.withdraw(new BigDecimal(".50"));
        assertEquals("101.00", checking.getBalance().toString());

//        checking.withdraw(new BigDecimal("101.01"));
//        System.out.println(checking.getBalance());
//        assertEquals("101.00", checking.getBalance().toString());
    }

    @Test (expected = IllegalArgumentException.class)
    public void negativeWithdraw(){
        Account saving = new SavingAccount();
        saving.deposit(Transaction.FIFTY);

        assertEquals("50.00", saving.getBalance().toString());
        saving.withdraw(new BigDecimal("-20"));
    }

    @Test
    public void insufficientFunds(){
        Account saving = new SavingAccount();
        saving.deposit(Transaction.FIFTY);

        assertEquals("50.00", saving.getBalance().toString());
        saving.withdraw(new BigDecimal("70.00"));
        assertEquals("50.00", saving.getBalance().toString());
    }

    @Test
    public void checkingInterest(){
        Account checking = new CheckingAccount();
        checking.deposit(new BigDecimal("500.00"));
        assertEquals("0.50", checking.interestEarned().toString());
    }

    @Test
    public void savingInterest(){
        Account saving = new SavingAccount();
        saving.deposit(new BigDecimal("900.00"));
        assertEquals("0.90", saving.interestEarned().toString());
    }

    @Test
    public void savingInterest2(){
        Account saving = new SavingAccount();
        saving.deposit(new BigDecimal("1000.00"));
        assertEquals("1.00", saving.interestEarned().toString());
    }

    @Test
    public void savingInterest3(){
        Account saving = new SavingAccount();
        saving.deposit(new BigDecimal("1500.00"));
        assertEquals("3.00", saving.interestEarned().toString());
    }

    @Test
    public void maxiInterest() {
        // balance <= 1000
        Account maxi = new MaxiAccount();
        maxi.deposit(new BigDecimal("899.00"));
        assertEquals("17.98", maxi.interestEarned().toString());
    }

    @Test
    public void maxiInterest2() {
        // balance == 1000
        Account maxi = new MaxiAccount();
        maxi.deposit(new BigDecimal("1000.00"));
        assertEquals("20.00", maxi.interestEarned().toString());
    }

    @Test
    public void maxiInterest3() {
        // balance <= 2000
        Account maxi = new MaxiAccount();
        maxi.deposit(new BigDecimal("1500.00"));
        assertEquals("75.00", maxi.interestEarned().toString());
    }

    @Test
    public void maxiInterest4() {
        // balance == 2000
        Account maxi = new MaxiAccount();
        maxi.deposit(new BigDecimal("2000.00"));
        assertEquals("100.00", maxi.interestEarned().toString());
    }

    @Test
    public void maxiInterest5() {
        //balance >2000
        Account maxi = new MaxiAccount();
        maxi.deposit(new BigDecimal("3000.00"));
        assertEquals("300.00", maxi.interestEarned().toString());
    }

}