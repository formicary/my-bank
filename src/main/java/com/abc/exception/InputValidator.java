package com.abc.exception;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.service.TransactionManager;

import java.math.BigDecimal;

public class InputValidator {

    public static void verifyAccountOpen( Account account) {

        if(account == null){
            throw new InvalidAccountException("Account cannot be null");
        }
    }

    public static void validateDeposit(Account account, BigDecimal amount) {
        if(account == null){
            throw new InvalidAccountException("Account cannot be null");
        }
        if(amount == null || amount.doubleValue() <= 0){
            throw new InvalidAmountException("Amount must be greater than 0");
        }
    }

    public static void validateWithdrawal(Account account, BigDecimal amount) {
        validateDeposit(account, amount);
        if(TransactionManager.sumTransactions(account).doubleValue() <= 0){
            throw new InvalidAccountException("Cannot withdraw from an account without positive balance");
        }
    }
}
