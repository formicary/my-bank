package main.java.com.abc.Transactions;

import main.java.com.abc.Accounts.AccountBase;

/*
 * "Program to interface, Not to the Class"
 * 
 */
public interface ITransaction {
	public Object Read(AccountBase accountBase) throws Exception;
	public void Write(AccountBase accountBase, double newBalance) throws Exception;
	public void Begin();
	public TransRecord GetRecord();
	public void End(String transName, double amount);
}
