package main.java.com.abc.Accounts;

import main.java.com.abc.Transactions.ITransaction;
import main.java.com.abc.Transactions.Transaction;

public class CheckingAccount extends AccountBase {

	private double dailyRate;
	private double annualRate;

	public CheckingAccount(){
		this.annualRate = 0.001;
		this.dailyRate = Math.pow((1 + this.annualRate/365),365) - 1;
		System.out.format("CHECKING DAILY RATE: %f \n",this.dailyRate);
	}
	@Override
	public AccountType GetAccountType() {
		return AccountType.Checking;
	}

	@Override
	public void GiveInterest() {
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

	}
	@Override
	public double GetAnnualRate() {
		// TODO Auto-generated method stub
		return this.annualRate;
	}

}
