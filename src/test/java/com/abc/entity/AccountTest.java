package com.abc.entity;

import com.abc.entity.Account;
import com.abc.entity.impl.AccountImpl;
import com.abc.entity.impl.CustomerImpl;
import com.abc.exception.InvalidAccountException;
import org.junit.Test;

public class AccountTest {

    @Test(expected = InvalidAccountException.class)
    public void cannotCreateNullAccountType(){
        Account account = new AccountImpl(new CustomerImpl("customer"), null, "123");
    }
}
