package com.abc.AccountTypes;

import com.abc.MainClasses.Account;
import com.abc.MainClasses.AccountType;

//Subclass of Account
public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount() {
        //Call constructor of Account with appropriate parameter
        super(AccountType.MAXI_SAVINGS);
    }
}