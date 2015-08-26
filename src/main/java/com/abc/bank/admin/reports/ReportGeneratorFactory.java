package com.abc.bank.admin.reports;

import com.abc.bank.admin.Customer;

public enum ReportGeneratorFactory {
	INSTANCE;
	
	public enum ReportType {CUSTOMER_ACC_SIZE,CUSTOMER_STATEMENT,BANK_INTEREST_PAID};
	
	/**
	 * Gets the report generartor.
	 *
	 * @param reportType the report type
	 * @param c the c
	 * @return the report generartor
	 */
	public ReportGenerator<?,String> getReportGenerartor(ReportType reportType,Customer c){
		switch (reportType){
		case BANK_INTEREST_PAID:
			return new InterestPaidGenerator();
		case CUSTOMER_ACC_SIZE:
			return new CustomerAccountSizeGenerator();
		case CUSTOMER_STATEMENT:
			return new CustomerStatementGenerator(c);
		default: throw new IllegalArgumentException("Not Implemented");
		
		}
		
		
	}
}
