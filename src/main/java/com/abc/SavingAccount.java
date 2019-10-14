package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingAccount extends Account {

    public SavingAccount() {
        super("Savings Account");
    }

    @Override
    BigDecimal interestEarned() {
        BigDecimal balance = getBalance();
        BigDecimal result;

        //if balance <= 1000
        if(balance.compareTo(PRIMARY_BALANCE) <= 0){
            result = balance.multiply(INTEREST_F1);
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        }else{
            result = balance.subtract(PRIMARY_BALANCE).multiply(INTEREST_F2).add(BigDecimal.ONE);
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        }
//        if (amount <= 1000)
//            return amount * 0.001;
//        else
//            return 1 + (amount - 1000) * 0.002;
    }
}
