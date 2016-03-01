package com.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BankTest.class, CheckingAccountTest.class, CustomerTest.class,
		MaxiSavingAccountTest.class, SavingAccountTest.class,
		TransactionTest.class })
public class AllTests {

}
