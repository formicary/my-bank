package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MaxiAccount extends Account {
    private static final BigDecimal RATE_1 = new BigDecimal("0.02");    //2%
    private static final BigDecimal RATE_2 = new BigDecimal("0.05");    //5%
    private static final BigDecimal RATE_3 = new BigDecimal("0.10");    //10%

    public MaxiAccount() {
        super("Maxi Saving Account");
    }

    @Override
    BigDecimal interestEarned() {
        System.out.println("MAXI INTEREST CALCULATE**************");
        BigDecimal balance = getBalance();
        BigDecimal result;

        if(balance.compareTo(PRIMARY_BALANCE) <= 0){
            System.out.println("balance <= 1000");
            result = balance.multiply(RATE_1);
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        }else if(balance.compareTo(SECONDARY_BALANCE) <= 0){
            System.out.println("balance <= 2000");
            result = balance.subtract(PRIMARY_BALANCE).multiply(RATE_2).add(new BigDecimal("20.00"));
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        }else{
            System.out.println("balance > 2000");
//            System.out.println("balance: "+balance.toString());
            //balance > £2000
            result = balance.subtract(SECONDARY_BALANCE).multiply(RATE_3).add(new BigDecimal("70.00"));
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        }

//        if (balance <= 1000)
//            return balance * 0.02;
//        if (balance <= 2000)
//            return 20 + (balance - 1000) * 0.05;
//        return 70 + (balance - 2000) * 0.1;
    }
}
