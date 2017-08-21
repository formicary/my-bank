package com.abc;

import com.abc.constants.AccountConstantsNew;
import com.abc.organisation.Bank;
import com.abc.person.*;
import com.abc.rates.Band;
import com.abc.rates.Interest;
import com.abc.rates.InterestRate;
import com.abc.utilities.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vahizan on 08/08/2017.
 */
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Bank bank;
    private BankManager bankManager;

    @Before
    public void setUp(){
        bank = new Bank();
        Surname surname = new Surname("Brown");
        GivenNames names = new GivenNames();
        names.addName("Simon");
        names.addName("William");
        Name name = new Name(names,surname);
        bankManager = new BankManager(name);
    }
    @Test
    public void createCustomer(){

    }

    @Test
    public void createEmployee(){
        bank.addEmployee(bankManager);
        assertEquals(1,bank.employeeCount());
    }

    @Test
    public void reportOnCustomersShouldBeUnavailableAsNoCustomersExist(){
        //Bank can provide customer information to certain employees
        bankManager.requestCustomers(bank.customerInformation());
        System.out.println(bankManager.customerReport());
    }

    @Test
    public void reportOnCustomersShouldShowDetailsOfOneCustomer(){
        //Bank can provide customer information to certain employees
        bankManager.requestCustomers(bank.customerInformation());

        //Create and add customer
        Surname surname = new Surname("Winterbury");
        GivenNames names = new GivenNames();
        names.addName("Johnathan");
        names.addName("Brian");
        Name name = new Name(names,surname);
        Customer customer = new Customer(name);
        bank.addCustomer(customer);

        System.out.println(bankManager.customerReport());
    }


    //Pay Daily Interest To Customer
    //Interest Paid Must Be Shown On Interest Report
    @Test
    public void reportOnInterestPaidInOneCustomerAccounts(){
        //Bank can provide customer information to certain employees
        bankManager.requestCustomers(bank.customerInformation());

        //Create and add customer
        Surname surname = new Surname("Winterbury");
        GivenNames names = new GivenNames();
        names.addName("Johnathan");
        names.addName("Brian");
        Name name = new Name(names,surname);
        Customer customer = new Customer(name);
        bank.addCustomer(customer);

        customer.openAccount(AccountConstantsNew.SAVINGS);

        Band band = new Band();
        InterestRate rate = new InterestRate(0.1f,AccountConstantsNew.SAVINGS);
        band.addBand(new Money(0), rate);
        InterestRate rateOverThousand = new InterestRate(0.2f,AccountConstantsNew.SAVINGS);
        band.addBand(new Money(1000), rateOverThousand);
        Interest interest= new Interest(rate,band);
        bank.payInterest(interest,AccountConstantsNew.SAVINGS);

        customer.depositMoney(AccountConstantsNew.SAVINGS, new Money(3000));
        bank.payInterest(interest, AccountConstantsNew.SAVINGS);

        System.out.println(bankManager.interestPaidReport());
    }

    @Test
    public void reportOnInterestPaidInTwoCustomerAccounts(){
        //Bank can provide customer information to certain employees
        bankManager.requestCustomers(bank.customerInformation());

        //Create and add customer
        Surname surname = new Surname("Winterbury");
        GivenNames names = new GivenNames();
        names.addName("Johnathan");
        names.addName("Brian");
        Name name = new Name(names,surname);
        Customer customer = new Customer(name);
        bank.addCustomer(customer);

        //Create and add another customer
        Surname surnameTwo = new Surname("Clarkson");
        GivenNames namesTwo = new GivenNames();
        namesTwo.addName("Barry");
        namesTwo.addName("Sidwell");
        Name nameTwo = new Name(namesTwo,surnameTwo);
        Customer customerTwo = new Customer(nameTwo);
        bank.addCustomer(customerTwo);

        customer.openAccount(AccountConstantsNew.SAVINGS);
        customerTwo.openAccount(AccountConstantsNew.SAVINGS);

        Band band = new Band();
        InterestRate rate = new InterestRate(0.1f,AccountConstantsNew.SAVINGS);
        band.addBand(new Money(0), rate);
        InterestRate rateOverThousand = new InterestRate(0.2f,AccountConstantsNew.SAVINGS);
        band.addBand(new Money(1000), rateOverThousand);
        Interest interest= new Interest(rate,band);
        bank.payInterest(interest,AccountConstantsNew.SAVINGS);

        customer.depositMoney(AccountConstantsNew.SAVINGS, new Money(3000));
        customerTwo.depositMoney(AccountConstantsNew.SAVINGS, new Money(3000));
        bank.payInterest(interest, AccountConstantsNew.SAVINGS);

        System.out.println(bankManager.interestPaidReport());
    }

    @Test
    public void reportOnInterestPaidInTwoDifferentCustomerAccounts(){
        //Bank can provide customer information to certain employees
        bankManager.requestCustomers(bank.customerInformation());

        //Create and add customer
        Surname surname = new Surname("Winterbury");
        GivenNames names = new GivenNames();
        names.addName("Johnathan");
        names.addName("Brian");
        Name name = new Name(names,surname);
        Customer customer = new Customer(name);
        bank.addCustomer(customer);

        //Create and add another customer
        Surname surnameTwo = new Surname("Clarkson");
        GivenNames namesTwo = new GivenNames();
        namesTwo.addName("Barry");
        namesTwo.addName("Sidwell");
        Name nameTwo = new Name(namesTwo,surnameTwo);
        Customer customerTwo = new Customer(nameTwo);
        bank.addCustomer(customerTwo);

        customer.openAccount(AccountConstantsNew.MAXI_SAVINGS);
        customerTwo.openAccount(AccountConstantsNew.SAVINGS);

        Band band = new Band();
        InterestRate rate = new InterestRate(0.1f,AccountConstantsNew.SAVINGS);
        band.addBand(new Money(0), rate);
        InterestRate rateOverThousand = new InterestRate(0.2f,AccountConstantsNew.SAVINGS);
        band.addBand(new Money(1000), rateOverThousand);
        Interest interest= new Interest(rate,band);
        bank.payInterest(interest, AccountConstantsNew.SAVINGS);


        Band bandTwo = new Band();
        InterestRate rateOverThousandTwo = new InterestRate(0.2f,AccountConstantsNew.MAXI_SAVINGS);
        bandTwo.addBand(new Money(1000), rateOverThousandTwo);
        InterestRate rateTwo = new InterestRate(5.0f,AccountConstantsNew.MAXI_SAVINGS);
        bandTwo.addBand(new Money(2000), rateTwo);
        InterestRate rateThree = new InterestRate(10.0f,AccountConstantsNew.MAXI_SAVINGS);
        bandTwo.addBand(new Money(3000), rateThree);
        Interest interestTwo= new Interest(rateTwo,bandTwo);
        bank.payInterest(interestTwo,AccountConstantsNew.MAXI_SAVINGS);

        customer.depositMoney(AccountConstantsNew.MAXI_SAVINGS, new Money(3000));
        customerTwo.depositMoney(AccountConstantsNew.SAVINGS, new Money(3000));

        //Pay interest to savings and maxi savings account
        bank.payInterest(interest, AccountConstantsNew.SAVINGS);
        bank.payInterest(interestTwo,AccountConstantsNew.MAXI_SAVINGS);

        System.out.println(bankManager.interestPaidReport());
    }



}
