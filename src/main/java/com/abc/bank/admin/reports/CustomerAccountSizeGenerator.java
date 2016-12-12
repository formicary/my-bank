package com.abc.bank.admin.reports;

import java.util.Map;
import java.util.Map.Entry;

import com.abc.bank.account.Account;
import com.abc.bank.admin.Customer;

public class CustomerAccountSizeGenerator extends ReportGenerator<Entry<Customer,Map<Integer,Account>>,String > {

	private static final String REPORT_NAME = "Customer Summary";
	
	public CustomerAccountSizeGenerator() {
		super(REPORT_NAME);
	}

	public boolean visit(Entry<Customer, Map<Integer, Account>> t) {
		sb.append(t.getKey().getName() + " (" + format(t.getValue().size(), "account") + ")");
		sb.append(System.lineSeparator());
		return false;
	}

	@Override
	public String getReport() {
		return sb.toString();
	}
	
	 private String format(int number, String word) {
	        return number + " " + (number == 1 ? word : word + "s");
	    }


	

}
