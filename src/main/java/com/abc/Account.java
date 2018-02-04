
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
    private int accountId;
    private double balance;
    private final int accountType;
    private List<Transaction> transactions;

    /**
     * Account constructor.
     * @param accountType
     * @param accountId - this is added as to differentiate between different user accounts
     * @throws IllegalArgumentException
     */
    public Account(int accountType) throws IllegalArgumentException {
    	if (accountType<0||accountType>2) {
            throw new IllegalArgumentException("Account type should be between 0 and 2. Remember: /n  Checking accounts have code 0;/n Savings accounts have code 1;/n Maxi Savings accounts have code 2!");
    	} else {
	        this.accountType = accountType;
	        this.transactions = new ArrayList<Transaction>();
    	}
    }
    /**
     * Account constructor.
     * @param accountType
     * @param accountId - this is added as to differentiate different user accounts
     * @throws IllegalArgumentException
     */
    public Account(int accountType, int accountId) throws IllegalArgumentException {
    	if (accountType<0||accountType>2) {
            throw new IllegalArgumentException("Account type should be between 0 and 2. Remember: /n  Checking accounts have code 0;/n Savings accounts have code 1;/n Maxi Savings accounts have code 2!");
    	} else {
    		this.accountId = accountId;
	        this.accountType = accountType;
	        this.transactions = new ArrayList<Transaction>();
    	}
    }
    /**
     * Method to deposit an amount to an account
     * @param amount
     * @throws IllegalArgumentException
     */
    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            setBalance(getBalance() + amount);
        }
    }
    /**
     * Method to withdraw amount from an account
     * @param amount
     * @throws IllegalArgumentException
     */
	public void withdraw(double amount) throws IllegalArgumentException {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	        setBalance(getBalance() - amount);
	    }
	}
	/**
	 * Method to calculate interest earned on this account, depending on the type of account.
	 * @return
	 * @throws IllegalArgumentException
	 */

    public double interestEarned() throws IllegalArgumentException  {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000){
                    return amount * 0.001;
                }
                else{
                    return (1 + (amount-1000) * 0.002);
                }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                //if (amount <= 1000){
                //    return amount * 0.02/365;
                //}
                //else if (amount <= 2000){
                //    return (20 + (amount-1000) * 0.05)/365;
                //}else{
                //return (70 + (amount-2000) * 0.1)/365;
                //}
                if(checkIfTransactionsExist()){
                	Date currDate = DateProvider.getInstance().now();
                    Date lastTransactionDate = transactions.get(transactions.size() - 1).getTransactionDate();
                
	                if((currDate.getTime() - lastTransactionDate.getTime())/(24 * 60 * 60 * 1000)<10){
	                	return amount * 0.05;
	                }else{
	                	return amount * 0.001;
	                }
                }else{
                	return amount*0.001;
                }
                	
            case CHECKING:
                return amount * 0.001;
            default:
            	throw new IllegalArgumentException("There's been an error. Please check account type.");
        }
    	
    }
    /**
     * Method to calculate the sum of all the transactions associated with this account.
     * @return double amount
     */
    public double sumTransactions() {
       boolean checkTransactions = checkIfTransactionsExist();
       if (!checkTransactions){
    	   return 0;
       }
       double amount = 0.0;
       for (Transaction t: transactions)
           amount += t.amount;
       return amount;
    }
    /**
     * Method to make sure that there are currently transactions associated with this account.
     * this method was not performing its intended task of testing if transactions currently exist -- fixed
     * @return boolean isEmpty 
     */
    private boolean checkIfTransactionsExist() {
    	boolean transactionsExist;
    	if (transactions.isEmpty()){
    		transactionsExist = false;
    	} else {
    		transactionsExist = true;
    	}
    	return transactionsExist;
    }

    public int getAccountType() {
        return accountType;
    }


	public int getAccountId() {
		return this.accountId;
	}


	public double getBalance() {
		return balance;
	}


	private void setBalance(double balance) {
		this.balance = balance;
	}
	
	public List<Transaction>  getTransactions(){
		return transactions;
	}
}
