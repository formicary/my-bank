package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a Maxi Savings Account.
 */
public class MaxiSavingsAccount extends Account {

    /**
     * Constructs a new Maxi Savings Account.
     */
    public MaxiSavingsAccount() {
        super(AccountType.MAXI_SAVINGS);
    }

    /**
     * Calculates and returns the interest rate for the Maxi Savings Account 
     * based on the last withdrawal date and the current date.
     *
     * @param balance The current balance of the account.
     * @return The calculated interest rate.
     */
    @Override
    public BigDecimal getInterestRate(BigDecimal balance) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastWithdrawalDate = getLastWithdrawalDate();

        if (lastWithdrawalDate == null) {
            // Default interest rate when lastWithdrawalDate is null
            return MAXI_SAVINGS_HIGH_INTEREST_RATE;
        }

        long daysBetween = ChronoUnit.DAYS.between(lastWithdrawalDate, currentDate);

        BigDecimal interestRate = (daysBetween <= MAXI_SAVINGS_WITHDRAWAL_DAYS) ? MAXI_SAVINGS_LOW_INTEREST_RATE
                : MAXI_SAVINGS_HIGH_INTEREST_RATE;

        return interestRate;
    }
}
