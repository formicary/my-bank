package com.abc.classes;

import java.util.ArrayList;
import java.util.List;
import com.abc.helpers.AccountInterests;

public class Account {

    public enum AccountType {CHECKING, SAVINGS, MAXI_SAVINGS}
    AccountType accountType;
    private List<Transaction> transactions;
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

    public double getAccruedInterest(){
        return accruedInterest;
    }

    //Transaction Functions//
    //Update balance, called everytime a deposit or withdrawal is made
    public void updateBalance(double amount){
        balance += amount;
    }

    //Update the accrued interest, called everytime interest is added to the account
    public void updateAccuredInterest(double amount){
        accruedInterest += amount;
    }

    //Private deposit functions wrapped in public try catch functions
    private void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            updateBalance(amount);
        }
    }

    private void withdraw(double amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        else if(amount > balance) {
            throw new IllegalArgumentException("Amount withdrawn must be less than your current balance");
            
        } else {
            transactions.add(new Transaction(-amount));
            updateBalance(-amount);
        }
    }

    public void tryDeposit(double amount){
        try {
            // Attempt to make a deposit
            deposit(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void tryWithdraw(double amount){
        try {
            // Attempt to make a withdrawal
            withdraw(amount);
            System.out.println("Withdrawal successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Interest functions//
    public void addInterest() {
        switch(accountType){
            case CHECKING:
                AccountInterests.calculateInterestChecking(getAccount());
                break;
            case SAVINGS:
                AccountInterests.calculateInterestSavings(getAccount());
                break;
            case MAXI_SAVINGS:
                AccountInterests.calculateInterestMaxiSavings(getAccount());
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
