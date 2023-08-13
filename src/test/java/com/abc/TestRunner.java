package com.abc;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import com.abc.features.BankTest;

public class TestRunner {
    public static void main(String[] args){
        Result result = JUnitCore.runClasses(BankTest.class);

        for (Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
        
    }
}
