package com.abc.accounts;
import com.abc.Account;

public class AccountFactory{

    public static Account createAccount(AccountType accountType) throws EnumConstantNotPresentException{

        switch(accountType){
            case CHECKING: return new CheckingAccount(accountType);
            case SAVINGS: return new SavingsAccount(accountType);
            case MAXI_SAVINGS: return new MaxiSavingsAccount(accountType);
            default: {
                //TODO: fix null
                throw new EnumConstantNotPresentException(null, "AccountType \"" + accountType + "\" is not existant");
            }
        }
    }
}