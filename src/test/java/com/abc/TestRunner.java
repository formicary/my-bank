package com.abc;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import com.abc.features.BankTest;
import com.abc.features.CustomerTest;
import com.abc.features.TransactionTest;
import com.abc.features.AccountTest;

public class TestRunner {
    public static void main(String[] args){
        //Result result = JUnitCore.runClasses(BankTest.class);
        Result result = JUnitCore.runClasses(CustomerTest.class);
        //Result result = JUnitCore.runClasses(AccountTest.class);

        for (Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
        
    }
}
