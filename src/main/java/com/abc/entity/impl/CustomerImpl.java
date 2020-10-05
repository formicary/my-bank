package com.abc.entity.impl;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.exception.InputValidator;

import java.math.BigDecimal;
import java.util.*;

/**
 * Implementation of {@code Customer}
 * @author aneesh
 */
public class CustomerImpl implements Customer {


    private String name;
    private HashMap<String, Account> accounts;

    public CustomerImpl(String name) {
        this.name = name;
        this.accounts = new HashMap<>();
    }

    public String getName() {
        return name;
    }


    public void addAccount(Account account){
        InputValidator.validateAddingCustomerAccount(this, account);
        accounts.putIfAbsent(account.getAccountNumber() ,account);
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public void deposit(BigDecimal amount, Account account) {
        InputValidator.validateDeposit(account,  amount);
        account.addTransaction(new TransactionImpl(amount));
    }

    public void withdraw(BigDecimal amount, Account account) {
        InputValidator.validateWithdrawal(account,  amount);
        account.addTransaction(new TransactionImpl(amount.multiply(new BigDecimal("-1"))));
    }

    public void transfer(BigDecimal amount,Account from, Account to){
        InputValidator.validateTransfer(from, to, amount);
        withdraw(amount, from);
        deposit(amount, to);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                '}';
    }
}
