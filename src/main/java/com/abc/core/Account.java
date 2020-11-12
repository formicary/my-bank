package com.abc.core;

import com.abc.utils.BankUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Account {

    private final AccountType accountType;
    private final List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        validateAmount(amount);
        transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        // TODO: check if there is enough money on the account
        validateAmount(amount);
        transactions.add(new Transaction(-amount));
    }

    public double interestEarned() {
        double amount = sumOfTransactions();
        switch (accountType) {          // TODO: strategy pattern
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount - 1000) * 0.05;
                return 70 + (amount - 2000) * 0.1;
            default:        // checking account
                return amount * 0.001;
        }
    }

    public double sumOfTransactions() {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }

    public String statementForAccount() {
//        String s = accountType.getValue() + "\n";
//        double total = 0.0;
//        for (Transaction t : transactions) {
//            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + BankUtils.formatAmount(t.getAmount()) + "\n";
//            total += t.getAmount();
//        }
//        s += "Total " + BankUtils.formatAmount(total);
//        return s;

        return transactions.stream()
                .map(BankUtils::formatTransaction)
                .collect(
                        () -> new StringBuilder(accountType.getValue() + "\n"),
                        StringBuilder::append,
                        StringBuilder::append)
                .append(String.format("Total %s", BankUtils.formatAmount(sumOfTransactions())))
                .toString();
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

}
