package com.abc.helpers;

public class AccountInterests {

    //Checking accounts have a flat interest rate of 0.1%
    public static double calculateInterestChecking(double balance){
        double interest;
        
        interest = balance * 0.001;
        return interest;

    }

    //Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
    public static double calculateInterestSavings(double balance){
        double interest;
        
        if (balance <= 1000){
            interest = balance * 0.001;
            return interest;
        }   
        else if(balance > 1000){
            interest = (1000 * 0.001) + (balance - 1000) * 0.002;
            return interest;
        }

        //If interest cannot be calculated, return no interest
        return 0.0;
         
    }

    //Maxi-Savings accounts have a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%
    public static double calculateInterestMaxiSavings(double balance){
        double interest;
        
        if (balance <= 1000){
            interest = balance * 0.02;
            return interest;
        }   
        else if(balance <= 2000){
            interest = (1000 * 0.02) + (balance - 1000) * 0.05;
            return interest;
        }
        else if(balance > 3000){
            interest = (1000 * 0.02) + (1000 * 0.05) + (balance - 2000) * 0.1;
            return interest;
        }

        //If interest cannot be calculated, return no interest
        return 0.0;
         
    }
}
