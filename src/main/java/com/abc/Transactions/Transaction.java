package main.java.com.abc.Transactions;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import main.java.com.abc.Accounts.AccountBase;

/*
 * All this class does to for making all the transaction ATOMIC
 * Yes it's a bit slower but for a bank you need ACID a lot
 */
public class Transaction implements ITransaction {
	
	class Memento {
		   private double state;
		   public Memento(double d) { state = d; }
		   public double GetSavedState() { return state; }
	}
	

    //The starting state for each Account Involved
    private Map<AccountBase, Memento> accRecover;
    private	Set<ReentrantReadWriteLock> rwLockList;
    private TransRecord record;
    private String state;
    
    public Transaction() {
    	accRecover = new HashMap<AccountBase, Memento>();
    	rwLockList = new HashSet<ReentrantReadWriteLock>();
    	record = null;
    	state = "NOT BEGIN";
    }

	@Override
	public void Begin() {
    	state = "BEGAN";

	}

	@Override
	public Object Read(AccountBase accountBase) throws Exception{
		rwLockList.add(accountBase.rwLock);
		
		Lock lock = accountBase.rwLock.readLock();

		//Try to get the lock in 2 seconds
		try {
			if( accountBase.rwLock.isWriteLockedByCurrentThread() || lock.tryLock() || lock.tryLock(2000, TimeUnit.MILLISECONDS) ) {
				//Save the lock to release at LAST
				return (Object)accountBase.GetBalance();
			}
			else{
				throw new Exception("Transaction Time Out, probly deadLocked, ABORTING");
			}
		} catch (Exception e) {
			this.abort();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void Write(AccountBase accountBase, double newBalance) throws Exception{
		

		rwLockList.add(accountBase.rwLock);

		ReentrantReadWriteLock lock = accountBase.rwLock;
		//Try to get the lock in 2 seconds\
		
		//NOTE: need to release all the read locks in the current thread or it will never get the write lock
		final int holdCount = lock.getReadHoldCount();
		for (int i = 0; i < holdCount; i++)
		{
		   lock.readLock().unlock();
		}
		
		try{
			if(lock.writeLock().tryLock(2, TimeUnit.SECONDS) ) {
		    	/* Save the starting State of the Accounts in case of ROLLBACK */
				if(!this.accRecover.containsKey(accountBase)){
		    		this.accRecover.put(accountBase, new Memento(accountBase.GetBalance()));    
				}
				
				accountBase.SetBalance(newBalance);
			}
			else{
				throw new Exception("Transaction Time Out, probly deadLocked, ABORTING");
			}		
		}
		catch (Exception e){
			this.abort();
			throw e;
		}
	}

	
	@Override
	public void End(String transName, double amount){
		if( this.state != "FAILED"){
			this.state = "SUCCESS";
		}
		this.record = new TransRecord(transName, amount, this.state);
		this.ReleaseAllLocks();

	}
	private void abort(){
    	this.state = "FAILED";

		this.rollBack();
	}
	
	private void rollBack(){
		for (Map.Entry<AccountBase, Memento> entry : this.accRecover.entrySet())
		{ 
			AccountBase acc = entry.getKey();
			Memento startMem = entry.getValue();
			try {
				acc.SetBalance(startMem.GetSavedState());
			} catch (Exception e) {
				//Not Possible to below 0, since we going back
				e.printStackTrace();
			}
		}
	}
	@Override
	public TransRecord GetRecord() {
		return this.record;
	}

	
	private void ReleaseAllLocks() {
		for (ReentrantReadWriteLock rwLock : this.rwLockList){
			final int readHoldCount = rwLock.getReadHoldCount();
			for (int i = 0; i < readHoldCount; i++)
			{
				rwLock.readLock().unlock();
			}

			final int writeHoldCount = rwLock.getWriteHoldCount();
			for (int i = 0; i < writeHoldCount; i++)
			{
				rwLock.writeLock().unlock();
			}
		}
	}

}
