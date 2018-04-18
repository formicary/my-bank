package com.abc.Accounts;

import com.abc.Utils.BankUtils;
import com.abc.Utils.IDateProvider;

import java.util.Date;

/**
 * Represents a maxi-savings bank account.
 */
public class MaxiSavingsAccount extends AbstractAccount {
    /**
     * The interest rate for the tier one interest earned group.
     */
    private static final double tierOneInterestRate = 0.01;

    /**
     * The interest rate for the tier two interest earned group.
     */
    private static final double tierTwoInterestRate = 0.05;

    /**
     * Keeps track of the last account balance value for interest earned calculations.
     */
    private double lastBalance;

    /**
     * Initializes a new instance of the MaxiSavingsAccount class.
     *
     * @param dateProvider The date provider.
     * @param accountId The account ID.
     * @param customerId The customer ID.
     */
    public MaxiSavingsAccount(IDateProvider dateProvider, int accountId, int customerId) {
        super(dateProvider, accountId, customerId);

        // Initialize last account balance recorded to -1 to denote that it hasn't been set yet.
        lastBalance = -1;
    }

    /**
     * Gets a string representing the account type.
     *
     * @return The string representing the account type.
     */
    protected String getAccountType() {
        return "Maxi-Savings";
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
        int numberOfDaysSinceLastWithdrawal = 10;

        double dailyInterestRate = tierOneInterestRate / numberOfDaysInYear;
        double closingBalance = initialBalance;

        while (numberOfDaysBetweenTransactions > 0 &&
               this.didWithdrawLastTenDays(closingBalance, numberOfDaysSinceLastWithdrawal)) {
            closingBalance *= (1 + dailyInterestRate);

            numberOfDaysBetweenTransactions--;
            numberOfDaysSinceLastWithdrawal--;
        }

        dailyInterestRate = tierTwoInterestRate / numberOfDaysInYear;

        while (numberOfDaysBetweenTransactions > 0) {
            closingBalance *= (1 + dailyInterestRate);

            numberOfDaysBetweenTransactions--;
        }

        this.lastBalance = closingBalance;

        return closingBalance;
    }

    /**
     * Returns a value representing whether there has been a withdrawal from this account in the last 10 days.
     *
     * @param currentBalance The current balance.
     * @param numberOfDaysSinceLastWithdrawal The number of days since the last withdrawal.
     *
     * @return The value representing whether there has been a withdrawal from this account in the last 10 days.
     */
    private boolean didWithdrawLastTenDays(double currentBalance, int numberOfDaysSinceLastWithdrawal) {
        return this.lastBalance >= 0 &&
               currentBalance < this.lastBalance &&
               numberOfDaysSinceLastWithdrawal > 0;
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[MaxiSavingsAccount: accountId=%s, customerId=%s, transactions=%s]", this.accountId, this.customerId, this.transactions);
    }
}
