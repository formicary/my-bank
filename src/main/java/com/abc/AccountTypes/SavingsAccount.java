package com.abc.AccountTypes;

import com.abc.MainClasses.Account;
import com.abc.MainClasses.AccountType;

//Subclass of Account
public class SavingsAccount extends Account {
    public SavingsAccount() {
        //Call constructor of Account with appropriate parameter
        super(AccountType.SAVINGS);
    }
}