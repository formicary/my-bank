package com.abc.interfaces;

public interface IRateHelper {
	public double getDailyRate(double yearlyRate);
	public double getEarnedInterest(double dailyRate, int period);
}
