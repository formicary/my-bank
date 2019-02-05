package com.abc;

import com.abc.Exceptions.NotEnoughFundsAvailableException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Account {

    // Removed static finals for account type, not very scalable.
    private final AccountType accountType;
    // Transactions now private with a getter
    private List<Transaction> transactions;


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


    public void withdraw(double amount) throws NotEnoughFundsAvailableException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } 
        else if(hasEnoughFunds(amount)){
            transactions.add(new Transaction(-amount));
        }
        else
            throw new NotEnoughFundsAvailableException();
    }


    // Wanted to remove all the calculations from this class. All now done in
    // AccountType.java
    public double interestEarned() {
        double amount = sumTransactions();
        return accountType.InterestEarned(amount,daysSinceLastWithdrawal());
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }
    /**
     * Creates a subset with only withdrawals, if empty, returns max integer
     * value, else, sorts by most recent and calculates days since that
     * transaction.
     * 
     * @return int number of days since last transaction
     */
    public int daysSinceLastWithdrawal(){
        
        List<Transaction> withdrawals = transactions.stream()
                                                    .filter( tran -> tran.getAmount() < 0)
                                                    .collect(Collectors.toList());
        if(withdrawals.isEmpty())
            return Integer.MAX_VALUE;
        
        withdrawals.sort(Comparator.comparing(Transaction::getTransactionDate).reversed());
        
        Transaction mostRecent = withdrawals.get(0);
        int daysSince = DateProvider.getInstance().differenceInDays(mostRecent.getTransactionDate());
        return daysSince;
    }

    // Simply checks if user has enough funds for a withdrawal
    public boolean hasEnoughFunds(double amount){
        return (sumTransactions() > amount);
    }

    public AccountType getAccountType() {
        return accountType;
    }
    
 
    public List<Transaction> getTransactions(){
        return transactions;
    }

}
