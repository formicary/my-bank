package com.abc;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

// UPDATE:
// This class now hanldes the calculation of interest earned for accounts 
public class InterestProvider{

    public InterestProvider(){

    }

    public double interestEarnedSaving(double amount){
        if (amount <= 1000)
            return amount * 0.001;
        else
            return 1 + (amount-1000) * 0.002;
    }

    public double interestEarnedChecking(double amount){
        return amount * 0.001;
    }

    public double interestEarnedMaxiSaving(double amount){
        if (amount <= 1000)
            return amount * 0.02;
        if (amount <= 2000)
            return 20 + (amount-1000) * 0.05;
        return 70 + (amount-2000) * 0.1;
    }


}