package com.abc.helpers;

import com.abc.classes.Account;

public class AccountInterests {
    //Pay deposit and update running accured interest for that account
    public static void payInterest(Account account, double interest){
        account.deposit(interest);
        account.updateAccuredInterest(interest);
    }

    //Checking accounts have a flat interest rate of 0.1%
    public static void calculateInterestChecking(Account account){
        double interest;
        double balance = account.getBalance();
        
        interest = balance * 0.001;
        payInterest(account, interest);

    }

    //Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
    public static void calculateInterestSavings(Account account){
        double interest;
        double balance = account.getBalance();
        
        if (balance <= 1000){
            interest = balance * 0.001;
            payInterest(account, interest);
        }   
        else if(balance > 1000){
            interest = (1000 * 0.001) + (balance - 1000) * 0.002;
            payInterest(account, interest);
        }

         
    }

    //Maxi-Savings accounts have a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%
    public static void calculateInterestMaxiSavings(Account account){
        double interest;
        double balance = account.getBalance();
        
        if (balance <= 1000){
            interest = balance * 0.02;
            payInterest(account, interest);
        }   
        else if(balance <= 2000){
            interest = (1000 * 0.02) + (balance - 1000) * 0.05;
            payInterest(account, interest);
        }
        else if(balance > 3000){
            interest = (1000 * 0.02) + (1000 * 0.05) + (balance - 2000) * 0.1;
            payInterest(account, interest);
        }
         
    }
}
