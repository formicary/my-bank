package com.abc.helpers;

import com.abc.classes.Account;
import com.abc.classes.Transaction;

public class AccountInterests {
    // Deposit interest and update running accured interest for that account
    private static void payInterest(Account account, double interest) {
        account.tryDeposit(interest);
        account.updateAccuredInterest(interest);
    }

    // Checking accounts have a flat interest rate of 0.1%
    public static void calculateInterestChecking(Account account) {
        double interest;
        double balance = account.getBalance();

        interest = balance * 0.001;
        payInterest(account, interest);
    }

    // Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
    public static void calculateInterestSavings(Account account) {
        double interest;
        double balance = account.getBalance();

        if (balance <= 1000) {
            interest = balance * 0.001;
            payInterest(account, interest);
        } else if (balance > 1000) {
            interest = (1000 * 0.001) + (balance - 1000) * 0.002;
            payInterest(account, interest);
        }
    }

    // Maxi-Savings accounts have a rate of 2% for the first $1,000 then 5% for the
    // next $1,000 then 10%
    public static void calculateInterestMaxiSavings(Account account) {
        double interest;
        double balance = account.getBalance();

        if (balance <= 1000) {
            interest = balance * 0.02;
            payInterest(account, interest);
        } else if (balance <= 2000) {
            interest = (1000 * 0.02) + (balance - 1000) * 0.05;
            payInterest(account, interest);
        } else if (balance >= 2000 && balance < 3000) {
            interest = (1000 * 0.02) + (1000 * 0.05) + (balance - 2000) * 0.1;
            payInterest(account, interest);
        } else if (balance >= 3000) {
            interest = (1000 * 0.02) + (1000 * 0.05) + (1000 * 0.1);
            payInterest(account, interest);
        }
    }

    // ADDITIONAL FEATURE//
    // This feature was kept seperate to test indivdually
    // Change Maxi-Savings accounts to have an interest rate of 5% assuming no
    // withdrawals in the past 10 days otherwise 0.1%

    public static void calculateInterestMaxiSavingsPlus(Account account) {
        double interest;
        double balance = account.getBalance();

        Transaction latestTransaction = account.getLatestTransaction();
        Boolean isOverTenDays = Transaction.transactionDateOverTenDays(latestTransaction);

        if (isOverTenDays) {
            interest = balance * 0.05;
            payInterest(account, interest);
        } else {
            interest = balance * 0.001;
            payInterest(account, interest);
        }
    }
}