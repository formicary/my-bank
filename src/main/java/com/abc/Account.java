package com.abc;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.abc.Bank.*;
import static java.lang.Math.abs;

@Getter
public class Account {

    public enum AccountType {

        CHECKING("Checking Account"),               //has a flat rate of 0.1%
        SAVINGS("Savings Account"),                 //has a rate of 0.1% for the first $1,000 then 0.2%
        MAXI_SAVINGS_I("Maxi Savings Account I"),   //has a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%
        MAXI_SAVINGS_II("Maxi Savings Account II"); //has an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%

        @Getter
        private String desc;

        AccountType(String desc) {
            this.desc = desc;
        }
    }

    public enum Rate {
        CHECKING(0.001),            //0.1%
        SAVINGS_MIN(0.001),         //0.1%
        SAVINGS_MAX(0.002),         //0.2%
        MAXI_SAVINGS_I_MIN(0.02),   //2%
        MAXI_SAVINGS_I_MID(0.05),   //5%
        MAXI_SAVINGS_I_MAX(0.1),    //10%
        MAXI_SAVINGS_II_MIN(0.001), //0.1%
        MAXI_SAVINGS_II_MAX(0.05);  //5%

        @Getter
        private Double rate;

        public String print() {
            return PERCENT.format(rate);
        }

        Rate(Double rate) {
            this.rate = rate;
        }
    }

    @NonNull
    private final AccountType accountType;
    @NonNull
    private List<Transaction> transactions;
    @NonNull
    private Double balance;

    public Account(@NonNull AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0d;
    }

    public void deposit(@NonNull Double amount) {
        deposit(amount, null);
    }

    public void deposit(@NonNull Double amount, LocalDateTime transactionDateTime) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero!");
        } else {
            if (transactionDateTime == null)
                transactions.add(new Transaction(amount));
            else
                transactions.add(new Transaction(amount, transactionDateTime));
            balance += amount;
        }
    }

    public boolean withdraw(@NonNull Double amount) {
        return withdraw(amount, null);
    }

    public boolean withdraw(@NonNull Double amount, LocalDateTime transactionDateTime) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero!");
        } else if (balance.compareTo(amount) >= 0) {
            if (transactionDateTime == null)
                transactions.add(new Transaction(-amount));
            else
                transactions.add(new Transaction(-amount, transactionDateTime));
            balance -= amount;
            return true;
        } else
            return false;
    }

    public Double getInterestEarned() {
        switch(accountType) {
            case SAVINGS:
                if (balance <= 1000)
                    return balance * Rate.SAVINGS_MIN.rate;
                else
                    return 1 + (balance - 1000) * Rate.SAVINGS_MAX.rate;
            case MAXI_SAVINGS_I:
                if (balance <= 1000)
                    return balance * Rate.MAXI_SAVINGS_I_MIN.rate;
                if (balance <= 2000)
                    return 20 + (balance - 1000) * Rate.MAXI_SAVINGS_I_MID.rate;
                return 70 + (balance - 2000) * Rate.MAXI_SAVINGS_I_MAX.rate;
            case MAXI_SAVINGS_II:
                LocalDate transactionDate, prevTransactionDate, periodStartDate;
                Double totalInterest = 0d;
                boolean withdrawal;
                transactions.sort(Comparator.comparing((Transaction t) -> t.getTransactionDateTime().toLocalDate())
                        .thenComparing((Transaction t) -> t.getAmount())
                        .reversed());
                for (int i = 0; i < transactions.size(); ++i) {
                    transactionDate = transactions.get(i).getTransactionDateTime().toLocalDate();
                    periodStartDate = transactionDate.minusDays(10);

                    withdrawal = transactions.get(i).getAmount() < 0 ? true : false;
                    if (!withdrawal)
                        for (int j = i+1; j < transactions.size(); ++j) {
                            prevTransactionDate = transactions.get(j).getTransactionDateTime().toLocalDate();
                            if (prevTransactionDate.isBefore(periodStartDate))
                                break;
                            else if (transactions.get(j).getAmount() < 0) {
                                withdrawal = true;
                                break;
                            }
                        }
                    totalInterest += withdrawal ? transactions.get(i).getAmount() * Rate.MAXI_SAVINGS_II_MIN.rate : transactions.get(i).getAmount() * Rate.MAXI_SAVINGS_II_MAX.rate;
                    System.out.println("Transaction[" + (transactions.size()-i) + "]: "
                            + DATETIME.format(transactions.get(i).getTransactionDateTime()) + " (period start: " + DATE.format(periodStartDate) + ")"
                            + ", " + DOLLAR.format(transactions.get(i).getAmount())
                            + " -> " + (withdrawal ? Rate.MAXI_SAVINGS_II_MIN.print() : Rate.MAXI_SAVINGS_II_MAX.print()));
                }
                return totalInterest.doubleValue();
            default: //CHECKING
                return balance * Rate.CHECKING.rate;
        }
    }

    public String printStatement() {
        StringBuilder s = new StringBuilder(accountType.desc + "\n");
        for (Transaction t : transactions)
            s.append("  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + DOLLAR.format(abs(t.getAmount())) + "\n");
        s.append("Total " + DOLLAR.format(balance));
        return s.toString();
    }

}
