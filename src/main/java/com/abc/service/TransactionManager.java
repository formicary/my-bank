package com.abc.service;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.entity.impl.Transaction;
import com.abc.exception.InputValidator;
import java.math.BigDecimal;

/**
 * Transaction manager responsible for creating deposit, withdrawal and transfer of funds.
 * @author aneesh
 */

public class TransactionManager {

    private Customer customer;
    public TransactionManager(Customer customer){
        this.customer = customer;
    }

    public void deposit(Account account, BigDecimal amount) {
        InputValidator.validateDeposit(customer, account, amount);
        account.getTransactions().add(new Transaction(amount));

    }

    public void withdraw(Account account, BigDecimal amount) {
        InputValidator.validateWithdrawal(customer, account, amount);
        account.getTransactions().add(new Transaction(amount.multiply(new BigDecimal(-1))));
    }

    public void transfer(Account from, Account to, BigDecimal amount){
        InputValidator.validateTransfer(customer, from, to, amount);
        withdraw(from, amount);
        deposit(to, amount);
    }

    public static BigDecimal sumTransactions(Account account) {

        BigDecimal amount = new BigDecimal(0);
        for (Transaction transaction: account.getTransactions()) {
            amount = amount.add(transaction.amount);
        }
        return amount;

    }
}
