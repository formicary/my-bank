package com.abc.account;

import com.abc.Transaction;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class SavingsAccount extends Account {

    /**
     * Savings Accounts have a rate of 0.1% for the first $1,000 then 0.2%
     */
    @Override
    public double getRateByDate(LocalDateTime date) {
       Set<Transaction> relevantTransactions = getTransactions().stream().filter(
               transaction -> transaction.getTransactionDate().isBefore(date)).collect(Collectors.toSet());
       double balance = 0;
       double rate = 0.1;
       for (Transaction t : relevantTransactions) {
           balance += t.getAmount();
           if (balance>1000) {
               rate = 0.2;
               break;
           }
       }
       return rate;
    }

    @Override
    public String getType() {
        return "Savings";
    }

}
