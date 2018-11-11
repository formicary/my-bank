package src.main.java.com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * daily interest values calculated using the Excel worksheet included in the main folder
 * all tests work
 */
public class BankTest
{
    private static final double DOUBLE_DELTA = 1e-15;
    private int testDays1 = 3; //i.e. what is the interest after 3 days; 6 days; 10 days.
    private int testDays2 = 6;
    private int days = 10;
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }    
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        double result;
        
        result = Math.round(bank.totalInterestPaidTestVer(testDays1) * 10000d)/10000d;
        assertEquals(0.0008, result, DOUBLE_DELTA);        

        result = Math.round(bank.totalInterestPaidTestVer(testDays2) * 10000d)/10000d;
        assertEquals(0.0016, result, DOUBLE_DELTA);
        
        result = Math.round(bank.totalInterestPaidTestVer(days) * 10000d)/10000d;
        assertEquals(0.0027, result, DOUBLE_DELTA);
    }
    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);
        double result;

        result = Math.round(bank.totalInterestPaidTestVer(testDays1) * 10000d) / 10000d;
        assertEquals(0.0164, result, DOUBLE_DELTA);
        
        result = Math.round(bank.totalInterestPaidTestVer(testDays2) * 10000d) / 10000d;
        assertEquals(0.0329, result, DOUBLE_DELTA);
        
        result = Math.round(bank.totalInterestPaidTestVer(days) * 10000d) / 10000d;
        assertEquals(0.0548, result, DOUBLE_DELTA);
    }
    @Test
    public void maxi_savings_account() { //annual rate of 5%
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        
        double result;

        result = Math.round(bank.totalInterestPaidTestVer(testDays1) * 10000d) / 10000d;
        assertEquals(1.2330, result, DOUBLE_DELTA);
        
        result = Math.round(bank.totalInterestPaidTestVer(testDays2) * 10000d) / 10000d;
        assertEquals(2.4666, result, DOUBLE_DELTA);
        
        result = Math.round(bank.totalInterestPaidTestVer(days) * 10000d) / 10000d; 
        assertEquals(4.1121, result, DOUBLE_DELTA);
    }    
    @Test
    public void maxi_savings_accountWithdrawal() { //i..e using annual rate of 0.1%
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3001.0);
        checkingAccount.withdraw(1.0);
        double result;

        result = Math.round(bank.totalInterestPaidTestVer(testDays1) * 10000d) / 10000d;
        assertEquals(0.0247, result, DOUBLE_DELTA);
        
        result = Math.round(bank.totalInterestPaidTestVer(testDays2) * 10000d) / 10000d;
        assertEquals(0.0493, result, DOUBLE_DELTA);
        
        result = Math.round(bank.totalInterestPaidTestVer(days) * 10000d) / 10000d;        
        assertEquals(0.0822, result, DOUBLE_DELTA);
    }
}
