package com.abc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;
import java.time.temporal.ChronoUnit;

public abstract class Account {

    public DateProvider dateProvider = DateProvider.getInstance();

    protected double balance;

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    protected int accountType;
    public List<Transaction> transactions = new ArrayList<>();

    /**
     * Method used to deposit money into account
     * @param amount of currency to be deposited into account
     */
    public synchronized void deposit(double amount, boolean transfer) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, transfer));
            balance = balance + amount;
        }
    }

    /**
     * Method used to remove money from account
     * @param amount of currency to be withdrawn from account
     */
    public synchronized void withdraw(double amount, boolean transfer) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        if(amount > this.sumTransactions()){
            throw new IllegalArgumentException("not enough money to withdraw requested amount from account");
        } else {
            transactions.add(new Transaction(-amount, transfer));

            balance = balance - amount;
        }
    }

    /**
     * Method used to transfer money from one account to another
     * @param accountTo the account to which we will transfer money to
     * @param amount of currency involved in the transfer
     * @param transfer
     */
    public void transferTo(Account accountTo, double amount, boolean transfer){
        this.withdraw(amount, transfer);
        accountTo.deposit(amount, transfer);
    }

    /**
     * Method used to calculate interested earned by a customer depending on the account type
     * @return amount of currency earned as interest
     */
    public double interestEarned() {
        LocalDateTime currentDate = dateProvider.now();

        double interest = 0;
        double prevBalance = 0;

        for (Transaction transaction : transactions){
            LocalDateTime transDate = transaction.getTransactionDate();

            double diffInDays = ChronoUnit.DAYS.between(transDate, currentDate);

            prevBalance = prevBalance + transaction.getAmount();

            if (diffInDays > 0) {
                interest = interest + calculateInterest(diffInDays, prevBalance);
            }
        }

        return interest;

    }

    /**
     *
     * @param diffInDays the number of days used to calculate the accrued interest
     * @param balance amount of currency in account
     * @return earned interest in the period of time
     */
    public abstract double calculateInterest(double diffInDays, double balance);

    /**
     * Method used to retrieve a customer's account, their type and the transactions involved in that account
     * @return a string describing the account type and the transactions involved in the account
     */
    public String getStatement() {
        String s = "";

        //Translate to pretty account type
        switch(this.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction transaction : this.transactions) {
            switch (transaction.transactionType) {
                case 0:
                    s += "  withdrawal " + toDollars(transaction.amount) + "\n";
                    break;
                case 1:
                    s += "  deposit " + toDollars(transaction.amount) + "\n";
                    break;
                case 2:
                    if(transaction.amount >= 0){
                        s += "  transfer " + toDollars(transaction.amount) + "\n";
                    } else {
                        s += "  transfer -" + toDollars(transaction.amount) + "\n";
                    }
                    break;
            }

            total += transaction.amount;
        }

        s += "Total " + toDollars(total);

        return s;
    }

    /**
     * Method used to calculate available balance by looping through all the transactions
     * @return amount of currency available
     */
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction transaction: transactions)
            amount += transaction.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    /**
     * Method to format a number representing amount of currency to a string with a dollar ($) sign
     * @param d amount of dolars (stored as a number) to be formatted as a string with dollar ($) sign
     * @return string containing the dollar ($) sign and the amount involved
     */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

}
