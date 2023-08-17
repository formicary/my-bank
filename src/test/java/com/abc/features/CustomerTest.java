package com.abc.features;

import org.junit.Test;

import com.abc.classes.Account;
import com.abc.classes.Account.AccountType;
import com.abc.classes.Customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    // Testing customer statement generation
    @Test
    public void getAllAccountStatementsTest() {
        // Create a customer with name 'Henry'
        Customer henry = new Customer("Henry");

        // Open a 'CHECKING' and 'SAVINGS' account for Henry
        Account checkingAccount = henry.openAccount(AccountType.CHECKING);
        Account savingsAccount = henry.openAccount(AccountType.SAVINGS);

        // Make various transactions
        checkingAccount.tryDeposit(100.0);
        savingsAccount.tryDeposit(4000.0);
        savingsAccount.tryWithdraw(200.0);

        // Declare expected outcomes
        String expectedsubString1 = "(IN): $100.00";
        String expectedsubString2 = "(IN): $4,000.00";
        String expectedsubString3 = "(OUT): $200.00";

        // Get account statements
        String actual = henry.getAllAccountStatements(henry);

        System.out.println(actual);
        boolean containsAllSubstrings = false;

        // Check to see if actual statement contains expected transactions
        if (actual.contains(expectedsubString1) && actual.contains(expectedsubString2)
                && actual.contains(expectedsubString3)) {
            containsAllSubstrings = true;
        }

        assertTrue(containsAllSubstrings);
    }

    // Test to ensure an accounts can be opened
    @Test
    public void openOneAccountTest() {
        // Create a customer with the Name 'Oscar'
        Customer oscar = new Customer("Oscar");
        // Open one 'SAVINGS' account
        oscar.openAccount(AccountType.SAVINGS);

        // Check to see if only one account has been opened
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    // Test to ensure multiple accounts can be opened
    @Test
    public void openMultipleAccountsTest() {
        // Create a customer with the Name 'Oscar'
        Customer oscar = new Customer("Oscar");

        // Open multiple accounts of different types
        oscar.openAccount(AccountType.SAVINGS);
        oscar.openAccount(AccountType.CHECKING);
        oscar.openAccount(AccountType.MAXI_SAVINGS);

        // Check to see if multiple accounts have been created
        assertTrue(oscar.getNumberOfAccounts() > 1);
    }

    // Test to ensure balance can be transferred to different accounts
    @Test
    public void transferBetweenAccountsTest() {
        // Create a customer with the Name 'Oscar'
        Customer oscar = new Customer("Oscar");

        // Open two accounts, desposit '100' into each
        Account savings = oscar.openAccount(AccountType.SAVINGS);
        savings.tryDeposit(100);
        Account checking = oscar.openAccount(AccountType.CHECKING);
        checking.tryDeposit(100);

        // Transfer 20 from 'savings' to 'checkings'
        oscar.transferBetweenAccounts(20, savings, checking);

        // Check to see if the transfer went through
        assertTrue(savings.getBalance() == 80 && checking.getBalance() == 120);
    }
}
