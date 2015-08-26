package com.abc.bank.bankops;

import java.util.List;

import com.abc.bank.IBankService;
import com.abc.bank.admin.Customer;

public abstract class BankOp {

	protected Customer customer;
	protected IBankService bank;
	
	public BankOp(IBankService bank,Customer customer){
		this.customer=customer;
		this.bank = bank;
	}

	public final List<Transaction> execute() throws AuthorizationException  {
		authorizeTransactionType(customer);
		List<Transaction> rets = doOperation();
		return rets;
	}

	protected void authorizeTransactionType(Customer c) throws AuthorizationException{
		//simple command Eligibility Verification
		//subClasses can extend

	}

	
	public final void rollBack(){
		doRollBack();
	}

	abstract void doRollBack();
	abstract List<Transaction> doOperation() throws AuthorizationException;
	
}
