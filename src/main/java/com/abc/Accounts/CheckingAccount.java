package com.abc.Accounts;

import com.abc.Utils.BankUtils;
import com.abc.Utils.IDateProvider;

import java.util.Date;

/**
 * Represents a checking bank account.
 */
public class CheckingAccount extends AbstractAccount {
    /**
     * The interest rate.
     */
    private static final double interestRate = 0.001;

    /**
     * Initializes a new instance of the CheckingAccount class.
     *
     * @param dateProvider The date provider.
     * @param accountId The account ID.
     * @param customerId The customer ID.
     */
    public CheckingAccount(IDateProvider dateProvider, int accountId, int customerId) {
        super(dateProvider, accountId, customerId);
    }

    /**
     * Gets a string representing the account type.
     *
     * @return The string representing the account type.
     */
    protected String getAccountType() {
        return "Checking";
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

        double dailyInterestRate = interestRate / numberOfDaysInYear;

        return initialBalance * Math.pow(1 + dailyInterestRate, numberOfDaysBetweenTransactions);
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[CheckingAccount: accountId=%s, customerId=%s, transactions=%s]", this.accountId, this.customerId, this.transactions);
    }
}
