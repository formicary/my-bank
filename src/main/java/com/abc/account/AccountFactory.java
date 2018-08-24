package com.abc.account;

import com.abc.branch.Customer;

/**
 * Created by sameen on 24/08/2018.
 */
public class AccountFactory {

    public AccountFactory() {
    }

    public Account createAccount(Customer owner, AccountType accountType, double openingBalance) {
        Account account = null;

        switch (accountType) {
            case CHECKING:
                account = new CheckingAccount(owner, openingBalance);
            case SAVINGS:
                account = new SavingsAccount(owner, openingBalance);
            case MAXI_SAVINGS:
                account = new MaxiSavingsAccount(owner, openingBalance);
        }
        return account;
    }
}
