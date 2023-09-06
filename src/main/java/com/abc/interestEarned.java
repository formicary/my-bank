package com.abc;

import com.abc.MainClasses.Account;
import com.abc.MainClasses.DateProvider;

//Class to calculate interest earned for each type of account
public class interestEarned {
    //Interest earned for checking account
    public static double interestEarnedForChecking(double amount) {
        double flatInterestRate = 0.001;

        return amount * flatInterestRate;
    }

    //Interest earned for savings account
    public static double interestEarnedForSavings(double amount) {
        double firstInterestRate = 0.001, secondInterestRate = 0.002;

        if (amount <= 1000)
            return amount * firstInterestRate;
        else
            return 1 + (amount-1000) * secondInterestRate;
    }

    //Interest earned for maxi savings account
    public static double interestEarnedForMaxiSavings(Account account, double amount) {
        //Set withdrawDateInMilliseconds to last recorded transaction date in milliseconds
        long withdrawDateInMilliseconds = account.getTransactions().get(account.getTransactions().size() - 1)
            .getTransactionDate().getTime();
        //Store difference, in milliseconds, between current time and withdrawDateInMilliseconds
        long diffInMilliseconds = DateProvider.getInstance().now().getTime() - withdrawDateInMilliseconds;

        //Withdrawal within the past 10 days (864000000ms = 10 days)
        if (amount <= 1000 && diffInMilliseconds < 864000000) {
            return amount * 0.02;  
        }
        
        //No withdrawal in the past 10 days
        if (amount <= 1000 && diffInMilliseconds >= 864000000)
            return amount * 0.05;

        if (amount <= 2000)
            return 20 + (amount-1000) * 0.05;

        return 70 + (amount-2000) * 0.1;
    }
}