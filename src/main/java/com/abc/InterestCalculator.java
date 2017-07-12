/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import com.abc.accounts.Account.AccountType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author comet
 */
public class InterestCalculator {

    List<Transaction> transactions;

    public InterestCalculator(List<Transaction> transactions) {
        this.transactions = transactions;
        //All transactions should already be in chronological order, this confirms it
        Collections.sort(this.transactions);
    }

    public double calcCompoundInterest(AccountType accountType) throws ParseException {
        return checkingInterest();       
    }

    private double checkingInterest() throws ParseException {
        Transaction currentT = null;
        double accountTotal = 0.0;
        int differenceInDays;
        double interestRate = 0.1;

        for (Transaction nextT : transactions) {
            if (currentT != null) {
                differenceInDays = DateProvider.differenceInDays(currentT.getDate(), nextT.getDate());
                accountTotal += currentT.getAmount();
                accountTotal = calcCheckingInterest(accountTotal, differenceInDays, interestRate);
            }
            currentT = nextT;
        }
        //Test code
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String testDateInString = "30-01-2000 00:00:00";
        Date testDate = sdf.parse(testDateInString);
        differenceInDays = DateProvider.differenceInDays(currentT.getDate(), testDate);
         
        
        //differenceInDays = DateProvider.differenceInDays(currentT.getDate(), DateProvider.now());
        accountTotal += currentT.getAmount();
        accountTotal = calcCheckingInterest(accountTotal, differenceInDays, interestRate);

        return accountTotal - sumTransactions();
    }

    public double calcCheckingInterest(double amount, int days, double yearlyInterestRate) {
        double dailyInterestRate = calculateDailyInterestRate(yearlyInterestRate);
        
        for (int i = 0; i < days; i++){
            amount = amount * (1+dailyInterestRate);
        }
        
        return amount;
        //return amount * Math.pow((1 + dailyInterestRate), days);
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }

    private double calculateDailyInterestRate(double yearlyInterestRate) {
        return yearlyInterestRate / 365;
    }

}
