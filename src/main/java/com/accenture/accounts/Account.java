package com.accenture.accounts;

import com.accenture.Transaction;
import com.accenture.intereststrategies.InterestStrategy;
import com.accenture.DollarAmount;

import java.util.*;
import java.util.stream.Collectors;

public class Account {

    private static final String INTEREST_PAYMENT_DESCRIPTION = "INTEREST PAYMENT";

    private final String id = UUID.randomUUID().toString();
    private final String ownerCustomerId;

    private List<Transaction> transactions = new ArrayList<>();

    // Strategy pattern for calculating the interest for an account
    private InterestStrategy interestStrategy;

    public Account(String ownerCustomerId, InterestStrategy interestStrategy) {
        this.ownerCustomerId = ownerCustomerId;
        this.interestStrategy = interestStrategy;
    }

    private DollarAmount interestEarned() {
        return interestStrategy.getInterest(transactions);
    }

    public String getOwnerCustomerId() {
        return ownerCustomerId;
    }

    public String getId() {
        return id;
    }

    public void applyTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public InterestStrategy getInterestStrategy() {
        return interestStrategy;
    }

    // Allow the interest strategy applied to this account to be changed at runtime
    public void setInterestStrategy(InterestStrategy interestStrategy) {
        this.interestStrategy = interestStrategy;
    }

    public DollarAmount getAccountBalance() {
        return transactions.stream()
                .map(transaction -> {
                    if (transaction.getType() == Transaction.Type.WITHDRAWAL)
                        return transaction.getAmount().negate();
                    else
                        return transaction.getAmount();
                })
                .reduce(DollarAmount.fromInt(0), DollarAmount::plus);
    }

    public void applyDailyInterest() {
        DollarAmount interestEarned = interestEarned();
        if (interestEarned.gt(DollarAmount.fromInt(0))) {
            Transaction interestTransaction = new Transaction.Builder()
                    .setAmount(interestEarned)
                    .setType(Transaction.Type.INTEREST)
                    .setDescription(INTEREST_PAYMENT_DESCRIPTION)
                    .build();

            applyTransaction(interestTransaction);
        }
    }


    public String generateStatement(boolean showTotalInterestPaid) {
        StringBuilder statement = new StringBuilder();
        statement.append("Account Statement. \n");
        transactions.forEach(transaction -> {
            statement.append(transaction.toString());
            statement.append("\n");
        });

        if (showTotalInterestPaid) {
            statement.append("Total Interest Paid: ").append(getTotalInterestPaid()).append("\n");
        }

        DollarAmount currentBallance = getAccountBalance();
        statement.append("Current Balance: ").append(currentBallance);
        return statement.toString();
    }


    public DollarAmount getTotalInterestPaid() {
        List<DollarAmount> interestPayments = transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.INTEREST)
                .map(Transaction::getAmount)
                .collect(Collectors.toList());

        return DollarAmount.sum(interestPayments);
    }


    public static synchronized void transfer(Account accountFrom, Account accountTo, DollarAmount amount) {
        if (!accountFrom.ownerCustomerId.equals(accountTo.ownerCustomerId))
            throw new IllegalArgumentException("Both accounts in a transfer must belong to the same owner");

        Transaction.Builder builder = new Transaction.Builder()
                .setAmount(amount)
                .setDescription("Transfer from " + accountFrom.id + " to " + accountTo.id);

        Transaction deposit = builder.setType(Transaction.Type.DEPOSIT).build();
        Transaction withdrawal = builder.setType(Transaction.Type.WITHDRAWAL).build();

        accountFrom.applyTransaction(withdrawal);
        accountTo.applyTransaction(deposit);
    }


    @Override
    public String toString() {
        return "Account: " + interestStrategy.toString() + " Transaction Count: " + transactions.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
