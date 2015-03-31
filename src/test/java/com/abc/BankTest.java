package com.abc;

import static java.lang.Math.abs;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author R. Fei
 */
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    public BankTest() {
    }
    /**
     * Test of addCustomer, customerSummary method, of class Bank.
     */
    @Test
    public void testCustomerSummary() {
        System.out.println("Customer Summary");
        Bank instance = new Bank();
        
        String expResult0 = "No Customer Found!";
        String result0 = instance.customerSummary();
        assertEquals(expResult0, result0);
        
        Customer john = new Customer("John");
        Customer henry = new Customer("Henry");
        
        john.openAccount(new AccountChecking());
        john.openAccount(new AccountSavings());
        
        henry.openAccount(new AccountChecking());
        
        instance.addCustomer(john);
        instance.addCustomer(henry);
        
        String expResult1 = "Customer Summary\n - John (2 accounts)\n"
                                                      +" - Henry (1 account)\n";
        String result1 = instance.customerSummary();
        assertEquals(expResult1, result1);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomers, totalInterestPaid method, of class Bank.
     */
    @Test
    public void testTotalInterestPaid() {
        System.out.println("Total Interest Paid");
        Bank instance = new Bank();
        //Interest to be paid when no accounts exist with the bank
        double expResult00 = 0.0;
        double result00 = instance.totalInterestPaid();
        String expResult10 = toDollars(expResult00);
        String result10 = instance.reportTotalInterestPaid();
        //Interest to be paid when there are several accounts with the bank
        Customer john = new Customer("John");
        Customer henry = new Customer("Henry");
        
        testAccount tChecking = new AccountChecking();
        testAccount tSavings = new AccountSavings();
        
        Date Date0_1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date Date0_2 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        tChecking.depositwithDate(1500, Date0_1);
        tChecking.withdrawwithDate(500, Date0_2);
        
        Date Date1_1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date Date1_2 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        tSavings.depositwithDate(1500, Date1_1);        
        tSavings.withdrawwithDate(800, Date1_2);
        
        john.openAccount(tChecking);
        john.openAccount(tSavings);
        
        testAccount tMaxiSavings = new AccountMaxiSavings();
        Date Date2_1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date Date2_2 = new Date(115,0,20); //"2015-01-20", "yyyy-MM-dd"
        Date Date2_3 = new Date(115,0,25); //"2015-01-25", "yyyy-MM-dd"
        Date Date2_4 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        tMaxiSavings.depositwithDate(1500, Date2_1);
        tMaxiSavings.depositwithDate(500, Date2_2);
        tMaxiSavings.withdrawwithDate(500, Date2_3);
        tMaxiSavings.depositwithDate(500, Date2_4);
        
        henry.openAccount(tMaxiSavings);
        
        instance.addCustomer(john);
        instance.addCustomer(henry);                
        //Interest from John's Checking Account
        Date Now = new Date();
        int days = (int)((Now.getTime() - Date0_2.getTime())
                                                        /(24 * 60 * 60 * 1000));
        double expResult01 = 1500.0*31*0.001/365+1000.0*days*0.001/365;
        //Interest from John's Savings Account
        days = (int)((Now.getTime() - Date1_2.getTime())
                                                        /(24 * 60 * 60 * 1000));
        expResult01 += (1+500.0*0.002)*31/365 + 700*days*0.001/365;        
        //Interest from Henry's Maxi Savings Account
        days = (int)((Now.getTime() - Date2_4.getTime())
                                                        /(24 * 60 * 60 * 1000));
        expResult01 += (1500*10*0.001+1500*9*0.05+
                      2000*5*0.05+
                      1500*0.001*7+
                      2000*3*0.001+2000*(days-3)*0.05) / 365;
                
        double result01 = instance.totalInterestPaid();
        
        double[] result0 = {result00, result01};
        double[] expResult0 = {expResult00, expResult01};
        assertArrayEquals(expResult0, result0, DOUBLE_DELTA);
        //Interests to be paid in String
        String expResult11 = toDollars(expResult01);
        String result11 = instance.reportTotalInterestPaid();
        String[] result1 = {result10, result11};
        String[] expResult1 = {expResult10, expResult11};
        assertArrayEquals(expResult1, result1);
        //TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    private String toDollars(double amount){
            return String.format("$%,.2f", abs(amount));
    }
    /**
     * Test of getFirstCustomer method, of class Bank.
     */
    @Test
    public void testGetFirstCustomer() {
        System.out.println("Get the First Customer");
        Bank instance = new Bank();

        Customer john = new Customer("John");
        Customer henry = new Customer("Henry");
        
        instance.addCustomer(john);
        instance.addCustomer(henry);
        
        String expResult0 = "John";
        String result0 = instance.getFirstCustomer();
        assertEquals(expResult0, result0);
        
        Bank eInstance = new Bank();
        String expResult1 = "Error";
        String result1 = eInstance.getFirstCustomer();
        assertEquals(expResult1, result1);
        //TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }   
}
