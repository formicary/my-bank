package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    public Account(int accountType) {
        if (accountType < 0 || accountType > 2) {
            throw new IllegalArgumentException("Invalid type account");
        }
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
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

    public double getInterestEarned(){
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                // NEW MAXI-SAVING ACCOUNT
                return hasWithdrawalsInLastTenDays() ? (amount * 0.05) : (amount * 0.001);
                // OLD MAXI-SAVING ACCOUNT
//                if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    public boolean hasWithdrawalsInLastTenDays() {
        List<Transaction> transactionsList = transactions.stream().collect(Collectors.toList());
        transactionsList.removeIf(t -> t.getTransactionDate().isBefore(LocalDate.now().minusDays(10)));
        transactionsList.removeIf(t -> t.getAmount() > 0);

        return transactionsList.size() > 0 ? true : false;
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
