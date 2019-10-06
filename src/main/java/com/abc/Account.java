package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public List<Transaction> transactions;
    private BigDecimal accountBalance;
    private String customerID;

    public Account() {
        this.transactions = new ArrayList<>();
        this.accountBalance = BigDecimal.valueOf(0);
    }

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }

    public BigDecimal getAccountBalance() {
        return this.accountBalance;
    }

    public BigDecimal getInterestAccrued() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Transaction t : transactions) {
            if (t.type.equals("Interest Earned")) {
                total = total.add(t.amount);
            }
        }
        return total;
    }

    public void accrueInterest() {
        if(accountBalance.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal interestReceived = this.interestEarnedDaily();
            transactions.add(new Transaction(interestReceived, "Interest Earned"));
            updateAccountBalance(interestReceived);
        }
    }
    public String stringForAccountStatement() {
        StringBuilder s = new StringBuilder(this.getAccountType());
        for (Transaction transaction : transactions) {
            s.append("\n").append(transaction.toString());
        }
        s.append("\n").append("Total ").append(BankUtils.toDollars(this.getAccountBalance()));
        return s.toString();
    }

    public abstract BigDecimal interestEarnedDaily();

    public abstract String getAccountType();

    private void updateAccountBalance(BigDecimal amount) {
        this.accountBalance = accountBalance.add(amount);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Customer ID " + customerID + " tried to deposit a negative or nil quantity " +
                    "to their " + this.getAccountType() + ".");
        } else {
            transactions.add(new Transaction(amount, "Deposit"));
            updateAccountBalance(amount);
        }
    }
    public void withdraw(BigDecimal amount) { // Indentation
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Customer ID " + customerID + " tried to withdraw a negative or nil quantity " +
                    "from their " + this.getAccountType() + ".");
        } else if (amount.compareTo(this.getAccountBalance()) > 0) {
            throw new IllegalArgumentException("Customer ID " + customerID + " tried to withdraw funds exceeding their" +
                    " account balance from their " + this.getAccountType() + ".");
        } else {
            transactions.add(new Transaction(amount, "Withdrawal"));
            updateAccountBalance(amount.negate());
        }
    }
    public void outwardTransfer(Account transferTo, BigDecimal amountToTransfer) {
        String transactionType = "Transfer to " + transferTo.getAccountType();
        transactions.add(new Transaction(amountToTransfer, transactionType));
        updateAccountBalance(amountToTransfer.negate());
    }
    public void inwardTransfer(Account transferFrom, BigDecimal amountToTransfer) {
        String transactionType = "Transfer from " + transferFrom.getAccountType();
        transactions.add(new Transaction(amountToTransfer, transactionType));
        updateAccountBalance(amountToTransfer);
    }

    public boolean withdrawalExists() {
        for (Transaction t : transactions) {
            if (t.getTransactionType().equals("Withdrawal")) {
                return true;
            }
        }
        return false;
    }
    public BigDecimal dailyCompoundInterestRate(BigDecimal annualRate){
        return annualRate.divide(BigDecimal.valueOf(365), 50, RoundingMode.HALF_UP); //Rounding to 50DP.
    }
    public LocalDate getLatestWithdrawalDate() {
        if (transactions.size() >= 1) {
            for (int i = (transactions.size() - 1); i > 0; i--) {
                Transaction t = transactions.get(i);
                if (t.type.equals("Withdrawal")) {
                    return t.getTransactionDate();
                }
            }
        }
        throw new IllegalArgumentException("GetLatestWithdrawalDate function called on " + this.getAccountType() +
                " owned by Customer ID " + customerID + " with no history of withdrawals");
    }
}