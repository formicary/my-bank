package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    private String accountName1 = "first account";
    private String accountName2 = "second account";

    @Test //Test customer statement generation
    public void checkWithDrawInPastTest(){
        Account account = new Account(accountName1, Account.CHECKING);
        account.withdraw(100);
        assertFalse(account.checkWithdrawInThePast(10));

    }







}
