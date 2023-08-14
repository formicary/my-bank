package com.abc.features;

import org.junit.Ignore;
import org.junit.Test;

import com.abc.classes.Account;
import com.abc.classes.Account.AccountType;
import com.abc.helpers.CustomerStatementBuilder;
import com.abc.classes.Customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Customer henry = new Customer("Henry");

        Account checkingAccount = henry.openAccount(AccountType.CHECKING);
        Account savingsAccount = henry.openAccount(AccountType.SAVINGS);

        checkingAccount.tryDeposit(100.0);
        savingsAccount.tryDeposit(4000.0);
        savingsAccount.tryWithdraw(200.0);

        // List<String> accountStatements = henry.getAllAccountStatements(henry);
        // String comparisonString = CustomerStatementBuilder.concatenate(accountStatements,",");

        // assertEquals("Henry,\n" +
        //         "Account Type: CHECKING\n" +
        //         "Balance: $100.00\n" +
        //         "Accrued Interest: $0.00\n" +
        //         ",\n" +
        //         "Account Type: SAVINGS\n" +
        //         "Balance: $3,800.00\n" +
        //         "Accrued Interest: $0.00\n"+
        //         ",\n" +
        //         "Total balance of all accounts: $3,900.00", comparisonString);

    //     assertEquals("Henry\r\n" +
    //             "Account Type: CHECKING\r\n" +
    //             "Balance: $100.00\r\n" +
    //             "Accrued Interest: $0.00\r\n" +
    //             "2023-08-14 10:16:55 (DEPOSIT): $100.00\r\n" +
    //             "Henry\r\n" + //
    //             "Account Type: SAVINGS\r\n" +
    //             "Balance: $3,800.00\r\n" +
    //             "Accrued Interest: $0.00\r\n" +
    //             "2023-08-14 10:16:55 (DEPOSIT): $4,000.00\r\n" +
    //             "2023-08-14 10:16:55 (WITHDRAWAL): $200.00", henry.getAllAccountStatements(henry));
    // }

    String text =
    "Henry\n" +
    "Account Type: CHECKING\n" +
    "Balance: $100.00\n" +
    "Accrued Interest: $0.00\n" +
    "2023-08-14 10:44:23 (DEPOSIT): $100.00\n" +
    "Henry\n" +
    "Account Type: SAVINGS\n" +
    "Balance: $3,800.00\n" +
    "Accrued Interest: $0.00\n" +
    "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}) \\(DEPOSIT\\): \\$\\d+(,\\d{3})*(\\.\\d{2})?\\n" +
    "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}) \\(WITHDRAWAL\\): \\$\\d+(,\\d{3})*(\\.\\d{2})?\\n";

    String actual = henry.getAllAccountStatements(henry);

        System.out.println(text);
        System.out.println(actual);

        Pattern pattern = Pattern.compile(text);
        Matcher matcher = pattern.matcher(actual);

        boolean foundMatch = false;
        while (matcher.find()) {
            String match = matcher.group();
            System.out.println(match);
            foundMatch = true;
        }

        assertTrue(foundMatch);

    

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
