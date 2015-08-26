package com.abc.bank.admin.reports.interestaccumulator;

public class AccTreshold {
	double rate;
	double threshold;
	
	AccTreshold(double rate,double threshold){
		this.rate =rate;
		this.threshold =threshold;
	}

	public double getRate() {
		return rate;
	}

	public double getThreshold() {
		return threshold;
	}
}
