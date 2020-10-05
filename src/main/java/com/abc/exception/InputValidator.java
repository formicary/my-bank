package com.abc.exception;

import com.abc.entity.*;

import java.math.BigDecimal;

/**
 * Validation class used to validate the arguments passed into services
 * @author aneesh
 */
public class InputValidator {


    /**
     * Validate a deposit is correct by validating the account and amount
     * @param account to be validated
     * @param amount to be validated
     */
    public static void validateDeposit(Account account, BigDecimal amount) {
        validateAccountNotNull(account);
        validateAmountNotNullAndPositive(amount);
    }


    /**
     * Validate a withdrawal is correct by validating the account and amount
     * @param account to be validated
     * @param amount to be validated
     */
    public static void validateWithdrawal(Account account, BigDecimal amount) {
        validateAccountNotNull(account);
        validateAmountNotNullAndPositive( amount);
        if(account.calculateBalance().doubleValue() <= 0){
            throw new InvalidAccountException("Cannot withdraw from an account without positive balance");
        }
    }

    /**
     * Validate a bank is not null
     * @param bank to be validated
     */
    public static void validateBank(Bank bank){
        if(bank == null){
            throw new InvalidBankException("Bank must not be null");
        }
    }

    /**
     * Validate transfer is correct by validating the accounts and amount
     * @param from to be validated
     * @param to to be validated
     * @param amount to be validated
     */
    public static void validateTransfer(Account from, Account to, BigDecimal amount) {

        validateAccountNotNull(from);
        validateAccountNotNull(to);
        validateAmountNotNullAndPositive(amount);
        if(from.equals(to)){
            throw new InvalidAccountException("Transfer cannot be made between same account");
        }

        if(transferExceedsBalance(amount, from)){
            throw new InvalidAmountException("Insufficient funds to transfer " + amount + " from " + from);
        }

    }

    /**
     * Validate a new account is correct by validating the customer, account type and account number
     * @param customer to be validated
     * @param accountType to be validated
     * @param accountNumber to be validated
     */
    public static void validateNewAccount(Customer customer, AccountType accountType, String accountNumber) {
        validateCustomerNotNull(customer);
        validateAccountTypeNotNull(accountType);
        if(customerAlreadyContainsAccount(customer, accountNumber)){
            throw new InvalidAccountException("Customer " + customer + " already contains the account number specified.");
        }
    }

    /**
     * Validate adding a new customer account by validating the customer and account
     * @param customer to be validated
     * @param account to be validated
     */
    public static void validateAddingCustomerAccount(Customer customer, Account account) {
        validateAccountNotNull(account);
        validateCustomerNotNull(customer);

        if(customerOwnsAccount(customer, account)){
            throw new InvalidCustomerException("Cannot add account to customer as it belongs to: " + customer);
        }
    }

    /**
     * Validate a transaction is not null
     * @param transaction to be validated
     */
    public static void validateTransaction(Transaction transaction) {
        if(transaction == null){
            throw new InvalidTransactionException("Transaction cannot be null");
        }
    }

    /**
     * Validate an amount is not null
     * @param amount to be validated
     */
    public static void validateAmountNotNull(BigDecimal amount) {
        if(amount == null){
            throw new InvalidAmountException("Amount cannot be null");
        }
    }

    /**
     * Validate the customer is not null
     * @param customer to be validated
     */
    public static void validateCustomerNotNull(Customer customer) {
        if(customer == null){
            throw new InvalidCustomerException("Customer cannot be null");
        }
    }

    private static void validateAccountNotNull(Account account) {
        if(account == null){
            throw new InvalidAccountException("Account cannot be null");
        }
    }

    private static void validateAccountTypeNotNull(AccountType accountType) {
        if(accountType == null){
            throw new InvalidAccountException("AccountType cannot be null for account");
        }
    }

    private static void validateAmountNotNullAndPositive(BigDecimal amount) {
        if(amount == null || amount.doubleValue() <= 0){
            throw new InvalidAmountException("Amount must be greater than 0");
        }
    }

    private static boolean transferExceedsBalance(BigDecimal amount, Account from) {
        return from.calculateBalance().doubleValue() <  amount.doubleValue();
    }

    private static boolean customerOwnsAccount(Customer customer, Account account) {
        return account.getCustomer() != customer;
    }

    private static boolean customerAlreadyContainsAccount(Customer customer, String accountNumber){
        return customer.getAccounts().keySet().contains(accountNumber);
    }
}
