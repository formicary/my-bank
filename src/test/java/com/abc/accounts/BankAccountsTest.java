package com.abc.accounts;

import com.abc.constants.AccountConstantsNew;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vahizan on 20/08/2017.
 */
public class BankAccountsTest {
    private BankAccounts accounts;

    @Before
    public void setUp(){
        accounts= new BankAccounts();
    }
    @Test
    public void savingsAccountMustNotExist(){
        assertFalse(accounts.exists(AccountConstantsNew.SAVINGS));
    }
    @Test
    public void savingsAccountMustExist(){
        Account account = new SavingsAccount();
        accounts.addAccount(account);
        assertTrue(accounts.exists(AccountConstantsNew.SAVINGS));
    }

}
