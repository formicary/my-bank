package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account 
{
    
    //added enum for different account types
    public enum accountType
    {
        CHECKING, SAVINGS, MAXI_SAVINGS;
    }

    private final accountType accountType;
    public List<Transaction> transactions;

    public Account(accountType accountType) 
    {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) 
    {
        if (amount <= 0) 
        {
            throw new IllegalArgumentException("Please add a valid amount!");
        } 
        else 
        {
            transactions.add(new Transaction(amount, "Deposit"));
        }
    }

    public void withdraw(double amount) 
    {
        if (amount <= 0) 
        {
            throw new IllegalArgumentException("Please enter a valid amount!");
        }
        //if the withdraw amount is bigger than the amount in the account 
        else if (amount > sumTransactions())
        {
            throw new IllegalArgumentException("You cannot withdraw more than your balance!");       
        }
        else 
        {
            transactions.add(new Transaction(-amount, "Withdrawal"));
        }
    }
   
    public double interestEarned() 
    {
        double amount = sumTransactions();
        
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) 
                {
                    return amount * 0.001;
                } 
                else 
                {
                    return 1 + (amount - 1000) * 0.002;
                }
            case MAXI_SAVINGS:
//                if (amount <= 1000) 
//                {
//                    return amount * 0.02;
//                }
//                if (amount <= 2000) 
//                {
//                    return 20 + (amount - 1000) * 0.05;
//                }
                
                //have a rate of 5% assuming no withdrawals in the past 10 days
                int transactionSize = this.transactions.size() - 1;
                
                boolean isHighInterestRateAvailable = false;
                //look through each transaction and check if the latest is made in the past 10 days
                for (int i = transactionSize; i > 0; i--) 
                {
                                      
            		Date current = DateProvider.getInstance().now();
                	Date transactionDate = transactions.get(transactionSize).getDate();
                        
                	double transactionAmount = transactions.get(i).amount;
                	String transactionType = transactions.get(i).transactionType;
                        
                        if(transactionType.equals("Withdrawal"))
                        {
                            if(current.getTime() - transactionDate.getTime() < (10 * 24 * 60 * 60 * 1000) && transactionAmount < 0.0) 
                            {
                                    isHighInterestRateAvailable = true;
                                    break;
                            }
                            if(current.getTime() - transactionDate.getTime() > (10 * 24 * 60 * 60 * 1000)) 
                            {
                                    isHighInterestRateAvailable = false;
                            }
                        }
            	}
                if(isHighInterestRateAvailable == true)
                {
                    return amount * 0.05;
                }                                   
                else
                {
                    return amount * 0.001;
                }
            default:
                return amount * 0.001;
        }
    }
    
    //deleted checkIftransactionsExist() as its not necessary.
    public double sumTransactions() 
    {
        double amount = 0.0;

        for (Transaction transaction: transactions) 
        {
            amount += transaction.amount;
        }
        
        return amount;    
    
    }

    public accountType getAccountType() 
    {
        return accountType;
    }

}
