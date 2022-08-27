package com.abc.account;

import com.abc.customer.Customer;

public class CheckingAccount extends Account {

    public CheckingAccount(Customer customer, AccountType accountType) {
        super(customer, accountType);
    }

    @Override
    public double calcInterestEarned() {
        double amount = getBalance();
        return amount * 0.001;
    }
}
