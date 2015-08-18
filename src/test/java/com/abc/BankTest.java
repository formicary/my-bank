package test.java.com.abc;

import static org.junit.Assert.*;
import main.java.com.abc.Bank.Bank;
import main.java.com.abc.Customer.Customer;

import org.junit.Test;


public class BankTest {

    
    @Test
    public void AddCustomerTest() {
    	System.out.println("######################################################\n" + "CustomerSummary Test\n");
        Bank bank = new Bank();

        Customer john = new Customer("John");
        Customer bob = new Customer("Bob");

        bank.addCustomer(john);
        bank.addCustomer(bob);
        
        assertEquals(bank.GetCustomerNum(), 2);

    }

    


}
