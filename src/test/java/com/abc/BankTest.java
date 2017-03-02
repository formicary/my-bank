package com.abc;

import jdk.nashorn.internal.AssertsEnabled;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    //Test to generate customer details for manager
    @Test 
    public void generateCustomerDetailsReportForManager(){
    	Bank bank = new Bank();
        Customer carol = new Customer("Carol");
        Account checkingAccount = new Account(Account.CHECKING);
        carol.openAccount(checkingAccount);
        carol.openAccount(checkingAccount);
        bank.addCustomer(carol);
        
        Customer victor = new Customer("Victor");
        Account savingAccount = new Account(Account.SAVINGS);
        victor.openAccount(savingAccount);
        victor.openAccount(savingAccount);
        victor.openAccount(checkingAccount);
        bank.addCustomer(victor);
        
        Employee smith = new Employee("Smith", Employee.BRANCH_MANAGER);
        bank.addEmployee(smith);
        
        String summary = "Customer Summary\n - Carol (2 accounts)\n - Victor (3 accounts)";
        assertEquals(summary, bank.allCustomerSummary(smith));
    }
    
    // Test to check if report for customer details will be generated for any other bank employee
    @Test
    public void generateCustomerDetailsReportForOtherEmployees(){
    	Bank bank = new Bank();
        Customer carol = new Customer("Carol");
        Account checkingAccount = new Account(Account.CHECKING);
        carol.openAccount(checkingAccount);
        carol.openAccount(checkingAccount);
        bank.addCustomer(carol);
        
        Customer victor = new Customer("Victor");
        Account savingAccount = new Account(Account.SAVINGS);
        victor.openAccount(savingAccount);
        victor.openAccount(savingAccount);
        victor.openAccount(checkingAccount);
        bank.addCustomer(victor);
        
        Employee roger = new Employee("Roger", Employee.CASHIER);
        bank.addEmployee(roger);
        
        String summary = "Only Branch Manager can view the customer details.";
        assertEquals(summary, bank.allCustomerSummary(roger));
    }
    
    // Test to generate report for the total interest paid for the manager
    @Test 
    public void generateTotalInterstPaidReportForManager(){
    	Bank bank = new Bank();
        Customer danny = new Customer("Danny");
        Account checkingAccount = new Account(Account.CHECKING);
        danny.openAccount(checkingAccount);
        checkingAccount.deposit(700);
        checkingAccount.deposit(500);
        checkingAccount.withdraw(100);
        bank.addCustomer(danny);
        
        Customer gary = new Customer("Gary");
        Account savingAccount = new Account(Account.SAVINGS);
        gary.openAccount(savingAccount);
        savingAccount.deposit(2000);
        bank.addCustomer(gary);
        
        Employee simone = new Employee("Simone", Employee.BRANCH_MANAGER);
        bank.addEmployee(simone);
        
        String summary = "Total Interest Paid by the Bank 4.1";
        assertEquals(summary, bank.reportTotalInterestPaid(simone));
    }
    
    // Test to check if report for total interest paid will be generated for any other bank employee
    @Test
    public void generateTotalInterstPaidReportForOtherEmployees(){
    	Bank bank = new Bank();
        Customer richard = new Customer("Richard");
        Account checkingAccount = new Account(Account.CHECKING);
        richard.openAccount(checkingAccount);
        checkingAccount.deposit(700);
        checkingAccount.deposit(500);
        checkingAccount.withdraw(100);
        bank.addCustomer(richard);
        
        Customer andy = new Customer("Andy");
        Account savingAccount = new Account(Account.SAVINGS);
        andy.openAccount(savingAccount);
        savingAccount.deposit(2000);
        bank.addCustomer(andy);
        
        Employee jerry = new Employee("Jerry", Employee.LOAN_OFFICER);
        bank.addEmployee(jerry);
        
        String summary = "Only Branch Manager can view the total interest paid details.";
        assertEquals(summary, bank.reportTotalInterestPaid(jerry));
    }
    
    // Test to check if the Exception is thrown when trying to deposit zero amount in an account
    @Test(expected = IllegalArgumentException.class)
    public void checkDepositWithZeroAmountException(){
    	
        Customer paul = new Customer("Paul");
        Account checkingAccount = new Account(Account.CHECKING);
        paul.openAccount(checkingAccount);
        checkingAccount.deposit(0);
    }
    
    // Test to check the Exception message when trying to deposit zero amount in an account
    @Test
    public void checkDepositWithZeroAmountExceptionMessage(){
    	
        Customer paul = new Customer("Paul");
        Account checkingAccount = new Account(Account.CHECKING);
        paul.openAccount(checkingAccount);
        
        expectedException.expectMessage("amount must be greater than zero");
        
        checkingAccount.deposit(0);
    	
    }
    
    // Test to check if the Exception is thrown when trying to withdraw zero amount in an account
    @Test(expected = IllegalArgumentException.class)
    public void checkWithdrawWithZeroAmountException(){
    	
        Customer paul = new Customer("Paul");
        Account checkingAccount = new Account(Account.CHECKING);
        paul.openAccount(checkingAccount);
        checkingAccount.withdraw(0);
    }
    
    // Test to check the Exception message when trying to withdraw zero amount in an account
    @Test
    public void checkWithdrawWithZeroAmountExceptionMessage(){
    	
        Customer paul = new Customer("Paul");
        Account checkingAccount = new Account(Account.CHECKING);
        paul.openAccount(checkingAccount);
        
        expectedException.expectMessage("amount must be greater than zero");
        
        checkingAccount.withdraw(0);
    }
    
    // Test to check deposit functionality in an account
    @Test
    public void checkDepositWithOtherAmountThanZero(){
    	
        Customer tim = new Customer("Tim");
        Account checkingAccount = new Account(Account.CHECKING);
        tim.openAccount(checkingAccount);
        checkingAccount.deposit(100);
        
        String statement = "Checking Account\n  deposit $100.00\nTotal $100.00";
        assertEquals(statement, tim.statementForAccount(checkingAccount));
        
    }
    
    // Test to check deposit functionality in an account
    @Test
    public void checkWithdrawWithOtherAmountThanZero(){
    	
        Customer tim = new Customer("Tim");
        Account checkingAccount = new Account(Account.CHECKING);
        tim.openAccount(checkingAccount);
        checkingAccount.deposit(200);
        checkingAccount.withdraw(50);
        
        String statement = "Checking Account\n  deposit $200.00\n  withdrawal $50.00\nTotal $150.00";
        assertEquals(statement, tim.statementForAccount(checkingAccount));
    }
}
