package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;

/** Represents a bank account.
 * @author James Rogers
 * @version 1.0
 * @since 1.0
*/
public class Account {

    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS;
    }
    
    /** Represents the checking rate percentage. */
    private static final BigDecimal CHECKING_RATE = new BigDecimal("0.001");
    
    /** Represents the savings rate lower percentage. */
    private static final BigDecimal SAVINGS_RATE_ONE = new BigDecimal("0.001");
    
    /** Represents the savings rate lower limit. */
    private static final BigDecimal SAVINGS_RATE_LIMIT_ONE = new BigDecimal("1000.00");
    
    /** Represents the savings rate upper percentage. */
    private static final BigDecimal SAVINGS_RATE_TWO = new BigDecimal("0.002");
    
    /** Represents the maxi savings rate upper percentage. */
    private static final BigDecimal MAXI_SAVINGS_RATE_ONE = new BigDecimal("0.05");
    
    /** Represents the maxi savings rate lower percentage. */
    private static final BigDecimal MAXI_SAVINGS_RATE_TWO = new BigDecimal("0.001");
    
    /** Type of account. */
    private final AccountType accountType;
    
    /** Transactions in account. */
    private final List<Transaction> transactions;
    
    /** Balance of account. */
    private BigDecimal balance;
    
    /** Last withdrawal of account. */
    private Transaction lastWithdrawal;

    /** 
    * Creates an account of specified account.
    * @param accountType The type of account to create.
    */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = BigDecimal.ZERO;
        this.lastWithdrawal = null;
    }

   /**
   * Deposits money into the account.
   * @param amount Amount of money to deposit.
   * @throws IllegalArgumentException if amount is smaller or equal to zero.
   */
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance = balance.add(amount);
        }
    }

   /**
   * Withdraws money from the account.
   * @param amount Amount of money to withdraw.
   * @throws InsufficientFundsException if the account has insufficient funds.
   * @throws IllegalArgumentException if amount is smaller or equal to zero.
   */
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

   /**
   * Calculates the amount of interest earned.
   * @return The interest earned.
   */
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
    
   /**
   * Produces a statement of the account.
   * @return The statement as a formatted string.
   */
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
    
   /**
   * Creates a formatted string of a given amount.
   * @param d the amount to be formatted to a string.
   * @return The formatted string.
   */
    public static String toDollars(BigDecimal d) {       
        return String.format("$%,.2f", d.abs().setScale(2, RoundingMode.DOWN)); 
    }

    /**
   * Returns the balance of the account.
   * @return The balance.
   */
    public BigDecimal sumTransactions() {
       return balance;
    }

   /**
   * Checks if the account has any transactions.
   * @return A boolean signalling if any transactions exist.
   */
    public Boolean checkIfTransactionsExist() {
        return !transactions.isEmpty();
    }

   /**
   * Gets the account type.
   * @return The account type.
   */
    public AccountType getAccountType() {
        return accountType;
    }

   /**
   * Gets the interest for the checking account type.
   * @return The amount of interest earned.
   */
    private BigDecimal getCheckingInterest() {
        BigDecimal amount = sumTransactions();
        return amount.multiply(CHECKING_RATE);
    }
    
   /**
   * Gets the interest for the savings account type.
   * @return The amount of interest earned.
   */
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
    
   /**
   * Gets the interest for the maxi savings account type.
   * @return The amount of interest earned.
   */
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
    }
}
