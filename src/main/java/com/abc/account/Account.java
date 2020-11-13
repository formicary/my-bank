package com.abc.account;

import com.abc.account.interest.CheckingInterestCalculator;
import com.abc.account.interest.InterestCalculator;
import com.abc.account.interest.MaxiSavingsInterestCalculator;
import com.abc.account.interest.SavingsInterestCalculator;
import com.abc.bank.BankUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Account {

    private final AccountType accountType;
    private final List<Transaction> transactions;
    private InterestCalculator calculator;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
        setInterestCalculator();
    }

    public void deposit(double amount) {
        validateAmount(amount);
        transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        validateAmount(amount);
        if (amount > sumOfTransactions()) {
            throw new IllegalStateException("not enough money on account");
        }
        transactions.add(new Transaction(-amount)); // TODO ???
    }

    public double interestEarned() {
        return calculator.calculateInterest(this);
    }

    public double sumOfTransactions() {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }

    public String statementForAccount() {
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

    private void setInterestCalculator() {
        switch (accountType) {
            case CHECKING:
                calculator = new CheckingInterestCalculator();
                break;
            case SAVINGS:
                calculator = new SavingsInterestCalculator();
                break;
            case MAXI_SAVINGS:
                calculator = new MaxiSavingsInterestCalculator();
                break;
        }
    }

}
