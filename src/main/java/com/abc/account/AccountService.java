package com.abc.account;

import com.abc.transaction.Transaction;
import com.abc.transaction.TransactionType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {

    private static AccountService instance;
    private final Map<AccountType, AccountStrategy> strategyMap;

    private AccountService() {
        strategyMap = new HashMap<>();
        strategyMap.put(AccountType.CHECKING, new CheckingAccountStrategy());
        strategyMap.put(AccountType.SAVINGS, new SavingsAccountStrategy());
        strategyMap.put(AccountType.MAXI_SAVINGS, new MaxiSavingsAccountStrategy());
    }

    /**
     * getInstance
     * @return
     */
    public static synchronized AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }

        return instance;
    }

    /**
     * deposit
     * @param account
     * @param amount
     * @throws IllegalArgumentException
     */
    public void deposit(Account account, double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero: " + amount);
        } else {
            Transaction transaction = new Transaction(TransactionType.DEPOSIT,amount);
            account.getTransactions().add(transaction);
        }
    }

    /**
     * withdraw
     * @param account
     * @param amount
     * @throws IllegalArgumentException
     */
    public void withdraw(Account account, double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero: " + amount);
        } else {
            Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, -amount);
            account.getTransactions().add(transaction);
        }
    }

    /**
     * transfer
     * @param sourceAccount
     * @param destinationAccount
     * @param amount
     * @throws IllegalArgumentException
     */
    public void transfer(Account sourceAccount, Account destinationAccount, double amount) throws IllegalArgumentException {
        double balanceOfSourceAccount = sumTransactions(sourceAccount);
        if (balanceOfSourceAccount >= amount) {
            withdraw(sourceAccount, amount);
            deposit(destinationAccount, amount);
        } else {
            throw new IllegalArgumentException("Amount must be greater than the balance of the source account. Balance: "
                    + balanceOfSourceAccount + ", amount: " + amount);
        }
    }

    /**
     * interestEarned
     * @param account
     * @return
     */
    public double interestEarned(Account account) {
        AccountType accountType = account.getAccountType();
        AccountStrategy strategy = strategyMap.get(accountType);
        if (strategy == null) {
            throw new IllegalStateException("Unknown account type: " + accountType);
        }

        double amount = sumTransactions(account);
        Date dateOfLastWithdrawal = getDateOfLastWithdrawal(account);
        return strategy.interestEarned(amount, dateOfLastWithdrawal);
    }

    /**
     * sumTransactions
     * @param account
     * @return
     */
    public double sumTransactions(Account account) {
        double amount = 0.0;
        for (Transaction transaction : account.getTransactions()) {
            amount += transaction.getAmount();
        }

        return amount;
    }

    /**
     * getDateOfLastWithdrawal
     * @param account
     * @return
     */
    public Date getDateOfLastWithdrawal(Account account) {
        List<Transaction> transactions = account.getTransactions();

        if (transactions.isEmpty()) {
            return null;
        } else if (transactions.size() == 1) {
            return transactions.get(0).getTransactionDate();
        }

        Date maxDate = transactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.WITHDRAWAL)
                .map(Transaction::getTransactionDate)
                .max(Date::compareTo)
                .get();

        return maxDate;
    }
}
