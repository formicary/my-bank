package com.abc;

import com.abc.account.Account;
import com.abc.account.AccountFactory;
import com.abc.account.AccountType;
import com.abc.branch.Customer;
import com.abc.util.ZeroAmountException;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private AccountFactory accountFactory;

    @Before
    public void setUp() {
        this.accountFactory = new AccountFactory();
    }

    @After
    public void tearDown() {
        this.accountFactory = null;
    }

    @Test //Test customer statement generation
    public void testApp(){
        Customer henry = new Customer("Henry");

        try {
            Account checkingAccount =
                    accountFactory.createAccount(henry, AccountType.CHECKING, 100.00);
            Account savingsAccount =
                    accountFactory.createAccount(henry, AccountType.SAVINGS, 4000.0);

            checkingAccount.deposit(100.0);
            savingsAccount.deposit(4000.0);
            savingsAccount.withdraw(200.0);
        } catch (ZeroAmountException e) {
            // ...
        }

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar");
        accountFactory.createAccount(oscar, AccountType.SAVINGS, 0.01);

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        accountFactory.createAccount(oscar, AccountType.SAVINGS, 0.01);
        accountFactory.createAccount(oscar, AccountType.CHECKING, 0.01);

        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");

        accountFactory.createAccount(oscar, AccountType.SAVINGS, 0.01);
        accountFactory.createAccount(oscar, AccountType.CHECKING, 0.01);

        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
