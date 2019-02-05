package main.java.com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
	
	//Interest rates
	public final double CHECKING_INTEREST_RATE = 0.001;
	public final double SAVINGS_INTEREST_RATE = 0.002;
	

    public enum accountType{
    	CHECKING, SAVINGS, MAXI_SAVINGS
    }
    
    private final accountType accountType;
    public List<Transaction> transactions;

    public Account(accountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    //Deposit given amount into an instance of Account
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposited amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(amount, "Deposit"));
        }
    }

    //Withdraw given amount from an instance of Account
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
	    } 
	    else if (amount > sumTransactions()){
	    	throw new IllegalArgumentException("Insufficient funds in account.");
	    }
	    else {
	        transactions.add(new Transaction(-amount, "Withdrawal"));
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * SAVINGS_INTEREST_RATE;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	boolean highMaxiInterestRate = false;
                //Check previous transactions
        		Date currentTime = DateProvider.getInstance().now();
                for (int transactionNo = transactions.size(); transactionNo>0; transactionNo--){
                	
                	//Check whether transaction was a withdrawal
                	if (transactions.get(transactionNo).transactionType.equals("Withdrawal")){
                		
                		//Check how long ago transaction was made
                		Date transactionTime = transactions.get(transactionNo).getTime();
                		//If more than 10 days since this transaction occurred
                		if (DateProvider.moreThanTenDaysSince(currentTime, transactionTime)){
                			highMaxiInterestRate = true;
                		}
                		else{
                			highMaxiInterestRate = false;
                			break;
                		}
                	}
                }
                //Apply correct interest rate for Maxi-Savings account
                if (highMaxiInterestRate){
                	return amount * 0.05;
                }
                else{
                	return amount * 0.001;
                }           
            //Case for checking accounts
            default:
                return amount * CHECKING_INTEREST_RATE;
        }
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

    public accountType getAccountType() {
        return accountType;
    }

}
