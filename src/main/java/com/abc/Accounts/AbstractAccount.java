package com.abc.Accounts;

import com.abc.Transactions.ITransaction;
import com.abc.Transactions.Transaction;
import com.abc.Transactions.TransactionException;
import com.abc.Utils.BankUtils;
import com.abc.Utils.IDateProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a bank account.
 */
public abstract class AbstractAccount implements IAccount {
    /**
     * The account ID.
     */
    protected int accountId;

    /**
     * The customer ID that corresponds to this account.
     */
    protected int customerId;

    /**
     * The number of days in a year.
     */
    protected static final double numberOfDaysInYear = 365;

    /**
     * The date provider.
     */
    private IDateProvider dateProvider;

    /**
     * The list of transaction of this account to date.
     */
    protected List<ITransaction> transactions;

    /**
     * Initializes a new instance of the AbstractAccount class.
     *
     * @param dateProvider The date provider.
     * @param accountId The account ID.
     * @param customerId The customer ID.
     */
    public AbstractAccount(IDateProvider dateProvider, int accountId, int customerId) {
        this.dateProvider = dateProvider;
        this.accountId = accountId;
        this.customerId = customerId;

        this.transactions = new ArrayList<ITransaction>();
    }

    /**
     * Gets the transactions.
     *
     * @return The transactions.
     */
    public List<ITransaction> getTransactions() {
        return transactions;
    }

    /**
     * Gets the account ID.
     *
     * @return The account ID.
     */
    public int getAccountId() {
        return this.accountId;
    }

    /**
     * Gets the customer ID.
     *
     * @return The customer ID.
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Deposits the given amount into this bank account.
     *
     * @param amount The amount to be deposited.
     *
     * @throws TransactionException Thrown when the transaction failed.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new TransactionException(
                    String.format("Failed to deposit because amount %s is less than or equal to zero", amount));
        }

        transactions.add(new Transaction(amount, this.dateProvider.now()));
    }

    /**
     * Withdraws the given amount from this bank account.
     *
     * @param amount The amount to be withdrawn.
     *
     * @throws TransactionException Thrown when the transaction failed.
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new TransactionException(
                    String.format("Failed to withdraw because amount %s was less than or equal zero", amount));
        }

        if (this.sumTransactions() < amount) {
            throw new TransactionException(
                    String.format("Failed to withdraw because amount %s was more than the account's balance", amount));

        }

        transactions.add(new Transaction(-amount, this.dateProvider.now()));
    }

    /**
     * Returns the total amount of money moved in all transactions.
     *
     * @return The total amount of money moved in all transactions.
     */
    public double sumTransactions() {
        double amount = 0.0;

        for (ITransaction t: transactions) {
            amount += t.getAmount();
        }

        return amount;
    }

    /**
     * Returns the interest earned so far.
     *
     * @return The amount of interest earned.
     */
    public double calculateInterestEarned() {
        List<ITransaction> transactions = this.getTransactions();

        int numberOfTransactions = transactions.size();

        if (numberOfTransactions == 0) {
            return 0.0;
        }

        ITransaction transaction = transactions.get(0);
        ITransaction nextTransaction = null;

        double openingBalance = this.sumTransactions();
        double closingBalance = transaction.getAmount();
        double interestEarned;

        // Iterate over transactions and calculate accrued interest between transaction dates.
        for (int index = 1; index < numberOfTransactions; index++) {
            nextTransaction = transactions.get(index);

            closingBalance = this.accrueInterest(closingBalance, transaction.getTransactionDate(), nextTransaction.getTransactionDate());

            closingBalance += nextTransaction.getAmount();

            transaction = nextTransaction;
        }

        closingBalance = this.accrueInterest(closingBalance, transaction.getTransactionDate(), this.dateProvider.now());

        interestEarned = closingBalance - openingBalance;

        return interestEarned;
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
    protected abstract double accrueInterest(double initialBalance, Date firstTransactionDate, Date secondTransactionDate);

    /**
     * Returns a statement for this account.
     *
     * @return The statement.
     */
    public String getAccountStatement() {
        String statement = this.getAccountType() + " Account\n";

        // Total up all the transactions
        double total = 0.0;

        for (ITransaction t : this.getTransactions()) {
            statement += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + BankUtils.toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }

        statement += "Total " + BankUtils.toDollars(total);

        return statement;
    }

    /**
     * Gets a string representing the account type.
     *
     * @return The string representing the account type.
     */
    protected abstract String getAccountType();

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[Account: accountId=%s, customerId=%s, dateProvider=%s, transactions=%s]", this.accountId, this.customerId, this.dateProvider, this.transactions);
    }
}
