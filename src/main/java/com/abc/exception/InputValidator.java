package com.abc.exception;

import com.abc.entity.Account;
import com.abc.entity.Customer;

public class InputValidator {

    public static void verifyAccountOpen(Customer customer, Account account) {
        if(customer == null){
            throw new InvalidCustomerException("Customer cannot be null");
        }
        if(account == null){
            throw new InvalidAccountException("Account cannot be null");
        }
    }

}
