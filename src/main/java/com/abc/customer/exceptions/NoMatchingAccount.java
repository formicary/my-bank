package com.abc.customer.exceptions;

import com.abc.customer.Account;
import com.abc.customer.Customer;

public class NoMatchingAccount extends Exception {

    private Account account;
    private Customer customer;

    public NoMatchingAccount(Account account, Customer customer) {
        this.account = account;
        this.customer = customer;
    }

    @Override
    public String getMessage() {
        return String.format("Account %s does not exist for Customer %s", account, customer);
    }
}
