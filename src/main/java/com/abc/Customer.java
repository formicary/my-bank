package mybank;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
//private List<Account> accounts = new ArrayList<Account>();
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
    
    //for testing 
    public List<Account> getAccount (){
    	return accounts;
    }
    
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

	/*Previous code suffers inefficient bit use of String Statement, as going to For Loop next,each iteration will create a new Sting.
	However, most java compiler will optimize the string concatenation to "append operations" on a "StringBuilder"
    On second glance, the StringBuilder is being instantiated on each iteration of the loop, so it may not be the most efficient bytecode.
    By using  explicit StringBuilder with .append("") method, we can just add behind into existent String. */
    public String getStatement() {            	
        StringBuilder sb = new StringBuilder();
        sb.append("Statement for:" + name);
        double total = 0.0;
        for (Account a : accounts) {
            sb.append("\n" + statementForAccount(a) + "\n" );
            total += a.sumTransactions();
        }
        sb.append("\nTotal In All Accounts " + toDollars(total));
        return sb.toString();
    }


   
   // Same idea as above, using StringBuild() append method, add back into existing String, to have better efficiency. 
    private String statementForAccount(Account a) {
        
        StringBuilder sb = new StringBuilder();
        
        
        switch(a.getAccountType()){
            case CHECKING:
                sb.append("Checking Account\n");
                break;
            case SAVINGS:
                sb.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                sb.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
        	   	
           sb.append((t.amount < 0 ? "withdraw" : "deposit "));
           sb.append(toDollars(t.amount));
           total += t.amount;
        }
        sb.append("Account Total:  " + toDollars(total));
        return sb.toString();
    }

    private String toDollars(double d){
        return String.format("$%5.2f\n", abs(d));
    }
    
    
    //Transfer money between Account method added.
    public void transfersBetweenAccount (double amount, Account sendingAccount, Account recevingAccount){
	       if (amount>sendingAccount.sumTransactions()) {
	    	   throw new IllegalArgumentException("Insufficient remaining on Account");
		       	
		       } else if (sendingAccount == recevingAccount) {
		        	 throw new IllegalArgumentException("Can not transfers within same Account");
		           
		        } else {
		            sendingAccount.withdraw(amount);
		            recevingAccount.deposit(amount);
		        }
    	} 
    
}
