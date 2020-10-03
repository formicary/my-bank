package com.abc.exception;

import com.abc.entity.Account;
import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.entity.AccountType;

import java.math.BigDecimal;

/**
 * Validation class used to validate the arguments passed into services
 * @author aneesh
 */
public class InputValidator {

    public static void verifyAccount(Account account) {

        if(account == null){
            throw new InvalidAccountException("Account cannot be null");
        }
    }

    public static void validateDeposit(Account account, BigDecimal amount) {
        verifyAccount(account);
        if(amount == null || amount.doubleValue() <= 0){
            throw new InvalidAmountException("Amount must be greater than 0");
        }
    }

    public static void validateWithdrawal(Account account, BigDecimal amount) {
        validateDeposit(account, amount);
        if(account.calculateBalance().doubleValue() <= 0){
            throw new InvalidAccountException("Cannot withdraw from an account without positive balance");
        }
    }

    public static void validateBank(Bank bank){
        if(bank == null){
            throw new InvalidBankException("Bank must not be null");
        }
    }

    public static void validateTransfer(Account from, Account to, BigDecimal amount) {

        verifyAccount(from);
        verifyAccount(to);
        if(from.equals(to)){
            throw new InvalidAccountException("Transfer cannot be made between same account");
        }

        if(amount == null || amount.doubleValue() <= 0 ){
            throw new InvalidAmountException("Transfer amount must be a positive amount");
        }
        if(from.calculateBalance().doubleValue() <  amount.doubleValue()){
            throw new InvalidAmountException("Insufficient funds to transfer " + amount + " from " + from);
        }

//
//        if(!(customer.getAccounts().contains(from) && customer.getAccounts().contains(to))){
//            throw new InvalidAccountException("Transfer must be made between the two accounts of the customer");
//        }
    }

    public static void validateAccountType(AccountType accountType) {
        if(accountType == null){
            throw new InvalidAccountException("AccountType cannot be null for account");
        }
    }

    public static void validateNewAccount(Customer customer, AccountType accountType, String accountNumber) {
        if(customer == null){
            throw new InvalidCustomerException("Customer must not be null");
        }
        validateAccountType(accountType);
        if(customer.getAccounts().keySet().contains(accountNumber)){
            throw new InvalidAccountException("Customer " + customer + " already contains the account number specified.");
        }
    }

    public static void validateAddingCustomerAccount(Customer customer, Account account) {
        if(account == null){
            throw new InvalidAccountException("Account must not be null");
        }
        if(customer == null){
            throw new InvalidCustomerException("Customer must not be null");
        }
        if(account.getCustomer() != customer){
            throw new InvalidCustomerException("Cannot add account to customer as it belongs to: " + customer);
        }
    }
}
