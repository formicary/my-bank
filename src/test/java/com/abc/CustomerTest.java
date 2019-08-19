package com.abc;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    public class TestCustomer extends Customer {
        public TestCustomer(String name) {
            super(name);
        }

        public String getFirstAccountType() {
            if (accounts != null) {
                return accounts.get(0).getAccountType();
            }
            else return null;
        }
    }

    @Test
    public void testConstructorSetName() {
        String name = "John";
        Customer customer = new Customer(name);

        assertEquals(customer.getName(), name);
    }

    @Test
    public void testOpenNumberOfAccounts(){
        Customer oscar = new Customer("Oscar");

        for (int i = 0; i < 100; i++) {
            oscar.openAccount(new SavingsAccount());
            assertEquals(i + 1, oscar.getNumberOfAccounts());
        }
    }

    @Test
    public void testOpenSavingsAccount() {
        TestCustomer testCustomer = new TestCustomer("John");
        SavingsAccount savingsAccount = new SavingsAccount();
        testCustomer.openAccount(savingsAccount);

        assertEquals(1, testCustomer.getNumberOfAccounts());
        assertEquals("Savings Account", testCustomer.getFirstAccountType());
    }

    @Test
    public void testOpenMaxiSavingsAccount() {
        TestCustomer testCustomer = new TestCustomer("John");
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        testCustomer.openAccount(maxiSavingsAccount);

        assertEquals(1, testCustomer.getNumberOfAccounts());
        assertEquals("Maxi Savings Account", testCustomer.getFirstAccountType());
    }

    @Test
    public void testOpenCheckingAccount() {
        TestCustomer testCustomer = new TestCustomer("John");
        CheckingAccount checkingAccount = new CheckingAccount();
        testCustomer.openAccount(checkingAccount);

        assertEquals(1, testCustomer.getNumberOfAccounts());
        assertEquals("Checking Account", testCustomer.getFirstAccountType());
    }

    @Test
    public void testTransferLessThanZero() {
        Customer john = new Customer("John");
        Account withdrawAccount = new SavingsAccount();
        Account depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        try {
            john.transferBetweenAccounts(withdrawAccount, depositAccount, (-0.0));
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero and less than or equal to the widthrawing account's value"));
        }
    }

    @Test
    public void testTransferZero() {
        Customer john = new Customer("John");
        Account withdrawAccount = new SavingsAccount();
        Account depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        try {
            john.transferBetweenAccounts(withdrawAccount, depositAccount, 0.0);
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero and less than or equal to the widthrawing account's value"));
        }
    }

    @Test
    public void testTransferGreaterThanZeroLessThanWithdrawAccountValue() {
        Customer john = new Customer("John");
        Account withdrawAccount = new SavingsAccount();
        Account depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        john.transferBetweenAccounts(withdrawAccount, depositAccount, 1.0);

        assertEquals(withdrawAccount.getAccountValue(), 99.0, 0.001);
        assertEquals(depositAccount.getAccountValue(), 101.0, 0.001);
    }

    @Test
    public void testTransferGreaterThanZeroGreaterThanWithdrawAccountValue() {
        Customer john = new Customer("John");
        Account withdrawAccount = new SavingsAccount();
        Account depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        try {
            john.transferBetweenAccounts(withdrawAccount, depositAccount, 110.0);
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero and less than or equal to the widthrawing account's value"));
        }
    }

    @Test
    public void testStatementGeneration(){
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

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


}
