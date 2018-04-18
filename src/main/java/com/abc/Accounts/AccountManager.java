package com.abc.Accounts;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents an account manager.
 */
public class AccountManager implements IAccountManager {
    /**
     * The account ID manager.
     */
    private IAccountIdManager accountIdManager;

    /**
     * The account factory.
     */
    private IAccountFactory accountFactory;

    /**
     * The accounts opened by the customer.
     */
    private List<IAccount> accounts;

    /**
     * Initializes a new instance of the AccountManager class.
     *
     * @param accountIdManager The account ID manager.
     * @param accountFactory The account factory.
     */
    public AccountManager(IAccountIdManager accountIdManager, IAccountFactory accountFactory) {
        this.accountIdManager = accountIdManager;
        this.accountFactory = accountFactory;
        this.accounts = new LinkedList<IAccount>();
    }

    /**
     * Returns the number of accounts opened by the customer.
     *
     * @return The number of accounts opened by the customer.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Gets the accounts that correspond to the given customer ID.
     *
     * @param customerId The customer ID.
     *
     * @return The accounts.
     */
    public List<IAccount> getAccounts(int customerId) {
        List<IAccount> customerAccounts = new LinkedList<IAccount>();

        for (IAccount account : this.accounts) {
            if (account.getCustomerId() == customerId) {
                customerAccounts.add(account);
            }
        }

        return customerAccounts;
    }

    /**
     * Gets the account corresponding to given account ID.
     *
     * @param accountId The account ID.
     *
     * @return The account.
     */
    public IAccount getAccount(int accountId) {
        for (IAccount account : this.accounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }

        return null;
    }

    /**
     * Opens an account in the name of the given customer ID.
     *
     * @param customerId The customer ID.
     * @param accountType The account type.
     *
     * @returns The ID of the newly opened account.
     * @throws AccountException Thrown when the given account type is not recognized.
     */
    public int openAccount(int customerId, AccountType accountType) {
        int accountId = this.accountIdManager.generateAccountId();

        IAccount account;

        switch (accountType) {
            case CHECKING:
                account = this.accountFactory.createCheckingAccount(accountId, customerId);
                break;
            case SAVINGS:
                account = this.accountFactory.createSavingsAccount(accountId, customerId);
                break;
            case MAXI_SAVINGS:
                account = this.accountFactory.createMaxiSavingsAccount(accountId, customerId);
                break;
            default:
                throw new AccountException(
                        String.format("Failed to open account because of unknown account type %s", accountType));
        }

        this.accounts.add(account);

        return accountId;
    }

    /**
     * Withdraws the given amount from a customer's account.
     *
     * @param customerId The customer ID.
     * @param accountId The account ID.
     * @param amount The amount of money to be withdrawn.
     *
     * @throws AccountException Thrown when the given account ID or customer ID is not recognised.
     */
    public void withdraw(int customerId, int accountId, double amount) {
        IAccount account = this.getAccount(accountId);

        if (account == null) {
            throw new AccountException(
                    String.format("Could not withdraw amount %s as no account exists for ID %s", amount, accountId)
            );
        }

        if (account.getCustomerId() != customerId) {
            throw new AccountException(
                    String.format(
                            "Could not withdraw amount %s as customer ID %s does not match account's customer ID",
                            amount,
                            customerId)
            );
        }

        account.withdraw(amount);
    }

    /**
     * Deposits the given amount into a customer's account.
     *
     * @param customerId The customer ID.
     * @param accountId The account ID.
     * @param amount The amount of money to be deposited.
     *
     * @throws AccountException Thrown when the given account ID or customer ID is not recognised.
     */
    public void deposit(int customerId, int accountId, double amount) {
        IAccount account = this.getAccount(accountId);

        if (account == null) {
            throw new AccountException(
                    String.format("Could not deposit amount %s as no account exists for ID %s", amount, accountId)
            );
        }

        if (account.getCustomerId() != customerId) {
            throw new AccountException(
                    String.format(
                            "Could not deposit amount %s as customer ID %s does not match account's customer ID",
                            amount,
                            accountId)
            );
        }

        account.deposit(amount);
    }

    /**
     * Transfers the given amount from the source account ID to the destination account ID.
     *
     * @param customerId The customer ID.
     * @param sourceAccountId The ID of the source account.
     * @param destinationAccountId The ID of the destination account.
     * @param amount The amount of money to transfer.
     *
     * @throws AccountException Thrown when the given account IDs or customer ID are not recognised.
     */
    public void transfer(int customerId, int sourceAccountId, int destinationAccountId, double amount) {
        IAccount sourceAccount = this.getAccount(sourceAccountId);
        IAccount destinationAccount = this.getAccount(destinationAccountId);

        if (sourceAccount == null) {
            throw new AccountException(
                    String.format("Could not transfer amount %s as no account exists for ID %s", amount, sourceAccountId)
            );
        }

        if (sourceAccount.getCustomerId() != customerId) {
            throw new AccountException(
                    String.format(
                            "Could not transfer amount %s as customer ID %s does not match account's customer ID",
                            amount,
                            sourceAccountId)
            );
        }

        if (destinationAccount == null) {
            throw new AccountException(
                    String.format("Could not transfer amount %s as no account exists for ID %s", amount, destinationAccountId)
            );
        }

        sourceAccount.withdraw(amount);
        destinationAccount.deposit(amount);
    }

    /**
     * Calculates the total interest earned across all accounts for a given customer ID.
     *
     * @return The total interest.
     */
    public double calculateTotalInterestEarned(int customerId) {
        double total = 0;

        for (IAccount a : accounts) {
            if (customerId == a.getCustomerId()) {
                total += a.calculateInterestEarned();
            }
        }

        return total;
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[AccountManager: accountIdManager=%s, accountFactory=%s, accounts=%s]", this.accountIdManager, this.accountFactory, this.accounts);
    }
}
