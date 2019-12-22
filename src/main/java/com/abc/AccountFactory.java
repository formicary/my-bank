package com.abc;


public class AccountFactory {
	
	private static AccountFactory instance = null;
	
    public static AccountFactory getInstance() {
        if (instance == null)
            instance = new AccountFactory();
        return instance;
    }

    public Account createAccount(int accountType) {
    	switch(accountType){
    	case Account.CHECKING:
    		return new CheckingAccount();
        case Account.SAVINGS:
            return new SavingsAccount();
        case Account.MAXI_SAVINGS:
            return new MaxiSavingsAccount();
        default:
            return null;
    }

    }

}
