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
    private BigDecimal sum;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        sum = BigDecimal.ZERO;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            sum = sum.add(amount);
        }
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount.negate()));
            sum = sum.add(amount.negate());
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
       return sum;
    }

    private Boolean checkIfTransactionsExist() {
        return !transactions.isEmpty();
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
