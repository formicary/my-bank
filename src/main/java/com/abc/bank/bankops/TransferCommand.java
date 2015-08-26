package com.abc.bank.bankops;

import java.util.List;

import com.abc.bank.IBankService;
import com.abc.bank.admin.Customer;
import com.google.common.collect.Lists;

public class TransferCommand extends BankOp {
	
	
	private WithdrawlCommand wCommand;
	private DepostCommand dCommand;

	public TransferCommand(IBankService bank, Customer c,WithdrawlCommand wCommand,DepostCommand dCommand)
			throws AuthorizationException {
		super(bank, c);
		this.wCommand = wCommand;
		this.dCommand = dCommand;
	}

	@Override
	List<Transaction> doOperation() throws AuthorizationException {
		List<Transaction> rets = Lists.newArrayList();
 		rets.addAll(wCommand.execute());
		rets.addAll(dCommand.execute());
		return rets;
	}

	@Override
	void doRollBack() {
		// TODO Auto-generated method stub
		
	}

}
