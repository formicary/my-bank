package com.abc.helpers;

import com.abc.classes.Account;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;

public class CustomerStatementBuilder {

    //Singular statement for one account
    public static String createStatement(String name, Account account) {
        
        if(account == null){
            return "Account not found for " + name;
        }
        else{
            String accountType = account.getAccountType().toString();
            String balance = toDollars(account.getBalance());
            String accruedInterest = toDollars(account.getAccruedInterest());

            String accountInfo = name + "\nAccount Type: " + accountType +  "\nBalance: " + balance + "\nAccrued Interest: " + accruedInterest +"\n";

            return accountInfo;
        }      
    }

    //Multiple statements for all accounts
    public static List<String> createStatement(String name, List<Account> accounts){
        List<String> accountInfoList = new ArrayList<>();

        //Return a default value if no accounts found for customer.
        if(accounts.isEmpty()){
            accountInfoList.add("No accounts found for " + name);
            return accountInfoList;
        }
        else{
            accountInfoList.add(name);
            //Loop through customer account and return a statement for each bank
            double total = 0;
            for (Account account : accounts) {
                String accountType = account.getAccountType().toString();
                
                //Get balance as double, add to running total before converting to string
                double balance = account.getBalance();
                total += balance;
                String balanceString = toDollars(account.getBalance());

                String accruedInterest = toDollars(account.getAccruedInterest());
                
                //Format into readable statement
                String accountInfo = "\nAccount Type: " + accountType +  "\nBalance: " + balanceString + "\nAccrued Interest: " + accruedInterest +"\n";
                accountInfoList.add(accountInfo);
                
            }
            accountInfoList.add("\nTotal balance of all accounts: " + toDollars(total));
            return accountInfoList;
        }     
    }
    
    //Convert double to dollars for readable balance/accured interest
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    //Used to 
    public static String concatenate(List<String> stringList, String delimiter) {
        return String.join(delimiter, stringList);
    }



}
