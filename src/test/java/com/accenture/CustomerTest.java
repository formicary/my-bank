package com.accenture;

import com.accenture.accounts.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Bank bank = new Bank();

        Account checkingAccount = new CheckingAccount(11111111);
        Account savingsAccount = new SavingsAccount(22222222);
        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);

        Customer henry = new Customer("Henry");
        bank.addCustomer(henry);
        bank.linkCustomerToAccount(henry,savingsAccount);
        bank.linkCustomerToAccount(henry,checkingAccount);

        checkingAccount.deposit(100.0,"Regular Deposit");
        savingsAccount.deposit(4000.0,"Regular Deposit");
        savingsAccount.withdraw(200.0);

        List<Account> linkedAccounts = bank.getCustomersAccount(henry);

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
                "Total In All Accounts $3,900.00", henry.getStatement(linkedAccounts));
    }

    @Test
    public void testOneAccount(){
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount(11111111);
        Customer oscar = new Customer("Oscar");

        bank.addCustomer(oscar);
        bank.addAccount(checkingAccount);
        bank.linkCustomerToAccount(oscar,checkingAccount);
        assertEquals(1, bank.getCustomersAccount(oscar).size());
    }

    @Test
    public void testTwoAccount(){
        Bank bank = new Bank();

        Customer oscar = new Customer("Oscar");
        Account checkingAccount = new CheckingAccount(11111111);
        Account checkingAccount2 = new CheckingAccount(22222222);

        bank.addCustomer(oscar);
        bank.addAccount(checkingAccount);
        bank.addAccount(checkingAccount2);
        bank.linkCustomerToAccount(oscar,checkingAccount);
        bank.linkCustomerToAccount(oscar,checkingAccount2);

        assertEquals(2, bank.getCustomersAccount(oscar).size());
    }

    @Test
    public void testThreeAccounts() {
        Bank bank = new Bank();

        Customer oscar = new Customer("Oscar");
        Account checkingAccount = new CheckingAccount(11111111);
        Account checkingAccount2 = new CheckingAccount(22222222);
        Account checkingAccount3 = new CheckingAccount(33333333);

        bank.addCustomer(oscar);
        bank.addAccount(checkingAccount);
        bank.addAccount(checkingAccount2);
        bank.addAccount(checkingAccount3);
        bank.linkCustomerToAccount(oscar,checkingAccount);
        bank.linkCustomerToAccount(oscar,checkingAccount2);
        bank.linkCustomerToAccount(oscar,checkingAccount3);

        assertEquals(3, bank.getCustomersAccount(oscar).size());
    }


    @Test
    public void testName() {

        Customer oscar = new Customer("Oscar");
        assertEquals("Oscar", oscar.getName());
    }






}
