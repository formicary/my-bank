package com.abc.features;

import org.junit.Test;

import com.abc.classes.Account;
import com.abc.classes.Customer;
import com.abc.classes.Account.AccountType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    //Testing to ensure a deposit of zero does not go through
    @Test
    public void attemptDepositZeroTest() {
        //Add a customer with the name 'Bill' and open a checking account
        Customer bill = new Customer("Bill");
        Account checking = bill.openAccount(AccountType.CHECKING);

        //Attempt normal deposit of amount '100'
        checking.tryDeposit(100);
        //Attempt to deposit 0
        checking.tryDeposit(0);

        //Check to make sure deposit of 0 didn't go through
        assertTrue(checking.getBalance() == 100);
    }

    //Testing to ensure a withdrawl that is greater than the total balance does not go through
    @Test
    public void attemptGreaterWithdrawalTest(){
        //Add a customer with the name 'Bill' and open a checking account
        Customer bill = new Customer("Bill");
        Account checking = bill.openAccount(AccountType.CHECKING);

        //Attempt normal deposit of amount '100'
        checking.tryDeposit(100);
        //Attempt to withdraw amount of '500'
        checking.tryWithdraw(500);

        //Check to make sure withdrawal didn't go through
        assertTrue(checking.getBalance() == 100);
    }

    //Testing to ensure MAXI_ACCOUNT_PLUS accounts give the correct interest of 0.01% if not over 10 days
    @Test
    public void underTenDaysTest(){
        //Add a customer with the name 'Bill' and open a checking account
        Customer bill = new Customer("Bill");
        Account maxiSavingPlus = bill.openAccount(AccountType.MAXI_SAVINGS_PLUS);

        System.out.println(maxiSavingPlus.getAccountType());

        //Attempt normal deposit of amount '100'
        maxiSavingPlus.tryDeposit(1000);
        //Add interest to the account
        maxiSavingPlus.addInterest();

        //Check to see if correct interest was added
        assertEquals(1, maxiSavingPlus.getAccruedInterest(), DOUBLE_DELTA);
    }

    //Testing to ensure when latest transaction was over 10 days ago, interest rate is 5%
    @Test
    public void overTenDaysTest(){
        //Add a customer with the name 'Bill' and open a checking account
        Customer bill = new Customer("Bill");
        Account maxiSavingPlus = bill.openAccount(AccountType.MAXI_SAVINGS_PLUS);

        System.out.println(maxiSavingPlus.getAccountType());

        //Attempt normal deposit of amount '100'
        maxiSavingPlus.tryDeposit(1000);

        //Force date to be over 10 days (TEST USE ONLY)
        maxiSavingPlus.getLatestTransaction().forceTransactionDateOverTenDays();
        //Add interest to the account
        maxiSavingPlus.addInterest();

        //Check to see if correct interest was added
        assertEquals(50, maxiSavingPlus.getAccruedInterest(), DOUBLE_DELTA);
    }
}