package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import java.util.Date;


public class Account {
	
	private String accountID;

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
  
  
    public DateProvider dateProvider = DateProvider.getInstance();
    public Date lastWithdrawalDate;
    public Date lastDepositDate;
    
    private double balance = 0;
    
    private boolean deposit = false;
  
    private final int accountType;
    public List<Transaction> transactions;
    

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountID  = UUID.randomUUID().toString();
        
        this.balance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	transactions.add(new Transaction(amount,1));
        	lastDepositDate = dateProvider.now();
        	deposit = true;
        	balance += amount;
        }
    }
    
    

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	    	
	    	transactions.add(new Transaction(-amount,0));
	    	lastWithdrawalDate = dateProvider.now();
	    	deposit = false;
	    	balance -= amount;
	       
	    }
	}
	
	public void transfer(Account a, double amount) {
		if (this == a) {
			throw new IllegalArgumentException("cannot transfer money to the same account");
		} else {
			// exception checks for withdrawal and transfer are within the corresponding functions
			withdraw(amount);
			a.deposit(amount);
		}
	}


    public double interestEarned() {
        double amount = sumTransactions();
    	switch(accountType){
		 
        	case SAVINGS:
                if (amount <= 1000) {
                	if (!deposit)	{
            			return withdrawalI(0.001, amount);
            		} else {
            			return depositI(0.001, amount);
            		}
                } else {
				  if (!deposit) {
					  return (withdrawalI(0.001, 1000)) + (withdrawalI(0.002,amount-1000));
				   } else {
					  return (depositI(0.001, 1000)) + (depositI(0.002,amount-1000)); }		
                }

            case MAXI_SAVINGS:
            	
            	if (deposit)	 {
            		return (depositI(0.05, amount));
            	} else {
            		if ((daysPassed(dateProvider.now(), lastWithdrawalDate) > 10 )) {
            			return withdrawalI(0.05, amount);
            		} else {
            			return withdrawalI(0.001, amount);
            		}
            	}
            default:
			 if (!deposit){
				 return withdrawalI(0.001, amount); }
			 else { 
				 return depositI(0.001, amount); 
			 
			 }
        }
    }
    
	
	  public double withdrawalI(double interestRate, double amount) { 
		  System.out.print(daysPassed(dateProvider.now(),lastWithdrawalDate));
		  return (amount*Math.pow((interestRate), daysPassed(dateProvider.now(),lastWithdrawalDate)+1)); }
	  
	  public double depositI(double interestRate, double amount) { 
		  System.out.print(daysPassed(dateProvider.now(),lastDepositDate));
		  return (amount*Math.pow((interestRate), (daysPassed(dateProvider.now(),lastDepositDate)+1))); }

	    public boolean tenDaysWithdrawal(Date firstDay, Date secondDay)	{
	    	if (daysPassed(firstDay, secondDay) <= 10) {
	    		return true;
	    	} 
	    	return false;
	    }
	    public long daysPassed(Date firstDay, Date secondDay) {
	    	long difference = ((firstDay.getTime() - secondDay.getTime()) / 86400000);
	    	return Math.abs(difference);
	    }
	    
	    
    	
    
	
	  
	  public double sumTransactions() { 
		  double sum = 0.0;
	  
		  if (checkIfTransactionsExist(true)) {
			  for (Transaction t: transactions) { 
				  sum += t.amount; 
			  } 
		  }
		  return sum; 
	  }
	  
	  private boolean checkIfTransactionsExist(boolean checkAll) {
		  return (transactions.size() > 0);
	  }
	  
	  public String getStatementForAccount() {
		  String s = " ";
		  
		  switch(accountType) {
		  	case CHECKING:
		  		s += "Checking Account\n";
		  		break;
		  	case SAVINGS:
		  		s += "Savings Account\n";
		  		break;
		  	case MAXI_SAVINGS:
		  		s += "Maxi Savings Account\n";
		  		break;
		  }
		  
		  double total = 0.0;
		  
		  
		  for (Transaction t: transactions) {
			  
			  s += " " + t.toString() + "\n";
			  total += t.amount;
 		  }
		 
		 s += "\nTotal " + toDollars(total);
		  
		  return s;
		  
	  }
	  

    
    public int getAccountType() {
        return accountType;
    }
    
    public double getBalance()	{
    	return balance;
   }
    
	
	  public String getAccountID() { return accountID; }
	  
	  
	  public boolean equals(Account a) { return a.getAccountID() ==
	  this.getAccountID(); }
	  
	  private String toDollars(double d){
	        return String.format("$%,.2f", abs(d));
	    }
	 

}
