package com.abc;

import java.util.ArrayList;
import java.util.List;

public class MaxiSavingsAccount extends Account {

    public double interestEarned() {
        double balance = getBalance();

        if (balance <= 1000) {
            return balance * 0.02;
        } else if (balance <= 2000) {
            return 20 + (balance-1000) * 0.05;
        } else {
            return 70 + (balance-2000) * 0.1;
        }
    }

    public String accountType() {
        return "Maxi Savings Account";
    }

}
