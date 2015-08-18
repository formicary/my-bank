package test.java.com.abc;

import main.java.com.abc.Accounts.AccountBase;
import main.java.com.abc.Accounts.AccountFactory;
import main.java.com.abc.Accounts.AccountType;
import main.java.com.abc.Customer.Customer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void AccountsStatementTest(){

        AccountBase checkingAccount = AccountFactory.CreateAccount(AccountType.Checking);
        AccountBase savingsAccount = AccountFactory.CreateAccount(AccountType.Savings);

        Customer henry = new Customer("Henry");
        henry.OpenAccount(checkingAccount);
        henry.OpenAccount(savingsAccount);

        checkingAccount.Deposit(100.0);
        savingsAccount.Deposit(4000.0);
        savingsAccount.Withdraw(200.0);
        System.out.println(henry.GetAccountsStatement());
        
        assertEquals("Statement for Henry\n"+

					"\nChecking\n" +
					"  DEPOSITE SUCCESS : 100.000000\n"+
					"Total $100.00\n"+
					
					"\nSavings\n"+
					"  DEPOSITE SUCCESS : 4000.000000\n"+
					"  WITHDRAW SUCCESS : 200.000000\n"+
					"Total $3,800.00\n"+
					
					"\nTotal In All Accounts $3,900.00", henry.GetAccountsStatement());
    }

    @Test
    public void OneAccountTest(){
        Customer oscar = new Customer("Oscar");
        oscar.OpenAccount(AccountFactory.CreateAccount(AccountType.Checking));
        assertEquals(1, oscar.GetNumberOfAccounts());
    }

    @Test
    public void TwoAccountTest(){
        Customer oscar = new Customer("Oscar");
        
        oscar.OpenAccount(AccountFactory.CreateAccount(AccountType.Checking));
        oscar.OpenAccount(AccountFactory.CreateAccount(AccountType.Checking));
        assertEquals(2, oscar.GetNumberOfAccounts());
    }
    
}
