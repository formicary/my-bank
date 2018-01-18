package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;

public class Account {
    
    public enum AccountType {
        CHECKING, SAVINGS, MAXI_SAVINGS
    }
    
    private double cashAtLastCheck;
    private LocalDate lastCheckDate;
    private LocalDate lastWithdrawalDate;
    private double secularCashInAccount;
            
    private final AccountType accountType;
    private final int accountID;
    private List<Transaction> transactions;
    
    private static int nextAccountID=0;
    
    public Account(AccountType accountType) {
        this.accountType = accountType;
        transactions = new ArrayList<Transaction>();
        accountID=nextAccountID;
        nextAccountID++;
        lastWithdrawalDate= LocalDate.now();
        lastCheckDate=LocalDate.now();
    } 

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Transaction transaction = new Transaction(amount);
            updateBalance(transaction.transactionDate);
            transactions.add(transaction);
            secularCashInAccount+=amount;
            cashAtLastCheck+=amount;
        }
    }

    //returns false if not enough money in account to withdraw
    public Boolean withdraw(double amount) {
        if (amount <= 0) {
           throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Transaction transaction = new Transaction(-amount);
            
            updateBalance(transaction.transactionDate);
            if(amount>cashAtLastCheck) return false;
            transactions.add(transaction);
            secularCashInAccount-=amount;
            cashAtLastCheck-=amount;
            return true;
        }
    }
    
    private double calculateDailyInterestRate(LocalDate date){
        switch(accountType){
            case CHECKING:
                return Math.pow(1.01,1/365);
            case SAVINGS:
                if(cashAtLastCheck<1000) return Math.pow(1.01,1/365);
                else {
                    double averageInterestRate=(1.01*1000+1.02*(cashAtLastCheck-1000))/cashAtLastCheck;
                    return Math.pow(averageInterestRate, 1/365);
                }
            case MAXI_SAVINGS:
                if(DAYS.between(lastWithdrawalDate,date)<10) return Math.pow(1.01,1/365);
                else return Math.pow(1.05,1/365);
            default:
                throw new UnsupportedOperationException("Account Type not recognised");
        }   
    }
    
    private double calculateInterestSinceLastCheck(LocalDate date) {
        return cashAtLastCheck*Math.pow(calculateDailyInterestRate(date),DAYS.between(lastCheckDate,date))-cashAtLastCheck;
    }
    
    private void updateBalance(LocalDate date){
        cashAtLastCheck+=calculateInterestSinceLastCheck(date);
        lastCheckDate=date;
    }
    
    public double totalInterestAccrued(){
        updateBalance(LocalDate.now());
        return cashAtLastCheck-secularCashInAccount;
    }
    
    public double getBalance(){
        updateBalance(LocalDate.now());
        return cashAtLastCheck;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    
    public String getStatement() {
        String s = "";

        switch(accountType){
            case CHECKING:
                s = "Checking Account:\n";
                break;
            case SAVINGS:
                s = "Savings Account:\n";
                break;
            case MAXI_SAVINGS:
                s = "Maxi Savings Account:\n";
                break;
        }

        //Now total up all the transactions
        for (Transaction t : transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal:" : "deposit:") + " " + HelperMethods.toDollars(t.amount) + "\n";
        }
        s += "SECULAR TOTAL: " + HelperMethods.toDollars(secularCashInAccount);
        s += "\nINTEREST ACCRUED: "+ HelperMethods.toDollars(totalInterestAccrued());
        s += "\nTOTAL BALANCE: "+ HelperMethods.toDollars(getBalance());
        return s;
    }
    
    public int getAccountID()
    {
        return accountID;
    }

}
