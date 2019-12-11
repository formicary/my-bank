package com.abc;

public class AccountFactory {
	
	  public static final int CHECKING = 0;
	  public static final int SAVINGS = 1;
	  public static final int MAXI_SAVINGS = 2;
	  
	  private final int accountType;
	  
	  public AccountFactory(int accountType){
	       this.accountType = accountType;
	  }
	  
	 public Account getAccount(){
		 switch(accountType){
	         case CHECKING:
	            return new CheckingAccount();
	         case SAVINGS:
	             return new SavingsAccount();
	         case MAXI_SAVINGS:
	        	 return new MaxiSavingsAccount();
	         default: 
	        	 return null;
		 }
	 }
}
