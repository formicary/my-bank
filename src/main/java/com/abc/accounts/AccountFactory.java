package com.abc.accounts;

import com.abc.interestbehaviors.FlatRateInterest;
import com.abc.interestbehaviors.InterestBehavior;
import com.abc.interestbehaviors.MaxiSavingsInterest;
import com.abc.interestbehaviors.SavingsInterest;

public class AccountFactory {

    public enum AccountType {
        CHECKING, SAVINGS, MAXI_SAVINGS
    }

    private AccountFactory() { }

    public static Account createAccount(AccountType accountType) {
        switch (accountType) {
            case CHECKING:
                return createCheckingAccount();
            case SAVINGS:
                return createSavingsAccount();
            case MAXI_SAVINGS:
                return createMaxiSavingsAccount();
                default:
                    throw new IllegalArgumentException("Unknown account type: " + accountType.name());
        }
    }

    private static Account createCheckingAccount() {
        InterestBehavior interestBehavior = FlatRateInterest.getInstance(0.001);
        return new Account(interestBehavior);
    }

    private static Account createSavingsAccount() {
        InterestBehavior interestBehavior = new SavingsInterest();
        return new Account(interestBehavior);
    }

    private static Account createMaxiSavingsAccount() {
        InterestBehavior interestBehavior = new MaxiSavingsInterest();
        return new Account(interestBehavior);
    }

}

