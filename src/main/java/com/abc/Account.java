package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public static final int DAYS_IN_YEAR=365;
    public static final int MILLISECONDS_IN_YEAR=86400000;

    private final int accountType;
    public List<Transaction> transactions;

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
        } else if (amount<sumTransactions()){
            transactions.add(new Transaction(-amount));
        }
    }

    public void transfer(double amount, Account a) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (amount<sumTransactions()){
            transactions.add(new Transaction(-amount));
            a.transactions.add(new Transaction((amount)));
        }
    }

    public double interestEarned() {
        double startingAmount = sumTransactions();
        double currentAmount=startingAmount;
        double interest=0.0;
        Calendar currentDate = Calendar.getInstance();
        Calendar transactionDate = this.getLastTransaction().getDate();
        for (int i = 0; i < getDaysBetweenDates(transactionDate,currentDate);i++) {
            switch (accountType) {
                case CHECKING:
                    interest=currentAmount*(0.001 / DAYS_IN_YEAR);
                    break;
                case SAVINGS:
                    if (currentAmount <= 1000)
                        interest = currentAmount * (0.001 / DAYS_IN_YEAR);
                    else
                        interest = (1000 * (0.001 / DAYS_IN_YEAR)) + ((currentAmount - 1000) * (0.002 / DAYS_IN_YEAR));
                    break;
                case MAXI_SAVINGS:
                    Calendar dateTenDaysAgo = Calendar.getInstance();
                    dateTenDaysAgo.add(Calendar.DATE, -10); //10 days ago
                    if (this.getLastTransaction().getDate().getTime().before(dateTenDaysAgo.getTime())) //If the last transaction was more than 10 days ago
                        interest=currentAmount*(0.05 / DAYS_IN_YEAR);
                    else
                        interest=currentAmount*(0.001 / DAYS_IN_YEAR);
                    break;
            }
            currentAmount+=interest;
        }

        return currentAmount-startingAmount;
    }

    public long getDaysBetweenDates(Calendar date1, Calendar date2){
        long end = date1.getTimeInMillis() + date1.getTimeZone().getOffset(date1.getTimeInMillis()); ;
        long start = date2.getTimeInMillis()+ date2.getTimeZone().getOffset(date2.getTimeInMillis()); ;
        return(Math.abs((end - start) / MILLISECONDS_IN_YEAR));
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

    public Transaction getLastTransaction(){
        return transactions.get(transactions.size()-1);
    }

}
