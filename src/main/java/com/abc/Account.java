package com.abc;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;



public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;




    private double accountBalance;
    private final int accountType;
    private ArrayList<Transaction> transactions;

    //Have to initialize the date to avoid null pointer exceptions
    private LocalDateTime lastWithdrawDate = LocalDateTime.now().minusDays(100);


    // Annual interest rates for the accounts
    private final double MAXI_BASE_INTEREST = 0.001;
    private final double MAXI_HIGH_INTEREST = 0.05;
    private final double CHECKING_INTEREST = 0.001;
    private final double SAVING_BASE_INTEREST = 0.001;
    private final double SAVING_HIGH_INTEREST = 0.002;

    // Interest period for the accounts
    private final double INTEREST_PERIOD = 365;



    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
        this.accountBalance = 0;

    }


    // Deposits money into an account
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposits must be greater than zero");
        }
        // Deposit limit
        else if(amount > 1e10){
            throw new IllegalArgumentException("Deposits cannot be greater than 10000000000");
        }
        else {
            transactions.add(new Transaction(amount, "deposit"));
            accountBalance += amount;
        }
    }


    // Withdraws money from an account and notes the withdrawal date
    public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Withdrawals must be greater than zero");
    }
    // Withdrawal
    else if(amount > 1e10){
        throw new IllegalArgumentException("Withdrawals cannot be greater than 10000000000");
    }
    else {
        Transaction trans = new Transaction(-amount, "withdrawal");
        transactions.add(trans);
        lastWithdrawDate = trans.getTransactionDate();
        accountBalance -= amount;
    }

    }

    // Transfers money between two accounts
    public void transfer(double amount){
        transactions.add(new Transaction(amount, "Transfer"));
        accountBalance += amount;
    }


    // Calculates the daily interest earned on the different accounts
    public double interestEarned() {
        double amount = accountBalance;
        switch(accountType){
            case CHECKING:
                return amount * (CHECKING_INTEREST / INTEREST_PERIOD);
            case SAVINGS:
                if (amount <= 1000)
                    return amount * (SAVING_BASE_INTEREST / INTEREST_PERIOD);
                else
                    return ((1000 * (SAVING_BASE_INTEREST / INTEREST_PERIOD)) +
                            ((amount - 1000) * (SAVING_HIGH_INTEREST / INTEREST_PERIOD)));
            case MAXI_SAVINGS:
                if(ChronoUnit.DAYS.between(lastWithdrawDate, LocalDateTime.now()) < 10)
                {
                    return amount * (MAXI_BASE_INTEREST / INTEREST_PERIOD);
                }
                else{
                    return amount * (MAXI_HIGH_INTEREST / INTEREST_PERIOD);
                }
            default:
                return 0;
        }
    }



    // Getters

    public int getAccountType() {
        return accountType;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }


    public double getAccountBalance() {
        return accountBalance;
    }

}
