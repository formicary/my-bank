package mybank;

public  enum AccountType {
	   CHECKING(0),
	   SAVINGS(1),
	   MAXI_SAVINGS(2)
	   ;
	   
	  AccountType accoutType(){
	  }
	  
	  private int  typeofAccount;	 
	  
	
	  private void accountType(int type){
		  this.typeofAccount = type;
	  }
	  
	  public int getAccounttype(){
		  return typeofAccount;
	  }
}
