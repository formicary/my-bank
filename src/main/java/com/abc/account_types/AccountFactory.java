package com.abc.account_types;

import com.abc.Constants.AccountTypes;

public class AccountFactory {
    public BaseAccount openAccount(AccountTypes accountType){
        switch(accountType){
            case CheckingAccount:
                return new CheckingAccount();

            case SavingsAccount:
                return new SavingsAccount();

            case MaxiSavingsAccount:
                return new MaxiSavingAccount();
        }

        return null;
    }
}
