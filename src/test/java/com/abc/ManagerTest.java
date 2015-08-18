package test.java.com.abc;

import static org.junit.Assert.*;
import main.java.com.abc.Accounts.AccountFactory;
import main.java.com.abc.Accounts.AccountType;
import main.java.com.abc.Bank.Bank;
import main.java.com.abc.Customer.Customer;
import main.java.com.abc.Managers.BankManager;

import org.junit.Test;

public class ManagerTest {
	
    @Test
    public void CustomerSummaryTest() {
    	System.out.println("######################################################\n" + "CustomerSummaryTest \n");
        Bank bank = new Bank();
    	BankManager manager = new BankManager(bank);

        Customer john = new Customer("John");
        Customer bob = new Customer("Bob");

        john.OpenAccount(AccountFactory.CreateAccount(AccountType.Checking));
        john.OpenAccount(AccountFactory.CreateAccount(AccountType.Savings));

        bank.addCustomer(john);
        bank.addCustomer(bob);
        
        assertEquals(bank.GetCustomerNum(), 2);

        System.out.println(manager.GetSummary());
        assertEquals("Customer Summary\n - John (2 accounts)\n - Bob (0 account)", manager.GetSummary());
    }

}
