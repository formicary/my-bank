package com.abc;

import com.abc.entity.Account;
import com.abc.entity.AccountType;
import com.abc.entity.Customer;
import com.abc.exception.InvalidAccountException;
import com.abc.exception.InvalidCustomerException;
import com.abc.service.CustomerService;
import com.abc.service.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static Customer customer;

    @Before
    public void setup(){
        customer = new Customer("Customer A");
    }

    @Test
    public void newCustomerHasZeroAccounts(){
        assertEquals("New customer does not have 0 accounts", 0, customer.getAccounts().size());
    }

    @Test(expected = InvalidCustomerException.class)
    public void customerCannotBeNullForNewAccount(){
        CustomerService.openAccount(null, new Account(AccountType.CURRENT));
    }

    @Test(expected = InvalidAccountException.class)
    public void accountCannotBeNullForNewAccount(){
        CustomerService.openAccount(customer, null);
    }

    @Test
    public void customerCanAddCheckingAccount(){
        CustomerService.openAccount(customer, new Account(AccountType.CURRENT));
        assertEquals("Customer checking account is not added to account list", 1, customer.getAccounts().size());
    }

    @Test
    public void customerCanAddSavingsAccount(){
        CustomerService.openAccount(customer,new Account(AccountType.SAVINGS));
        assertEquals("Customer savings account is not added to account list", 1, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMaxiSavingsAccount(){
        CustomerService.openAccount(customer,new Account(AccountType.MAXI_SAVINGS));
        assertEquals("Customer maxi savings account is not added to account list", 1, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleCheckingAccount(){
        CustomerService.openAccount(customer,new Account(AccountType.CURRENT));
        CustomerService.openAccount(customer,new Account(AccountType.CURRENT));
        assertEquals("Mutliple customer checking accounts are not added to account list", 2, customer.getAccounts().size());
    }

    @Test
    public void customerCanAddMultipleSavingsAccount(){
        CustomerService.openAccount(customer,new Account(AccountType.SAVINGS));
        CustomerService.openAccount(customer,new Account(AccountType.SAVINGS));
        assertEquals("Multiple customer savings accounts are not added to account list", 2, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleMaxiSavingsAccount(){
        CustomerService.openAccount(customer,new Account(AccountType.MAXI_SAVINGS));
        CustomerService.openAccount(customer,new Account(AccountType.MAXI_SAVINGS));
        assertEquals("Multiple customer maxi savings accounts are not added to account list", 2, customer.getAccounts().size());

    }

    @Test
    public void customerCanAddMultipleAccountsOfDifferentTypes(){
        CustomerService.openAccount(customer,new Account(AccountType.CURRENT));
        CustomerService.openAccount(customer,new Account(AccountType.SAVINGS));
        CustomerService.openAccount(customer,new Account(AccountType.MAXI_SAVINGS));
        assertEquals("Multiple accounts of different types are not added to account list", 3, customer.getAccounts().size());

    }

    @Test
    public void newAccountHasZeroBalance(){
        Account account = new Account(AccountType.CURRENT);
        CustomerService.openAccount(customer, account);
        assertEquals("", 0.0, TransactionManager.sumTransactions(account));
    }
    //customer can deposit funds

    /*
    new account has nothing in it
    adding cash in will add money to account
    adding cash twice will add money twice
    adding 0 will throw exception
    adding negative will throw exception


     */

    //customer can withdraw funds



//    @Test //Test customer statement generation
//    public void testApp(){
//
//        Account checkingAccount = new Account(Account.CHECKING);
//        Account savingsAccount = new Account(Account.SAVINGS);
//
//        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
//
//        checkingAccount.deposit(100.0);
//        savingsAccount.deposit(4000.0);
//        savingsAccount.withdraw(200.0);
//
//        assertEquals("Statement for Henry\n" +
//                "\n" +
//                "Checking Account\n" +
//                "  deposit $100.00\n" +
//                "Total $100.00\n" +
//                "\n" +
//                "Savings Account\n" +
//                "  deposit $4,000.00\n" +
//                "  withdrawal $200.00\n" +
//                "Total $3,800.00\n" +
//                "\n" +
//                "Total In All Accounts $3,900.00", henry.getStatement());
//    }
//
//    @Test
//    public void testOneAccount(){
//        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
//        assertEquals(1, oscar.getNumberOfAccounts());
//    }
//
//    @Test
//    public void testTwoAccount(){
//        Customer oscar = new Customer("Oscar")
//                .openAccount(new Account(Account.SAVINGS));
//        oscar.openAccount(new Account(Account.CHECKING));
//        assertEquals(2, oscar.getNumberOfAccounts());
//    }
//
//    @Ignore
//    public void testThreeAcounts() {
//        Customer oscar = new Customer("Oscar")
//                .openAccount(new Account(Account.SAVINGS));
//        oscar.openAccount(new Account(Account.CHECKING));
//        assertEquals(3, oscar.getNumberOfAccounts());
//    }
}
