package main.java.com.abc.Transactions;

import java.util.Calendar;
import java.util.Date;

public class TransRecord {
	public String transName;
	public double amount;
	public Date date;
	public String state;
	
	/*
	 * A record that each transaction will Generate after finish
	 * Saving the transaction result
	 */
	public TransRecord(String transName, double amount, String state){
		this.transName = transName;
		this.amount = amount;
		this.state = state;
		this.date = Calendar.getInstance().getTime();
	}
	
	public String GetSummary(){

		String sum =  String.format("%s %s : %f", transName, this.state, amount);;
		return sum;
	}
}
