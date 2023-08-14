package com.abc.features;

import org.junit.Test;

import com.abc.classes.Account;
import com.abc.classes.Customer;
import com.abc.classes.Account.AccountType;

import static org.junit.Assert.assertTrue;

public class AccountTest {
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

        assertTrue(checking.getBalance() == 100);
    }
}