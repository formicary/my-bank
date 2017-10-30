package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.joda.time.LocalDateTime;

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
    
    private static final BigDecimal MAXI_SAVINGS_RATE_ONE = new BigDecimal("0.05");
    private static final BigDecimal MAXI_SAVINGS_RATE_TWO = new BigDecimal("0.001");
    
    private final AccountType accountType;
    private final List<Transaction> transactions;
    private BigDecimal balance;
    private Transaction lastWithdrawal;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = BigDecimal.ZERO;
        this.lastWithdrawal = null;
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
            throw new InsufficientFundsException("Withdrawal amount must be lower or equal to balance");
        } else {
            transactions.add(new Transaction(amount.negate()));
            balance = balance.add(amount.negate());
            lastWithdrawal = transactions.get(transactions.size()-1);
        }
    }

    public BigDecimal interestEarned() {
        
        switch(accountType){
            case SAVINGS:
                return getSavingsInterest();
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                return getMaxiSavingsInterest();
            default:
                return getCheckingInterest();
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
            s.append((t.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit"));
            s.append(" ");
            s.append(toDollars(t.getAmount()));
            s.append("\n");
            total = total.add(t.getAmount());
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

    private BigDecimal getCheckingInterest() {
        BigDecimal amount = sumTransactions();
        return amount.multiply(CHECKING_RATE);
    }
    
    private BigDecimal getSavingsInterest() {
        BigDecimal amount = sumTransactions();
        
        if (amount.compareTo(SAVINGS_RATE_LIMIT_ONE) <= 0)
            return amount.multiply(SAVINGS_RATE_ONE);
        else {
            BigDecimal a = amount.subtract(SAVINGS_RATE_LIMIT_ONE);
            BigDecimal b = a.multiply(SAVINGS_RATE_TWO);
                    
            BigDecimal c = SAVINGS_RATE_LIMIT_ONE.multiply(SAVINGS_RATE_ONE);
            return c.add(b);
        }
    }
    
    private BigDecimal getMaxiSavingsInterest() {
        
        BigDecimal amount = sumTransactions();
   
        LocalDateTime today10 = LocalDateTime.now().minusDays(10);
        
        if(lastWithdrawal == null) {
            return amount.multiply(MAXI_SAVINGS_RATE_ONE); 
        } else if(lastWithdrawal.getDate().compareTo(today10) < 0) {
            return amount.multiply(MAXI_SAVINGS_RATE_ONE); 
        } else {
            return amount.multiply(MAXI_SAVINGS_RATE_TWO);
        }
        
        /*if (amount.compareTo(MAXI_SAVINGS_RATE_LIMIT_ONE) <= 0)
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
        }*/
    }
}
