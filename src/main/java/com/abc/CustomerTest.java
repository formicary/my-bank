package com.abc;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 0.001;
    @Test //Test customer statement generation
    public void testApp(){
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Account checkingAccount = new Checking_Account(henry,0, UUID.randomUUID());
        Account savingsAccount = new Savings_Account(henry,0,UUID.randomUUID());
//        com.abc.Account checkingAccount = new com.abc.Checking_Account(henry,0, UUID.randomUUID());

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry James\n" +
                "\n" +
                "Checking com.abc.Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings com.abc.Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Account checkingAccount = new Checking_Account(henry,0, UUID.randomUUID());
        assertEquals(1, henry.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Account checkingAccount = new Checking_Account(henry,0, UUID.randomUUID());
        Account savingsAccount = new Savings_Account(henry,0,UUID.randomUUID());
        assertEquals(2, henry.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Account checkingAccount = new Checking_Account(henry,0, UUID.randomUUID());
        Account savingsAccount = new Savings_Account(henry,0,UUID.randomUUID());
        Account maxisavingsAccount = new Maxi_Savings_Account(henry,2,UUID.randomUUID());
        assertEquals(3, henry.getNumberOfAccounts());
    }

    @Test
    public void TestTotalInterestEarnedDaily(){
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Account checkingAccount = new Checking_Account(henry,1000, UUID.randomUUID());
        assertEquals(0.002,henry.totalInterestEarnedDaily(),DOUBLE_DELTA);
    }
    @Test
    public  void testTotalInterestEarnedYearly(){
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Account checkingAccount = new Checking_Account(henry,1000, UUID.randomUUID());
        assertEquals(1,henry.totalInterestEarnedYearly(),DOUBLE_DELTA);
    }
    @Test
    public void CheckTransfer(){
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Account checkingAccount = new Checking_Account(henry,1000, UUID.randomUUID());
        henry.transferInterestEarnedOfCustomer();
        assertEquals(1000.002,henry.getAccounts().iterator().next().getAmount(),DOUBLE_DELTA);

    }

    @Test
    public void checkTransferBetweenAccounts(){
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Account checkingAccount = new Checking_Account(henry,100, UUID.randomUUID());
        Account savingsAccount = new Savings_Account(henry,50,UUID.randomUUID());
        henry.transfer(checkingAccount,savingsAccount,100.0);
        assertEquals(0.0,checkingAccount.getAmount(),DOUBLE_DELTA);
        assertEquals(150.0,savingsAccount.getAmount(),DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTransferBetweenTwoAccountsWithDifferentOwner() {
        //create two different customers
        Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
        Customer jack = new Customer("Jack","James","52 Abbey road","07656261464",30,"ASFA6ADG65");
        //create an account for each customer
        Account checkingAccount1 = new Checking_Account(henry,100, UUID.randomUUID());
        Account checkingAccount2 = new Checking_Account(jack,100, UUID.randomUUID());
        // Attempt to transfer money from an account
        henry.transfer(checkingAccount1,checkingAccount2,100.0);
    }

}

