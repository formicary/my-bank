package com.abc.bank.admin.reports;

import com.abc.bank.admin.Visitor;

public abstract class ReportGenerator<T,R> implements Visitor<T> {

	private static final String COLON = ":";
	protected StringBuilder sb = new StringBuilder();
	protected ReportGenerator(String reportTitle){
		sb.append(reportTitle).append(COLON);
		sb.append(System.lineSeparator());
	}
	
		/**
		 * Gets the report.
		 *
		 * @return the report
		 */
		public abstract R getReport();
	
}
