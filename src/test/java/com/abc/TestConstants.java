package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public final class TestConstants {
	
	private TestConstants() {
	}
	
	public static final String CUSTOMER1 = "cust";
	
	public static final String CUSTOMER2 = "cust2";
	
	public static final String CHECKING_ACCOUNT = "CHECKING ACCOUNT";
	
	public static final String CHECKING_ACCOUNT_VARYING_CASES = "cHeckING AccOuNT";
	
	public static final String SAVINGS_ACCOUNT = "SAVINGS ACCOUNT";
	
	public static final String MAXISAVINGS_ACCOUNT = "MAXI-SAVINGS ACCOUNT";
	
	public static final String MISSPELLED_ACCOUNT = "chuqing accont"; 
	
	public static final String ZERO_INTEREST = "Total interest paid by the bank : $0.00";
	
	public static final int FIRST_ACCOUNT_ID = 1;

	public static final int SECOND_ACCOUNT_ID = 2;
	
	public static final int THIRD_ACCOUNT_ID = 3;
	
	public static final BigDecimal DEPOSIT_AMOUNT = new BigDecimal("20");
	
	public static final BigDecimal DEPOSIT_AMOUNT2 = new BigDecimal("40");
	
	public static final BigDecimal WITHDRAWAL_AMOUNT = new BigDecimal("20");
	
	public static final BigDecimal WITHDRAWAL_AMOUNT2 = new BigDecimal("40"); 
	
	public static final BigDecimal ILLEGAL_WITHDRAWAL_AMOUNT = new BigDecimal("30");
	
	public static final BigDecimal CHECKING_ACCOUNT_INTEREST_EARNED = new BigDecimal("0.02");
	
	public static final BigDecimal SAVINGS_ACCOUNT_INTEREST_EARNED_FIRST_TIER = new BigDecimal("0.02");
	
	public static final BigDecimal SAVINGS_ACCOUNT_INTEREST_EARNED_SECOND_TIER = new BigDecimal("1.00");
	
	public static final BigDecimal MAXISAVINGS_INTEREST_EARNED_FIRST_TIER = new BigDecimal("0.40");
	
	public static final BigDecimal MAXISAVINGS_INTEREST_EARNED_SECOND_TIER = new BigDecimal("20.05");
	
	public static final BigDecimal MAXISAVINGS_INTEREST_EARNED_THIRD_TIER = new BigDecimal("70.10");
	
	public static final String TOTAL_INTEREST_PAID = "Total interest paid by the bank : $0.02";
	
	public static final String TOTAL_INTEREST_PAID_TWO_ACCOUNTS = "Total interest paid by the bank : $0.42";
	
	public static final String NO_CUSTOMERS_WITH_BANK = "There are currently no registered customers with this bank.";
	
	public static final String ONE_CUSTOMER_NO_ACCOUNTS = "Name : cust / Number of accounts : 0\n";
	
	public static final String TWO_CUSTOMERS_ONE_ACCOUNT = "Name : cust / Number of accounts : 1\nName : cust2 / Number of accounts : 0\n";
	
	public static final int ZERO = 0;
	
	public static final int ONE = 1;
	
	public static final int TWO = 2;
	
	public static final int THREE = 3;
	
	static final String SUCCESSFUL_DEPOSIT = "Deposit completed successfully. Account balance has been updated.";
	
	public static final String SUCCESSFUL_WITHDRAWAL = "Withdrawal completed successfully. Account balance has been updated.";
	
	public static final String INSUFFICIENT_FUNDS = "Sorry but you have insufficient funds to withdraw this amount";
	
	public static final BigDecimal ZERO_BD = new BigDecimal("0.00");
	
	public static final BigDecimal CHECKING_ACCOUNT_TOTAL_INTEREST_EARNED = new BigDecimal("0.02");
	
	public static final BigDecimal CHECKING_ACCOUNT_AND_MAXISAVINGS_TOTAL_INTEREST_EARNED = new BigDecimal("0.42");
	
	public static final BigDecimal BALANCE_AFTER_DEPOSIT = new BigDecimal("20.00");
	
	public static final BigDecimal SAVINGS_BALANCE_AFTER_DEPOSIT = new BigDecimal("20.00");
	
	public static final BigDecimal MAXISAVINGS_BALANCE_AFTER_DEPOSIT = new BigDecimal("20.00");
	
	public static final BigDecimal STARTING_BALANCE = new BigDecimal("0.00");
	
	public static final BigDecimal BALANCE_AFTER_TRANSFER_TO = new BigDecimal("40.00");
	
	public static final BigDecimal BALANCE_AFTER_TRANSFER_FROM = new BigDecimal("0.00");
	
	public static final String SUCCESSFUL_TRANSFER = "The money has successfully been transferred from account 1 to account 2";
	
	public static final String INSUFFICIENT_TRANSFER_FUNDS = "Sorry, there are insufficient funds for this money transfer";
	
	public static final BigDecimal TRANSFER_AMOUNT = new BigDecimal("20.00");
	
	public static final Boolean TRUE = true;

	public static final String EMPTY_STATEMENT = "You have no accounts.";
	
	public static final String STATEMENT_NO_TRANSACTIONS = "";
	
	public static final String DEFAULT_CREATE_ACCOUNT_MESSAGE = "Please input a valid account type: Checking Account, Savings Account or Maxi-Savings Account.";
	
	public static final String CHECKING_ACCOUNT_SUCCESSFULLY_CREATED = "Checking account has been successfully created.";
	
	public static final String SAVINGS_ACCOUNT_SUCCESSFULLY_CREATED = "Savings account has been successfully created.";
	
	public static final String MAXISAVINGS_ACCOUNT_SUCCESSFULLY_CREATED = "Maxi-Savings account has been successfully created.";
	
	public static final String ONE_ACCOUNT_NO_TRANSACTIONS = "\n\nSTATEMENT\nAccount : 1 / Account balance: $0\nDeposits:\nNo deposits have been made to this account.\n\nWithdrawals:\nNo Withdrawals have been made from this account.\n\nEND OF STATEMENT";
	
	public static final String TWO_ACCOUNT_NO_TRANSACTIONS = "\n\nSTATEMENT\nAccount : 1 / Account balance: $0\nDeposits:\nNo deposits have been made to this account.\n\nWithdrawals:\nNo Withdrawals have been made from this account.\n\nAccount : 2 / Account balance: $0\nDeposits:\nNo deposits have been made to this account.\n\nWithdrawals:\nNo Withdrawals have been made from this account.\n\nEND OF STATEMENT";
	
	public static final String TWO_ACCOUNT_ONE_DEPOSIT_FIRST_HALF = "\n\nSTATEMENT\nAccount : 1 / Account balance: $20.00\n\nDeposits :\nDEPOSIT:   $20 ( ";
	
	public static final String TWO_ACCOUNT_ONE_DEPOSIT_AND_WITHDRAWAL_FIRST_THIRD = "\n\nSTATEMENT\nAccount : 1 / Account balance: $0.00\n\nDeposits :\nDEPOSIT:   $20 ( ";
	
	public static final String TWO_ACCOUNT_ONE_DEPOSIT_AND_WITHDRAWAL_SECOND_THIRD = " )\n\n\nWithdrawals :\nWITHDRAWAL:   $20 ( ";
	
	public static final String TWO_ACCOUNT_ONE_DEPOSIT_AND_WITHDRAWAL_LAST_THIRD = " )\n\nAccount : 2 / Account balance: $0\nDeposits:\nNo deposits have been made to this account.\n\nWithdrawals:\nNo Withdrawals have been made from this account.\n\nEND OF STATEMENT";
	
	public static final String TWO_ACCOUNT_ONE_DEPOSIT_SECOND_HALF = " )\n\nWithdrawals:\nNo Withdrawals have been made from this account.\n\nAccount : 2 / Account balance: $0\nDeposits:\nNo deposits have been made to this account.\n\nWithdrawals:\nNo Withdrawals have been made from this account.\n\nEND OF STATEMENT";
			
	public static final BigDecimal NEGATIVE_AMOUNT = new BigDecimal("-20");	
	
	public static final String ACCOUNT_WITHDRAW_ILLEGAL_ARGUMENT_EXCEPTION = "Withdrawal amount must be greater than 0";
	
	public static final String ACCOUNT_DEPOSIT_ILLEGAL_ARGUMENT_EXCEPTION = "Deposit amount must be greater than 0";
	
	public static final BigDecimal SECOND_TIER_DEPOSIT = new BigDecimal("981.00");
	
	public static final BigDecimal THIRD_TIER_DEPOSIT = new BigDecimal("1000.00");
	
	public static final String DEPOSIT_STATEMENT_FIRST_HALF = "\n\nDeposits :\nDEPOSIT:   $20 ( ";
	
	public static final String DEPOSIT_STATEMENT_SECOND_HALF = " )\n";
	
	public static final String WITHDRAWAL_STATEMENT_FIRST_HALF = "\n\nWithdrawals :\nWITHDRAWAL:   $20 ( ";
	
	public static final String WITHDRAWAL_STATEMENT_SECOND_HALF = " )\n";
	
	public static final String DEPOSIT_TOSTRING_FIRST_HALF = "DEPOSIT:   $20 ( ";
	
	public static final String DEPOSIT_TOSTRING_SECOND_HALF = " )\n";
	
	public static final String WITHDRAWAL_TOSTRING_FIRST_HALF = "WITHDRAWAL:   $20 ( ";
	
	public static final String WITHDRAWAL_TOSTRING_SECOND_HALF = " )\n";
	
} 