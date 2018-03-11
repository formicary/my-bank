package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account {

    //Account types as enumeration
    public enum AccountType {
        CHECKING("CHECKING") , SAVINGS("SAVINGS"), MAXI_SAVINGS("MAXI_SAVINGS");

        private String value;
        private AccountType(String value){
            this.value = value;
        }

        public String getAccountType(){
            return value;
        }
    }

    private final AccountType accountType;
    public List<Transaction> transactions;

    public Date openingDate;

    public Account(AccountType accountType) {
        this.openingDate = DateProvider.getInstance().now();
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount,"deposit"));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount,"withdraw"));
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
                if (withdrawLastTenDays())
                    return amount *0.001;
                else
                    return amount*0.05;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    //Return true if transaction was made in last 10 days, else returns true
    public boolean withdrawLastTenDays(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10);
        for (Transaction t: transactions)
            if (t.amount < 0 && t.getTransactionDate().after(cal.getTime())){
                return true;
            }
        return false;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}