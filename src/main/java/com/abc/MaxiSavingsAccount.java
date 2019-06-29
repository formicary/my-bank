package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of Account specifically for a Maxi-Savings Account
 */
public class MaxiSavingsAccount extends Account {

    /*
    protected final double initialInterestRate = 0.02;
    protected final double higherInterestRate = 0.05;
    protected final double topInterestRate = 0.1;
     */

    public MaxiSavingsAccount(Customer owner){
        super(owner);
        this.accountType = 2;
        this.accountTypeString = "Maxi-Savings";
    }

    /**
     * Maxi-Savings accounts have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
     * @return interest earned on money in account
     */
    public double interestEarned(){

        double interestRate;

        Date now = Calendar.getInstance().getTime();

        if(this.lastWithdrawal == null) {

            interestRate = 0.05; // no withdrawals have ever taken place, so higher rate is used

        } else if(DateManager.daysDifference(now, this.lastWithdrawal) >= 10){

            interestRate = 0.05; // withdrawal(s) have been made, but not in the past 10 days

        }else{

            interestRate = 0.001; // withdrawal has been made in past 10 days, so lower interest rate is used

        }

        return this.getAccountBalance() * interestRate;
    }


    // the old maxi savings calculation technique

    /*
    public double interestEarned() {

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