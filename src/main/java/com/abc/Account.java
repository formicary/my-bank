package com.abc;

import java.util.*;

public class Account {

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
            if(amount <= sumTransactions()) { //Make sure the money requested for withdrawal is already in the account. (Earned interest is not included/impossible to withdraw interest - known bug with this implementation)
                Transaction t = new Transaction(-amount, Transaction.WITHDRAWAL);
                transactions.add(t);
                return t;
            } else {
                throw new IllegalStateException("amount must be less than account total");
            }
        }
    }

    public double interestEarned() {
        if(!transactions.isEmpty()) { //no interest can be earned without depositing first
            return calculateCompoundInterest();
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

    public String statementForAccount() { //this statement does not provide interest information
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
            s += "- "+ DateProvider.getInstance().stringify(t.transactionDate) + " " + //Display transaction date
                    (t.type == Transaction.WITHDRAWAL ? "Withdrawal" : "Deposit") + " " + //Display transaction type
                    Format.toDollars(t.amount) + "\n"; //Display transaction amount
            total += t.amount;
        }
        s += "Total " + Format.toDollars(total);
        System.out.println(s);
        return s;
    }

    private double calculateCompoundInterest() {
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
                    t2 = (i+1>=transactions.size()) ? (new Transaction(0, Transaction.DEPOSIT)) : transactions.get(i+1); //if loop is at last transaction, create a new transaction that will have todays date for later use
                    total+=t1.amount;
                    if(t1.type==Transaction.WITHDRAWAL) { //apply 10 day cooldown to interest rate after withdrawal
                        Date split = (dp.daysBetween(t1.transactionDate, dp.now())>10) ? dp.addDays(t1.transactionDate, 10) : t2.transactionDate; //if days between today and last transaction day is less than 10 days, don't split
                        interest=calculateAccruedInterest(0.001, total, t1.transactionDate, split);
                        interest+=calculateAccruedInterest(0.05, total, split, t2.transactionDate);
                    }
                    else { //calculate interest with 5% rate otherwise
                        interest=calculateAccruedInterest(0.05, total, t1.transactionDate, t2.transactionDate);
                    }
                    total+=interest; //compounded daily
                    totalInterest+=interest;
                }
                return totalInterest;
            case SAVINGS:
                for(int i = 0; i<transactions.size(); i++) {
                    t1 = transactions.get(i);
                    t2 = (i+1>=transactions.size()) ? (new Transaction(0, Transaction.DEPOSIT)) : transactions.get(i+1);
                    total+=t1.amount;
                    if(total<=1000){
                        interest = calculateAccruedInterest(0.001, total, t1.transactionDate, t2.transactionDate);
                    }
                    else { //if above 1000 calculate the first 1000 at 0.1% and the rest at 0.2%
                        interest = calculateAccruedInterest(0.001, 1000, t1.transactionDate, t2.transactionDate);
                        interest += calculateAccruedInterest(0.002, total-1000, t1.transactionDate, t2.transactionDate);
                    }

                    total+=interest; //compounded daily
                    totalInterest+=interest;
                }
                return totalInterest;
            default:
                for(int i = 0; i<transactions.size(); i++) {
                    t1 = transactions.get(i);
                    t2 = (i+1>=transactions.size()) ? (new Transaction(0, Transaction.DEPOSIT)) : transactions.get(i+1);
                    total+=t1.amount;
                    interest=calculateAccruedInterest(0.001, total, t1.transactionDate, t2.transactionDate);
                    total+=interest; //compounded daily
                    totalInterest+=interest;
                }
                return totalInterest;

        }
    }

    private double calculateAccruedInterest(double rate, double amount, Date d1, Date d2){ //calculate total accrued interest for a period between dates d1 and d2
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
