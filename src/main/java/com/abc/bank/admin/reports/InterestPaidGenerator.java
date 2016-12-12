package com.abc.bank.admin.reports;

import com.abc.bank.account.Account;
import com.abc.bank.admin.reports.interestaccumulator.InterestCalculator;

public class InterestPaidGenerator extends ReportGenerator<Account, String> {

	private static final String REPORT_TITLE = "Interest Paid";
	private double totalInterest = 0D;
	
	protected InterestPaidGenerator() {
		super(REPORT_TITLE);
	}

	public boolean visit(Account t) {
		InterestCalculator calculator = InterestCalculator.getCalculator(t.getAccType());
		double interestPaid = calculator.calcInterestEarned(t.getTransactions());
		totalInterest += interestPaid;
		sb.append("AccNo:"+t.getAccNo()).append(":").append(String.format("$%,.2f", interestPaid));
		sb.append(System.lineSeparator());
		
		return false;
	}

	@Override
	public String getReport() {
		sb.append("Total: ").append(String.format("$%,.2f", totalInterest));
		return sb.toString();
	}

}
