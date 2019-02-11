package com.abc;

import static org.junit.Assert.assertEquals;

import com.abc.accounts.CheckingAccount;
import com.abc.accounts.MaxiSavingsAccount;
import com.abc.accounts.SavingsAccount;
import com.abc.accounts.AccountType;
import org.junit.Test;

import java.util.Date;

import java.util.Calendar;

public class AccountTests {


    @Test
    public void setUpTest() {

        // Setting up 3 accounts
        CheckingAccount checkingAccount = new CheckingAccount(1451894);
        SavingsAccount savingsAccount = new SavingsAccount(1451895);
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount(1451896);

        // Making transactions on each account
        checkingAccount.depositAmount(300, "deposit");
        checkingAccount.depositAmount(150, "deposit");
        checkingAccount.withdrawAmount(200, "Withdrawal");

        savingsAccount.depositAmount(300, "deposit");
        savingsAccount.depositAmount(150, "deposit");
        savingsAccount.withdrawAmount(200, "Withdrawal");

        maxiSavingsAccount.depositAmount(300, "deposit");
        maxiSavingsAccount.depositAmount(150, "deposit");
        maxiSavingsAccount.withdrawAmount(200, "Withdrawal");

        //checking account
        assertEquals(250, checkingAccount.viewBalance(), 0.0001);
        assertEquals(1451894, checkingAccount.getAccountID());
        assertEquals(AccountType.Checking.getName(), checkingAccount.getAccountType());

        //savings account
        assertEquals(250, savingsAccount.viewBalance(), 0.0001);
        assertEquals(1451895, savingsAccount.getAccountID());
        assertEquals(AccountType.Savings.getName(), savingsAccount.getAccountType());

        //maxi savings account
        assertEquals(250, maxiSavingsAccount.viewBalance(), 0.0001);
        assertEquals(1451896, maxiSavingsAccount.getAccountID());
        assertEquals(AccountType.MaxiSavings.getName(), maxiSavingsAccount.getAccountType());

    }

    @Test
    public void interestMethods() {

        //checking account interest over 14 days
        CheckingAccount checkingAccount = new CheckingAccount(1451894);
        double expectedInterest = 0.38356847;
        double actualInterest = checkingAccount.interestEarned(10000.00,14);
        assertEquals(expectedInterest,actualInterest, 0.0001);

        //savings account interest over 1 day
        SavingsAccount savingsAccount = new SavingsAccount(1451894);
        expectedInterest = 0.052054794;
        actualInterest = savingsAccount.interestEarned(10000.00,1);
        assertEquals(expectedInterest,actualInterest, 0.0001);

        //savings account interest over 1 day
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount(1451894);
        expectedInterest = 1.3698630136;
        actualInterest = maxiSavingsAccount.interestEarned(10000.00, 1);
        assertEquals(expectedInterest,actualInterest, 0.0001);

        double compoundFormula = maxiSavingsAccount.compoundFormula(10000.00, 0.05, 41);
        assertEquals(56.3185, compoundFormula, 0.001);


    }

    @Test
    public void checkingAccountTest() {

        Calendar calendar = Calendar.getInstance();
        Date date;

        // Interest is compounded daily. I'll be adding transactions on different days to
        // test the compounded interest.

        // Setting up Checking account
        CheckingAccount checkingAccount = new CheckingAccount(1451894);

        // Adding a transaction of 10,000;
        checkingAccount.depositAmount(10000, "deposit");
        checkingAccount.depositAmount(5000, "deposit");

        // Set transaction of 10000 to 01/01/2019
        calendar.set(2019, Calendar.JANUARY, 01);
        date = calendar.getTime();
        checkingAccount.getTransactions().get(0).setTransactionDate(date);

        // Set transaction of 5000 to 15/01/2019
        calendar.set(2019, Calendar.JANUARY, 15);
        date = calendar.getTime();
        checkingAccount.getTransactions().get(1).setTransactionDate(date);

        // Interest earned on Transaction one.
        // Interest period: Current date - 01/01/2019 gives the Interest period

        int numberOfDays01 = checkingAccount.getDateDiff(checkingAccount.getTransactions().get(0).getTransactionDate(),
                Calendar.getInstance().getTime());
        double interestEarned01 = checkingAccount.interestEarned(10000.00, numberOfDays01);

        // Interest earned on Transaction two.
        // Interest period: Current date - 15/01/2019 gives the Interest period
        int numberOfDays02 = checkingAccount.getDateDiff(checkingAccount.getTransactions().get(1).getTransactionDate(),
                Calendar.getInstance().getTime());
        double interestEarned02 = checkingAccount.interestEarned(5000.00, numberOfDays02);

        // Expected interest is interestEarned on 10,000 & 5,000;
        double expectedInterest = interestEarned01 + interestEarned02;

        assertEquals(expectedInterest, checkingAccount.compoundDailyInterest(), 0.001);

    }

    @Test
    public void savingsAccountTest() {

        Calendar calendar = Calendar.getInstance();
        Date date;

        // Interest is compounded daily. I'll be adding transactions on different days to
        // test the compounded interest.

        // Setting up Checking account
        SavingsAccount savingsAccount = new SavingsAccount(1451894);

        // Adding a transaction of 10,000;
        savingsAccount.depositAmount(10000, "deposit");
        savingsAccount.depositAmount(5000, "deposit");

        // Set transaction of 10000 to 01/01/2019
        calendar.set(2019, Calendar.JANUARY, 01);
        date = calendar.getTime();
        savingsAccount.getTransactions().get(0).setTransactionDate(date);

        // Set transaction of 5000 to 15/01/2019
        calendar.set(2019, Calendar.JANUARY, 15);
        date = calendar.getTime();
        savingsAccount.getTransactions().get(1).setTransactionDate(date);

        // Interest earned on Transaction one.
        // Interest period: 15/01/2019 - 01/01/2019 gives the Interest period

        double interestEarned01 = savingsAccount.interestEarned(10000.00, 14);

        // Interest earned on Transaction two.
        // Interest period: Current date - 15/01/2019 gives the Interest period
        // New balance is interest earned on 01 + 10,0000 + 5000.
        int numberOfDays02 = savingsAccount.getDateDiff(savingsAccount.getTransactions().get(1).getTransactionDate(),
                Calendar.getInstance().getTime());
        double interestEarned02 = savingsAccount.interestEarned(interestEarned01 + 15000, numberOfDays02);

        // Expected interest is interestEarned on 10,000 & 5,000;
        double expectedInterest = interestEarned01 + interestEarned02;

        assertEquals(expectedInterest, savingsAccount.compoundDailyInterest(), 0.001);
    }

    @Test
    public void maxiSavingsAccountTest() {
        // No withdrawal within past 10 days.
        Calendar calendar = Calendar.getInstance();
        Date date;

        // Setting up account
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount(141896);
        maxiSavingsAccount.depositAmount(10000.00, "Deposit");
        maxiSavingsAccount.withdrawAmount(5000.00, "Withdraw");

        // Set transaction of 10000 to 01/01/2019
        calendar.set(2019, Calendar.JANUARY, 01);
        date = calendar.getTime();
        maxiSavingsAccount.getTransactions().get(0).setTransactionDate(date);

        // Set transaction of 5000 to 10/01/2019
        calendar.set(2019, Calendar.JANUARY, 15);
        date = calendar.getTime();
        maxiSavingsAccount.getTransactions().get(1).setTransactionDate(date);

        /*
         compound daily interest

         01/01/2019 - deposit 10,000
         15/01/2019 - withdraw 5000

         01/01/2019 - 15/01/2019 : interest 5%
         15/01/2019 - 25/01/2019 : interest 0.01% (Because withdrawal within 10 days
         25/01/2019 - NOW : interest 5%
         */

        //Number of days since new balance

        int numberOfDays03 = maxiSavingsAccount.getDateDiff(maxiSavingsAccount.getTransactions().get(1).getTransactionDate(),
                Calendar.getInstance().getTime());

        // Compound interest method has already been tested in the AccountTests so it can be used here.

        //Interest earned on 10,000 between 01/01/2019 to 15/01/2019
        double interestEarned01 = maxiSavingsAccount.compoundFormula(10000.00, 0.05, 14);
        //10,000 - 5000 + interestEarned01: Interest earned for next 10 days will be 0.001/365 per day
        double interestEarned02 = maxiSavingsAccount.compoundFormula(5000 + interestEarned01, 0.001, 10);
        // Interest earned on balance till now
        double interestEarned03 = maxiSavingsAccount.compoundFormula(5000 + interestEarned01 + interestEarned02, 0.05, numberOfDays03 - 10);

        // Total interest
        double expectedInterest = interestEarned01 + interestEarned02 + interestEarned03;

        assertEquals(expectedInterest, maxiSavingsAccount.compoundDailyInterest(), 0.001);

    }

}