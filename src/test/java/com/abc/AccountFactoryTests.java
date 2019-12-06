package com.abc;

import com.abc.account_types.*;
import com.abc.Constants.AccountTypes;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AccountFactoryTests {
    @Test
    public void openAccount_WhenCheckingAccountValue_ReturnsCheckingAccount(){
        AccountFactory factory = new AccountFactory();

        BaseAccount account = factory.openAccount(AccountTypes.CheckingAccount);

        assertTrue(account instanceof CheckingAccount);
    }

    @Test
    public void openAccount_WhenSavingsAccountValue_ReturnsSavingsAccount(){
        AccountFactory factory = new AccountFactory();

        BaseAccount account = factory.openAccount(AccountTypes.SavingsAccount);

        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    public void openAccount_WhenMaxiSavingsAccountValue_ReturnsMaxiSavingsAccount(){
        AccountFactory factory = new AccountFactory();

        BaseAccount account = factory.openAccount(AccountTypes.MaxiSavingsAccount);

        assertTrue(account instanceof MaxiSavingAccount);
    }

    //TODO: When passing null
}
