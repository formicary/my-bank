package com.abc.bank.admin;


public interface IAdminService {
	
	User autheticate(Credential credentials);
	String createCustomerSummary(BankManager manager);
	String createInterestPaidReport(BankManager manager);
	
	
//	void iterateCustomers(BankManager manager, Visitor<Customer> visitor);
//	void iterateAccounts(BankManager manager,Visitor<Account> visitor);
}
