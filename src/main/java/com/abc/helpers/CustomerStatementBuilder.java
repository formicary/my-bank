package com.abc.helpers;

import com.abc.classes.Account;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;

public class CustomerStatementBuilder {

    //Singular statement for one account
    public static String createStatement(Account account) {
        String accountType = account.getAccountType().toString();
        String balance = toDollars(account.getBalance());

        String accountInfo = "\nAccount Type: " + accountType + "\nBalance: " + balance + "\n";

        return accountInfo;
    }

    //Multiple statements for all accounts
    public static List<String> createStatement(List<Account> accounts){
        List<String> accountInfoList = new ArrayList<>();

        //Return a default value if no accounts found for customer.
        if(accounts.isEmpty()){
            accountInfoList.add("No accounts found for this customer");
            return accountInfoList;
        }

        //Loop through customer account and return a statement for each bank
        for (Account account : accounts) {
            String accountType = account.getAccountType().toString();
            String balance = toDollars(account.getBalance());
            String accruedInterest = toDollars(account.getAccruedIntered());

            //Format into readable statement
            String accountInfo = "\nAccount Type: " + accountType +  "\nBalance: " + balance + "\nAccrued Interest: " + accruedInterest +"\n";
            accountInfoList.add(accountInfo);
            
        }
        return accountInfoList;
    }
    
    //Convert double to dollars for readable balance/accured interest
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

}
