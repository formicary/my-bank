package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.Utilities.Enums.AccountType;
import static com.abc.Utilities.AmountValidator.isNegativeAmount;
import static com.abc.Utilities.AmountValidator.canWithdraw;

//Todo: refactor into abstract class and consider an interface. Create new childclasses per account type. Include similar to JSDoc.
public class Account {
    private final AccountType accountType;
    private double balance; // Todo: best practice to use BigDecimal for currencies to ensure accuracy, refactor all instances of double
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0.0d;
    }

    public void depositFunds(double amount) {
        isNegativeAmount(amount);
        balance += amount;
        transactions.add(new Transaction(amount));
    }

    public void withdrawFunds(double amount) {
        isNegativeAmount(amount);
        canWithdraw(this.getBalance(), amount);
        balance -= amount;
        transactions.add(new Transaction(-amount));
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
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    public boolean checkIfTransactionsExist() {
        return transactions.isEmpty();
    }

    public double sumTransactions() {
        double amount = 0.0d;

        if (checkIfTransactionsExist()) {
            return amount;
        }

        for (Transaction transaction : transactions)
            amount += transaction.getAmount();
        return amount;
    }

    // Todo: consider a setter for accountType
    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
