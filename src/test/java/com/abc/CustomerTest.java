package com.abc;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Customer customer = TestHelper.createCustomerWithAccounts("Olivia", 2);
        Account account1 = customer.getAllCustomerAccounts().get(0);
        Account account2 = customer.getAllCustomerAccounts().get(1);

        account1.deposit(100.0);
        account2.deposit(4000.0);
        account2.withdraw(200.0, false);

        customer.transferBetweenAccounts(account1, account2, 10.0);

        assertEquals("Statement for Olivia\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  transfer $10.00\n" +
                "Total $90.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdraw $200.00\n" +
                "  deposit $10.00\n" +
                "Total $3,810.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", customer.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer customer = TestHelper.createCustomerWithAccounts("Oscar", 1);
        assertEquals(1, customer.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer customer = TestHelper.createCustomerWithAccounts("Oscar", 2);
        assertEquals(2, customer.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer customer = TestHelper.createCustomerWithAccounts("Oscar", 3);
        assertEquals(3, customer.getNumberOfAccounts());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDepositAmountIsAlwaysPositive(){

        Customer customer = TestHelper.createCustomerWithAccounts("Henry", 1);
        Account account1 = customer.getAllCustomerAccounts().get(0);
        account1.deposit(-100.0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWithdrawAmountIsAlwaysPositive(){

        Customer customer = TestHelper.createCustomerWithAccounts("Henry", 1);
        Account account1 = customer.getAllCustomerAccounts().get(0);
        account1.withdraw(-100.0, false);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAccountShouldHaveSufficientFundsForWithdraw(){

        Customer customer = TestHelper.createCustomerWithAccounts("James", 1);
        Account account1 = customer.getAllCustomerAccounts().get(0);
        account1.deposit(500.0);
        account1.withdraw(1000.0, false);
    }

    @Test
    public void testTransfer() {
        Customer customer = TestHelper.createCustomerWithAccounts("Sophia", 2);
        Account account1 = customer.getAllCustomerAccounts().get(0);
        Account account2 = customer.getAllCustomerAccounts().get(1);

        account1.deposit(2500.0);
        customer.transferBetweenAccounts(account1, account2, 250.0);

        assertEquals(250.0, account2.sumTransactions(), 0);
        assertEquals(2250.0, account1.sumTransactions(), 0);
    }

    @Test(expected=NullPointerException.class)
    public void testTransferToShouldThrowIfAccountIsNull() {
        Customer customer = TestHelper.createCustomerWithAccounts("Henry", 1);
        Account account1 = customer.getAllCustomerAccounts().get(0);

        account1.deposit(2500.0);
        customer.transferBetweenAccounts(null, account1, 250.0);
    }

    @Test(expected=NullPointerException.class)
    public void testTransferFromShouldThrowIfAccountIsNull() {
        Customer customer = TestHelper.createCustomerWithAccounts("Henry", 1);
        Account account1 = customer.getAllCustomerAccounts().get(0);

        account1.deposit(2500.0);
        customer.transferBetweenAccounts(account1, null, 250.0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testTransferShouldThrowIfAccountDoesNotExist() {
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer customer = TestHelper.createCustomerWithAccounts("Henry", 1);
        Account account1 = customer.getAllCustomerAccounts().get(0);

        savingsAccount.deposit(2500.0);
        customer.transferBetweenAccounts(account1, savingsAccount, 250.0);
    }

    @Test
    public void testCheckCurrentAmount() {
        Customer customer = TestHelper.createCustomerWithAccounts("Henry", 1);
        Account account1 = customer.getAllCustomerAccounts().get(0);

        account1.deposit(1000.0);
        account1.withdraw(200.0, false);

        assertEquals(800.0, account1.getCurrentBalance(), 0);
    }
}
