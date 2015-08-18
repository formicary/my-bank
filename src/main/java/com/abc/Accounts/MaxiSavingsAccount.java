package main.java.com.abc.Accounts;

import main.java.com.abc.Util;
import main.java.com.abc.Transactions.ITransaction;
import main.java.com.abc.Transactions.Transaction;

public class MaxiSavingsAccount extends AccountBase {

	private double dailyRate;
	private double annualRate;
	private int lastWithDrawalPeriod;

	public MaxiSavingsAccount(){
		this.annualRate = 0.001;
		this.dailyRate = Util.GetDailyFromAnnualRate(this.annualRate);
		this.lastWithDrawalPeriod = 0;
	}
	

	@Override
	public AccountType GetAccountType() {
		return AccountType.Savings;
	}
	
	@Override
    public void Withdraw(double amount) {
		super.Withdraw(amount);
		if( this.records.get(this.records.size() - 1).state == "SUCCESS" ){
			System.out.format("MAXI DETECT WITHDRAW CHANGE RATE LOWER \n");
			this.lastWithDrawalPeriod = 0;
			this.annualRate = 0.001;
			this.dailyRate = Util.GetDailyFromAnnualRate(this.annualRate);

		}
	}
	@Override
	public void GiveInterest() {
		this.lastWithDrawalPeriod += 1;

		
    	ITransaction transaction = new Transaction();
        transaction.Begin();
        double interest = 0;
		try{
	    	double balance = (double)transaction.Read(this);
	    	interest = balance * this.dailyRate;
	    	double newBalance = balance + interest;
	    	transaction.Write(this, newBalance);
	    	this.interestPaid += interest;
	        System.out.format("%s Interest SUCCESS, current Balance: %f \n", this.GetAccountType(), newBalance);

		}
		catch( Exception e){
			System.out.println(e.getMessage());
        	System.out.format("%s Interest FAILED (ABORTED), current Balance: %f \n", this.GetAccountType(),this.balance);
		}
		finally{
			transaction.End("Interest", interest);
		
			this.records.add(transaction.GetRecord());
		}	
		
		if ( this.lastWithDrawalPeriod >= 10 && this.annualRate != 0.05 ){
			System.out.format("MAXI GOOD HIGHER RATE \n");

			this.annualRate = 0.05;
			this.dailyRate = Util.GetDailyFromAnnualRate(this.annualRate);

		}
	}


	@Override
	public double GetAnnualRate() {
		// TODO Auto-generated method stub
		return this.annualRate;
	}

}
