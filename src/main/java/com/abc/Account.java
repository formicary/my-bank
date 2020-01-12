package com.abc;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
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
            case MAXI_SAVINGS:
                //Filter transactions to get withdrawals
                List<Transaction> withdrawals = transactions.stream()
                        .filter(t -> t.getAmount() < 0)
                        .collect(Collectors.toList());

                //If no withdrawals then 5% interest
                if (withdrawals.size() == 0) {
                    return amount * 0.05;
                }

                //Get period between now and last withdrawal
                int period = Period.between(
                        LocalDate.now(), withdrawals.get(withdrawals.size() - 1).getTransactionDate()
                ).getDays();

                //If there has been a withdrawal within the last 10 days, interest is 0.1%
                if (period < 10) {
                    return amount * 0.001;
                }

                //Interest is 5% otherwise
                return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        Optional<Double> sum = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(Double::sum);

        return sum.orElse(0.0);
    }

    public int getAccountType() {
        return accountType;
    }

}
