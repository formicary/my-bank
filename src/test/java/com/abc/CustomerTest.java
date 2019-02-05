package test.java.com.abc;

import org.junit.Ignore;
import org.junit.Test;

import main.java.com.abc.Account;
import main.java.com.abc.Bank;
import main.java.com.abc.Customer;
import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Account savingsAccount = new Account(Account.accountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

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

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.accountType.CHECKING));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.accountType.CHECKING));
        oscar.openAccount(new Account(Account.accountType.SAVINGS));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.accountType.CHECKING));
        oscar.openAccount(new Account(Account.accountType.SAVINGS));
        oscar.openAccount(new Account(Account.accountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransferInsufficientFunds(){
    	
    	Customer oscar = new Customer("Oscar");
    	oscar.openAccount(new Account(Account.accountType.CHECKING));	//Account a
    	oscar.openAccount(new Account(Account.accountType.SAVINGS));	//Account b
    	
    	Bank bank= new Bank();
    	bank.addCustomer(oscar);
    	
    	Account a = oscar.getCustomerAccounts(0);
    	Account b = oscar.getCustomerAccounts(1);
    	
    	//Expected $100 in Account A (Checking) and $0 in Account B (Savings)
    	a.deposit(100);
    	oscar.Transfer(150, a, b);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransferNonExistentAccount(){
    	
    	Customer oscar = new Customer("Oscar");
    	oscar.openAccount(new Account(Account.accountType.CHECKING));	//Account a
    	oscar.openAccount(new Account(Account.accountType.SAVINGS));	//Account b
    	
    	Bank bank= new Bank();
    	bank.addCustomer(oscar);
    	
    	Account a = oscar.getCustomerAccounts(0);
    	Account b = oscar.getCustomerAccounts(2);
    	
    	//Expected $100 in Account A (Checking) and $0 in Account B (Savings)
    	a.deposit(200);
    	oscar.Transfer(150, a, b);
    }
    
    @SuppressWarnings("deprecation")
	@Test
    public void testTransfer(){
    	
    	Customer oscar = new Customer("Oscar");
    	oscar.openAccount(new Account(Account.accountType.CHECKING));	//Account a
    	oscar.openAccount(new Account(Account.accountType.SAVINGS));	//Account b
    	
    	Bank bank= new Bank();
    	bank.addCustomer(oscar);
    	
    	Account a = oscar.getCustomerAccounts(0);
    	Account b = oscar.getCustomerAccounts(1);
    	
    	//Expected $100 in Account A (Checking) and $0 in Account B (Savings)
    	a.deposit(200);
    	oscar.Transfer(150, a, b);
    	
    	assertEquals(50, a.sumTransactions());
    	assertEquals(150, b.sumTransactions());
    }
}
