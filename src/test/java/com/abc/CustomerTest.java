package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testObjectInit(){
        Account checkingAccountHenry = new CheckingAccount();
        Account checkingAccountBill = new CheckingAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount("one", checkingAccountHenry);

        Customer bill = new Customer("Bill");
        bill.openAccount("one", checkingAccountBill);

        assertNotNull(henry);
        assertNotNull(bill);
        assertNotSame(henry, bill);
        assertNotSame(checkingAccountHenry, checkingAccountBill);
    }

    @Test //Test customer statement generation
    public void testScenarioOne(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount("one", checkingAccount);
        henry.openAccount("two", savingsAccount);
        henry.openAccount("three", maxiSavingsAccount);

        checkingAccount.deposit(100.0, true);
        savingsAccount.deposit(4000.0, true);
        savingsAccount.withdraw(200.0, true);

        String finalStatement = "Statement for Henry\n" +
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
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00";

        assertEquals(finalStatement, henry.getStatement());
    }

    @Test //Test customer statement generation
    public void testScenarioTransferToThemselves(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount("one", checkingAccount);
        henry.openAccount("two", savingsAccount);
        henry.openAccount("three", maxiSavingsAccount);

        checkingAccount.deposit(100.0, true);
        savingsAccount.deposit(4000.0, true);
        savingsAccount.withdraw(200.0, true);

        savingsAccount.transferTo(checkingAccount, 3000, false);

        String finalStatement = "Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  transfer $3,000.00\n" +
                "Total $3,100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  transfer -$3,000.00\n" +
                "Total $800.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00";

        assertEquals(finalStatement, henry.getStatement());
    }

    @Test
    public void testScenarioTransferToSomeoneElse(){

        Account checkingAccountHenry = new CheckingAccount();
        Account savingsAccountHenry = new SavingsAccount();
        Account maxiSavingsAccountHenry = new MaxiSavingsAccount();

        Account checkingAccountBill = new CheckingAccount();
        Account savingsAccountBill = new SavingsAccount();
        Account maxiSavingsAccountBill = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount("one", checkingAccountHenry);
        henry.openAccount("two", savingsAccountHenry);
        henry.openAccount("three", maxiSavingsAccountHenry);

        Customer bill = new Customer("Bill");
        bill.openAccount("one", checkingAccountBill);
        bill.openAccount("two", savingsAccountBill);
        bill.openAccount("three", maxiSavingsAccountBill);

        checkingAccountHenry.deposit(100.0, true);
        savingsAccountHenry.deposit(4000.0, true);
        savingsAccountHenry.withdraw(200.0, true);

        checkingAccountBill.deposit(100.0, true);
        savingsAccountBill.deposit(4000.0, true);
        savingsAccountBill.withdraw(200.0, true);

        savingsAccountHenry.transferTo(checkingAccountBill, 3000, true);

        assertEquals(savingsAccountHenry.sumTransactions(), 800, DOUBLE_DELTA);
        assertEquals(checkingAccountBill.sumTransactions(), 3100, DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScenarioIllegalTransferNotEnoughMoney(){

        Account checkingAccountHenry = new CheckingAccount();
        Account savingsAccountHenry = new SavingsAccount();
        Account maxiSavingsAccountHenry = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount("one", checkingAccountHenry);
        henry.openAccount("two", savingsAccountHenry);
        henry.openAccount("three", maxiSavingsAccountHenry);

        checkingAccountHenry.deposit(100.0, true);
        savingsAccountHenry.deposit(4000.0, true);
        savingsAccountHenry.withdraw(200.0, true);

        // Should not be able to send money between accounts when the amount is greater than
        // the available balance
        savingsAccountHenry.transferTo(checkingAccountHenry, 4000, true);
    }

    @Test
    public void customerWithoutAccounts(){
        Customer oscar = new Customer("Oscar");
        assertEquals(0, oscar.getNumberOfAccounts());
    }

    @Test
    public void customerWithOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount("one", new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void customerWithTwoAccounts(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount("one", new SavingsAccount());
        oscar.openAccount("two", new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void customerWithThreeAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount("one", new SavingsAccount());
        oscar.openAccount("two", new CheckingAccount());
        oscar.openAccount("three", new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void customerWithSameTypeAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount("one", new CheckingAccount());
        oscar.openAccount("two", new MaxiSavingsAccount());
        oscar.openAccount("three", new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void customerNameIsCorrect(){
        Customer oscar = new Customer("Oscar");
        assertEquals(oscar.getName(), "Oscar");
    }
}
