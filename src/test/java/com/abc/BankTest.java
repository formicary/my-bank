package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        
        // System.out.println(bank.customerSummary());
        
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        
        Account checkingAccountBill = new Account(Account.CHECKING);
        Account checkingAccountWill = new Account(Account.CHECKING);
        
        Customer bill = new Customer("Bill").openAccount(checkingAccountBill);
        Customer will = new Customer("Will").openAccount(checkingAccountWill);
       
        bank.addCustomer(bill);
        bank.addCustomer(will);
        
        checkingAccountBill.deposit(100.0);
        checkingAccountWill.deposit(500.0);        

        assertEquals(0.6, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);	// was $170 before Maxi-Savings interest rate was updated
    }
    
    @Test
    public void bankWithFourAccounts() {
        Bank bank = new Bank();

        Account savingsAccountKim		= new Account(Account.SAVINGS);
        Account maxiSavingsAccountKim 	= new Account(Account.MAXI_SAVINGS);
        Account maxiSavingsAccountTim 	= new Account(Account.MAXI_SAVINGS);
        Account checkingAccountJim 		= new Account(Account.CHECKING);
        
        Customer kim = new Customer("Kim");
        Customer tim = new Customer("Tim");
        Customer jim = new Customer("Jim");
        
        kim.openAccount(savingsAccountKim);
        kim.openAccount(maxiSavingsAccountKim);
        tim.openAccount(maxiSavingsAccountTim);
        jim.openAccount(checkingAccountJim);

        bank.addCustomer(kim);
        bank.addCustomer(tim);
        bank.addCustomer(jim);
        
        // System.out.println(bank.customerSummary());
        
        assertEquals(	"Customer Summary\n" 	+
		        		" - Kim (2 accounts)\n"	+
		        		" - Tim (1 account)\n" 	+
		        		" - Jim (1 account)"	, bank.customerSummary());
    }
    
    @Test
    public void bankWithFourAccountsInterest() {
        Bank bank = new Bank();

        Account savingsAccountKim		= new Account(Account.SAVINGS);
        Account maxiSavingsAccountKim 	= new Account(Account.MAXI_SAVINGS);
        Account maxiSavingsAccountTim 	= new Account(Account.MAXI_SAVINGS);
        Account checkingAccountJim 		= new Account(Account.CHECKING);
        
        Customer kim = new Customer("Kim");
        Customer tim = new Customer("Tim");
        Customer jim = new Customer("Jim");
        
        double kimSavingsInterest, kimMaxiSavingsInterest, timMaxiSavingsInterest, jimCheckingInterest, totalInterestPaid;
        
        kim.openAccount(savingsAccountKim);
        kim.openAccount(maxiSavingsAccountKim);
        tim.openAccount(maxiSavingsAccountTim);
        jim.openAccount(checkingAccountJim);

        bank.addCustomer(kim);
        bank.addCustomer(tim);
        bank.addCustomer(jim);
        
        savingsAccountKim.deposit(1000.0);
        maxiSavingsAccountKim.deposit(3000.0);
        maxiSavingsAccountTim.deposit(5000.0);
        checkingAccountJim.deposit(500.0);
        
        savingsAccountKim.transfer(maxiSavingsAccountKim, 500.0);	// Kim Savings now $500, Kim Maxi Savings now $3,500
        maxiSavingsAccountTim.transfer(checkingAccountJim, 1000.0);	// Tim Maxi Savings now $4,000, Jim Checking now $1,500
        
        kimSavingsInterest		= savingsAccountKim.interestEarned();
        kimMaxiSavingsInterest	= maxiSavingsAccountKim.interestEarned();
        timMaxiSavingsInterest	= maxiSavingsAccountTim.interestEarned();
        jimCheckingInterest		= checkingAccountJim.interestEarned();
        
        totalInterestPaid = kimSavingsInterest + kimMaxiSavingsInterest + timMaxiSavingsInterest + jimCheckingInterest;
        
        assertTrue(	kimSavingsInterest == 0.5 		&&
        			kimMaxiSavingsInterest == 175.0	&&
        			timMaxiSavingsInterest == 4.0	&&
        			jimCheckingInterest == 1.5		&&
        			totalInterestPaid == bank.totalInterestPaid());
    }
    
    @Test
    public void twoBankStatements() {
        Bank amBank = new Bank();
        Bank imBank = new Bank();
        
        String bankStatements;

        Account savingsAccountSam1 = new Account(Account.SAVINGS);
        Account savingsAccountPam1 = new Account(Account.SAVINGS);
        Account savingsAccountPam2 = new Account(Account.SAVINGS);
        Account savingsAccountKim1 = new Account(Account.SAVINGS);
        Account savingsAccountKim2 = new Account(Account.SAVINGS);
        Account savingsAccountTim1 = new Account(Account.SAVINGS);
        Account savingsAccountJim1 = new Account(Account.SAVINGS);
        Account savingsAccountJim2 = new Account(Account.SAVINGS);
        Account savingsAccountJim3 = new Account(Account.SAVINGS);

        Customer sam = new Customer("Sam");
        Customer pam = new Customer("Pam");
        Customer kim = new Customer("Kim");
        Customer tim = new Customer("Tim");
        Customer jim = new Customer("Jim");

        amBank.addCustomer(sam);
        amBank.addCustomer(pam);
        imBank.addCustomer(kim);
        imBank.addCustomer(tim);
        imBank.addCustomer(jim);
        
        sam.openAccount(savingsAccountSam1);
        pam.openAccount(savingsAccountPam1);
        pam.openAccount(savingsAccountPam2);
        kim.openAccount(savingsAccountKim1);
        kim.openAccount(savingsAccountKim2);
        tim.openAccount(savingsAccountTim1);
        jim.openAccount(savingsAccountJim1);
        jim.openAccount(savingsAccountJim2);
        jim.openAccount(savingsAccountJim3);

        bankStatements = amBank.customerSummary() + "\n\n" + imBank.customerSummary();
        
        System.out.println(bankStatements);
        
        assertEquals(	"Customer Summary\n"	+
	        		 	" - Sam (1 account)\n"	+
	        		 	" - Pam (2 accounts)"	+
	        		 	"\n\n"					+
		        		"Customer Summary\n"	+
		        		" - Kim (2 accounts)\n"	+
		        		" - Tim (1 account)\n"	+
		        		" - Jim (3 accounts)"	, bankStatements);
    }
    
    

}
