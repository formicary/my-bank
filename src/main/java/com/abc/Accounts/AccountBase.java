package main.java.com.abc.Accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import main.java.com.abc.Transactions.ITransaction;
import main.java.com.abc.Transactions.TransRecord;
import main.java.com.abc.Transactions.Transaction;



/*
 * The base class of An account
 * Have the basic functionality of all kinds of Accounts
 */
public abstract class AccountBase{

	protected double balance;
	protected double interestPaid;
    private Timer interestTimer;
    public final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    public final Lock readLock = rwLock.readLock();
    public final Lock writeLock = rwLock.writeLock();
    
    protected List<TransRecord> records;


    public AccountBase() {
    	records = new ArrayList<TransRecord>();
    	
    	this.interestTimer = new Timer();
    	//int millSecADay = 1000 * 60 * 60 * 24;
    	//For easier of testing. Rather not wait a day
    	int millSecADay = 1000;

    	this.interestTimer.scheduleAtFixedRate(new GiveInterestTask(this), millSecADay, millSecADay);
    }
    public abstract AccountType GetAccountType();
    public abstract void GiveInterest();
	public abstract double GetAnnualRate();

    public List<TransRecord> GetRecords(){
    	return records;
    }

    public void Deposit(double amount) {
    	ITransaction transaction = new Transaction();
        transaction.Begin();
		try{
	    	double balance = (double)transaction.Read(this);
	    	double newBalance = balance + amount;
	    	transaction.Write(this, newBalance);
	        System.out.format("%s Deposit %f SUCCESS, current Balance: %f \n", this.GetAccountType(), amount, this.balance);

		}
		catch( Exception e){
			System.out.println(e.getMessage());
        	System.out.format("%s Deposit %f FAILED (ABORTED), current Balance: %f \n``", this.GetAccountType(), amount, this.balance);
		}
		finally{
			transaction.End("DEPOSITE", amount);
			records.add(transaction.GetRecord());
		}

    }

	
    public void Withdraw(double amount) {
    	ITransaction transaction = new Transaction();
        transaction.Begin();
		try{
	    	double balance = (double)transaction.Read(this);
	    	double newBalance = balance - amount;
	    	transaction.Write(this, newBalance);
	        System.out.format("%s WITHDRAW %f SUCCESS, current Balance: %f \n", this.GetAccountType(), amount, this.balance);

		}
		catch( Exception e){
			System.out.println(e.getMessage());
        	System.out.format("%s WITHDRAW %f FAILED (ABORTED), current Balance: %f \n", this.GetAccountType(), amount, this.balance);
		}
		finally{
			transaction.End("WITHDRAW", amount);
			records.add(transaction.GetRecord());

		}
	}
    
    public void Transfer(AccountBase toAcc, double amount){
    	ITransaction transaction = new Transaction();
        transaction.Begin();
		try{
	    	//Depo to toAcc first
			double toBalance = (double)transaction.Read(toAcc);
	    	double toNewBalance = toBalance + amount;
	    	transaction.Write(toAcc, toNewBalance);
	    	
	    	//With from this Acc Sec
			double balance = (double)transaction.Read(this);
	    	double NewBalance = balance - amount;
	    	transaction.Write(this, NewBalance);
	        System.out.format("%s TRANSFER %f SUCCESS, current Balance: %f \n", this.GetAccountType(), amount, this.balance);

	    	
		}
		catch( Exception e){
			System.out.println(e.getMessage());
        	System.out.format("%s TRANSFER %f FAILED, current Balance: %f \n", this.GetAccountType(), amount, this.balance);
		}
		finally{
			transaction.End("TRANSFER", amount);
			records.add(transaction.GetRecord());

		}

    }
    

	public void SetBalance(double newBalance) throws Exception{
	    if (newBalance < 0) {
	        throw new Exception("amount must be greater than or equal to zero");
	    } else {
	        this.balance = newBalance;
	    }
	}
	
	public double GetInterestEarned(){
		return this.interestPaid;
	}
	
	public double GetBalance() {
		return this.balance;
	}
	
	public double GetBalanceSafe(){
    	ITransaction transaction = new Transaction();
        transaction.Begin();
		try{
	    	//Depo to toAcc first
			double balance = (double)transaction.Read(this);
	    	
	        System.out.format("%s READ SUCCESS, current Balance: %f \n", this.GetAccountType(), this.balance);

	    	return balance;
		}
		catch( Exception e){
			System.out.println(e.getMessage());
        	System.out.format("%s READ FAILED, current Balance: %f \n", this.GetAccountType(), this.balance);
        	return -1;
		}
		finally{
			transaction.End("READ", this.balance);

		}
		
	
	}
	
	public void StopInterest(){
		this.interestTimer.cancel();

	}
	public void Close() {
		this.interestTimer.cancel();
	}
	
}
