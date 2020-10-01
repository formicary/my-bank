package com.abc.exception;

import com.abc.entity.Bank;
import com.abc.entity.impl.AccountImpl;
import com.abc.entity.Customer;
import com.abc.service.TransactionManager;

import java.math.BigDecimal;

public class InputValidator {

    public static void verifyAccountOpen( AccountImpl account) {

        if(account == null){
            throw new InvalidAccountException("Account cannot be null");
        }
    }

    public static void validateDeposit(Customer customer, AccountImpl account, BigDecimal amount) {
        if(!customer.getAccounts().contains(account)){
            throw new InvalidAccountException("Account must exist within customers accounts");
        }
        if(account == null){
            throw new InvalidAccountException("Account cannot be null");
        }
        if(amount == null || amount.doubleValue() <= 0){
            throw new InvalidAmountException("Amount must be greater than 0");
        }
    }

    public static void validateWithdrawal(Customer customer, AccountImpl account, BigDecimal amount) {
        validateDeposit(customer, account, amount);
        if(TransactionManager.sumTransactions(account).doubleValue() <= 0){
            throw new InvalidAccountException("Cannot withdraw from an account without positive balance");
        }
    }

    public static void validateBank(Bank bank){
        if(bank == null){
            throw new InvalidBankException("Bank must not be null");
        }
    }
}
