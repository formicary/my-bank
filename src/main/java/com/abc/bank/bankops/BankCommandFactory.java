package com.abc.bank.bankops;

import com.abc.bank.IBankService;
import com.abc.bank.admin.Customer;

public enum BankCommandFactory {
	INSTANCE;

	public BankOp getCommand (IBankService bank, Customer c,TransactionType tType,Object... params) {
		Integer depositAccount; 
		Integer withdrawlAccount;
		Double transactionAmount;
		try{
			switch(tType){
			case DEPOSIT:
				depositAccount = (Integer)params[0];
				transactionAmount = (Double)params[1];
				return new DepostCommand(bank,c,depositAccount,transactionAmount);
			case WITHDRAWL:
				withdrawlAccount = (Integer)params[0];
				transactionAmount = (Double)params[1];
				return new WithdrawlCommand(bank, c,withdrawlAccount,transactionAmount);
			case TRANSFER:
				depositAccount = (Integer)params[0];
				withdrawlAccount = (Integer)params[1];
				transactionAmount = (Double)params[2];
				WithdrawlCommand wCommand = new WithdrawlCommand(bank, c,withdrawlAccount, transactionAmount);
				DepostCommand dCommand = new DepostCommand(bank, c, depositAccount, transactionAmount);
				return new TransferCommand(bank, c, wCommand, dCommand);		
			default:
				throw new IllegalArgumentException("Command Not Supported");
			}
		
	}catch (Exception e){
		throw new RuntimeException("Invalid Command Parameterization");
	}


}
}
