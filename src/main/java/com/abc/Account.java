package com.abc;

import java.util.*;

public class Account {

    //could use enum but in my opinion it's not worth it here
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public Transaction deposit(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Transaction t = new Transaction(amount, Transaction.DEPOSIT);
            transactions.add(t);
            return t;
        }
    }

    public Transaction withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if(amount <= sumTransactions()) {
                Transaction t = new Transaction(-amount, Transaction.WITHDRAWAL);
                transactions.add(t);
                return t;
            } else {
                throw new IllegalStateException("amount must be less than account total");
            }
        }
    }

    public double interestEarned() {
        if(!transactions.isEmpty()) {
            return calculateAccruedInterest();
        }
        else {
            return 0.0;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    public String statementForAccount() {
        String s = "";

        //Translate to pretty account type
        switch(this.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : this.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + Format.toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + Format.toDollars(total);
        return s;
    }

    private double calculateAccruedInterest() {
        double total=0;
        double interest;
        double totalInterest=0;
        Transaction t1;
        Transaction t2;
        DateProvider dp = DateProvider.getInstance();
        switch (accountType) {
            case MAXI_SAVINGS:
                for(int i=0; i<transactions.size(); i++) {
                    t1=transactions.get(i);
                    t2 = (i+1>=transactions.size()) ? (new Transaction(0, Transaction.DEPOSIT)) : transactions.get(i+1);
                    total+=t1.amount;
                    if(t1.type==Transaction.WITHDRAWAL) {
                        Date split = dp.addDays(t1.transactionDate, 10);
                        interest=calculateCompoundInterest(0.001, total, t1.transactionDate, split);
                        interest+=calculateCompoundInterest(0.05, total, split, t2.transactionDate);
                    }
                    else {
                        interest=calculateCompoundInterest(0.05, total, t1.transactionDate, t2.transactionDate);
                    }
                    total+=interest;
                    totalInterest+=interest;
                }
                return totalInterest;
            case SAVINGS:
                double rate;
                for(int i = 0; i<transactions.size(); i++) {
                    t1 = transactions.get(i);
                    t2 = (i+1>=transactions.size()) ? (new Transaction(0, Transaction.DEPOSIT)) : transactions.get(i+1);
                    total+=t1.amount;
                    if(total<=1000){
                        interest = calculateCompoundInterest(0.001, total, t1.transactionDate, t2.transactionDate);
                    }
                    else {
                        interest = calculateCompoundInterest(0.001, 1000, t1.transactionDate, t2.transactionDate);
                        interest += calculateCompoundInterest(0.002, total-1000, t1.transactionDate, t2.transactionDate);
                    }

                    total+=interest;
                    totalInterest+=interest;
                }
                return totalInterest;
            default:
                for(int i = 0; i<transactions.size(); i++) {
                    t1 = transactions.get(i);
                    t2 = (i+1>=transactions.size()) ? (new Transaction(0, Transaction.DEPOSIT)) : transactions.get(i+1);
                    total+=t1.amount;
                    interest=calculateCompoundInterest(0.001, total, t1.transactionDate, t2.transactionDate);
                    total+=interest;
                    totalInterest+=interest;
                }
                return totalInterest;

        }
    }

    private double calculateCompoundInterest(double rate, double amount, Date d1, Date d2){
        if(transactions.size() > 0) {
            int nOfDays = DateProvider.getInstance().daysBetween(d1, d2);
            double interest = Math.pow(1 + (rate/365), nOfDays);
            return (interest*amount) - amount;
        }
        else {
            return 0.0;
        }
    }

}
