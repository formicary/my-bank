package com.abc;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.abc.tests.AccountTest;
import com.abc.tests.BankTest;
import com.abc.tests.CustomerTest;
import com.abc.tests.TransactionTest;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BankTest.class, CustomerTest.class, TransactionTest.class,
                AccountTest.class);

        int totalTests = result.getRunCount();
        int failedTests = result.getFailureCount();
        int passedTests = totalTests - failedTests;

        System.out.println("---Test Report---");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed Tests: " + passedTests);
        System.out.println("Failed Tests: " + failedTests);

        System.out.println("\n---Failure Details---");
        for (Failure failure : result.getFailures()) {
            System.out.println("Test: " + failure.getTestHeader());
            System.out.println("Message: " + failure.getMessage() + "\n");
        }
    }
}
