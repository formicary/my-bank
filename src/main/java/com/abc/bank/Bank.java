package com.abc.bank;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.abc.bank.account.Account;
import com.abc.bank.account.AccountFactory;
import com.abc.bank.account.AccountType;
import com.abc.bank.admin.BankManager;
import com.abc.bank.admin.Credential;
import com.abc.bank.admin.Customer;
import com.abc.bank.admin.User;
import com.abc.bank.admin.Visitor;
import com.abc.bank.admin.reports.ReportGenerator;
import com.abc.bank.admin.reports.ReportGeneratorFactory;
import com.abc.bank.admin.reports.ReportGeneratorFactory.ReportType;
import com.abc.bank.bankops.AuthorizationException;
import com.abc.bank.bankops.BankCommandFactory;
import com.abc.bank.bankops.BankOp;
import com.abc.bank.bankops.Transaction;
import com.abc.bank.bankops.TransactionType;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.RowSortedTable;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TreeBasedTable;

public class Bank implements IBankService {

	
	
	private RowSortedTable<Customer, Integer, Account> allBankAccs =
			TreeBasedTable.<Customer, Integer, Account>create();
		
	
	public User autheticate(Credential credentials) {
		return null;
	}

	
	private <T>  void iterate(Iterator<T> it,Visitor<T> visitor){
		while (it.hasNext()){
			if (visitor.visit(it.next())){
			break;
			}
		}
	}


	public Integer addAccount(Customer customer, AccountType accType) {
		Account account  = AccountFactory.INSTANCE.getAccount(accType);
		allBankAccs.put(customer, account.getAccNo(),account);
		return account.getAccNo();
	}

	public List<Transaction> doBanking(Customer customer,
			TransactionType tType, Object... params) {
		BankOp command = BankCommandFactory.INSTANCE.getCommand(this, customer, tType, params);
		try{
			return command.execute();
		}catch (AuthorizationException e){
			command.rollBack();
			return Lists.newArrayList();
		}
	}

	public Map<Integer, Account> getAccounts(Customer c) {
		return allBankAccs.row(c);
	}

	public Account getAccount(Customer c, Integer accNo)
			throws AuthorizationException {
		Account ret =  allBankAccs.get(c, accNo);
		if (ret == null){
			throw new AuthorizationException("Account not Found");
		}
		return ret;

	}
	@SuppressWarnings("unchecked")
	public String createCustomerSummary(BankManager m) {
		ReportGenerator<Entry<Customer,Map<Integer,Account>>,String> accountSizeVisitor = 
				 (ReportGenerator<Entry<Customer,Map<Integer,Account>>, String>) ReportGeneratorFactory.INSTANCE.
		getReportGenerartor(ReportType.CUSTOMER_ACC_SIZE,null);
		iterate(allBankAccs.rowMap().entrySet().iterator(),accountSizeVisitor);
		return accountSizeVisitor.getReport();
	}

	@SuppressWarnings("unchecked")
	public String createStatement(Customer c) {
		ReportGenerator<Account,String> reportGenerator = 
				 (ReportGenerator<Account, String>) ReportGeneratorFactory.INSTANCE.
		getReportGenerartor(ReportType.CUSTOMER_STATEMENT,c);
		iterate(allBankAccs.row(c).values().iterator(),reportGenerator);
		return reportGenerator.getReport();
	}

	@SuppressWarnings("unchecked")
	public String createInterestPaidReport(BankManager manager) {
		ReportGenerator<Account,String> reportGenerator = 
				 (ReportGenerator<Account, String>) ReportGeneratorFactory.INSTANCE.
		getReportGenerartor(ReportType.BANK_INTEREST_PAID,null);
		Iterator<Account> accIterator = Iterables.transform(allBankAccs.cellSet(),
				new Function<Cell<Customer,Integer,Account>,Account>() {
			public Account apply(Cell<Customer, Integer, Account> input) {
				return input.getValue();
			}
		}).iterator();
		iterate(accIterator,reportGenerator);
		return reportGenerator.getReport();	}


	
}
