package com.abc;


public class TestHelper {

    public static Customer createCustomerWithAccounts(String name, int number) {

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
        Customer customer = new Customer(name);

        if (number >= 1) customer.openAccount(checkingAccount);
        if (number >= 2) customer.openAccount(savingsAccount);
        if (number >= 3) customer.openAccount(maxiSavingAccount);

        return customer;
    }
}
