package com.abc.Accounts;

import com.abc.Utils.BankUtils;
import com.abc.Utils.IDateProvider;

import java.util.Date;

/**
 * Represents a savings bank account.
 */
public class SavingsAccount extends AbstractAccount {
    /**
     * The upper boundary for the tier one interest earned group.
     */
    private static final double tierOneBoundary = 1000;

    /**
     * The interest rate for the tier one interest earned group.
     */
    private static final double tierOneInterestRate = 0.001;

    /**
     * The interest rate for the tier two interest earned group.
     */
    private static final double tierTwoInterestRate = 0.002;

    /**
     * Initializes a new instance of the SavingsAccount class.
     *
     * @param dateProvider The date provider.
     * @param accountId The account ID.
     * @param customerId The customer ID.
     */
    public SavingsAccount(IDateProvider dateProvider, int accountId, int customerId) {
        super(dateProvider, accountId, customerId);
    }

    /**
     * Gets a string representing the account type.
     *
     * @return The string representing the account type.
     */
    protected String getAccountType() {
        return "Savings";
    }

    /**
     * Calculates the new balance with the accrued interest added between dates of the two given transactions.
     *
     * @param initialBalance The initial balance.
     * @param firstTransactionDate The date of the first transaction.
     * @param secondTransactionDate The date of the second transaction
     *
     * @return The balance with interest added.
     */
    protected double accrueInterest(double initialBalance, Date firstTransactionDate, Date secondTransactionDate) {
        int numberOfDaysBetweenTransactions = BankUtils.calculateDaysBetween(firstTransactionDate, secondTransactionDate);

        double dailyInterestRate = tierOneInterestRate / numberOfDaysInYear;
        double closingBalance = initialBalance;

        while (closingBalance <= tierOneBoundary && numberOfDaysBetweenTransactions > 0) {
            closingBalance *= (1 + dailyInterestRate);

            numberOfDaysBetweenTransactions--;
        }

        dailyInterestRate = tierTwoInterestRate / numberOfDaysInYear;

        while (numberOfDaysBetweenTransactions > 0) {
            closingBalance *= (1 + dailyInterestRate);

            numberOfDaysBetweenTransactions--;
        }

        return closingBalance;
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[SavingsAccount: accountId=%s, customerId=%s, transactions=%s]", this.accountId, this.customerId, this.transactions);
    }
}
