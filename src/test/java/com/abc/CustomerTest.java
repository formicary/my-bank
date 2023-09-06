package com.abc;

import com.abc.MainClasses.Customer;
import com.abc.AccountTypes.CheckingAccount;
import com.abc.AccountTypes.MaxiSavingsAccount;
import com.abc.AccountTypes.SavingsAccount;
import com.abc.MainClasses.Account;
import com.abc.MainClasses.Bank;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {
    //Test customer statement generation
    @Test
    public void OneCustomerStatement() {
        Bank bank = new Bank();
        Customer jack = new Customer("Jack");
        bank.addCustomer(jack);
        jack.openAccount(new CheckingAccount());

        assertEquals("Statement for Jack\nChecking Account\nTotal : $0.00\n\nTotal Of Jack's Accounts : $0.00", jack.getStatement()[0]);
    }

    @Test
    public void summaryForZeroCustomers() {
        Bank bank = new Bank();

        assertEquals("There isn't a customer registered with the bank.", bank.customerSummary());
    }

    @Test
    public void statementForAccount() {
        Customer james = new Customer("James");
        Account max_savings = new MaxiSavingsAccount();
        james.openAccount(max_savings);
        max_savings.deposit(150);

        assertEquals("Maxi Savings Account\n  deposit : $150.00\nTotal : $150.00", james.statementForAccount(max_savings));
    }

    @Test
    public void testGetStatement(){
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "Checking Account\n" +
                "  deposit : $100.00\n" +
                "Total : $100.00\n" +
                "Savings Account\n" +
                "  deposit : $4,000.00\n" +
                "  withdrawal : $200.00\n" +
                "Total : $3,800.00\n" +
                "\n" +
                "Total Of Henry's Accounts : $3,900.00", henry.getStatement()[0]);
    }

    @Test
    public void getFirstAndLastCustomer() {
        Bank bank = new Bank();
        bank.addCustomer(new Customer("Bill"));
        bank.addCustomer(new Customer("Jack"));
        bank.addCustomer(new Customer("Emily"));

        assertTrue("Get first customer : unexpected name", bank.getFirstCustomer().equals("Bill"));
        assertTrue("Get last customer : unexpected name", bank.getLastCustomer().equals("Emily"));
    }
}