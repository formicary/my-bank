package com.abc;

import java.util.Calendar;
import java.util.Date;

public class MaxiSavingsAccount extends Account {

    protected final double initialInterestRate = 0.02;
    protected final double higherInterestRate = 0.05;
    protected final double topInterestRate = 0.1;

    public MaxiSavingsAccount(Customer owner){
        super(owner);
        this.accountType = 2;
        this.accountTypeString = "Maxi-Savings";
    }

    /**
     * Maxi-Savings accounts have a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%
     * @return interest earned on money in account
     */
    // ... an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
    public double interestEarnedAnnum(){

        double interestRate;

        Date now = Calendar.getInstance().getTime();

        //Date now = DateProvider.getInstance().now();

        if(this.lastWithdrawal == null) {
            interestRate = 0.05;
        } else if(DateManager.daysDifference(now, this.lastWithdrawal) >= 10){
            interestRate = 0.05;
        }else{
            interestRate = 0.001;
        }

        return this.getAccountBalance() * interestRate;
    }


    /*
    public double interestEarnedAnnum() {

        double balance = this.getAccountBalance();

        if(this.getAccountBalance() <= 1000.0) {
            return balance * this.initialInterestRate;
        } else if(balance <= 2000.0){
            return 20 + (balance-1000.0) * this.higherInterestRate;
        }else{
            return 70 + (balance-2000.0) * this.topInterestRate;
        }
    }

     */


}