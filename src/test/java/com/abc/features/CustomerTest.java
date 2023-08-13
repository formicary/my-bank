package com.abc.features;

import org.junit.Ignore;
import org.junit.Test;

import com.abc.classes.Account;
import com.abc.classes.Account.AccountType;
import com.abc.helpers.CustomerStatementBuilder;
import com.abc.classes.Customer;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Customer henry = new Customer("Henry");

        Account checkingAccount = henry.openAccount(AccountType.CHECKING);
        Account savingsAccount = henry.openAccount(AccountType.SAVINGS);

        checkingAccount.tryDeposit(100.0);
        savingsAccount.tryDeposit(4000.0);
        savingsAccount.tryWithdraw(200.0);

        List<String> accountStatements = henry.getAllAccountStatements(henry);
        String comparisonString = CustomerStatementBuilder.concatenate(accountStatements,",");

        assertEquals("Henry,\n" +
                "Account Type: CHECKING\n" +
                "Balance: $100.00\n" +
                "Accrued Interest: $0.00\n" +
                ",\n" +
                "Account Type: SAVINGS\n" +
                "Balance: $3,800.00\n" +
                "Accrued Interest: $0.00\n"+
                ",\n" +
                "Total balance of all accounts: $3,900.00", comparisonString);
    }

    // @Test
    // public void testOneAccount(){
    //     Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
    //     assertEquals(1, oscar.getNumberOfAccounts());
    // }

    // @Test
    // public void testTwoAccount(){
    //     Customer oscar = new Customer("Oscar")
    //             .openAccount(new Account(Account.SAVINGS));
    //     oscar.openAccount(new Account(Account.CHECKING));
    //     assertEquals(2, oscar.getNumberOfAccounts());
    // }

    // @Ignore
    // public void testThreeAcounts() {
    //     Customer oscar = new Customer("Oscar")
    //             .openAccount(new Account(Account.SAVINGS));
    //     oscar.openAccount(new Account(Account.CHECKING));
    //     assertEquals(3, oscar.getNumberOfAccounts());
    // }
}
