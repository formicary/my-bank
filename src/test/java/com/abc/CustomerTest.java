package com.abc;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author R. Fei
 */
public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;
    public CustomerTest() {
    }
    /**
     * Test of getName method, of class Customer.
     */
    @Test
    public void testGetName() {
        System.out.println("Get Account Name");
        Customer instance = new Customer("Henry");
        String expResult = "Henry";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of getNumberOfAccounts method, of class Customer.
     */
    @Test
    public void testGetNumberOfAccounts() {
        System.out.println("Get the Number of Accounts");
        Customer instance = new Customer("Henry");

        instance.openAccount(new AccountChecking());
        instance.openAccount(new AccountSavings());
        instance.openAccount(new AccountMaxiSavings());
        
        int expResult = 3;
        int result = instance.getNumberOfAccounts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of transferBetweenAccounts method, of class Customer.
     */
    @Test
    public void testTransferBetweenAccounts() {
        System.out.println("Transfer Money Between Accounts");
        Customer instance = new Customer("Henry");
        
        instance.openAccount(new AccountChecking());
        instance.openAccount(new AccountSavings());
        instance.openAccount(new AccountMaxiSavings());        
        
        instance.getAccounts().get(0).deposit(100.0);
        instance.getAccounts().get(1).deposit(1000.0);
        instance.getAccounts().get(2).deposit(3000.0);

        instance.transferBetweenAccounts(1, 2, 500);
        double expResult1 = 1000-500;
        double result1 = instance.getAccounts().get(1).getBalance();
        double expResult2 = 3000+500;
        double result2 = instance.getAccounts().get(2).getBalance();
        assertEquals(expResult1, result1, 0.0);
        assertEquals(expResult2, result2, 0.0);
        
        instance.transferBetweenAccounts(1, 3, 500);
        double expResult3 = 1000-500;
        double result3 = instance.getAccounts().get(1).getBalance();
        assertEquals(expResult3, result3, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of getAccounts, totalInterestEarned method, of class Customer.
     */
    @Test
    public void testTotalInterestEarned() {
        System.out.println("Total Interest Earned");
        Customer instance = new Customer("Henry");
        
        testAccount tChecking = new AccountChecking();
        testAccount tSavings = new AccountSavings();
        testAccount tMaxiSavings = new AccountMaxiSavings();
        
        Date Date0_1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date Date0_2 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        tChecking.depositwithDate(1500, Date0_1);
        tChecking.withdrawwithDate(500, Date0_2);
        
        Date Date1_1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date Date1_2 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        tSavings.depositwithDate(1500, Date1_1);        
        tSavings.withdrawwithDate(800, Date1_2);
        
        Date Date2_1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date Date2_2 = new Date(115,0,20); //"2015-01-20", "yyyy-MM-dd"
        Date Date2_3 = new Date(115,0,25); //"2015-01-25", "yyyy-MM-dd"
        Date Date2_4 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        tMaxiSavings.depositwithDate(1500, Date2_1);
        tMaxiSavings.depositwithDate(500, Date2_2);
        tMaxiSavings.withdrawwithDate(500, Date2_3);
        tMaxiSavings.depositwithDate(500, Date2_4);
        
        instance.openAccount(tChecking);
        instance.openAccount(tSavings);
        instance.openAccount(tMaxiSavings);
        //Interest from 1st Checking Account
        Date Now = new Date();
        int days = (int)((Now.getTime() - Date0_2.getTime())
                                                        /(24 * 60 * 60 * 1000));
        double expResult = 1500.0*31*0.001/365+1000.0*days*0.001/365;
        //Interest from 2nd Savings Account
        days = (int)((Now.getTime() - Date1_2.getTime())
                                                        /(24 * 60 * 60 * 1000));
        expResult += (1+500.0*0.002)*31/365 + 700*days*0.001/365;        
        //Interest from 3rd Maxi Savings Account
        days = (int)((Now.getTime() - Date2_4.getTime())
                                                        /(24 * 60 * 60 * 1000));
        expResult += (1500*10*0.001+1500*9*0.05+
                2000*5*0.05+
                1500*0.001*7+
                2000*3*0.001+2000*(days-3)*0.05) / 365;
        double result = instance.totalInterestEarned();
        assertEquals(expResult, result, DOUBLE_DELTA);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of Print method, of class Customer.
     */
    @Test
    public void testPrint() {
        System.out.println("Print Statements (all)");
        Customer instance = new Customer("Henry");
        
        String expResult0 = "No Account is Related to Henry";
        String result0 = instance.Print();
        assertEquals(expResult0, result0);

        instance.openAccount(new AccountChecking());
        instance.openAccount(new AccountSavings());
        instance.openAccount(new AccountMaxiSavings());
        
        String expResult1 = "Statement for Henry\n" +
                           "\n"+
                           "Checking Account\n"+
                           "Total $0.00\n"+
                           "\n"+"\n"+
                           "Savings Account\n"+
                           "Total $0.00\n"+
                           "\n"+"\n"+
                           "Maxi Savings Account\n"+
                           "Total $0.00\n"+
                           "\n"+
                           "Total In All Accounts $0.00";
        String result1 = instance.Print();
        assertEquals(expResult1, result1);
        
        instance.getAccounts().get(0).deposit(100.0);
        instance.getAccounts().get(1).deposit(1000.0);
        instance.getAccounts().get(1).withdraw(200.0);
        instance.getAccounts().get(2).deposit(3000.0);
        
        String expResult = "Statement for Henry\n" +
                           "\n"+
                           "Checking Account\n"+
                           "  deposit $100.00\n"+
                           "Total $100.00\n"+
                           "\n"+"\n"+
                           "Savings Account\n"+
                           "  deposit $1,000.00\n"+
                           "  withdrawal $200.00\n"+
                           "Total $800.00\n"+
                           "\n"+"\n"+
                           "Maxi Savings Account\n"+
                           "  deposit $3,000.00\n"+
                           "Total $3,000.00\n"+
                           "\n"+
                           "Total In All Accounts $3,900.00";
        String result = instance.Print();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test Money transfer to a non-existed account.
     */
    @Test(expected = Exception.class)
    public void testExceptions(){
        System.out.println("Invalid Transfer");
        Customer instance = new Customer("Henry");
        instance.openAccount(new AccountChecking());
        instance.openAccount(new AccountSavings());
        expect(IllegalArgumentException.class);
        instance.getAccounts().get(0).deposit(1000);        
        instance.transferBetweenAccounts(0, 3, 500);
        double expResult = 1000;
        double result = instance.getAccounts().get(0).getBalance();        
        assertEquals(expResult, result,DOUBLE_DELTA);
    }

    private void expect(Class<IllegalArgumentException> aClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
