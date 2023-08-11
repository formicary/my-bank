package com.abc.classes;

import java.util.ArrayList;
import java.util.List;
import com.abc.helpers.AccountInterests;

public class Account {

    enum AccountType {CHECKING, SAVINGS, MAXI_SAVINGS}
    AccountType accountType;
    public List<Transaction> transactions;
    private double balance;
    private double accruedInterest;

    public Account(AccountType account) {
        this.accountType = account;
        this.balance = 0; //Assuming a new account opens with an empty balance
        this.accruedInterest = 0; //Assuming a new account opens with no acrrued interest  
        this.transactions = new ArrayList<Transaction>();
    }

    //Getters//
    public Account getAccount(){
        return this;
    }

    public AccountType getAccountType(){
        return accountType;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public double getBalance(){
        return balance;
    }

    public double getAccruedIntered(){
        return accruedInterest;
    }

    //Transaction Functions//
    //Update balance, called everytime a deposit or withdrawal is made
    public void updateBalance(double amount){
        balance += amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            updateBalance(amount);
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            updateBalance(-amount);
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
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

    public void addInterest() {
        double interestEarned;
        switch(accountType){
            case CHECKING:
                interestEarned = AccountInterests.calculateInterestChecking(balance);
                deposit(interestEarned);
                accruedInterest += interestEarned;
                break;

            case SAVINGS:
                interestEarned = AccountInterests.calculateInterestSavings(balance);
                deposit(interestEarned);
                accruedInterest += interestEarned;
                break;

            case MAXI_SAVINGS:
                interestEarned = AccountInterests.calculateInterestMaxiSavings(balance);
                deposit(interestEarned);
                accruedInterest += interestEarned;
                break;

            default:
                System.out.println("Could not find account with account type: " + accountType);
        }
    }

    //CURRENTLY UNUSED, LOOK AT LATER DATE///
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }




//Remove after testing
public static void main(String[] args) {
    Account testChecking = new Account(AccountType.CHECKING);
    Account testSavings = new Account(AccountType.SAVINGS);
    Account testMaxi = new Account(AccountType.MAXI_SAVINGS);

    testChecking.deposit(200);
    testSavings.deposit(2500);
    testMaxi.deposit(5000);

    System.out.println("/////PRE INTEREST/////");
    System.out.println(testChecking.balance);
    System.out.println(testSavings.balance);
    System.out.println(testMaxi.balance);

    testChecking.addInterest();
    testSavings.addInterest();
    testMaxi.addInterest();

    // testAccount.deposit(200);
    // testAccount.withdraw(20);
    // testAccount.withdraw(20);

    System.out.println("/////POST INTEREST/////");
    System.out.println(testChecking.balance);
    System.out.println(testSavings.balance);
    System.out.println(testMaxi.balance);

    // List<Transaction> transactions = testAccount.getTransactions();
    // for (Transaction t: transactions)
    //         System.out.println(t.getTransactionDate() + " " + t.getTransactionAmount());


}
}
