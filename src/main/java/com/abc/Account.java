package mybank;

import java.util.*;


	public class Account {		
	    AccountType accountType;
	    List<Transaction> transactions;
	    DateProvider dateProvider = new DateProvider();
	    
	    public Account(AccountType accountType) {
	        this.accountType = accountType;
	        this.transactions = new ArrayList<Transaction>();
	    }
	    


	public void deposit(double amount) {
	        if (amount <= 0) {
	            throw new IllegalArgumentException("amount must be greater than zero");
	        } else {
	            transactions.add(new Transaction(amount));
	        }
	 	}

	//additional Exception Argument added  
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else if(amount> sumTransactions()){
	    	throw new IllegalArgumentException("Insufficient remaining");
	    } else {	
	        transactions.add(new Transaction(-amount));
	  
	    }
	}
	
    public AccountType getAccountType() {
        return accountType;
    }
    

	// new Checking/Saving/Max_Saving accounts interests calculation method added.
    // rewarding interest in daily basis, upon current inquiring data
	public double getcheckingInterest(){
		
		Date LastTransactionDay = transactions.get(transactions.size()-1).transactionDate();
		Date currentDate = dateProvider.now();
		// Days between 'current inquiring day' and 'most recent transaction day' 
		int betweenCurrentToLast = Days.daysBetween(LastTransactionDay, currentDate).getDays();
		int numberOfTransaction = transactions.size();
		
		double totalCheckingInterest = 0.0;
		double tempInterest = 0;
		double tempbalance;
		
		for(Transaction t : transactions){
			int i = 0;
			
			tempbalance += t.amount;

			//check at least two Transactions exist in account. 
			//Adding up interest from first transaction date to last transaction date;
			if(numberOfTransaction>1 && i<(numberOfTransaction-1)){

			//get days period between each(two) of transactions
				int daysBetween = Days.daysBetween(transactions.get(i).transactionDate(), transactions.get(++i).transactionDate()).getDays();
			//adding up interests base one account balance(after each transaction) for the time being.! Notice 'tempInterest' will not include(or add up) any interests
			//that begin from 'most recent transaction day' to 'current inquiring day'.
				tempInterest += tempbalance*(daysBetween/365)*0.001;
				i++;
			
			}else if(numberOfTransaction>=1 && (numberOfTransaction-1) == i ){
			//add interest that begin from 'most recent transaction day' to 'current inquiring day'
			return totalCheckingInterest = tempInterest + tempbalance*(betweenCurrentToLast/365)*0.001;	
			
			}else{
				
			throw new IllegalArgumentException("No transactions exist");
			}
		
		}
			
	}
	
	
	
	public double getSavingInterest(){
				
		Date LastTransactionDay = transactions.get(transactions.size()-1).transactionDate();
		Date currentDate = dateProvider.now();
		
		int betweenCurrentToLast = Days.daysBetween(LastTransactionDay, currentDate).getDays();
		int numberOfTransaction = transactions.size();
		
		double totalCheckingInterest = 0.0;
		double tempInterest;
		double tempbalance;
		double interestRate;
		
		for(Transaction t : transactions){
			int i = 0;
			tempbalance += t.amount;			
			//deciding interestRate
			interestRate =(tempbalance<1000 ? 0.001 : 0.002);
								
			//check if there is at least two Transactions exist in account. 
			//Adding up interest from first transaction date to last transaction date with variable interest rate values ;
			if (numberOfTransaction>1 && i<(numberOfTransaction-1)){
				    int daysBetween = Days.daysBetween(transactions.get(i).transactionDate(), transactions.get(++i).transactionDate()).getDays();
					tempInterest += tempbalance*(daysBetween/365)*interestRate;
					i++;
					
			//check if there at least one transactions.
			}else if(numberOfTransaction>= 1 && (numberOfTransaction-1) == i){
				return totalCheckingInterest = tempInterest  + tempbalance*(betweenCurrentToLast/365)*interestRate;
			}else{
				throw new IllegalArgumentException("No transactions exist");
			}
		}
				
	}
	
	
	//Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
	public double getMaxiSavingInterest(){
		Date LastTransactionDay = transactions.get(transactions.size()-1).transactionDate();
		Date currentDate = dateProvider.now();
		int betweenCurrentToLast = Days.daysBetween(LastTransactionDay, currentDate).getDays();
		int numberOfTransaction = transactions.size();
		
		double totalCheckingInterest = 0.0;
		double tempInterest;
		double tempbalance;
		double interestRate;
		
		for(Transaction t : transactions){
			int i = 0;
			int daysBetween;
			tempbalance += t.amount;	
			//check if there is at least two Transactions exist in account. 
			//Adding up interest from first transaction date to last transaction date with variable interest rate values ;
			if (numberOfTransaction>1 && i<(numberOfTransaction-1)){
				daysBetween = Days.daysBetween(transactions.get(i).transactionDate(), transactions.get(++i).transactionDate()).getDays();
				//checking if there is any withdraws in last 10 days, assigning different interest rate value.
				if(daysBetween>=11 && ((tempbalance + t.amount) > tempbalance)){
					interestRate = 0.05;
				}	
				tempInterest += tempbalance*(daysBetween/365)*interestRate;
				i++;
			}else if(numberOfTransaction>= 1 && (numberOfTransaction-1) == i){
				//checking if 'most recent transaction day' to 'current inquiring day' endures more than 10 days
				//if yes assigning different interest rate
				if(betweenCurrentToLast>=11){
					interestRate = 0.05;
				}
				return totalCheckingInterest = tempInterest  + tempbalance*(betweenCurrentToLast/365)*interestRate;
			}else{
				throw new IllegalArgumentException("No transactions exist");
			}
		}
		
	}
	
	    public double interestEarned() {
	        double amount = sumTransactions();
	        switch(accountType){
	            case CHECKING:                                                                                                                        
	                    return getcheckingInterest() ;	              
	            case SAVINGS:
	            		return getSavingInterest();	            		     
	            case MAXI_SAVINGS:              
	                return getMaxiSavingInterest();
	            default:
	                return amount * 0.001;
	        }
	    }
	    
	    /*Delete this checkIfTransactionsExist() method, replace its iteration under sumTransaction() method.
	     * improve efficiency of code
	    private double checkIfTransactionsExist(boolean checkAll) {
	        double amount = 0.0;
	        for (Transaction t: transactions)
	            amount += t.amount;
	        return amount;
	    }	 */
	    
	    public double sumTransactions() {
	        double amount = 0.0;
	        for (Transaction t: transactions){
	            amount += t.amount;
	        }
	        return amount;
	    }
	    

	}
