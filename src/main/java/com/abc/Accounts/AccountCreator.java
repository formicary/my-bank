package com.abc.Accounts;

public class AccountCreator {

    public static Account createAccount(AccountType accType) {
        Account account = null;
        switch (accType) {
            case CHECKING:
                account = new CheckingAccount();
                break;
            case SAVINGS:
                account = new SavingsAccount();
                break;
            case MAXI_SAVINGS:
                account = new MaxiSavingsAccount();
                break;
        }
        return account;

    }

}
