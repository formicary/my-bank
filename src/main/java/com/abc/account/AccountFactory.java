package com.abc.account;

import com.abc.customer.Customer;

public class AccountFactory {

    private AccountFactory() {
    }

    public static Account create(Customer customer, AccountType accountType) {
        switch (accountType) {
            case CHECKING:
                return new CheckingAccount(customer, accountType);
            case SAVINGS:
                return new SavingsAccount(customer, accountType);
            case MAXI_SAVINGS:
                return new MaxiSavingsAccount(customer, accountType);
        }
        throw new IllegalArgumentException("Invalid account type.");
    }
}
