package com.abc.helpers;

import com.abc.classes.Account;
import java.util.List;

public class CustomerStatementBuilder {
    
    public static String createStatement(List<Account> accounts){
        String dummyString = "";

        for (Account account : accounts) {
            System.out.println(account.getAccountType());
        }

        return dummyString;
}
}
