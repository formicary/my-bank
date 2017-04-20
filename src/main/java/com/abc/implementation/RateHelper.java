package com.abc.implementation;

import com.abc.interfaces.IRateHelper;

public class RateHelper implements IRateHelper {

	public double getDailyRate(double yearlyRate) 
	{
		return yearlyRate/365.0;
	}
    
    public double getEarnedInterest(double dailyRate, int period)
    {
    	return Math.pow((1+dailyRate), period) -1;
    }

}
