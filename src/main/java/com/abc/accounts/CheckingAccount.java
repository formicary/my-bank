package com.abc.accounts;

import com.abc.Account;
import com.google.common.collect.Maps;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        this.transactions = Maps.newLinkedHashMap();
        this.balance = 0;
    }

    public double interestEarned() {
        return getBalance() * 0.001;
    }

}

