package com.abc.person;

import static org.junit.Assert.*;

import com.abc.constants.AccountConstantsNew;
import com.abc.organisation.Bank;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vahizan on 20/08/2017.
 */
public class CustomerTest {
    private Customer customer;
    private Bank bank;
    @Before
    public void setUp(){
        Surname surname = new Surname("Winterbury");
        GivenNames names = new GivenNames();
        names.addName("Johnathan");
        names.addName("Brian");
        Name name = new Name(names,surname);
        customer = new Customer(name);
        bank=new Bank();
        bank.addCustomer(customer);
    }
    //Check names are correctly created and printed
    @Test
    public void printGivenNamesCorrectly(){
        GivenNames names = new GivenNames();
        names.addName("Johnathan");
        names.addName("Brian");

        assertEquals(2, names.size());
        String name=names.toString();
        System.out.println(name);
        assertTrue(name.equals("Johnathan Brian"));
    }
    @Test
    public void printSurnameCorrectly(){
        Surname surname = new Surname("Winterbury");
        System.out.println(surname.toString());
        assertTrue(surname.toString().equals("Winterbury"));
    }
    @Test
    public void printFullNameCorrectly(){
        GivenNames names = new GivenNames();
        names.addName("Johnathan");
        names.addName("Brian");
        Surname surname = new Surname("Stratten");
        Name name = new Name(names,surname);
        System.out.println(name.toString());
        assertTrue(name.toString().equals("Johnathan Brian Stratten"));
    }
    @Test
    public void openSavingsAccount(){
        customer.openAccount(AccountConstantsNew.SAVINGS);
        assertEquals(1, customer.getNumberOfAccounts());
    }
    @Test
    public void openingTwoOfSameAccountNotAllowed(){
        customer.openAccount(AccountConstantsNew.SAVINGS);
        customer.openAccount(AccountConstantsNew.SAVINGS);
        assertEquals(1, customer.getNumberOfAccounts());
    }





}
