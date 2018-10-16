package com.abc;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;


public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(BigDecimal amount) {
        if(amount.compareTo(sumTransactions())<0){
            if(amount.compareTo(BigDecimal.ZERO) <= 0){
                throw new IllegalArgumentException("amount must be greater than zero");
            }
            else{
                transactions.add(new Transaction(amount.negate()));
            }
        }
        else{
            System.out.println("Not enough funds.");
        }
    }

    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        amount.setScale(2, RoundingMode.HALF_UP);
        DateProvider date = new DateProvider();
        switch(accountType){
            case SAVINGS:
                if (amount.compareTo(BigDecimal.valueOf(1000)) <= 0)
                    return amount.multiply(BigDecimal.valueOf(0.001));
                else
                    return BigDecimal.valueOf(1).add((amount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.002)));
            case MAXI_SAVINGS:
                if (getLastWithdrawal()!=null){
                    if (!date.tenDayCheck(getLastWithdrawal().getDate()))
                        return amount.multiply(BigDecimal.valueOf(0.001));
                    else
                        return amount.multiply(BigDecimal.valueOf(0.05));
                }
                else{
                    return amount.multiply(BigDecimal.valueOf(0.05));
                }
            default:
                return amount.multiply(BigDecimal.valueOf(0.001));

        }
    }

    public Transaction getLastWithdrawal(){
        for (Transaction t : transactions){
            if (t.type == t.WITHDRAWAL) {
                return t;
            }
        }
        return null;
    }

    public BigDecimal sumTransactions() {
       return getTransactionsSum();
    }

    public int getNumberOfTransactions() {
        return transactions.size();
    }

    private BigDecimal getTransactionsSum() {
        BigDecimal amount = BigDecimal.valueOf(0.00);
        for (Transaction t: transactions)
            amount = t.amount.add(amount);
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
