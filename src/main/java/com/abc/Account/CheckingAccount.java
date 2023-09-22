package com.abc.Account;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.abc.Utilities.Enums.AccountType;

public class CheckingAccount extends Account {

    private final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.001);

    public CheckingAccount() {
        super(AccountType.CHECKING);
    }

    @Override
    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        amount = amount.multiply(INTEREST_RATE);
        return amount.setScale(2, RoundingMode.CEILING);
    };
}
