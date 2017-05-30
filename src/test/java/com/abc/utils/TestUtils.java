package com.abc.utils;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.accounts.CheckingAccount;
import com.abc.accounts.MaxiSavingsAccount;
import com.abc.accounts.SavingsAccount;
import com.abc.exceptions.*;

public class TestUtils {

    public static Customer createCustomer(Bank bank, String name) {
        Customer customer = new Customer(name);
        bank.getSystemManagement().addCustomer(customer);
        return customer;
    }

    public static Account createCheckingAccount(Customer customer) {
        Account account = new CheckingAccount();
        customer.getAccountManagement().openAccount(account);
        return account;
    }

    public static Account createSavingsAccount(Customer customer) {
        Account account = new SavingsAccount();
        customer.getAccountManagement().openAccount(account);
        return account;
    }

    public static Account createMaxiSavingsAccount(Customer customer) {
        Account account = new MaxiSavingsAccount();
        customer.getAccountManagement().openAccount(account);
        return account;
    }

    public static double depositMoney(Account account, double amount) throws NonPositiveAmountException {
        account.deposit(amount);
        return account.getBalance();
    }

    public static double withdrawMoney(Account account, double amount) throws ExceedsNegativeBalanceException, NonPositiveAmountException {
        account.withdraw(amount);
        return account.getBalance();
    }

    public static double transferMoney(Bank bank, Account fromAccount, int toCustomerID, int toAccountID, double amount) throws CustomerNotExistException, IdenticalAccountIDException, ExceedsNegativeBalanceException, NonPositiveAmountException, AccountNotExistException {
        fromAccount.transfer(toCustomerID, toAccountID, amount, bank);
        return fromAccount.getBalance();
    }
}
