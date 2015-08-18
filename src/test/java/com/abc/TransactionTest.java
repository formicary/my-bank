package test.java.com.abc;

import main.java.com.abc.Accounts.AccountBase;
import main.java.com.abc.Accounts.AccountFactory;
import main.java.com.abc.Accounts.AccountType;
import main.java.com.abc.Transactions.ITransaction;
import main.java.com.abc.Transactions.Transaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void AbortTest() {
    	System.out.println("######################################################\n" + "AbortTest\n");

        AccountBase ACheckingAccount = AccountFactory.CreateAccount(AccountType.Checking);
        AccountBase BCheckingAccount = AccountFactory.CreateAccount(AccountType.Checking);

    	
    	ITransaction transaction = new Transaction();
        transaction.Begin();
		try{
			transaction.Write(ACheckingAccount, 1000);
			
			transaction.Write(BCheckingAccount, -1000);

		}
		catch( Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			transaction.End("TEST DEPO", 1000);
			System.out.println(transaction.GetRecord().GetSummary());

		}
		
		ACheckingAccount.Close();
		BCheckingAccount.Close();

		
        assertEquals(ACheckingAccount.GetBalanceSafe(), 0, DOUBLE_DELTA);
        assertEquals(BCheckingAccount.GetBalanceSafe(), 0, DOUBLE_DELTA);

    }
	
    @Test
    public void ReadTest() {
    	System.out.println("######################################################\n" + "ReadTest\n");

        AccountBase CheckingAccount = AccountFactory.CreateAccount(AccountType.Checking);

    	
    	ITransaction transaction = new Transaction();
        transaction.Begin();
		try{
			double a = (double)transaction.Read(CheckingAccount);
	        assertEquals(a, 0, DOUBLE_DELTA);

		}
		catch( Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			transaction.End("TEST DEPO", 1000);
			System.out.println(transaction.GetRecord().GetSummary());

		}
        
    }
    
	@Test
    public void WriteTest() {
    	System.out.println("######################################################\n" + "WriteTest\n");

        AccountBase CheckingAccount = AccountFactory.CreateAccount(AccountType.Checking);

    	
    	ITransaction transaction = new Transaction();
        transaction.Begin();
		try{
			transaction.Write(CheckingAccount, 1000);

		}
		catch( Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			transaction.End("TEST DEPO", 1000);
			System.out.println(transaction.GetRecord().GetSummary());

		}
        assertEquals(CheckingAccount.GetBalanceSafe(), 1000, DOUBLE_DELTA);

    }
     
    @Test
    public void DeadLockTest() {
    	System.out.println("######################################################\n" + "DeadLockTest\n");

        AccountBase AcheckingAccount = AccountFactory.CreateAccount(AccountType.Checking);
        AccountBase BcheckingAccount = AccountFactory.CreateAccount(AccountType.Checking);
        AcheckingAccount.Close();
        BcheckingAccount.Close();
        
    	ITransaction ATransaction = new Transaction();
    	ITransaction BTransaction = new Transaction();
    	
        class Thread1 extends Thread {
            public void run() {
            	ATransaction.Begin();

    			try{
    				double currBalance = (double)ATransaction.Read(AcheckingAccount);
	            	ATransaction.Write(AcheckingAccount, currBalance + 1000 );
	
	            	System.out.println("Thread 1: Holding Write lock 1...");
	            	try { Thread.sleep(1000); }
	            	catch (InterruptedException e) {}
	              
    				currBalance = (double)ATransaction.Read(BcheckingAccount);
	          		ATransaction.Write(BcheckingAccount, currBalance + 1000);
	          		System.out.println("Thread 1: Waiting for Write lock 2...");

    			}
   				catch (Exception e){
   					System.out.format(e.getMessage());
   				}
    			finally{
    				ATransaction.End("TEST DEADLOCK", 0);
    				System.out.println(ATransaction.GetRecord().GetSummary());

    			}
                  
               
            }
         }
    
         class Thread2 extends Thread {
            public void run() {
            	BTransaction.Begin();

   				try{
    				double currBalance = (double)BTransaction.Read(BcheckingAccount);
	            	BTransaction.Write(BcheckingAccount, currBalance + 1000);
	                System.out.println("Thread 2: Holding Write lock 2...");
    				currBalance = (double)BTransaction.Read(AcheckingAccount);
	                
	            	BTransaction.Write(AcheckingAccount, currBalance + 1000);
	                System.out.println("Thread 2: Waiting for lock 1...");
   				}
   				catch (Exception e){
   					System.out.println(e.getMessage());
   				}
   				finally{
   					BTransaction.End("TEST DEPO", 0);
   					System.out.println(BTransaction.GetRecord().GetSummary());

   				}
            }
         } 
         
         Thread1 T1 = new Thread1();
         Thread2 T2 = new Thread2();
         
         T1.start();
         T2.start();
         
     	try { Thread.sleep(4000); }
     	catch (InterruptedException e) {}
     	
     	System.out.format("SSSSS: %f, %f", AcheckingAccount.GetBalanceSafe(), BcheckingAccount.GetBalanceSafe());
     	
        assertEquals(AcheckingAccount.GetBalanceSafe(), 1000, DOUBLE_DELTA);
        assertEquals(BcheckingAccount.GetBalanceSafe(), 1000, DOUBLE_DELTA);


    }
}
