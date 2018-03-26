package com.abc;

import java.time.LocalDateTime;

public class Transaction {
    public final double amount;
    public final int transactionType; // 0: withdrawal, 1: deposit, 2: transfer

    private LocalDateTime transactionDate;

    /**
     * Constructor for Transaction class used to store details of deposit/withdrawal/transfer transactions
     * @param amount of currency involved in the transaction
     * @param payment boolean to indicate if the transaction is a payment (true) or a transfer (false) between the accounts
     */
    public Transaction(double amount, boolean payment) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();

        if(payment == false){
            // Transfer
            this.transactionType = 2;
        } else {
            if (amount >= 0) {
                // Deposit
                this.transactionType = 1;
            } else {
                // Withdrawal
                this.transactionType = 0;
            }
        }
    }

    public double getAmount(){
        return amount;
    }

    public boolean isDeposit(){
        return transactionType == 1;
    }

    public boolean isWithdrawal(){
        return transactionType == 0;
    }

    public double getTransactionAmount(){
        return amount;
    }

    public LocalDateTime getTransactionDate(){
        return transactionDate;
    }
}
