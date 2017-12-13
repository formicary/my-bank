package com.abc.banking.presentation;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.abc.banking.Account;
import com.abc.banking.Customer;
import com.abc.banking.Transaction;
import com.abc.banking.config.ApplicationConfig;
import com.abc.banking.exceptions.ReportException;

public final class ReportGenerator {
	
	private static final String ACCOUNT_STATEMENT_TEMPLATE_PATH = "com/abc/banking/presentation/AccountStatement.vtl";
	private static final String CUSTOMER_STATEMENT_TEMPLATE_PATH = "com/abc/banking/presentation/CustomerStatement.vtl";
	private static final String CUSTOMER_SUMMARY_TEMPLATE_PATH = "com/abc/banking/presentation/CustomerSummary.vtl";
	
	private static VelocityEngine engine = null;
	
	private ReportGenerator() { }
	
	public static VelocityEngine getEngine() {
		if(engine == null) {
			engine = new VelocityEngine();
			engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			engine.init();
		}
		
		return engine;
	}
	
	protected static String generateReport(String templateClassPath, VelocityContext velocityContext) throws ReportException {
		
		if(templateClassPath == null)
			throw new IllegalArgumentException("templateClassPath cannot be null");
		
		if(velocityContext == null)
			throw new IllegalArgumentException("velocityContext cannot be null");
		
		StringWriter stringWriter = new StringWriter();
		
		try {
			Template template = getEngine().getTemplate(templateClassPath);
			
			template.merge(velocityContext, stringWriter);
			
		} catch(Exception ex) {
			throw new ReportException("Cannot generate report.", ex);
		}
		
		return stringWriter.toString();
	}

	private static String transactionVerbose(Transaction transaction) {
    	
    	if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0)
    		return "withdrawal";
		else
			return "deposit   ";
    }
	
	public static String generateAccountStatement(Account account) throws ReportException {
			
		if(account == null)
			throw new IllegalArgumentException("account cannot be null");

		List<Map<String, String>> transactions = new ArrayList<>();
		
		for(Transaction transaction : account.getTransactions()) {
			
			Map<String, String> transactionView = new HashMap<>();
			
			transactionView.put("date", ApplicationConfig.DATE_FORMAT.format(transaction.getTransactionDate()));
			transactionView.put("type", transactionVerbose(transaction));
			transactionView.put("amount", ApplicationConfig.CURRENCY_FORMAT.format(transaction.getAmount().abs()));
			
			transactions.add(transactionView);
		}
		
		VelocityContext ctx = new VelocityContext();
		
		ctx.put("accountTypeName", account.getAccountTypeName());
		ctx.put("transactions", transactions);
		ctx.put("accountTotal", ApplicationConfig.CURRENCY_FORMAT.format(account.getBalance()));
		ctx.put("currencyFormat", ApplicationConfig.CURRENCY_FORMAT);
		
		return generateReport(ACCOUNT_STATEMENT_TEMPLATE_PATH, ctx);
	}
	
	public static String generateCustomerStatement(Customer customer) throws ReportException {

		if(customer == null)
			throw new IllegalArgumentException("customer cannot be null");
		
		List<String> accountReports = new ArrayList<>();
		
		for(Account account:customer.getAccounts()) {
			accountReports.add(generateAccountStatement(account));
		}
		
		BigDecimal totalInAllAccounts =
    			customer.getAccounts().stream()
    				.map(account -> account.getBalance())
    				.reduce(
    						BigDecimal.ZERO,
    						(a, b) -> a.add(b));
		
		VelocityContext ctx = new VelocityContext();
		
		ctx.put("customerName", customer.getName());
		ctx.put("accountReports", accountReports);
		ctx.put("totalInAllAccounts", ApplicationConfig.CURRENCY_FORMAT.format(totalInAllAccounts));
		
		return generateReport(CUSTOMER_STATEMENT_TEMPLATE_PATH, ctx);
	}

    private static String formatPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

	public static String generateCustomerSummary(Collection<Customer> customers) throws ReportException {
		
		if(customers == null)
			throw new IllegalArgumentException("customer cannot be null");

		List<Map<String, String>> customersViewList = new ArrayList<>();
		
		for(Customer customer : customers) {
			
			Map<String, String> customerView = new HashMap<>();
			
			customerView.put("name", customer.getName());
			customerView.put("accountsNumber", formatPlural(customer.getNumberOfAccounts(), "account"));
			
			customersViewList.add(customerView);
		}
		
		VelocityContext ctx = new VelocityContext();
		
		ctx.put("customers", customersViewList);
		
		return generateReport(CUSTOMER_SUMMARY_TEMPLATE_PATH, ctx);
	}
}
