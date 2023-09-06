package com.abc;

import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

//Class to display the results of running all tests
public class RunAllTests {
    public static void main (String[] args) {
        //Run test classes and store the results
        Result testResults = JUnitCore.runClasses(AccountTest.class, BankTest.class, CheckingAccountTest.class,
        CustomerTest.class, DateProviderTest.class, MaxiSavingsAccountTest.class, SavingsAccountTest.class,
        TransactionTest.class); 
        
        //Display results
        System.out.println("---Test Results---\n");
        System.out.println("Number of tests : " + testResults.getRunCount());
        System.out.println("Passed tests : " + String.valueOf(testResults.getRunCount() - testResults.getFailureCount()));
        System.out.println("Failed Tests : " + testResults.getFailureCount());

        //If there's at least 1 failure, display the header(s) and relevant message(s)
        if (testResults.getFailureCount() > 0) {
            List<Failure> failedTests = testResults.getFailures();
            System.out.println("\n---Failed Tests---\n");

            for (Failure f : failedTests) {
                System.out.println("Test : " + f.getTestHeader());
                System.out.println("Message : " + f.getMessage());
            }
        }
    }
}