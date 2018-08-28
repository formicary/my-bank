package com.abc.account;

import com.abc.branch.Customer;
import com.abc.util.ZeroAmountException;

/**
 * Created by sameen on 24/08/2018.
 */
public class CheckingAccount extends Account {

    public CheckingAccount(Customer owner, double openingBalance) {
        super(owner, openingBalance);
    }

    @Override
    public double interestEarned() {
        return this.getBalance() * 0.001; // 0.1% flat rate
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CHECKING;
    }

}
