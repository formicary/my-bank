package com.abc;

import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class BankTest {

    @Test
    public void testAddCustomer() {
        Bank testBank = new Bank("My testBank");

        Customer Arnold = new Customer("Arnold");
        Customer Louie = new Customer("Louie");

        testBank.addCustomer(Arnold);

        testBank.addCustomer(Louie);
        assertEquals(2,testBank.numberOfCustomers());
    }

    @Test
    public void customerSummary() {
        Bank testBank = new Bank("My testBank");

        Customer Arnold = new Customer("Arnold");
        Customer Louie = new Customer("Louie");

        testBank.addCustomer(Arnold);

        testBank.addCustomer(Louie);

        Arnold.openNewAccount(AccountType.CHECKING, "Arnold's Checking account");
        Arnold.openNewAccount(AccountType.SAVINGS, "Arnold's Savings account");
        Arnold.openNewAccount(AccountType.MAXI_SAVINGS, "Arnold's Maxi_saving account");
        Arnold.depositIntoAccount(10,"Arnold's Checking account");
        Arnold.depositIntoAccount(20,"Arnold's Checking account");
        Arnold.withDrawFromAccount(20,"Arnold's Checking account");
        Arnold.depositIntoAccount(400,"Arnold's Savings account");
        Arnold.withDrawFromAccount(400,"Arnold's Savings account", Instant.now().plus(50, ChronoUnit.DAYS));


        Louie.openNewAccount(AccountType.CHECKING, "Louie's Checking account");
        Louie.openNewAccount(AccountType.SAVINGS, "Louie's Savings account");
        Louie.openNewAccount(AccountType.MAXI_SAVINGS, "Louie's Maxi_saving account");
        Louie.depositIntoAccount(10, "Louie's Checking account");
        Louie.depositIntoAccount(20, "Louie's Checking account");
        Louie.withDrawFromAccount(20, "Louie's Checking account");
        Louie.depositIntoAccount(400, "Louie's Savings account");
        Louie.withDrawFromAccount(400, "Louie's Savings account", Instant.now().plus(50, ChronoUnit.DAYS));

        String expectedCustomerSummary = "Customer Summary for My testBank\n2 Customers\n - Arnold (3 accounts)"+
                                        "\n - Louie (3 accounts)";
        assertEquals(expectedCustomerSummary,testBank.customerSummary());

    }

    @Test
    public void testTotalInterestPaid() {

        Bank testBank = new Bank("My testBank");

        Customer Arnold = new Customer("Arnold");
        Customer Louie = new Customer("Louie");

        testBank.addCustomer(Arnold);

        testBank.addCustomer(Louie);

        Arnold.openNewAccount(AccountType.CHECKING, "Arnold's Checking account");
        Arnold.openNewAccount(AccountType.SAVINGS, "Arnold's Savings account");
        Arnold.openNewAccount(AccountType.MAXI_SAVINGS, "Arnold's Maxi_saving account");
        Arnold.depositIntoAccount(10,"Arnold's Checking account");
        Arnold.depositIntoAccount(20,"Arnold's Checking account");
        Arnold.withDrawFromAccount(20,"Arnold's Checking account");
        Arnold.depositIntoAccount(400,"Arnold's Savings account");
        Arnold.withDrawFromAccount(400,"Arnold's Savings account", Instant.now().plus(50, ChronoUnit.DAYS));


        Louie.openNewAccount(AccountType.CHECKING, "Louie's Checking account");
        Louie.openNewAccount(AccountType.SAVINGS, "Louie's Savings account");
        Louie.openNewAccount(AccountType.MAXI_SAVINGS, "Louie's Maxi_saving account");
        Louie.depositIntoAccount(10, "Louie's Checking account");
        Louie.depositIntoAccount(20, "Louie's Checking account");
        Louie.withDrawFromAccount(20, "Louie's Checking account");
        Louie.depositIntoAccount(400, "Louie's Savings account");
        Louie.withDrawFromAccount(400, "Louie's Savings account", Instant.now().plus(50, ChronoUnit.DAYS));

        assertEquals(0.10959639739508567,testBank.totalInterestPaid(),1e-9);



    }

}
