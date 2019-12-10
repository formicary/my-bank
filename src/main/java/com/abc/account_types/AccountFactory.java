package com.abc.account_types;

import com.abc.shared.Constants;
import com.abc.shared.Constants.AccountTypes;

public class AccountFactory {
    public BaseAccount createAccount(AccountTypes accountType){
        switch(accountType){
            case CheckingAccount:
                return new CheckingAccount();

            case SavingsAccount:
                return new SavingsAccount();

            case MaxiSavingsAccount:
                return new MaxiSavingAccount();

            default:
                throw new IllegalArgumentException(Constants.UnexpectedAccountTypeErrorMessage);
        }
    }
}
