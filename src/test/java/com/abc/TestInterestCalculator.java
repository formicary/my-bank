package com.abc;

import com.abc.accounts.Account;
import com.abc.InterestCalculator;
import com.abc.Transaction;
import static com.abc.accounts.Account.AccountType.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author comet
 */
public class TestInterestCalculator {

    //Prototype test to ensure calculate compound interest works correctly
    @Ignore
    public void testCalculateCompoundInterest() {
        InterestCalculator calc = new InterestCalculator(new ArrayList<Transaction>(), CHECKING);

        double amount = 1000;
        int days = 20;
        double interestRate = 0.1;

        double expected = 1005.49;
        double result = calc.calcCheckingInterest(amount, days, interestRate, 0);

        assertEquals(expected, result, 0.01);
    }

    @Test
    public void testCheckingAccount() throws ParseException {
        List<Transaction> transactions = new ArrayList<Transaction>();
        InterestCalculator interester = new InterestCalculator(transactions, CHECKING);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        String date1InString = "01-01-2000 00:00:01";
        Date date1 = sdf.parse(date1InString);

        String date2InString = "05-01-2000 00:00:00";
        Date date2 = sdf.parse(date2InString);

        String date3InString = "15-01-2000 00:00:00";
        Date date3 = sdf.parse(date3InString);

        Transaction transaction1 = new Transaction(1000, date1);
        Transaction transaction2 = new Transaction(5000, date2);
        Transaction transaction3 = new Transaction(-2000, date3);

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        double expected = 34.10;

        double result = interester.calcCompoundInterest(CHECKING);

        assertEquals(expected, result, 0.01);
    }

    @Test
    public void testSavingsAccount() throws ParseException {
        List<Transaction> transactions = new ArrayList<Transaction>();
        InterestCalculator interester = new InterestCalculator(transactions, SAVINGS);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        String date1InString = "01-01-2000 00:00:01";
        Date date1 = sdf.parse(date1InString);

        String date2InString = "05-01-2000 00:00:00";
        Date date2 = sdf.parse(date2InString);

        String date3InString = "20-01-2000 00:00:00";
        Date date3 = sdf.parse(date3InString);

        Transaction transaction1 = new Transaction(1500, date1);
        Transaction transaction2 = new Transaction(-600, date2);
        Transaction transaction3 = new Transaction(1000, date3);

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        double expected = 17.49;
        double result = interester.calcCompoundInterest(CHECKING);

        assertEquals(expected, result, 0.01);
    }
    
    @Test
    public void testMaxiAccount() throws ParseException {
        List<Transaction> transactions = new ArrayList<Transaction>();
        InterestCalculator interester = new InterestCalculator(transactions, MAXI_SAVINGS);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        String date1InString = "01-01-2000 00:00:01";
        Date date1 = sdf.parse(date1InString);

        String date2InString = "05-01-2000 00:00:00";
        Date date2 = sdf.parse(date2InString);

        String date3InString = "20-01-2000 00:00:00";
        Date date3 = sdf.parse(date3InString);

        Transaction transaction1 = new Transaction(1500, date1);
        Transaction transaction2 = new Transaction(-600, date2);
        Transaction transaction3 = new Transaction(1000, date3);

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        double expected = 36.65;
        double result = interester.calcCompoundInterest(MAXI_SAVINGS);

        assertEquals(expected, result, 0.01);
    }

    
    @Test
    public void testcalculateInterestChecking() {

    }

}
