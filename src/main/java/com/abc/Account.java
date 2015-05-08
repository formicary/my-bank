package main.java.com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    public Transaction t;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
      
    }

    public void transfer(Account to, double amount) {
    	withdraw(amount);
    	to.deposit(amount);
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
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
			    //Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
                if (isWidthDrawInPastDays(10)){
                    return amount * 0.05;
                }else
                    return amount * 0.001;
            default:
                return amount * 0.001;
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

    public int getAccountType() {
        return accountType;
    }

	public boolean isWidthDrawInPastDays(int pastDays) {
		Boolean flag = false;
       for (Transaction t: transactions){
    	   	Calendar  mark = Calendar.getInstance();
    	   	mark.add(Calendar.DATE, -pastDays);
    	   	Date pastDate = mark.getTime();
            if (t.amount < 0 && t.transactionDate.before(pastDate) )
                    flag = true;
       }
       return flag;
    }
	
}
