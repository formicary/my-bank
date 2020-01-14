package com.abc;

// import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// The test of the customer class should:
// 1) Cover all public methods.
// 2) Test that a customer can open an account
// 3) Test that a customer can deposit / withdraw funds from an account
// Note: 3) Should not be tested here but in Accounts. 
// 4) Test that a customer can request a statement that shows transactions and
// totals for each of their accounts. 
public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;

    // Test the getName method. Also check that customers can be created
    @Test
    public void getNameTest() {
        Customer aang = new Customer("Aang");

        assertTrue("Customer should exist", (aang != null));
        assertEquals("Customer should have the name 'Aang'", "Aang", 
                                                    aang.getName());
    }

    // Test that a customer can open accounts. Also tests item 2) and the 
    // getNumberOfAccounts function. 
    @Test
    public void openAccountTest() {
        Customer katara = new Customer("Katara");
        CheckingAccount kataras_account_1 = new CheckingAccount();
        
        assertEquals("Customer should be initialised with 0 accounts",
                        0, katara.getNumberOfAccounts());

        katara.openAccount(kataras_account_1);

        assertEquals("Customer should have an account after it being opened",
                        1, katara.getNumberOfAccounts());

        for (int i = 0; i < 42; i++) {
            katara.openAccount(new SavingsAccount());
        }

        assertEquals("Customer should be able to open multiple accounts", 
                        43, katara.getNumberOfAccounts());
    }

    // Test that totalInterestEarned works as expected
    @Test
    public void totalInterestEarnedTest() {
        Customer toph = new Customer("Toph");
        CheckingAccount checking_account_1 = new CheckingAccount();
        toph.openAccount(checking_account_1);
        checking_account_1.deposit(91283.0);

        double total = 91283 * 0.001; 

        CheckingAccount checking_account_2 = new CheckingAccount();
        toph.openAccount(checking_account_2);
        checking_account_2.deposit(29292929292929292929.0);

        total += 29292929292929292929.0 * 0.001;

        assertEquals("totalInterestEarned should work as intended", 
                        total, toph.totalInterestEarned(), DOUBLE_DELTA);

    }

    @Test //Test customer statement generation. Also tests 4)
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer sokka = new Customer("Sokka").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Sokka\n" +
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
                "Total In All Accounts $3,900.00", sokka.generateStatement());
    }


}
