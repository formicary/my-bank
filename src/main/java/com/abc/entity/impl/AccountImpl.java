package com.abc.entity.impl;

import com.abc.entity.Account;
import com.abc.entity.AccountType;
import com.abc.entity.Customer;
import com.abc.entity.Transaction;
import com.abc.exception.InputValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@code Account}
 * @author aneesh
 */
public class AccountImpl implements Account {

    private final AccountType accountType;
    private List<Transaction> transactions;
    private final Customer customer;
    private String accountNumber;

    public AccountImpl(Customer customer, AccountType accountType, String accountNumber) {
        InputValidator.validateNewAccount(customer, accountType, accountNumber);

        this.customer = customer;
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
        this.accountNumber = accountNumber;
        customer.addAccount(this);
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal calculateBalance() {
        BigDecimal amount = new BigDecimal(0);
        for (Transaction transaction: getTransactions()) {
            amount = amount.add(transaction.getAmount());
        }

        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
