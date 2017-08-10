public class MaxiSavingAccount extends Account{

	private static final int MILLISECONDS_IN_DAY = 86400000;
	private static double lowerInterestRate = 0.001, upperInterestRate = 0.05;
	private static int upperInterestDayThreshold = 10;
	
	
	public MaxiSavingAccount(){
		super(AccountType.MAXI_SAVINGS);
	}

	public double interestEarned() {
		        
		if(!transactions.isEmpty()){
			double amount = sumTransactions();
			
			if(dateLastWithdrawal != null){
				
				long timeDiff = DateProvider.getInstance().now().getTime() - dateLastWithdrawal.getTime();  //Calculates the difference in milliseconds between the current time and the last withdrawal
				
				if(timeDiff > (MILLISECONDS_IN_DAY * upperInterestDayThreshold)){  //Checks if the time difference is greater than the number of milliseconds in a time * the number of days for the threshold to be passed.
					return amount * upperInterestRate;
				} else{
					return amount * lowerInterestRate;
				}
			}
			return amount * upperInterestRate;
		}
		return 0;
	}
	
	public static void setLowerInterestRate(double newInterestRate){
		lowerInterestRate = newInterestRate;
	}
		
	public static void setUpperInterestRate(double newInterestRate){
		upperInterestRate = newInterestRate;
	}
	
	public static void setUpperInterestDayThreshold(int newThreshold){
		upperInterestDayThreshold = newThreshold;
	}
	
	public static double getLowerInterestRate(){
		return lowerInterestRate;
	}

	public static double getUpperInterestRate(){
		return upperInterestRate;
	}
	
	public static int getUpperInterestDayThreshold(){
		return upperInterestDayThreshold;
	}
	
}
