package com.abc.service;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.exception.InputValidator;

public class CustomerService {

    public static Customer openAccount(Customer customer, Account account) {
        InputValidator.verifyAccountOpen(customer, account);
        customer.addAccount(account);
        return customer;
    }


}
