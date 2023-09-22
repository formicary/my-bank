package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.abc.Utilities.Enums.AccountType;
import static com.abc.Utilities.AmountValidator.isNegativeAmount;
import static com.abc.Utilities.AmountValidator.canWithdraw;

//Todo: refactor into abstract class and consider an interface. Create new childclasses per account type. Include similar to JSDoc.
public class Account {
    private final AccountType accountType;
    private BigDecimal balance; // Todo: best practice to use BigDecimal for currencies to ensure accuracy, refactor all instances of double
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = BigDecimal.ZERO;
    }

    public void depositFunds(BigDecimal amount) {
        isNegativeAmount(amount);
        balance = balance.add(amount);
        transactions.add(new Transaction(amount));
    }

    public void withdrawFunds(BigDecimal amount) {
        isNegativeAmount(amount);
        canWithdraw(this.getBalance(), amount);
        balance = balance.subtract(amount);
        transactions.add(new Transaction(amount.negate()));
    }

    // Todo: needs fixing and refactoring to use clean code but do once Account changed to abstract class and child classes implemented
    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount.compareTo(new BigDecimal(1000.00)) <= 0) {
                    amount = amount.multiply(BigDecimal.valueOf(0.001));
                } else
                    amount = BigDecimal.ONE.add(amount.subtract(BigDecimal.valueOf(1000.00))).multiply(BigDecimal.valueOf(0.002));
            case MAXI_SAVINGS:
                if (amount.compareTo(new BigDecimal(1000)) <= 0) {
                    amount = amount.multiply(new BigDecimal(0.02));
                } else if
                    (amount.compareTo(new BigDecimal(2000)) <= 0) {
                    amount = new BigDecimal(20).add(amount.subtract(new BigDecimal(1000))).multiply(new BigDecimal(0.05));
                } else
                    amount = new BigDecimal(70).add(amount.subtract(new BigDecimal(2000))).multiply(new BigDecimal(0.1));
            default:
                amount = amount.multiply(BigDecimal.valueOf(0.001));
        }
        return amount;
    }

    public boolean checkIfTransactionsExist() {
        return transactions.isEmpty();
    }

    public BigDecimal sumTransactions() {
        BigDecimal amount = BigDecimal.ZERO;

        if (checkIfTransactionsExist()) {
            return amount;
        }

        for (Transaction transaction : transactions) {
            amount = amount.add(transaction.getAmount());
        }
        return amount;
    }

    // Todo: consider a setter for accountType
    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
