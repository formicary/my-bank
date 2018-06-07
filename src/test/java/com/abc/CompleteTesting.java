package com.abc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//Run All Tests
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class, //Run Account Tests
        TransactionTest.class,   //Run Transaction Tests
        CustomerTest.class,  //Run Customer Tests
        BankTest.class //Run Bank Tests
})
public class CompleteTesting {

}
