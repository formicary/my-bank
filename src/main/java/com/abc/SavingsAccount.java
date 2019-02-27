package com.abc;

import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account {

    public double interestEarned() {
        double balance = getBalance();

        if (balance <= 1000) {
            return balance * 0.001;
        } else {
            return 1 + (balance-1000) * 0.002;
        }
    }

    public String accountType() {
        return "Savings Account";
    }

}
