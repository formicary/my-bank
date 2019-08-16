package com.abc;

public class CheckingsAccount extends Account {
    public CheckingsAccount(int accountType) {
        super(accountType);
    }

    public double interestEarned() {

//        switch(accountType){
//            case SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.001;
//                else
//                    return 1 + (amount-1000) * 0.002;
//            case MAXI_SAVINGS:
//                if (amount <= 1000) return amount * 0.02;
//                if (amount <= 2000) return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
//            default:
//                return amount * 0.001;
//        }
        return 0;
    }
}
