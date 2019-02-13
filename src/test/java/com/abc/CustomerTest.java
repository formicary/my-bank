package com.abc;

import org.junit.Test;

import com.abc.Account.AccountType;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testGetStatementForCustomer(){
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

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
    
    @Test //Test customer statement generation maxi account
    public void testGetStatementForCustomerMaxiAccount(){
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer henry = new Customer("Henry");
        henry.openAccount(maxiSavingsAccount);

        maxiSavingsAccount.deposit(100.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Total In All Accounts $100.00", henry.getStatement());
    }
    
    @Test
    public void testEmptyAccount(){
        Customer oscar = new Customer("Oscar");
        assertEquals(0, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testDeposit() {
		Customer oscar = new Customer("Oscar");
		oscar.openAccount(new Account(AccountType.SAVINGS));
		oscar.getAccounts().get(0).deposit(100.0);
		assertEquals(100, (int) oscar.getAccounts().get(0).getTransactions().get(0).getAmount());
    }
    
    @Test
    public void testWidthdraw() {
    	Customer oscar = new Customer("Oscar");
		oscar.openAccount(new Account(AccountType.SAVINGS));
		oscar.getAccounts().get(0).withdraw(100.0);
		assertEquals(-100, (int) oscar.getAccounts().get(0).getTransactions().get(0).getAmount());
    }
    
    @Test
    public void moneyTransfer() {
    	Customer oscar = new Customer("Oscar");
		oscar.openAccount(new Account(AccountType.CHECKING));
		oscar.openAccount(new Account(AccountType.SAVINGS));
		oscar.getAccounts().get(0).deposit(200.0);
		oscar.moneyTransfer(0, 1, 100.0);
		assertEquals(100, (int)oscar.getAccounts().get(0).sumTransactions());
		assertEquals(100, (int)oscar.getAccounts().get(1).sumTransactions());
    }
    
	@Test(expected = IllegalArgumentException.class)
    public void moneyTransferThowsIllegalArgument() {
    	Customer oscar = new Customer("Oscar");
		oscar.openAccount(new Account(AccountType.CHECKING));
		oscar.openAccount(new Account(AccountType.SAVINGS));
		oscar.getAccounts().get(0).deposit(200.0);
		oscar.moneyTransfer(0, 1, 0);
    }
}
