package com.abc.accounts;

import com.abc.Account;
import com.google.common.collect.Maps;

public class SavingsAccount extends Account {

    public SavingsAccount() {
        this.transactions = Maps.newLinkedHashMap();
        this.balance = 0;
    }

    public double interestEarned() {
        double amount = getBalance();
        return amount <= 1000 ? amount * 0.001 : 1 + (amount - 1000) * 0.002;
    }


}
