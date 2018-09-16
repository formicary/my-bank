package com.abc;

public class AccountsTestHelper 
{
    public static CheckingAccount createCheckingAcount(String accountNumber) {
    	final CheckingAccount checkingAccount = new CheckingAccount(accountNumber);
    	checkingAccount.setCurrentTimeProvider(new CurrentTimeProvider());
		return checkingAccount;
    }
    
    public static SavingsAccount createSavingsAccount(String accountNumber) {
    	SavingsAccount savingsAccount = new SavingsAccount(accountNumber);
    	savingsAccount.setCurrentTimeProvider(new CurrentTimeProvider());
		return savingsAccount;
    }
    
    public static MaxiSavingsAccount createMaxiSavingsAccount(String accountNumber) {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount(accountNumber);
    	maxiSavingsAccount.setCurrentTimeProvider(new CurrentTimeProvider());
		return maxiSavingsAccount;
    }
}
