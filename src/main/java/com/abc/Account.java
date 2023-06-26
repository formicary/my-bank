package com.abc;

import com.abc.ENUMS.AccountType;
import com.abc.ENUMS.TransactionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.abc.Utility.AccountInfoChecker.isSufficientBalance;
import static com.abc.Utility.AccountInfoChecker.isValidAmount;

public class Account {


    private double balance = 0.00;

    private final AccountType accountType;
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount, TransactionType transactionType) {
        isValidAmount(amount);
        transactions.add(new Transaction(amount, transactionType));
        this.addBalance(amount);
    }

    public void withdraw(double amount, TransactionType transactionType) {
        isValidAmount(amount);
        isSufficientBalance(amount, this);
        //Added transaction type to get better visibility of transaction in statement like
        //it is withdrawal transaction or fund transfer transaction etc
        transactions.add(new Transaction(-amount, transactionType));
        this.deductBalance(amount);

    }

    public double interestEarned() {
//        double amount = sumTransactions();
        double amount=getBalance();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
            case MAXI_SAVINGS:
                if (!checkIfWithdrawlExistInPast10Days()) //checking if there is any withdrawal in past10days
                    return amount * 0.05;
                else
                    return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        return checkIfTransactionsExist();
    }

    private double checkIfTransactionsExist() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    private void addBalance(double amount) {
        double currentBalance = this.getBalance();
        currentBalance += amount;
        setBalance(currentBalance);
    }

    public void deductBalance(double amount) {
        double currentBalance = this.getBalance();
        currentBalance -= amount;
        setBalance(currentBalance);
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    private boolean checkIfWithdrawlExistInPast10Days() {
        LocalDate past10Days = LocalDate.now().minusDays(10);
        for (Transaction t : transactions) {
            if (t.getTransactionDate().isAfter(past10Days)) {
                if (t.getTransactionType() == TransactionType.WITHDRAWL)
                    return true;
            }
        }
        return false;
    }

}
