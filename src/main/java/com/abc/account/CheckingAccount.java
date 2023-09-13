package com.abc.account;

import java.time.LocalDateTime;

public class CheckingAccount extends Account {

    @Override
    public String getType() {
        return "Checking";
    }

    /**
     * Checking account has a flat rate of 0.1
     */
    @Override
    public double getRateByDate(LocalDateTime date) {
        return 0.1;
    }
}
