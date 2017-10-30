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
    
    private static final BigDecimal CHECKING_RATE = new BigDecimal("0.001");
    
    private static final BigDecimal SAVINGS_RATE_ONE = new BigDecimal("0.001");
    private static final BigDecimal SAVINGS_RATE_LIMIT_ONE = new BigDecimal("1000.00");
    private static final BigDecimal SAVINGS_RATE_TWO = new BigDecimal("0.002");
    
    private static final BigDecimal MAXI_SAVINGS_RATE_ONE = new BigDecimal("0.02");
    private static final BigDecimal MAXI_SAVINGS_RATE_LIMIT_ONE = new BigDecimal("1000.00");
    private static final BigDecimal MAXI_SAVINGS_RATE_TWO = new BigDecimal("0.05");
    private static final BigDecimal MAXI_SAVINGS_RATE_LIMIT_TWO = new BigDecimal("2000.00");
    private static final BigDecimal MAXI_SAVINGS_RATE_THREE = new BigDecimal("0.1");
    
    private final AccountType accountType;
    private final List<Transaction> transactions;
    private BigDecimal balance;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        balance = BigDecimal.ZERO;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance = balance.add(amount);
        }
    }

    public void withdraw(BigDecimal amount) throws InsufficientFundsException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if(balance.compareTo(amount) < 0) {
            // Throw Exception
            throw new InsufficientFundsException("Withdrawal amount must be lower or equal to balance");
        } else {
            transactions.add(new Transaction(amount.negate()));
            balance = balance.add(amount.negate());
        }
    }

    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount.compareTo(SAVINGS_RATE_LIMIT_ONE) <= 0)
                    return amount.multiply(SAVINGS_RATE_ONE);
                else {
                    BigDecimal a = amount.subtract(SAVINGS_RATE_LIMIT_ONE);
                    BigDecimal b = a.multiply(SAVINGS_RATE_TWO);
                    
                    BigDecimal c = SAVINGS_RATE_LIMIT_ONE.multiply(SAVINGS_RATE_ONE);
                    return c.add(b);
                }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount.compareTo(MAXI_SAVINGS_RATE_LIMIT_ONE) <= 0)
                    return amount.multiply(MAXI_SAVINGS_RATE_ONE);
                else if (amount.compareTo(MAXI_SAVINGS_RATE_LIMIT_TWO) <= 0) {
                    BigDecimal a = amount.subtract(MAXI_SAVINGS_RATE_LIMIT_ONE);
                    BigDecimal b = a.multiply(MAXI_SAVINGS_RATE_TWO);
                    BigDecimal c = MAXI_SAVINGS_RATE_LIMIT_ONE.multiply(MAXI_SAVINGS_RATE_ONE);
                    return c.add(b);
                }
                else {
                    BigDecimal a = amount.subtract(MAXI_SAVINGS_RATE_LIMIT_TWO);
                    BigDecimal b = a.multiply(MAXI_SAVINGS_RATE_THREE);
                    
                    BigDecimal c = MAXI_SAVINGS_RATE_LIMIT_ONE.multiply(MAXI_SAVINGS_RATE_ONE);
                    BigDecimal d = MAXI_SAVINGS_RATE_LIMIT_TWO
                            .subtract(MAXI_SAVINGS_RATE_LIMIT_ONE)
                            .multiply(MAXI_SAVINGS_RATE_TWO);
                    return c.add(d).add(b);
                }
            default:
                return amount.multiply(CHECKING_RATE);
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
       return balance;
    }

    public Boolean checkIfTransactionsExist() {
        return !transactions.isEmpty();
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
