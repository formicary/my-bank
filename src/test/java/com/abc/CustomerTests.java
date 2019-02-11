package com.abc;

import static org.junit.Assert.assertEquals;

import com.abc.accounts.Account;
import com.abc.accounts.AccountType;
import com.abc.accounts.CheckingAccount;
import com.abc.accounts.MaxiSavingsAccount;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class CustomerTests {

    private Customer customer;

    @Before
    public void setUp() {
        /*
         *  Setting up an account and making a few transactions.
         */

        customer = new Customer("Waqas", 494);

        customer.openAccount(AccountType.Checking, 1451894);
        customer.openAccount(AccountType.Savings, 1451895);
        customer.openAccount(AccountType.MaxiSavings, 1451896);


    }

    @Test
    public void testAccountSetup() {


        assertEquals(customer.getNumberOfAccounts(), 3);
        assertEquals(customer.getCustomerID(), 494);
        assertEquals(customer.getName(), "Waqas");

        customer.getAccount(1451894).depositAmount(200, "Deposit");
        customer.getAccount(1451895).depositAmount(150, "Deposit");
        customer.getAccount(1451896).depositAmount(150, "Deposit");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.JANUARY, 1, 0, 0, 0);
        Date date = calendar.getTime();

        //Setting a pre set date so it can be tested.
        customer.getAccount(1451894).getTransactions().get(0).setTransactionDate(date);
        customer.getAccount(1451895).getTransactions().get(0).setTransactionDate(date);
        customer.getAccount(1451896).getTransactions().get(0).setTransactionDate(date);

        String expectedStatement = "Account information for: Waqas\n" +
                "Open accounts: 3\n\n" +
                "All transactions for account: 1451894 Account Type: Checking\n" +
                date.toString() + "\n" +
                "200.0 deposit\n" +
                "Your current account balance: 200.0\n\n" +
                "All transactions for account: 1451895 Account Type: Savings\n" +
                date.toString() + "\n" +
                "150.0 deposit\n" +
                "Your current account balance: 150.0\n\n" +
                "All transactions for account: 1451896 Account Type: MaxiSavings\n" +
                date.toString() + "\n" +
                "150.0 deposit\n" +
                "Your current account balance: 150.0\n";

        assertEquals(expectedStatement, customer.toString());


    }

    @Test
    public void transferBetweenAccounts() {

        customer.getAccount(1451894).depositAmount(200, "Deposit");
        Account from = customer.getAccount(1451894);
        Account to = customer.getAccount(1451895);


        customer.transferBetweenAccounts(from, to, 150);

        assertEquals(150.00, to.viewBalance(), 0.001);
    }

    @Test
    public void totalInterestEarned() {

        Account checkingAccount = customer.getAccount(1451894);
        Account savingsAccount = customer.getAccount(1451895);
        Account maxiSavingAccount = customer.getAccount(1451896);

        checkingAccount.depositAmount(10000.00, "Deposit");
        savingsAccount.depositAmount(10000.00, "Deposit");
        maxiSavingAccount.depositAmount(10000.00, "Deposit");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.JANUARY, 1, 0, 0, 0);
        Date date = Calendar.getInstance().getTime();

        // Will be calculating interest earned from 01/01/2019 - NOW
        checkingAccount.getTransactions().get(0).setTransactionDate(calendar.getTime());
        savingsAccount.getTransactions().get(0).setTransactionDate(calendar.getTime());
        maxiSavingAccount.getTransactions().get(0).setTransactionDate(calendar.getTime());
        int dateDif = checkingAccount.getDateDiff(calendar.getTime(), date);

        // Work out the interest earned on each account. interestEarned method has been test and it works! refer to AccountTest
        double interestChecking = checkingAccount.interestEarned(10000.00, dateDif);
        double interestSaving = savingsAccount.interestEarned(10000.00, dateDif);
        double interestMaxiSaving = maxiSavingAccount.interestEarned(10000.00, dateDif);

        // Total interest earned is the combined interest
        assertEquals(interestChecking + interestSaving + interestMaxiSaving, customer.totalInterestEarned(), 0.0001);

    }

}
