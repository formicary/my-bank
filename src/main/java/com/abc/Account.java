package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {
    
    protected List<Transaction> transactions;

    protected Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) throws IllegalArgumentException
    {
        deposit(amount, DateProvider.now());
    }
    public void withdraw(double amount) throws IllegalArgumentException
    {
        withdraw(amount, DateProvider.now());
	}
    
    public void deposit(double amount, Date date) throws IllegalArgumentException
    {
    	if (amount<0)
    	{
    		throw new IllegalArgumentException("Cannot deposit negative amount");
    	}
        addTransactionWithDate(amount, date);
    }

    public void withdraw(double amount, Date date) throws IllegalArgumentException
    {
    	if (amount<0)
    	{
    		throw new IllegalArgumentException("Cannot withdraw negative amount");
    	}
    	if (sumTransactions() - amount <0)
    	{
    		throw new IllegalArgumentException("Insufficient funds");
    	}
        addTransactionWithDate(-amount, date);
	}
    
    public double sumTransactions() 
    {
    	double amount = 0.0;
    	for (Transaction t: transactions)
    	{
    		amount += t.amount;
    	}
    	return amount;
    }
    public String getStatement()
    {
        String s = "";
        
        // valid since toString is must be override
        s += toString();
        double total = 0.0;
        for (Transaction t : transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }
    
    
    public static double getInterestPerDay(double currentSum, int days, double anumRate)
    {
    	System.out.println(currentSum * Math.pow( 1 + anumRate/ DateProvider.MEAN_YEAR, days) - currentSum);
    	return currentSum * Math.pow( 1 + anumRate/ DateProvider.MEAN_YEAR, days) - currentSum;
    }
    
    private void addTransactionWithDate(double amount, Date date) throws IllegalArgumentException
    {
    	if (amount == 0.0)
    	{
    		throw new IllegalArgumentException("cannot make an empty transaction");
    	}
    	if (transactions.size() == 0)
    	{
    		transactions.add(new Transaction(amount, date));
    		return;
    	}
    	Date lastTransactionDate = transactions.get(transactions.size()-1).getTransactionDate();
    	if (DateProvider.getDifferenceDays(lastTransactionDate, date) < 0.0)
    	{
    		throw new IllegalArgumentException("The date provided must be after the last transaction date");
    	}
    	transactions.add(new Transaction(amount, date));
    }
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public abstract double dailyInterestEarned();
    public abstract double interestEarned();
    public abstract String toString();
}
