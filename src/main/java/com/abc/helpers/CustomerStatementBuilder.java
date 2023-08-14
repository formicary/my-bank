package com.abc.helpers;

import com.abc.classes.Account;
import com.abc.classes.Transaction;

import java.util.List;
import static java.lang.Math.abs;

public class CustomerStatementBuilder {

    //Singular statement for one account
    public static String createStatement(String name, Account account) {
        
        //Check if any accounts exist for customer
        if(account == null){
            return "Account not found for " + name;
        }
        else{
            String transactionInfo = "";

            String accountType = account.getAccountType().toString();
            String balance = toDollars(account.getBalance());
            String accruedInterest = toDollars(account.getAccruedInterest());

            //Loop through transactions within account
            for (Transaction transaction : account.getTransactions()){
                //Format transaction to be more readable
                String formattedTransaction = transactionFormatter(transaction);

                //Add formatted transaction to string
                transactionInfo += formattedTransaction;
                transactionInfo += "\n";
                
            }

            String accountInfo = name + "\nAccount Type: " + accountType +  "\nBalance: " + balance + "\nAccrued Interest: " + accruedInterest +"\n";
            accountInfo += transactionInfo;
            return accountInfo;
        }      
    }

    //Multiple Statements for multiple accounts
    public static String createStatement(String name, List<Account> accounts) {
        String accountInfo = "";
        
        //Check if any accounts exist for customer
        if(accounts == null){
            return "Account not found for " + name;
        }
        else{
            
            //Loop through accounts
            for(Account account : accounts){
                String accountType = account.getAccountType().toString();
                String balance = toDollars(account.getBalance());
                String accruedInterest = toDollars(account.getAccruedInterest());

                String transactionInfo = "";

                //Loop through transactions within account
                for (Transaction transaction : account.getTransactions()){
                    //Format transaction to be more readable
                    String formattedTransaction = transactionFormatter(transaction);

                    //Add formatted transaction to string
                    transactionInfo += formattedTransaction;
                    transactionInfo += "\n";
                    
                }

                //Output Account infomation and the transactions
                accountInfo += name + "\nAccount Type: " + accountType +  "\nBalance: " + balance + "\nAccrued Interest: " + accruedInterest +"\n";
                accountInfo += transactionInfo;

            }

            return accountInfo;
        }      
    }

    //Format function to output transaction in more readable way: EXAMPLE: "2023-08-14 10:07:09 (DEPOSIT): $5.01"
    public static String transactionFormatter(Transaction transaction){
        String date = transaction.getTransactionDate();
        String type = transaction.getTransactionType();
        double amount = transaction.getTransactionAmount();

        String transactionString = date + " " + type + ": " + toDollars(amount);

        return transactionString;
    }
    
    //Convert double to dollars for readable balance/accured interest/transactions EXAMPLE :$5.01
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
