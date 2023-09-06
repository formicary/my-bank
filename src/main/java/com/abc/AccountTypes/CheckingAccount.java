package com.abc.AccountTypes;

import com.abc.MainClasses.Account;
import com.abc.MainClasses.AccountType;

//Subclass of Account
public class CheckingAccount extends Account {
    public CheckingAccount() {
        //Call constructor of Account with appropriate parameter
        super(AccountType.CHECKING);
    }
}