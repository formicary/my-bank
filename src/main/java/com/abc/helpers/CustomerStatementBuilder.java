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
            for (Account account : accounts) {
                String accountType = account.getAccountType().toString();
                String balance = toDollars(account.getBalance());
                String accruedInterest = toDollars(account.getAccruedInterest());

                //Format into readable statement
                String accountInfo = "\nAccount Type: " + accountType +  "\nBalance: " + balance + "\nAccrued Interest: " + accruedInterest +"\n";
                accountInfoList.add(accountInfo);
                
            }
            return accountInfoList;
        }     
    }
    
    //Convert double to dollars for readable balance/accured interest
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

}
