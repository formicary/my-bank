package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.accounts.AccountFactory;
import com.accenture.intereststrategies.FlatRateInterest;
import com.accenture.intereststrategies.SavingsInterest;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// CUSTOMER CREATION VALIDATION LOGIC TESTS ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////// Age Tests //////////////////////////////////////

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerAgeMustBeSet() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Oscar").setSurname("Testerson").setAccounts(Collections.emptyList()).build();
    }

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerMustBe18OrOver() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Oscar").setSurname("Testerson").setAge(17).setAccounts(Collections.emptyList()).build();
    }

    @Test()
    public void customerCanBe18YearsOld() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Oscar").setSurname("Testerson").setAge(18).setAccounts(Collections.emptyList()).build();
    }

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerMustNotBeOver120() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Oscar").setSurname("Testerson").setAge(121).setAccounts(Collections.emptyList()).build();
    }

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerMustNotBeOver120_test2() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Oscar").setSurname("Testerson").setAge(200).setAccounts(Collections.emptyList()).build();
    }

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerAgeMustNotBeNegative() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Oscar").setSurname("Testerson").setAge(-1).setAccounts(Collections.emptyList()).build();
    }


    /////////////// Name Tests //////////////////////////////////////

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerMustHaveAFirstName_EXPECT_FAIL() {
        Customer.Builder builder = new Customer.Builder();
        builder.setSurname("Testerson").setAge(20).setAccounts(Collections.emptyList()).build();
    }

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerFirstNameCannotBeEmptyString_EXPECT_FAIL() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("").setSurname("Testerson").setAge(20).setAccounts(Collections.emptyList()).build();
    }

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerFirstNameCannotBe1Character_EXPECT_FAIL() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("a").setSurname("Testerson").setAge(20).setAccounts(Collections.emptyList()).build();
    }

    @Test()
    public void customerFirstNameCanBe1Character_EXPECT_PASS() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("ab").setSurname("Testerson").setAge(20).setAccounts(Collections.emptyList()).build();
    }


    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerMustHaveASurname_EXPECT_FAIL() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Bill").setAge(20).setAccounts(Collections.emptyList()).build();
    }

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerSurNameCannotBeEmptyString_EXPECT_FAIL() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Bill").setSurname("").setAge(20).setAccounts(Collections.emptyList()).build();
    }

    @Test(expected = Customer.InvalidCustomerConfigurationException.class)
    public void customerSurNameCannotBe1Character_EXPECT_FAIL() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Bill").setSurname("a").setAge(20).setAccounts(Collections.emptyList()).build();
    }

    @Test()
    public void customerFirstNameCanBe2Character_EXPECT_PASS() {
        Customer.Builder builder = new Customer.Builder();
        builder.setForename("Bill").setSurname("ab").setAge(20).setAccounts(Collections.emptyList()).build();
    }


    @Test
    public void customerBuilderReturnsExpectedCustomerObject() {
        Customer.Builder builder = new Customer.Builder();
        Customer customer = builder.setForename("Bill").setSurname("ab").setAge(20).build();

        assertEquals("Bill", customer.getForename());
        assertEquals("ab", customer.getSurname());
        assertEquals(20, customer.getAge());
        assertEquals(Collections.EMPTY_LIST, customer.getAccounts());
    }


    @Test
    public void customerCanOpenASingleSavingsAccount() {

        Customer.Builder builder = new Customer.Builder();
        Customer customer = builder.setForename("Oscar").setSurname("Testerson").setAge(22).setAccounts(Collections.emptyList()).build();
        customer.openAccount(AccountFactory.AccountType.SAVINGS);

        assertEquals(1, customer.getNumberOfAccounts());

        boolean isInstanceOfSavingAccount = customer.getAccounts().get(0).getInterestStrategy() instanceof SavingsInterest;
        assertEquals(true, isInstanceOfSavingAccount);
    }


    @Test
    public void customerCanOpenACheckingAccount() {

        Customer.Builder builder = new Customer.Builder();
        Customer customer = builder.setForename("Oscar").setSurname("Testerson").setAge(22).setAccounts(Collections.emptyList()).build();
        customer.openAccount(AccountFactory.AccountType.CHECKING);

        assertEquals(1, customer.getNumberOfAccounts());

        boolean isInstanceOfSavingAccount = customer.getAccounts().get(0).getInterestStrategy() instanceof FlatRateInterest;
        assertEquals(true, isInstanceOfSavingAccount);
    }

    @Test
    public void customerCanOpenTwoDifferentAccounts() {
        Customer.Builder builder = new Customer.Builder();
        Customer customer = builder.setForename("Oscar").setSurname("Testerson").setAge(22).setAccounts(Collections.emptyList()).build();

        customer.openAccount(AccountFactory.AccountType.SAVINGS);
        customer.openAccount(AccountFactory.AccountType.CHECKING);

        assertEquals(2, customer.getNumberOfAccounts());
    }


    @Test
    public void customerCanGetStatementForAllAccounts() {
        Customer.Builder builder = new Customer.Builder();
        Customer customer = builder.setForename("Oscar").setSurname("Testerson").setAge(22).build();

        customer.openAccount(AccountFactory.AccountType.SAVINGS);
        customer.openAccount(AccountFactory.AccountType.CHECKING);

        Account savingsAccount = customer.getAccounts().get(0);
        Account checkingAccount = customer.getAccounts().get(1);

        Transaction transaction1 = new Transaction.Builder().setType(Transaction.Type.INTEREST).setAmount(DollarAmount.fromInt(100)).setDescription("Test").build();
        Transaction transaction2 = new Transaction.Builder().setType(Transaction.Type.DEPOSIT).setAmount(DollarAmount.fromInt(100)).setDescription("Test").build();
        Transaction transaction3 = new Transaction.Builder().setType(Transaction.Type.INTEREST).setAmount(DollarAmount.fromInt(100)).setDescription("Test").build();
        savingsAccount.applyTransaction(transaction1);
        savingsAccount.applyTransaction(transaction2);
        checkingAccount.applyTransaction(transaction3);

        String expectedResult = "Statement for Oscar Testerson\n" +
                "Account Statement. \n" +
                "Transaction Type: INTEREST  Amount:100.00 USD\n" +
                "Transaction Type: DEPOSIT  Amount:100.00 USD\n" +
                "Current Balance: 200.00 USD\n" +
                "Account Statement. \n" +
                "Transaction Type: INTEREST  Amount:100.00 USD\n" +
                "Current Balance: 100.00 USD\n" +
                "\n" +
                "Total in all accounts: 300.00 USD";

        String actualResult = customer.generateStatement();

        assertEquals(expectedResult, actualResult);
    }


}