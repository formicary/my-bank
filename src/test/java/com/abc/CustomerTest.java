package com.abc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void generateCustomerStatementTest(){

        Customer henry = new Customer("Henry");
        int checkingAccountNo = henry.openAccount(AccountType.CHECKING);
        int savingsAccountNo = henry.openAccount(AccountType.SAVINGS);

        henry.getAccount(checkingAccountNo).deposit(100.0);
        henry.getAccount(savingsAccountNo).deposit(4000.0);
        henry.getAccount(savingsAccountNo).withdraw(200.0);

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
    public void openOneAccountTest(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(AccountType.SAVINGS);

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void openThreeAccountsTest() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(AccountType.SAVINGS);
        oscar.openAccount(AccountType.CHECKING);
        oscar.openAccount(AccountType.SAVINGS);

        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void interestEarnedTest() {
        Customer oscar = new Customer("Oscar");
        int savingsAccountNo = oscar.openAccount(AccountType.SAVINGS);
        int checkingAccountNo = oscar.openAccount(AccountType.CHECKING);

        oscar.getAccount(savingsAccountNo).deposit(500.0);
        oscar.getAccount(savingsAccountNo).withdraw(200.0);

        oscar.getAccount(checkingAccountNo).deposit(700.0);
        oscar.getAccount(checkingAccountNo).withdraw(300.0);

        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void validTransferBetweenAccounts(){
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        int checkingAccountNo = bill.openAccount(AccountType.CHECKING);
        int maxiSavingsAccountNo = bill.openAccount(AccountType.MAXI_SAVINGS);
        bank.addCustomer(bill);

        bill.getAccount(checkingAccountNo).deposit(3000.0);
        bill.getAccount(maxiSavingsAccountNo).deposit(1500.0);
        bill.transferBetweenAccounts(checkingAccountNo, maxiSavingsAccountNo, 1000.0);

        assertEquals(2000.0, bill.getAccount(checkingAccountNo).getCurrentAmount(), DOUBLE_DELTA);
        assertEquals(2500.0, bill.getAccount(maxiSavingsAccountNo).getCurrentAmount(), DOUBLE_DELTA);
    }

    @Test
    public void invalidTransferBetweenAccounts(){
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        int checkingAccountNo = bill.openAccount(AccountType.CHECKING);
        int maxiSavingsAccountNo = bill.openAccount(AccountType.MAXI_SAVINGS);
        bank.addCustomer(bill);

        bill.getAccount(checkingAccountNo).deposit(3000.0);
        bill.getAccount(maxiSavingsAccountNo).deposit(1500.0);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("there are not sufficient fonds in this account");
        bill.transferBetweenAccounts(1, 2, 4000.0);
    }


}
