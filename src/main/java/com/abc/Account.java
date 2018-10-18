package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private Date today;

    public Account(int accountType) {
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

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
    }
}

    public double interestEarned() {
    	today = DateProvider.getInstance().now();
        double amount = sumTransactions();
        
        // The interest is earned daily with these compound interest multiples
        int days = diffDays(firstDeposit(), today);
        double firstCat=0, secCat=0, thirdCat=0, fourthCat=0, fifthCat=0;
        if(days>0) {
        firstCat = Math.pow((1+(0.001/365)), days);
        secCat = Math.pow((1+(0.002/365)), days);
        thirdCat = Math.pow((1+(0.02/365)), days);
        fourthCat = Math.pow((1+(0.05/365)), days);
        fifthCat = Math.pow((1+(0.1/365)), days);
        }
        
        
	
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * firstCat;
                else
                    return 1000*firstCat + (amount-1000) * secCat;
            case MAXI_SAVINGS:            	
                if (amount <= 1000)
                    return amount * thirdCat;
                
                if (amount <= 2000 && diffDays(lastWithdrawal(), today) > 10) // no withdrawal in the last 10 days
                    return 1000*thirdCat + (amount-1000) * fourthCat;
                if(amount <= 2000 && diffDays(lastWithdrawal(), today)  <= 10)  // withdrawal done in the last 10 days
                	return 1000*thirdCat + (amount-1000) * firstCat;
                
                // if it has more than 2000 
                if(diffDays(lastWithdrawal(), today)  > 10)
                	return 1000*thirdCat + 1000*fourthCat + (amount-2000) * fifthCat;
                if(diffDays(lastWithdrawal(), today)  <= 10)
                	return 1000*thirdCat + 1000*firstCat + (amount-2000) * fifthCat;
            default:
                return amount * firstCat;
        }
    }
    
    
    private int diffDays(Date from, Date to) {
    	int diffDays;
    	long temp = to.getTime() - from.getTime();   // Calculating difference of days
    	diffDays = (int) ((temp) / (1000 * 60 * 60 * 24));  // Converting from millsec to days
    	return diffDays;
    }
    
    private Date firstDeposit() {
    	int i=0;
    	while(i<transactions.size() && transactions.get(i).amount<0) {i++;};
    	return transactions.get(i).transactionDate;
    }
    // Looking for the last withdrawal that has been done on this account
    private Date lastWithdrawal() {
    	Date lastDay = null;
    	for(Transaction t : transactions) {
    		if(t.amount < 0) {
    			lastDay = t.transactionDate;
    		}
    	}
    	
    	// No withdrawals has been done yet and since we dont know when he opened his account 
		// I suppose that he won't get the 5% interest
    	if (lastDay == null) 
    		lastDay = DateProvider.getInstance().now();
    	return lastDay;
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
