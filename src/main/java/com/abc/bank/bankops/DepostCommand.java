package com.abc.bank.bankops;

import java.util.List;

import com.abc.bank.IBankService;
import com.abc.bank.account.Account;
import com.abc.bank.admin.Customer;
import com.google.common.collect.Lists;

public class DepostCommand extends BankOp {

	private static TransactionType TRAN_TYPE = TransactionType.DEPOSIT;
	Double transactionAmount;
	private Integer accNo;
	
	public DepostCommand(IBankService bank, Customer c,Integer accNo,Double tranactionAmount)
			throws AuthorizationException {
		super(bank, c);
		this.transactionAmount = tranactionAmount;
		this.accNo = accNo;
	
	}

	@Override
	List<Transaction> doOperation() throws AuthorizationException {
	
		Account account = bank.getAccount(customer,accNo);
		Transaction transaction  = TransactionBuilder.INSTANCE.createTransaction(transactionAmount, TRAN_TYPE);
		account.addTransaction(transaction);
		return Lists.newArrayList(transaction);
	}

	@Override
	void doRollBack() {
		// TODO Auto-generated method stub
		
	}

}
