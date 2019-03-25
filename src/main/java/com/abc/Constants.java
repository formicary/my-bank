package com.abc;

import java.math.BigDecimal;

public final class Constants {

	private Constants() {
	}
	
	public static final String ACCOUNT_DEPOSIT_ILLEGAL_ARGUMENT_EXCEPTION = "Deposit amount must be greater than 0";
	
	public static final String ACCOUNT_WITHDRAW_ILLEGAL_ARGUMENT_EXCEPTION = "Withdrawal amount must be greater than 0";	
	
	public static final String INSUFFICIENT_FUNDS = "Sorry but you have insufficient funds to withdraw this amount";
	
	public static final BigDecimal TRANSACTION_AMOUNT_LOWER_LIMIT = new BigDecimal("0");
	
	public static final String DEFAULT_CREATE_ACCOUNT_MESSAGE = "Please input a valid account type: Checking Account, Savings Account or Maxi-Savings Account.";
	
	public static final String SUCCESSFUL_DEPOSIT = "Deposit completed successfully. Account balance has been updated.";
	
	public static final String SUCCESSFUL_WITHDRAWAL = "Withdrawal completed successfully. Account balance has been updated.";
	
	public static final BigDecimal INTEREST_EARNED = new BigDecimal("0");
	
	public static final String ACCOUNT_BALANCE = " / Account balance: $";
	
	public static final String WITHDRAWALS_TITLE = "\n\nWithdrawals :\n";
	
	public static final String WITHDRAWAL = "WITHDRAWAL:   $";
	
	public static final String DEPOSITS_TITLE = "\n\nDeposits :\n";
	
	public static final String DEPOSIT = "DEPOSIT:   $";
	
	public static final String CURRENCY = "$ ";
	
	public static final String DATE_FORMAT_START = " ( ";
	
	public static final String DATE_FORMAT_END = " )\n";
	
	public static final String NEW_LINE = "\n";
	
	public static final String NAME = "Name : ";
	
	public static final String NUMBER_OF_ACCOUNTS = " / Number of accounts : ";
	
	public static final String NO_CUSTOMERS_WITH_BANK = "There are currently no registered customers with this bank.";
	
	public static final String TOTAL_INTEREST_PAID = "Total interest paid by the bank : $";
	
	public static final int MINIMUM_ACCOUNT_NUMBER = 000000;
	
	public static final int MAXIMUM_ACCOUNT_NUMBER = 999999;
	
	public static final int ONE = 1;
	
	public static final int ZERO_INT = 0;
	
	public static final BigDecimal ZERO_BD = new BigDecimal("0");
	
	public static final int NEGATIVE_ONE = -1;
	
	public static final int ACCOUNT_ID_DENOMINATIONS = 1;
	
	public static final String SUCCESSFUL_MONEY_TRANSFER = "The money has successfully been transferred from account ";
	
	public static final String INSUFFICIENT_TRANSFER_FUNDS = "Sorry, there are insufficient funds for this money transfer";
	
	public static final String TO_ACCOUNT = " to account ";
	
	public static final String ACCOUNT_ID = "\nAccount : ";
	
	public static final String EMPTY_STRING = "";
	
	public static final String EMPTY_STATEMENT = "You have no accounts.";
	
	public static final String END_OF_STATEMENT = "\nEND OF STATEMENT";
	
	public static final String 	STATEMENT_TITLE = "\n\nSTATEMENT";
	
	public static final Boolean TRUE = true;
	
	public static final String NO_WITHDRAWALS = "\nWithdrawals:\nNo Withdrawals have been made from this account.\n";
	
	public static final String NO_DEPOSITS = "\nDeposits:\nNo deposits have been made to this account.\n";
	
	public static final BigDecimal ONE_THOUSAND = new BigDecimal("1000.00");
	
	
	public static final String CHECKING_ACCOUNT = "CHECKING ACCOUNT";
	
	public static final BigDecimal CHECKING_ACCOUNT_INTEREST_RATE = new BigDecimal("0.001");
	
	public static final String CHECKING_ACCOUNT_SUCCESSFULLY_CREATED = "Checking account has been successfully created.";
	
	
	
	
	public static final String SAVINGS_ACCOUNT = "SAVINGS ACCOUNT";
	
	public static final BigDecimal SAVINGS_ACCOUNT_FIRST_TIER_INTEREST_RATE = new BigDecimal("0.001");
	
	public static final BigDecimal SAVINGS_ACCOUNT_SECOND_TIER_INTEREST_RATE = new BigDecimal("0.002");
	
	public static final BigDecimal SAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD = new BigDecimal("1000");
	
	public static final String SAVINGS_ACCOUNT_SUCCESSFULLY_CREATED = "Savings account has been successfully created.";
	
	
	
	
	public static final String MAXISAVINGS_ACCOUNT = "MAXI-SAVINGS ACCOUNT";
	
	public static final BigDecimal MAXISAVINGS_ACCOUNT_FIRST_TIER_INTEREST_RATE = new BigDecimal("0.02");
	
	public static final BigDecimal MAXISAVINGS_ACCOUNT_SECOND_TIER_INTEREST_RATE = new BigDecimal("0.05");
	
	public static final BigDecimal MAXISAVINGS_ACCOUNT_THIRD_TIER_INTEREST_RATE = new BigDecimal("0.1");
	
	public static final BigDecimal MAXISAVINGS_ACCOUNT_THIRD_TIER_LOWER_THRESHOLD = new BigDecimal("2000");
	
	public static final BigDecimal MAXISAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD = new BigDecimal("1000");
	
	public static final BigDecimal MAXISAVINGS_ACCOUNT_FIRST_TIER_LOWER_THRESHOLD = new BigDecimal("1");
	
	public static final String MAXISAVINGS_ACCOUNT_SUCCESSFULLY_CREATED = "Maxi-Savings account has been successfully created.";
}