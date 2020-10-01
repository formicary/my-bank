package com.abc.exception;

import com.abc.entity.Account;
import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.service.TransactionManager;
import java.math.BigDecimal;

/**
 * Validation class used to validate the arguments passed into services
 * @author aneesh
 */
public class InputValidator {

    public static void verifyAccountOpen( Account account) {

        if(account == null){
            throw new InvalidAccountException("Account cannot be null");
        }
    }

    public static void validateDeposit(Customer customer, Account account, BigDecimal amount) {
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

    public static void validateWithdrawal(Customer customer, Account account, BigDecimal amount) {
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

    public static void validateTransfer(Customer customer, Account from, Account to, BigDecimal amount) {
        if(customer == null){
            throw new InvalidCustomerException("Customer cannot be null");
        }
        if(from == null || to == null){
            throw new InvalidAccountException("Accounts must not be null");
        }
        if(amount.doubleValue() <= 0 ){
            throw new InvalidAmountException("Transfer amount must be a positive amount");
        }
        if(!(customer.getAccounts().contains(from) && customer.getAccounts().contains(to))){
            throw new InvalidAccountException("Transfer must be made between the two accounts of the customer");
        }
    }
}
