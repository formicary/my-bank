package com.accenture.accounts;

import com.accenture.intereststrategies.*;

public class AccountFactory {

    private static final Double CHECKING_ACCOUNT_INTEREST_RATE = 0.001;

    public enum AccountType {
        CHECKING, SAVINGS, MAXI_SAVINGS
    }

    private AccountFactory() { }

    public static Account createAccount(AccountType accountType, String customerId) {
        switch (accountType) {
            case CHECKING:
                return createCheckingAccount(customerId);
            case SAVINGS:
                return createSavingsAccount(customerId);
            case MAXI_SAVINGS:
                return createMaxiSavingsAccount(customerId);
                default:
                    throw new IllegalArgumentException("Unknown account type: " + accountType.name());
        }
    }

    private static Account createCheckingAccount(String customerId) {
        InterestStrategy interestStrategy = FlatRateInterest.newInstance(CHECKING_ACCOUNT_INTEREST_RATE);
        return new Account(customerId, interestStrategy);
    }

    private static Account createSavingsAccount(String customerId) {
        InterestStrategy interestStrategy = new SavingsInterest();
        return new Account(customerId, interestStrategy);
    }

    private static Account createMaxiSavingsAccount(String customerId) {
        InterestStrategy interestStrategy = new MaxiSavingsInterestNoWithdrawals(10);
        return new Account(customerId, interestStrategy);
    }


}

