package com.abc.accounts;

import com.abc.constants.AccountConstantsNew;

/**
 * Created by vahizan on 18/08/2017.
 */
public class NullAccount extends Account {
    private static NullAccount nullAccount = new NullAccount();
    private NullAccount(){}
    @Override
    public AccountConstantsNew accountType() {
        return AccountConstantsNew.NULL;
    }
    public static NullAccount getInstance(){
        return nullAccount;
    }
}
