package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public List<Transaction> transactions;
    private double accountBalance;
    private String customerID;

    public Account() {
        this.transactions = new ArrayList<>();
        this.accountBalance = 0;
    }

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }

    public double getAccountBalance() {
        return this.accountBalance;
    }

    public double getInterestAccrued() {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.type.equals("Interest Earned")) {
                total += t.amount;
            }
        }
        return total;
    }

    public void accrueInterest() {
        if(accountBalance > 0) {
            double interestReceived = this.interestEarnedDaily();
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

    public abstract double interestEarnedDaily();

    public abstract String getAccountType();

    private void updateAccountBalance(double amount) {
        this.accountBalance += amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Customer ID " + customerID + " tried to deposit a negative or nil quantity " +
                    "to their " + this.getAccountType() + ".");
        } else {
            transactions.add(new Transaction(amount, "Deposit"));
            updateAccountBalance(amount);
        }
    }
    public void withdraw(double amount) { // Indentation
        if (amount <= 0) {
            throw new IllegalArgumentException("Customer ID " + customerID + " tried to withdraw a negative or nil quantity " +
                    "from their " + this.getAccountType() + ".");
        } else if (amount > this.getAccountBalance()) {
            throw new IllegalArgumentException("Customer ID " + customerID + " tried to withdraw funds exceeding their" +
                    " account balance from their " + this.getAccountType() + ".");
        } else {
            transactions.add(new Transaction(amount, "Withdrawal"));
            updateAccountBalance(-amount);
        }
    }
    public void outwardTransfer(Account transferTo, double amountToTransfer) {
        String transactionType = "Transfer to " + transferTo.getAccountType();
        transactions.add(new Transaction(amountToTransfer, transactionType));
        updateAccountBalance(-amountToTransfer);
    }
    public void inwardTransfer(Account transferFrom, double amountToTransfer) {
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
    public Double dailyCompoundInterestRate(double annualRate){
        return annualRate/ 365;
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