
package com.abc.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** The class {@code CheckingAccount} extends {@code Account} that represents a checking account */
public class CheckingAccount extends Account {

    /** Initialise a {@code CheckingAccount} */
    public CheckingAccount() {
        super();
    }

    @Override
    protected BigDecimal getDailyInterestEarned(final LocalDateTime day) {
        return getTransactionsSum().multiply(convertToDailyRate(BigDecimal.valueOf(0.001)));
    }

    @Override
    public String getName() {
        return "Checking Account";
    }
}