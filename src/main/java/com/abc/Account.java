package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.Utilities.Enums.AccountType;
import static com.abc.Utilities.AmountValidator.isNegativeAmount;
import static com.abc.Utilities.AmountValidator.canWithdraw;

//Todo: refactor into abstract class and consider an interface. Create new childclasses per account type.
public class Account {
    private final AccountType accountType;
    private Double balance;
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0.00;
    }

    public void depositFunds(double amount) {
        isNegativeAmount(amount);
        balance += amount;
        transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
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

    // Todo: revist this function/assess it's need
    public double checkIfTransactionsExist() {
        if (transactions.isEmpty()) {
            return transactions.get(0).getAmount();
        };
        
        return 0.0d; // Todo: this is good practice, modify 0.00 to 0.0d across project
    }

    // Todo: revist this function
    public double sumTransactions() {
        checkIfTransactionsExist();
        double amount = 0.0d;
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
