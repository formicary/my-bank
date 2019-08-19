package com.abc;

import com.abc.bank.Account;
import com.abc.bank.CheckingAccount;
import com.abc.bank.Customer;
import com.abc.bank.MaxiSavingsAccount;
import com.abc.bank.SavingsAccount;

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
        SavingsAccount withdrawAccount = new SavingsAccount();
        SavingsAccount depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        try {
            john.transferBetweenAccounts(withdrawAccount, depositAccount, (-0.0));
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero and less than or equal to the withdrawing account's value"));
        }
    }

    @Test
    public void testTransferZero() {
        Customer john = new Customer("John");
        SavingsAccount withdrawAccount = new SavingsAccount();
        SavingsAccount depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100);
        depositAccount.deposit(100);

        try {
            john.transferBetweenAccounts(withdrawAccount, depositAccount, 0.0);
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("amount must be greater than zero and less than or equal to the withdrawing account's value"));
        }
    }

    @Test
    public void testTransferGreaterThanZeroLessThanWithdrawAccountValue() {
        Customer john = new Customer("John");
        SavingsAccount withdrawAccount = new SavingsAccount();
        SavingsAccount depositAccount = new SavingsAccount();

        john.openAccount(withdrawAccount);
        john.openAccount(depositAccount);

        withdrawAccount.deposit(100.0);
        depositAccount.deposit(100.0);

        john.transferBetweenAccounts(withdrawAccount, depositAccount, 1.0);

        assertEquals(99.0, withdrawAccount.getAccountValue(), 0.001);
        assertEquals(101.0, depositAccount.getAccountValue(), 0.001);
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
            assertThat(e.getMessage(), containsString("amount must be greater than zero and less than or equal to the withdrawing account's value"));
        }
    }

    @Test
    public void testTotalInterestEarned() {
        Customer john = new Customer("John");
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        john.openAccount(savingsAccount);
        john.openAccount(checkingAccount);

        savingsAccount.deposit(100);
        checkingAccount.deposit(100);

        assertEquals(0.2, john.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testStatementGeneration(){
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);
        henry.openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        maxiSavingsAccount.deposit(100.0);

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
                "Maxi Savings Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Total In All Accounts $4,000.00", henry.getStatement());
    }


}
