package com.abc;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private static LocalDate withdrawDate;
    private List<Transaction> transactions;
    
    /**
     * Constructor
     * @param accountType 
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    
    /**
     * Deposits a specific amount
     * @param amount 
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }
    
    /**
     * Withdraws a specific amount
     * @param amount 
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    
    }
     
    /**
     * Transfers a specific amount to a different account
     * 
     * @param amount
     * @param accountType 
     */
    public void transfer(double amount, int accountType){
        Account transferTo = new Account(accountType);
        
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if(this.accountType == accountType){
            throw new IllegalArgumentException("cannot transfer to the same account");
        } else {
            withdraw(amount);
            transferTo.deposit(amount);
        }

    } 
    
    /**
     * 
     * @return the interest earned
     */
    public double interestEarned() {
        double amount = sumTransactions();
        this.withdrawDate = transactions.get(0).getTransDate().toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate();
        LocalDate start = this.withdrawDate;
        LocalDate end = LocalDate.now();
        
        Period period = Period.between(start, end);
        
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
                if (period.getDays() >= 10)
                    return amount * 0.05;
                else
                    return amount * 0.01;
            default:
                return amount * 0.001;
        }
    }

    /**
     * 
     * @return sum of transactions
     */
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    /**
     * 
     * @param checkAll
     * @return amount of transaction
     */
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction transaction: transactions)
            amount += transaction.getAmount();
        return amount;
    }

    /**
     * 
     * @return account type
     */
    public int getAccountType() {
        return accountType;
    }
    
    /**
     * 
     * @return list of transactions
     */
    public List<Transaction> getTransactions(){
        return this.transactions;
    }

}
