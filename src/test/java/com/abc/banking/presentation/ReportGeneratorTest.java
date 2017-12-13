package com.abc.banking.presentation;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.velocity.VelocityContext;
import org.junit.Assert;
import org.junit.Test;

import com.abc.banking.Account;
import com.abc.banking.Bank;
import com.abc.banking.CheckingAccount;
import com.abc.banking.Customer;
import com.abc.banking.MaxiSavingsAccount;
import com.abc.banking.SavingsAccount;
import com.abc.banking.exceptions.ReportException;
import com.abc.utils.TestingUtils;

public class ReportGeneratorTest {
	
	@Test
	public void reportGeneratorShallGenerateAccountReport()  throws Exception {
		Bank bank = new Bank();
		
		Account account = bank.newCustomer("John").openAccount(SavingsAccount.class);
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("200000.00")),
				"transactionDate",
				LocalDate.of(2017, 11, 1));
	
		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("1.00")),
				"transactionDate",
				LocalDate.of(2017, 11, 2));
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("14.00")),
				"transactionDate",
				LocalDate.of(2017, 11, 12));
	
		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("198000.00")),
				"transactionDate",
				LocalDate.of(2017, 11, 13));
		
		
		String actualOutput = ReportGenerator.generateAccountStatement(account);
		
		String expectedOutput = 
				TestingUtils.getResourceAsString("account_report_expected_output.txt", "UTF8", this);

		Assert.assertEquals(expectedOutput, actualOutput);
	
	}

	@Test
	public void reportGeneratorShallGenerateCustomerReport() throws Exception {
		Bank bank = new Bank();
		Customer henry = bank.newCustomer("Henry");
        Account checkingAccount = henry.openAccount(CheckingAccount.class);
        Account savingsAccount = henry.openAccount(SavingsAccount.class);

        TestingUtils.changePrivateField(
        		checkingAccount.deposit(new BigDecimal("100.0")),
				"transactionDate",
				LocalDate.of(2017, 12, 12));
        
        TestingUtils.changePrivateField(
        		savingsAccount.deposit(new BigDecimal("4000.0")),
				"transactionDate",
				LocalDate.of(2017, 12, 12));

        TestingUtils.changePrivateField(
        		savingsAccount.withdraw(new BigDecimal("200.0")),
				"transactionDate",
				LocalDate.of(2017, 12, 12));

        String actualOutput = ReportGenerator.generateCustomerStatement(henry);
		
		String expectedOutput = 
				TestingUtils.getResourceAsString("customer_report_expected_output.txt", "UTF8", this);

        Assert.assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
    public void reportGeneratorShallGenerateCustomerSummary() throws Exception {
        Bank bank = new Bank();
        Customer john = bank.newCustomer("John");
        john.openAccount(CheckingAccount.class);
        
        Customer mark = bank.newCustomer("Mark");
        mark.openAccount(CheckingAccount.class);
        mark.openAccount(SavingsAccount.class);
        mark.openAccount(MaxiSavingsAccount.class);

        String actualOutput = ReportGenerator.generateCustomerSummary(bank.getCustomers());
		
		String expectedOutput = 
				TestingUtils.getResourceAsString("customer_summary_expected_output.txt", "UTF8", this);

        Assert.assertEquals(expectedOutput, actualOutput);
    }
	
	@Test(expected = ReportException.class)
	public void generateReportShallFailIfTemplatePathIsUnreachable() throws Exception {
		ReportGenerator.generateReport("non/existing/location/file.vtl", new VelocityContext());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void generateReportShallFailIfTemplateClassPathIsNull() throws Exception {
		ReportGenerator.generateReport(null, new VelocityContext());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void generateReportShallFailIfVelocityContextIsNull() throws Exception {
		ReportGenerator.generateReport("com/abc/banking/presentation/AccountStatement.vtl", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void generateCustomerStatementShallFailIfArgumentIsNull() throws Exception {
		ReportGenerator.generateCustomerStatement(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void generateAccountStatementShallFailIfArgumentIsNull() throws Exception {
		ReportGenerator.generateAccountStatement(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void generateCustomerSummary() throws Exception {
		ReportGenerator.generateCustomerSummary(null);
	}
	
}

