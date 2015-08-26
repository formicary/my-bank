package com.abc.bank.bankops;

import java.util.List;

import com.abc.bank.IBankService;
import com.abc.bank.account.Account;
import com.abc.bank.admin.Customer;
import com.google.common.collect.Lists;

public class WithdrawlCommand extends BankOp {

	private static TransactionType TRAN_TYPE = TransactionType.WITHDRAWL;
	Account account;
	private Double amount;
	private Integer accNo;
	
	public WithdrawlCommand(IBankService bank,Customer cust,Integer accNo,Double amount)  {
		super(bank,cust);
		this.amount = amount;
		this.accNo = accNo;
	}

	@Override
	List<Transaction> doOperation() {
		List<Transaction> rets= Lists.newArrayList();
		Account account = bank.getAccounts(customer).get(accNo);
		
		Transaction tranaction = TransactionBuilder.INSTANCE.createTransaction(Math.abs(amount)*-1F, TRAN_TYPE);
		account.addTransaction(tranaction);
		rets.add(tranaction);
		return rets;
	}

	@Override
	void doRollBack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void authorizeTransactionType(Customer c)
			throws AuthorizationException {
		super.authorizeTransactionType(c);
		//TODO: check sufficient funds
	}

}
