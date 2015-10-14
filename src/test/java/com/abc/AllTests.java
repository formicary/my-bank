package test.java.com.abc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BankTest.class, CustomerTest.class, TransactionTest.class })
public class AllTests {

}
