package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS;
    }
    
    private final AccountType accountType;
    private final List<Transaction> transactions;
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
    
    public String statementForAccount() {
        
        StringBuilder s = new StringBuilder();

       //Translate to pretty account type
       
        switch(getAccountType()){
            case CHECKING:
                s.append("Checking Account\n");
                break;
            case SAVINGS:
                s.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : transactions) {
            s.append("  ");
            s.append((t.amount.compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit"));
            s.append(" ");
            s.append(toDollars(t.amount));
            s.append("\n");
            total = total.add(t.amount);
        }
        s.append("Total ");
        s.append(toDollars(total));
        return s.toString();
    }
    
    public static String toDollars(BigDecimal d) {       
        return String.format("$%,.2f", d.abs().setScale(2, RoundingMode.DOWN)); 
    }

    public BigDecimal sumTransactions() {
       return sum;
    }

    public Boolean checkIfTransactionsExist() {
        return !transactions.isEmpty();
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
