package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
	
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);
        Customer hugo = new Customer("Hugo");
        hugo.openAccount(new CheckingAccount()).openAccount(new SavingsAccount());
        bank.addCustomer(hugo);
        String summary = "Customer Summary\n" + 
        		" - John (1 account)\n" + 
        		" - Hugo (2 accounts)";
        assertEquals(summary, bank.customerSummary());
    }

    @Test
    public void checkTotalInterestPaid() {
        Bank bank = new Bank();
        
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Account savingsAccountProRate = new SavingsAccount();
        Account maxiSavingsAccountWithdrawRate = new MaxiSavingsAccount();
        
        Customer bill = new Customer("Bill").openAccount(checkingAccount).openAccount(savingsAccount)
        		.openAccount(maxiSavingsAccount).openAccount(savingsAccountProRate).openAccount(maxiSavingsAccountWithdrawRate);
        
        bank.addCustomer(bill);

        checkingAccount.deposit(100);
        savingsAccount.deposit(200);
        maxiSavingsAccount.deposit(250);
        savingsAccountProRate.deposit(1200);
        maxiSavingsAccountWithdrawRate.deposit(500);
        maxiSavingsAccountWithdrawRate.withdraw(100);
        
        checkingAccount.gainInterest();
        savingsAccount.gainInterest();
        maxiSavingsAccount.gainInterest();
        savingsAccountProRate.gainInterest();
        maxiSavingsAccountWithdrawRate.gainInterest();
        
        double checkingInterest = 0.001 / 365;
        double savingsInterestRate = 0.001 / 365;
        double maxiSavingsInterestRate = 0.05 / 365;
        double savingsProInterestRate = 0.002 / 365;
        double maxiSavingsWithdrawInterestRate = 0.001 / 365;
        
        double checkingGain = checkingInterest * 100;
        double savingsGain = savingsInterestRate * 200;
        double maxiSavingsGain = maxiSavingsInterestRate * 250;
        double savingsProGain = savingsProInterestRate * 1200;
        double maxiSavingsWithdrawGain = maxiSavingsWithdrawInterestRate * 400;
        
        double interestGain = checkingGain + savingsGain + maxiSavingsGain + savingsProGain + maxiSavingsWithdrawGain;
        

        assertEquals(interestGain, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
