package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.CheckingAccount;
import com.abc.Accounts.MaxiSavingsAccount;
import com.abc.Accounts.SavingsAccount;
import com.abc.util.Money;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(new Money("100.0"), Transaction.CUSTOMER);
        savingsAccount.deposit(new Money("4000.0"), Transaction.CUSTOMER);
        savingsAccount.withdraw(new Money("200.0"), Transaction.CUSTOMER);

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testNoAccounts() {
        Customer oscar = new Customer("Oscar");
        assertEquals(0, oscar.getNumberOfAccounts());
    }

    /**
     * normal transfer situation (user transferred all money from one account to another
     */
    @Test
    public void accountTransfer(){
        Account account1 = new CheckingAccount();
        Account account2 = new SavingsAccount();

        Customer oscar = new Customer("Oscar");

        oscar.openAccount(account1);
        oscar.openAccount(account2);

        // amount that will be deposited and transferred
        Money amountToTransfer = new Money("100");

        // deposit total amount that will be transferred to account1
        account1.deposit(amountToTransfer, Transaction.CUSTOMER);

        // transfer all funds from account1 to account2
        oscar.transfer(amountToTransfer, account1, account2);

        boolean account1FundsEmpty = false;
        boolean account2TransferSuccess = false;

        // check if account one has no funds left
        if (account1.sumTransactions().compareTo(Money.ZERO) == 0){
            account1FundsEmpty = true;
        }

        // check if money from account 1 has been transferred to account 2
        if (account2.sumTransactions().compareTo(amountToTransfer) == 0){
            account2TransferSuccess = true;
        }

        assert (account1FundsEmpty);
        assert (account2TransferSuccess);
    }

    /**
     * try to transfer more than is allowed from account1 to account2
     */
    @Test(expected = IllegalArgumentException.class)
    public void accountTransferNotEnoughFunds(){
        Account account1 = new CheckingAccount();
        Account account2 = new SavingsAccount();

        Customer oscar = new Customer("Oscar");

        oscar.openAccount(account1);
        oscar.openAccount(account2);

        // amount that will fail to transfer
        Money amountToTransfer = new Money("100");

        // deposit less than the amount attempting to be transferred
        account1.deposit(new Money("50"), Transaction.CUSTOMER);

        // try to transfer $100 from account1 to account2
        oscar.transfer(amountToTransfer, account1, account2);
    }

    /**
     * throw exception when attempting to transfer to the same account
     */
    @Test(expected = IllegalArgumentException.class)
    public void accountTransferSameAccount(){
        Account account1 = new CheckingAccount();

        Customer oscar = new Customer("Oscar");

        oscar.openAccount(account1);

        // amount that will fail to transfer
        Money amountToTransfer = new Money("20");

        // deposit arbitrary amount
        account1.deposit(new Money("50"), Transaction.CUSTOMER);

        // try to transfer $20 from account1 to the same account
        oscar.transfer(amountToTransfer, account1, account1);
    }

    /**
     * exception thrown when attempting to transfer to or from an account of another customer
     */
    @Test(expected = IllegalArgumentException.class)
    public void accountTransferAnotherCustomer(){
        Account account1 = new CheckingAccount();
        Account account2 = new CheckingAccount();


        Customer oscar = new Customer("Oscar");
        Customer steve = new Customer("steve");

        oscar.openAccount(account1);
        steve.openAccount(account2);

        // amount that will fail to transfer
        Money amountToTransfer = new Money("20");

        // deposit less than the amount attempting to be transferred
        account1.deposit(new Money("50"), Transaction.CUSTOMER);

        // try to transfer $20 from account1 to account2
        oscar.transfer(amountToTransfer, account1, account2);
    }
}
