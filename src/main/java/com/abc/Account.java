package com.abc;

import com.abc.utils.DateUtils;
import com.abc.utils.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS
    }

    private final AccountType accountType;
    private final List<Transaction> transactions;
    private final InterestCalculator interestCalculator;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.interestCalculator = new InterestCalculator(transactions, accountType);
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void deposit(double amount) {
        deposit(amount, DateUtils.now());
    }

    public void deposit(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, date));
        }
    }

    public boolean withdraw(double amount) {
        return withdraw(amount, DateUtils.now());
    }
   
    public boolean withdraw(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if (sumTransactions() > amount) {
                transactions.add(new Transaction(-amount, date));
                return true;
            }
            return false;
        }
    }

    public double interestEarned(Date dateToCalculateTo) {
        return interestCalculator.calcCompoundInterest(dateToCalculateTo);
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }

    public String statement() {
        StringBuilder sb = new StringBuilder();

        //Translate to pretty account type
        switch (getAccountType()) {
            case CHECKING:
                sb.append("Checking Account\n");
                break;
            case SAVINGS:
                sb.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                sb.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            sb.append("  ")
                    .append(t.getAmount() < 0 ? "withdrawal " : "deposit ")
                    .append(StringUtils.toDollars(t.getAmount()))
                    .append("\n");
            total += t.getAmount();
        }
        sb.append("Total ").append(StringUtils.toDollars(total));
        return sb.toString();
    }

}
