package com.abc;

import com.util.BigDecimalProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest {

    Customer customer;
    AccountChecking checkingAccount;
    AccountSavings savingsAccount;

    @Before
    public void setup() {
        customer = new Customer("Henry");
        checkingAccount = new AccountChecking();
        savingsAccount = new AccountSavings();
    }

    @After
    public void tearDown() {
        customer = null;
        checkingAccount = null;
        savingsAccount = null;
    }

    @Test //Test customer statement generation
    public void getStatementTest() {
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);

        checkingAccount.deposit(BigDecimal.valueOf(100.0));
        savingsAccount.deposit(BigDecimal.valueOf(4000.0));
        savingsAccount.withdraw(BigDecimal.valueOf(200.0));

        assertEquals(
                "Statement for Henry\n\nChecking Account\n deposit $100.00\nTotal $100.00\n\nSavings Account\n deposit $4,000.00\n withdrawal -$200.00\nTotal $3,800.00\n\nTotal In All Accounts $3,900.00",
                customer.getStatement());
    }

    @Test
    public void openAccount_ONE() {
        customer.openAccount(checkingAccount);
        assertEquals(1, customer.getNumberOfAccounts());
    }

    @Test
    public void openAccount_TWO() {
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);
        assertEquals(2, customer.getNumberOfAccounts());
    }

    @Test
    public void getName() {
        assertEquals("Henry", customer.getName());
    }

    @Test
    public void totalInterestEarned() {
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);
        Account maxiSavingsACcount = new AccountMaxiSavings();
        customer.openAccount(maxiSavingsACcount);

        checkingAccount.deposit(BigDecimalProvider.format(1928)); // 1.93
        savingsAccount.deposit(BigDecimalProvider.format(3765)); // 6.53
        maxiSavingsACcount.deposit(BigDecimalProvider.format(2587)); // 128.70

        assertEquals(BigDecimalProvider.format(137.16), customer.totalInterestEarned());


    }

    @Test
    public void transferTest_OK() {
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);
        customer.transfer(checkingAccount, savingsAccount, BigDecimalProvider.format(100));
    }

    @Test
    public void transferTest_Fail1() {
        try {
            customer.openAccount(checkingAccount);
            customer.transfer(checkingAccount, savingsAccount, BigDecimalProvider.format(100));
            fail("Expected exception has not occured!");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void transferTest_Fail2() {
        try {
            customer.openAccount(savingsAccount);
            customer.transfer(checkingAccount, savingsAccount, BigDecimalProvider.format(100));
            fail("Expected exception has not occured!");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void transferTest_Fail3() {
        try {
            customer.openAccount(checkingAccount);
            customer.openAccount(savingsAccount);
            customer.transfer(checkingAccount, savingsAccount, BigDecimalProvider.format(-100));
            fail("Expected exception has not occured!");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            throw e;
        }
    }


}
