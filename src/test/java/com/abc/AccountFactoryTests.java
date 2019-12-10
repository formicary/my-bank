package com.abc;

import com.abc.account_types.*;
import com.abc.shared.Constants.AccountTypes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AccountFactoryTests {
    AccountFactory factory;

    @Before
    public void initEach(){
        factory = new AccountFactory();
    }

    @Test
    public void createAccount_WhenCheckingAccountValue_ReturnsCheckingAccount(){
        BaseAccount account = factory.createAccount(AccountTypes.CheckingAccount);

        assertTrue(account instanceof CheckingAccount);
    }

    @Test
    public void createAccount_WhenSavingsAccountValue_ReturnsSavingsAccount(){
        BaseAccount account = factory.createAccount(AccountTypes.SavingsAccount);

        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    public void createAccount_WhenMaxiSavingsAccountValue_ReturnsMaxiSavingsAccount(){
        BaseAccount account = factory.createAccount(AccountTypes.MaxiSavingsAccount);

        assertTrue(account instanceof MaxiSavingAccount);
    }
}
