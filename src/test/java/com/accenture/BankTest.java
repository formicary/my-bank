package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.accounts.AccountFactory.AccountType;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {

    @Test
    public void bankManagerCanGetReportShowingCustomersAndTheirNumberOfAccounts_1Customer_1Account() {
        Customer customer = new Customer.Builder().setAge(20).setForename("testy").setSurname("mcTesty").setAccounts(Collections.emptyList()).build();

        customer.openAccount(AccountType.SAVINGS);

        Bank bank = new Bank();
        bank.addCustomer(customer);

        String actualResult = bank.customerSummary();

        String expectedResult = "Customer Summary\n" +
                " - testy mcTesty (1 account)";

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void bankManagerCanGetReportShowingCustomersAndTheirNumberOfAccounts_3Customers_variableAccounts() {

        Customer customer1 = new Customer.Builder().setAge(20).setForename("testy").setSurname("mcTesty").build();
        Customer customer2 = new Customer.Builder().setAge(20).setForename("Bill").setSurname("Billerson").build();
        Customer customer3 = new Customer.Builder().setAge(20).setForename("Ted").setSurname("Tedderson").build();

        customer1.openAccount(AccountType.SAVINGS);
        customer1.openAccount(AccountType.CHECKING);

        customer2.openAccount(AccountType.MAXI_SAVINGS);
        customer2.openAccount(AccountType.SAVINGS);
        customer2.openAccount(AccountType.MAXI_SAVINGS);

        customer3.openAccount(AccountType.SAVINGS);

        Bank bank = new Bank();
        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        bank.addCustomer(customer3);

        String expectedResult = "Customer Summary\n" +
                " - testy mcTesty (2 accounts)\n" +
                " - Bill Billerson (3 accounts)\n" +
                " - Ted Tedderson (1 account)";


        String actualResult = bank.customerSummary();

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void bankCanApplyInterestToAccounts() {

        Bank bank = new Bank();

        Customer customer1 = new Customer.Builder().setAge(20).setForename("testy").setSurname("mcTesty").build();
        Customer customer2 = new Customer.Builder().setAge(20).setForename("Bill").setSurname("Billerson").build();

        customer1.openAccount(AccountType.CHECKING);
        customer2.openAccount(AccountType.SAVINGS);

        Account account = customer1.getAccounts().get(0);
        Account account2 = customer2.getAccounts().get(0);

        Transaction transaction = new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(125))
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .build();


        Transaction transaction2 = new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(1000))
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .build();

        account.applyTransaction(transaction);
        account2.applyTransaction(transaction2);


        bank.applyDailyInterest();
    }


    @Test
    public void customerCanBeAdded() {

        Bank bank = new Bank();
        Customer customer = new Customer.Builder().setAge(20).setForename("testy").setSurname("mcTesty").build();

        assertEquals(0, bank.getTotalCustomers());
        bank.addCustomer(customer);

        assertEquals(1, bank.getTotalCustomers());
    }


    @Test
    public void bankCanDeleteCustomer() {

        Bank bank = new Bank();
        Customer customer = new Customer.Builder().setAge(20).setForename("testy").setSurname("mcTesty").build();
        bank.addCustomer(customer);
        assertEquals(1, bank.getTotalCustomers());

        bank.deleteCustomer(customer);

        assertEquals(0, bank.getTotalCustomers());
    }


    @Test(expected = IllegalArgumentException.class)
    public void bankCannotDeleteCustomerThanIsNotAlreadyACustomer() {
        Bank bank = new Bank();
        Customer NOTaCustomerOfTheBack = new Customer.Builder().setAge(20).setForename("testy").setSurname("Not member").build();
        bank.deleteCustomer(NOTaCustomerOfTheBack);
    }


    @Test(expected = IllegalArgumentException.class)
    public void bankDeletesTheCorrectCustomer() {
        Bank bank = new Bank();
        Customer customer1 = new Customer.Builder().setAge(20).setForename("testy").setSurname("Not member").build();
        Customer customer2 = new Customer.Builder().setAge(20).setForename("testy").setSurname("Not member").build();
        Customer customer3 = new Customer.Builder().setAge(20).setForename("testy").setSurname("Not member").build();
        Customer customer4 = new Customer.Builder().setAge(20).setForename("testy").setSurname("Not member").build();
        Customer customer5 = new Customer.Builder().setAge(20).setForename("testy").setSurname("Not member").build();
        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        bank.addCustomer(customer3);
        bank.addCustomer(customer4);
        bank.addCustomer(customer5);
        assertEquals(5, bank.getTotalCustomers());

        bank.deleteCustomer(customer3);
        assertEquals(4, bank.getTotalCustomers());
        bank.deleteCustomer(customer3);
    }


    @Test
    public void bankCanGetTotalNumberOfOpenAccounts() {
        Bank bank = new Bank();

        assertEquals(0, bank.getNumberOfOpenAccounts());

        Customer customer = new Customer.Builder().setAge(20).setForename("testy").setSurname("mcTesty").build();
        customer.openAccount(AccountType.SAVINGS);
        bank.addCustomer(customer);

        assertEquals(1, bank.getNumberOfOpenAccounts());
    }

}
