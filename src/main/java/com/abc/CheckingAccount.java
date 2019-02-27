package com.abc;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends Account {

    public double interestEarned() {
        double balance = getBalance();

        return balance * 0.001;
    }

    public String accountType() {
        return "Checking Account";
    }

}
