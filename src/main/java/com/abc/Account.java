package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS;
    }
    
    private final AccountType accountType;
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount.negate()));
        }
    }

    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount.compareTo(BigDecimal.valueOf(1000)) <= 0)
                    return amount.multiply(BigDecimal.valueOf(0.001));
                else {
                    BigDecimal a = amount.subtract(BigDecimal.valueOf(1000));
                    BigDecimal b = a.multiply(BigDecimal.valueOf(0.002));
                    return BigDecimal.ONE.add(b);
                }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount.compareTo(BigDecimal.valueOf(1000)) <= 0)
                    return amount.multiply(BigDecimal.valueOf(0.002));
                else if (amount.compareTo(BigDecimal.valueOf(2000)) <= 0) {
                    BigDecimal a = amount.subtract(BigDecimal.valueOf(1000));
                    BigDecimal b = a.multiply(BigDecimal.valueOf(0.05));
                    return BigDecimal.valueOf(20).add(b);
                }
                else {
                    BigDecimal a = amount.subtract(BigDecimal.valueOf(2000));
                    BigDecimal b = a.multiply(BigDecimal.valueOf(0.1));
                    return BigDecimal.valueOf(70).add(b);
                }
            default:
                return amount.multiply(BigDecimal.valueOf(0.001));
        }
    }

    public BigDecimal sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private BigDecimal checkIfTransactionsExist(boolean checkAll) {
        BigDecimal amount = BigDecimal.ZERO;
        for (Transaction t: transactions)
            amount = amount.add(t.amount);
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
